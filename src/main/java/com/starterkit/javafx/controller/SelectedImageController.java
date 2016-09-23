package com.starterkit.javafx.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Timer;
import java.util.TimerTask;

import com.starterkit.javafx.dataprovider.FileDataProvider;
import com.starterkit.javafx.model.FileSearch;
import com.sun.glass.ui.Screen;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;

public class SelectedImageController {

	@FXML
	ImageView imageView;
	@FXML
	ScrollPane scrollane;
	@FXML
	BorderPane borderPane;

	private int imageQuantity;

	private int imageIndex;

	private FileSearch newModel;

	private boolean isTimerRunning = false;

	private FileDataProvider fileDataProvider = FileDataProvider.INSTANCE;

	private Timer timer;
	
	final DoubleProperty zoomProperty = new SimpleDoubleProperty(200);

	@FXML
	ScrollPane scrollPane;

	@FXML
	private void initialize() {
//		imageView.fitWidthProperty().bind(borderPane.widthProperty());
//		imageView.fitHeightProperty().bind(borderPane.heightProperty());
		imageView.preserveRatioProperty().set(true);
		scrollPane.setFitToWidth(true);
		scrollPane.setFitToHeight(true);
		timer = new Timer();
		
		zoomProperty.addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable arg0) {
                imageView.setFitWidth(zoomProperty.get() * 4);
                imageView.setFitHeight(zoomProperty.get() * 3);
            }
        });

        scrollPane.addEventFilter(ScrollEvent.ANY, new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                if (event.getDeltaY() > 0) {
                    zoomProperty.set(zoomProperty.get() * 1.1);
                } else if (event.getDeltaY() < 0) {
                    zoomProperty.set(zoomProperty.get() / 1.1);
                }
            }
        });
		
	}

	public void initData(File imageFile, FileSearch model, int index) throws FileNotFoundException {

		imageIndex = index;
		newModel = model;
		setImageToWindow(imageFile);
		imageQuantity = fileDataProvider.getImages(newModel.getPath()).size();
	}

	private void setImageToWindow(File imageFile) throws FileNotFoundException {
		Image image = new Image(new FileInputStream(imageFile));
		imageView.setImage(image);
		
	}

	@FXML
	public void previousButtonAction() throws FileNotFoundException {

		if (imageIndex <= 0) {
			imageIndex = imageQuantity - 1;
		} else {
			--imageIndex;
		}

		File imageFile = getImageByIndex(imageIndex);
		setImageToWindow(imageFile);

	}

	@FXML
	public void slideshowButtonAction() throws InterruptedException {

		if (!isTimerRunning) {

			timer.scheduleAtFixedRate(new TimerTask() {

				@Override
				public void run() {
					try {
						nextButtonAction();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}

			}, 2000, 2000);

			
		} else {
			timer.cancel();
			timer.purge();
		}
		isTimerRunning = !isTimerRunning;
	}

	@FXML
	public void nextButtonAction() throws FileNotFoundException {

		if (imageIndex < imageQuantity - 1) {
			++imageIndex;
		} else {
			imageIndex = 0;
		}

		File imageFile = getImageByIndex(imageIndex);
		setImageToWindow(imageFile);

	}

	private File getImageByIndex(int index) {
		return fileDataProvider.getImages(newModel.getPath()).get(index);
	}

}
