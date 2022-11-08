package org.cityu.group6.generator;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author Kevin
 *
 */
@SuppressWarnings("restriction")
public class MainUI extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		// Load FXML and controller
		URL url = Thread.currentThread().getContextClassLoader().getResource("fxml/MainUI.fxml");
		FXMLLoader fxmlLoader = new FXMLLoader(url);
		Parent root = fxmlLoader.load();

		primaryStage.setResizable(true);
		primaryStage.setScene(new Scene(root));
		primaryStage.setTitle("Java Code Generator GUI");
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
