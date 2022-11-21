package org.cityu.group6.generator.util;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.cityu.group6.generator.entity.PageEnum;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

@SuppressWarnings("restriction")
public class StageManager {

	private static Map<Integer, Stage> stageCache = new HashMap<>();

	/**
	 * get stage from cache, if first time, create the stage and put it into cache
	 * 
	 * @param pageOrder
	 * @return
	 * @throws Exception
	 */
	public static Stage getStage(Integer pageOrder) throws Exception {
		Stage stage = stageCache.get(pageOrder);
		if (stage != null) {
			return stage;
		} else {
			stage = createNewStage(pageOrder);
			stageCache.put(pageOrder, stage);
			return stage;
		}
	}

	private static Stage createNewStage(Integer pageOrder) throws IOException {
		Stage stage = new Stage();
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		URL url;
		if (pageOrder == 1) {
			url = classLoader.getResource(PageEnum.FIRST_PAGE.getFxmlPath());
		} else if (pageOrder == 2) {
			url = classLoader.getResource(PageEnum.SECOND_PAGE.getFxmlPath());
		} else if (pageOrder == 3) {
			url = classLoader.getResource(PageEnum.THIRD_PAGE.getFxmlPath());
		} else if (pageOrder == 4) {
			url = classLoader.getResource(PageEnum.FOURTH_PAGE.getFxmlPath());
		} else if (pageOrder == 5) {
			url = classLoader.getResource(PageEnum.FIFTH_PAGE.getFxmlPath());
		} else {
			return stage;
		}
		FXMLLoader fxmlLoader = new FXMLLoader(url);
		Parent root = fxmlLoader.load();
		stage.setResizable(true);
		stage.setScene(new Scene(root));
		stage.setTitle("Java Code Generator GUI");
		// icon
		stage.getIcons().add(new Image("image/cj-logo.png"));
		return stage;
	}

}
