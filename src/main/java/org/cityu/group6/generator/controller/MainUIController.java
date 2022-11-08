package org.cityu.group6.generator.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.cityu.group6.generator.entity.DatabaseConnectionConfig;
import org.cityu.group6.generator.entity.GlobalConfig;
import org.cityu.group6.generator.util.AlertUtil;
import org.cityu.group6.generator.util.DbConfigUtil;

import com.greedystar.generator.invoker.SingleInvoker;
import com.greedystar.generator.invoker.base.Invoker;
import com.greedystar.generator.utils.ConfigUtil;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * @author Kevin LIN
 *
 */
@SuppressWarnings("restriction")
public class MainUIController implements Initializable {
	@FXML
	private TextField author;
	@FXML
	private TextField tableName;
	@FXML
	private TextField className;
	@FXML
	private TextField packageName;
	@FXML
	private TextField projectFolderPath;
	// controller
	@FXML
	private CheckBox isController;
	@FXML
	private Label controllerNameLabel;
	@FXML
	private TextField controllerName;
	@FXML
	private Label controllerPathLabel;
	@FXML
	private TextField controllerPath;
	// service
	@FXML
	private CheckBox isService;
	@FXML
	private Label serviceNameLabel;
	@FXML
	private TextField serviceName;
	@FXML
	private Label servicePathLabel;
	@FXML
	private TextField servicePath;
	// interface
	@FXML
	private CheckBox isInterface;
	@FXML
	private Label interfaceNameLabel;
	@FXML
	private TextField interfaceName;
	@FXML
	private Label interfacePathLabel;
	@FXML
	private TextField interfacePath;
	// DAO
	@FXML
	private CheckBox isDao;
	@FXML
	private Label daoNameLabel;
	@FXML
	private TextField daoName;
	@FXML
	private Label daoPathLabel;
	@FXML
	private TextField daoPath;
	// entity
	@FXML
	private CheckBox isEntity;
	@FXML
	private Label entityNameLabel;
	@FXML
	private TextField entityName;
	@FXML
	private Label entityPathLabel;
	@FXML
	private TextField entityPath;
	// mapper
	@FXML
	private CheckBox isMapper;
	@FXML
	private Label mapperNameLabel;
	@FXML
	private TextField mapperName;
	@FXML
	private Label mapperPathLabel;
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

	/**
	 * click "Generate Code" button:generate Code
	 * 
	 * @throws IOException
	 */
	@FXML
	private void generateCode() throws IOException {
		// get gloabConfig & dbConfig from UI
		GlobalConfig globalConfig = this.getGlobalConfigFromUI();
		DatabaseConnectionConfig dbConfig = DbConfigUtil.getDbConfig();
		ConfigUtil.readConfigurationFromUI(globalConfig, dbConfig);
		// invoke, then generate code
		Invoker invoker = new SingleInvoker.Builder().setTableName(globalConfig.getTableName())
				.setClassName(globalConfig.getClassName()).build();
		invoker.execute();
		// TODO success notice (can use try catch to notice if fail)
		URL url = Thread.currentThread().getContextClassLoader().getResource("fxml/SuccessNotice.fxml");
		FXMLLoader fxmlLoader = new FXMLLoader(url);
		Parent root = fxmlLoader.load();
		Stage successStage = new Stage();
		successStage.setScene(new Scene(root));
		successStage.setTitle("Code generation success!");
		successStage.initModality(Modality.APPLICATION_MODAL);
		successStage.show();
	}

	/**
	 * openTargetFolder
	 */
	@FXML
	private void openTargetFolder() {
		String projectFolder = projectFolderPath.getText();
		try {
			Desktop.getDesktop().browse(new File(projectFolder).toURI());
		} catch (Exception e) {
			AlertUtil.showErrorAlert("Open Folder Fail! Please Check Path!" + e.getMessage());
		}
	}

	/**
	 * open database connection page
	 * 
	 * @throws IOException
	 */
	@FXML
	private void openDatabaseConnection() throws IOException {
		URL url = Thread.currentThread().getContextClassLoader().getResource("fxml/NewConnection.fxml");
		FXMLLoader fxmlLoader = new FXMLLoader(url);
		Parent root = fxmlLoader.load();
		Stage dbConenctionStage = new Stage();
		dbConenctionStage.setResizable(true);
		dbConenctionStage.setScene(new Scene(root));
		dbConenctionStage.setTitle("New Database Connection");
		// Set dbConenctionStage mask
		dbConenctionStage.initModality(Modality.APPLICATION_MODAL);
		dbConenctionStage.show();
	}

