package com.starterkit.javafx;

import static com.starterkit.javafx.ApplicationProperties.BASE_BUNDLE_PATH;
import static com.starterkit.javafx.ApplicationProperties.GALLERY_VIEW_FXML_PATH;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Application entry point.
 *
 * @author Leszek
 */
public class Startup extends Application {


	public static void main(String[] args) {
		Application.launch(args);
	}

	
	@Override
	public void start(Stage primaryStage) throws Exception {

		String langCode = getParameters().getNamed().get("lang");
		if (langCode != null && !langCode.isEmpty()) {
			Locale.setDefault(Locale.forLanguageTag(langCode));
		}
		
		primaryStage.setTitle("Przemo-View");
		
		
		Parent root = FXMLLoader.load(getClass().getResource(GALLERY_VIEW_FXML_PATH),
				ResourceBundle.getBundle(BASE_BUNDLE_PATH));
		Scene scene = new Scene(root);
		
		primaryStage.setWidth(Screen.getPrimary().getVisualBounds().getWidth());
		primaryStage.setHeight(Screen.getPrimary().getVisualBounds().getHeight());
		primaryStage.setMinWidth(760);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	
}
