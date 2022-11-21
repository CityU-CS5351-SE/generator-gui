package org.cityu.group6.generator.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.cityu.group6.generator.entity.DatabaseConnectionConfig;
import org.cityu.group6.generator.util.ParameterManager;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @author Kevin LIN
 *
 */
@SuppressWarnings("restriction")
public class NewConnectionController implements Initializable {

	@FXML
	private TextField saveName;
	@FXML
	private TextField hostIp;
	@FXML
	private TextField port;
	@FXML
	private TextField username;
	@FXML
	private TextField password;
	@FXML
	private TextField schema;
	@FXML
	private TextField encoding;
	@FXML
	private Button saveDbConfig;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	/**
	 * set database configuration and close this window
	 * 
	 * @param config
	 */
	@FXML
	private void saveDbConfig() {
		DatabaseConnectionConfig dbconfig = new DatabaseConnectionConfig();
		// 7 textfields
		dbconfig.setSaveName(saveName.getText());
		dbconfig.setHostIp(hostIp.getText());
		dbconfig.setPort(port.getText());
		dbconfig.setUsername(username.getText());
		dbconfig.setPassword(password.getText());
		dbconfig.setSchema(schema.getText());
		dbconfig.setEncoding(encoding.getText());
		// set static dbconfig
//		ParameterManager.setDbConfig(dbconfig);
		// close the window
		Stage dbStage = (Stage) saveDbConfig.getScene().getWindow();
		dbStage.close();
	}

	@FXML
	private void testConnection() {

	}

}
