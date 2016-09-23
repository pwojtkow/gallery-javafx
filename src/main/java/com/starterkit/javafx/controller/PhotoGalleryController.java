package com.starterkit.javafx.controller;

import static com.starterkit.javafx.ApplicationProperties.BASE_BUNDLE_PATH;
import static com.starterkit.javafx.ApplicationProperties.DIRECTORY_CHOOSER_WINDOW_TITLE;
import static com.starterkit.javafx.ApplicationProperties.SELECTED_IMG_VIEW_FXML_PATH;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.starterkit.javafx.dataprovider.FileDataProvider;
import com.starterkit.javafx.model.FileSearch;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PhotoGalleryController {

	@FXML
	private TilePane tilePane;

	@FXML
	private ScrollPane scrollPane;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	private List<File> images;

	private FileDataProvider fileDataProvider = FileDataProvider.INSTANCE;

	private FileSearch model = new FileSearch();

	@FXML
	private void initialize() {

		loadImagesFromFolder(model.getPath());

		scrollPane.setFitToWidth(true);
	}

	private void loadImagesFromFolder(String path) {

		tilePane.getChildren().clear();
		images = fileDataProvider.getImages(path);

		int index = 0;

		for (final File file : images) {
			ImageView imageView;
			imageView = createImageView(file, index);
			tilePane.getChildren().addAll(imageView);
			index++;
		}
	}

	private ImageView createImageView(File imageFile, int index) {

		ImageView imageView = null;
		int imageIndex = index;

		try {
			final Image image = new Image(new FileInputStream(imageFile), 350, 350, true, true);
			imageView = new ImageView(image);
			imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent mouseEvent) {

					if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {

						if (mouseEvent.getClickCount() == 2) {
							try {
								openNewImageWindow(imageFile, imageIndex);

							} catch (IOException e) {
								e.printStackTrace();
							}

						}
					}
				}

			});
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}
		return imageView;
	}

	private void openNewImageWindow(File imageFile, int imageIndex) throws IOException, FileNotFoundException {

		FXMLLoader loader = new FXMLLoader(getClass().getResource(SELECTED_IMG_VIEW_FXML_PATH),
				ResourceBundle.getBundle(BASE_BUNDLE_PATH));
		Stage newStage = new Stage(StageStyle.DECORATED);
		newStage.setScene(new Scene((BorderPane) loader.load()));

		SelectedImageController controller = loader.<SelectedImageController> getController();
		controller.initData(imageFile, model, imageIndex);
		newStage.initModality(Modality.APPLICATION_MODAL);
		newStage.setTitle(imageFile.getName());
		newStage.setMinHeight(600);
		newStage.setMinWidth(600);
		newStage.show();
	}

	@FXML
	public void openFolderAction() {
		// TODO
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle(DIRECTORY_CHOOSER_WINDOW_TITLE);
		String newPath = directoryChooser.showDialog(scrollPane.getScene().getWindow()).getAbsolutePath();
		model.setPath(newPath);
		loadImagesFromFolder(model.getPath());

	}

}