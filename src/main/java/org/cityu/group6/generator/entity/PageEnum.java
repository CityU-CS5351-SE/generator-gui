package org.cityu.group6.generator.entity;

public enum PageEnum {
	FIRST_PAGE("DatabaseConnection","fxml/DatabaseConnection.fxml"),
	SECOND_PAGE("GenerationParameters","fxml/GenerationParameters.fxml"),
	THIRD_PAGE("ChooseTables","fxml/ChooseTables.fxml"),
	FOURTH_PAGE("ChooseFiles","fxml/ChooseFiles.fxml"),
	FIFTH_PAGE("FinishAndCheck","fxml/FinishAndCheck.fxml");

	private String pageName;
	private String fxmlPath;
	
	private PageEnum(String pageName, String fxmlPath) {
		this.pageName = pageName;
		this.fxmlPath = fxmlPath;
	}
	
	public String getPageName() {
		return pageName;
	}
	
	public String getFxmlPath() {
		return fxmlPath;
	}
}