	/**
	 * getGlobalConfigFromUI
	 * 
	 * @return
	 */
	private GlobalConfig getGlobalConfigFromUI() {
		GlobalConfig globalConfig = new GlobalConfig();
		globalConfig.setAuthor(author.getText());
		globalConfig.setTableName(tableName.getText());
		globalConfig.setClassName(className.getText());
		// set projectFolderPath and packageName
		globalConfig.setProjectFolderPath(projectFolderPath.getText());
		globalConfig.setPackageName(packageName.getText());
		// CheckBox-controller
		globalConfig.setController(isController.isSelected());
		if (isController.isSelected()) {
			globalConfig.setControllerName(controllerName.getText());
			globalConfig.setControllerPath(controllerPath.getText());
		}
		// CheckBox-service
		globalConfig.setService(isService.isSelected());
		if (isService.isSelected()) {
			globalConfig.setServiceName(serviceName.getText());
			globalConfig.setServicePath(servicePath.getText());
		}
		// CheckBox-interface
		globalConfig.setInterf(isInterface.isSelected());
		if (isInterface.isSelected()) {
			globalConfig.setInterfaceName(interfaceName.getText());
			globalConfig.setInterfacePath(interfacePath.getText());
		}
		// CheckBox-dao
		globalConfig.setDao(isDao.isSelected());
		if (isDao.isSelected()) {
			globalConfig.setDaoName(daoName.getText());
			globalConfig.setDaoPath(daoPath.getText());
		}
		// CheckBox-entity
		globalConfig.setEntity(isEntity.isSelected());
		if (isEntity.isSelected()) {
			globalConfig.setEntityName(entityName.getText());
			globalConfig.setEntityPath(entityPath.getText());
		}
		// CheckBox-mapper
		globalConfig.setMapper(isMapper.isSelected());
		if (isMapper.isSelected()) {
			globalConfig.setMapperName(mapperName.getText());
			globalConfig.setMapperPath(mapperPath.getText());
		}
		// other CheckBoxes
		globalConfig.setMybatisPlusEnable(mybatisPlusEnable.isSelected());
		globalConfig.setJpaEnable(jpaEnable.isSelected());
		globalConfig.setSwaggerEnable(swaggerEnable.isSelected());
		globalConfig.setLombokEnable(lombokEnable.isSelected());
		globalConfig.setIdStrategy(idStrategy.isSelected());
		globalConfig.setFileOverride(fileOverride.isSelected());
		globalConfig.setMapperUnderSource(mapperUnderSource.isSelected());
		return globalConfig;
	}

	/**
	 * initialize
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// CheckBox click events
		this.checkboxClickEvent(isController, controllerNameLabel, controllerName, controllerPathLabel, controllerPath);
		this.checkboxClickEvent(isService, serviceNameLabel, serviceName, servicePathLabel, servicePath);
		this.checkboxClickEvent(isInterface, interfaceNameLabel, interfaceName, interfacePathLabel, interfacePath);
		this.checkboxClickEvent(isDao, daoNameLabel, daoName, daoPathLabel, daoPath);
		this.checkboxClickEvent(isEntity, entityNameLabel, entityName, entityPathLabel, entityPath);
		this.checkboxClickEvent(isMapper, mapperNameLabel, mapperName, mapperPathLabel, mapperPath);
		// generateTextByClassName
		this.generateTextByClassName();
	}

	/**
	 * generateTextByClassName
	 */
	private void generateTextByClassName() {
		className.focusedProperty().addListener((obs, oldVal, newVal) -> {
			if (!newVal) {
				// Not focus, generate text by className
				String name = className.getText();
				controllerName.setText(name + "Controller");
				serviceName.setText(name + "ServiceImpl");
				interfaceName.setText(name + "Service");
				daoName.setText(name + "Dao");
				entityName.setText(name);
				mapperName.setText(name + "Mapper");
			}
		});
	}

	/**
	 * checkboxClickEvent
	 * 
	 * @param checkbox
	 * @param label1
	 * @param textfield1
	 * @param label2
	 * @param textfield2
	 */
	private void checkboxClickEvent(CheckBox checkbox, Label label1, TextField textfield1, Label label2,
			TextField textfield2) {
		checkbox.setOnMouseClicked(event -> {
			if (checkbox.isSelected()) {
				label1.setDisable(false);
				label2.setDisable(false);
				textfield1.setDisable(false);
				textfield2.setDisable(false);
			} else {
				label1.setDisable(true);
				label2.setDisable(true);
				textfield1.setDisable(true);
				textfield2.setDisable(true);
			}
		});
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
