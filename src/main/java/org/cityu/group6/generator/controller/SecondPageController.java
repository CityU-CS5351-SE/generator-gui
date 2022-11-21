package org.cityu.group6.generator.controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import org.cityu.group6.generator.entity.GlobalConfig;
import org.cityu.group6.generator.entity.PageEnum;
import org.cityu.group6.generator.util.ParameterManager;
import org.cityu.group6.generator.util.StageManager;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/**
 * SecondPageController: generate parameters configuration, such as project
 * path, lombokEnable etc.
 * 
 * @author Kevin
 *
 */
@SuppressWarnings("restriction")
public class SecondPageController implements Initializable {
	@FXML
	private TextField author;
	@FXML
	private TextField packageName;
	@FXML
	private TextField projectFolderPath;
	@FXML
	private TextField controllerName;
	@FXML
	private TextField controllerPath;
	@FXML
	private TextField serviceName;
	@FXML
	private TextField servicePath;
	@FXML
	private TextField interfaceName;
	@FXML
	private TextField interfacePath;
	@FXML
	private TextField daoName;
	@FXML
	private TextField daoPath;
	@FXML
	private TextField entityName;
	@FXML
	private TextField entityPath;
	@FXML
	private TextField mapperName;
	@FXML
	private TextField mapperPath;
	@FXML
	private CheckBox mybatisPlusEnable;
	@FXML
	private CheckBox jpaEnable;
	@FXML
	private CheckBox swaggerEnable;
	@FXML
	private CheckBox lombokEnable;
	@FXML
	private CheckBox idStrategy;
	@FXML
	private CheckBox fileOverride;
	@FXML
	private CheckBox mapperUnderSource;
	// Button: back, next
	@FXML
	private Button nextPage;
	@FXML
	private Button backPage;
	private static final String pageName = PageEnum.SECOND_PAGE.getPageName();

	/**
	 * initialize
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.renderPage();
	}

	/**
	 * renderPage() first when show the page
	 */
	private void renderPage() {
		GlobalConfig config = (GlobalConfig) ParameterManager.getParam(pageName);
		if (config != null) {
			this.author.setText(config.getAuthor());
			this.packageName.setText(config.getPackageName());
			this.projectFolderPath.setText(config.getProjectFolderPath());
			this.controllerName.setText(config.getControllerName());
			this.controllerPath.setText(config.getControllerPath());
			this.serviceName.setText(config.getServiceName());
			this.servicePath.setText(config.getServicePath());
			this.interfaceName.setText(config.getInterfaceName());
			this.interfacePath.setText(config.getInterfacePath());
			this.daoName.setText(config.getDaoName());
			this.daoPath.setText(config.getDaoPath());
			this.entityName.setText(config.getEntityName());
			this.entityPath.setText(config.getEntityPath());
			this.mapperName.setText(config.getMapperName());
			this.mapperPath.setText(config.getMapperPath());
			this.mybatisPlusEnable.setSelected(config.isMybatisPlusEnable());
			this.jpaEnable.setSelected(config.isJpaEnable());
			this.swaggerEnable.setSelected(config.isSwaggerEnable());
			this.lombokEnable.setSelected(config.isLombokEnable());
			this.idStrategy.setSelected(config.isIdStrategy());
			this.fileOverride.setSelected(config.isFileOverride());
			this.mapperUnderSource.setSelected(config.isMapperUnderSource());
		}
	}

	private void saveParameters() {
		GlobalConfig config = new GlobalConfig();
		config.setAuthor(author.getText());
		config.setPackageName(packageName.getText());
		config.setProjectFolderPath(projectFolderPath.getText());
		config.setControllerName(controllerName.getText());
		config.setControllerPath(controllerPath.getText());
		config.setServiceName(serviceName.getText());
		config.setServicePath(servicePath.getText());
		config.setInterfaceName(interfaceName.getText());
		config.setInterfacePath(interfacePath.getText());
		config.setDaoName(daoName.getText());
		config.setDaoPath(daoPath.getText());
		config.setEntityName(entityName.getText());
		config.setEntityPath(entityPath.getText());
		config.setMapperName(mapperName.getText());
		config.setMapperPath(mapperPath.getText());
		config.setMybatisPlusEnable(mybatisPlusEnable.isSelected());
		config.setJpaEnable(jpaEnable.isSelected());
		config.setSwaggerEnable(swaggerEnable.isSelected());
		config.setLombokEnable(lombokEnable.isSelected());
		config.setIdStrategy(idStrategy.isSelected());
		config.setFileOverride(fileOverride.isSelected());
		config.setMapperUnderSource(mapperUnderSource.isSelected());
		ParameterManager.putParam(pageName, config);
	}

	@FXML
	private void backPage() throws Exception {
		this.saveParameters();
		Stage backStage = StageManager.getStage(1);
		backStage.show();
		Stage stage = (Stage) backPage.getScene().getWindow();
		stage.close();
	}

	@FXML
	private void nextPage() throws Exception {
		this.saveParameters();
		Stage nextStage = StageManager.getStage(3);
		nextStage.show();
		Stage stage = (Stage) backPage.getScene().getWindow();
		stage.close();
	}

	/**
	 * chooseProjectFolder
	 */
	@FXML
	private void chooseProjectFolder() {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		// get the stage which owning the folder:
		Stage primaryStage = (Stage) projectFolderPath.getScene().getWindow();
		File selectedFolder = directoryChooser.showDialog(primaryStage);
		// projectFilePath
		if (selectedFolder != null) {
			String projectFilePath = selectedFolder.getAbsolutePath();
			projectFolderPath.setText(projectFilePath);
			// show packageName(auto-generate, could change)
			packageName.setText(this.pathToPackageName(projectFilePath));
		}
	}

	/**
	 * pathToPackageName
	 * 
	 * @param filePath
	 * @return
	 */
	private String pathToPackageName(String filePath) {
		// get string after "src\main\java\"
		String reg = "src\\main\\java\\";
		String packageName = filePath.substring(filePath.lastIndexOf(reg) + reg.length());
		// replace "\\" to "."
		packageName = packageName.replace("\\", ".");
		return packageName;
	}

}
