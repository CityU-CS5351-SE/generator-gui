package org.cityu.group6.generator.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

import org.cityu.group6.generator.entity.DatabaseConnectionConfig;
import org.cityu.group6.generator.entity.PageEnum;
import org.cityu.group6.generator.util.ParameterManager;
import org.cityu.group6.generator.util.StageManager;

import com.greedystar.generator.db.DataBaseFactory;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * First page: database connection configuration
 * 
 * @author Kevin
 *
 */
@SuppressWarnings("restriction")
public class FirstPageController implements Initializable {
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
	private Button nextPage;
	@FXML
	private Button importFile;
	@FXML
	private Button exportFile;

	private static final String pageName = PageEnum.FIRST_PAGE.getPageName();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.renderPage();
	}

	/**
	 * when turn to this page, renderPage() first
	 */
	private void renderPage() {
		DatabaseConnectionConfig dbconfig = (DatabaseConnectionConfig) ParameterManager.getParam(pageName);
		if (dbconfig != null) {
			this.hostIp.setText(dbconfig.getHostIp());
			this.port.setText(dbconfig.getPort());
			this.username.setText(dbconfig.getUsername());
			this.schema.setText(dbconfig.getSchema());
		}
	}

	@FXML
	private void importFile() throws IOException {
		// open a fileChooser, choose file and parse
		FileChooser fileChooser = new FileChooser();
		// limit .txt files
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
		fileChooser.getExtensionFilters().add(extFilter);
		File file = fileChooser.showOpenDialog(importFile.getScene().getWindow());
		if (file != null) {
			List<String> list = Files.readAllLines(file.toPath());
			// Get db string from the first line
			if (list != null && list.size() > 0) {
				hostIp.setText(list.get(0).replace("Host Ip/Name:", ""));
				port.setText(list.get(1).replace("Port:", ""));
				username.setText(list.get(2).replace("Username:", ""));
				password.setText(list.get(3).replace("Password:", ""));
				schema.setText(list.get(4).replace("Schema/Database:", ""));
			}
		}
	}

	@FXML
	private void exportFile() throws IOException {
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
		fileChooser.getExtensionFilters().add(extFilter);
		File file = fileChooser.showSaveDialog(exportFile.getScene().getWindow());
		if (file == null)
			return;
		if (file.exists()) {// 文件已存在，则删除覆盖文件
			file.delete();
		}
		// TODO overwrite problem
		BufferedWriter writer = new BufferedWriter(new FileWriter(file.getAbsolutePath()));
		writer.write("Host Ip/Name:" + hostIp.getText() + "\r\n");
		writer.write("Port:" + port.getText() + "\r\n");
		writer.write("Username:" + username.getText() + "\r\n");
		writer.write("Password:" + password.getText() + "\r\n");
		writer.write("Schema/Database:" + schema.getText() + "\r\n");
		writer.close();
	}

	/**
	 * test database connection
	 * 
	 * @throws IOException
	 */
	@FXML
	private void testConnection() throws IOException {
		try {
			String dbUrl = "jdbc:mysql://" + this.hostIp.getText() + ":" + this.port.getText() + "/"
					+ this.schema.getText() + "?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai";
			Class.forName(DataBaseFactory.getDriver(dbUrl));
			String username = this.username.getText();
			String password = this.password.getText();
			Properties properties = new Properties();
			properties.put("user", username);
			properties.put("password", password == null ? "" : password);
			properties.setProperty("remarks", "true");
			properties.setProperty("useInformationSchema", "true");
			properties.setProperty("nullCatalogMeansCurrent", "true");
			Connection connection = DriverManager.getConnection(dbUrl, properties);
			// Test database connection successful!
			connection.close();
			this.openSuccessWindow();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void openSuccessWindow() throws IOException {
		URL url = Thread.currentThread().getContextClassLoader().getResource("fxml/SuccessNotice.fxml");
		FXMLLoader fxmlLoader = new FXMLLoader(url);
		Parent root = fxmlLoader.load();
		Stage successStage = new Stage();
		successStage.setScene(new Scene(root));
		successStage.setTitle("Database Connection Testing Successful!");
		successStage.initModality(Modality.APPLICATION_MODAL);
		successStage.getIcons().add(new Image("image/cj-logo.png"));
		successStage.show();
	}

	private void saveParameters() {
		DatabaseConnectionConfig dbconfig = new DatabaseConnectionConfig();
//		dbconfig.setSaveName(saveName.getText());
		dbconfig.setHostIp(hostIp.getText());
		dbconfig.setPort(port.getText());
		dbconfig.setUsername(username.getText());
		dbconfig.setPassword(password.getText());
		dbconfig.setSchema(schema.getText());
		String dbUrl = "jdbc:mysql://" + dbconfig.getHostIp() + ":" + dbconfig.getPort() + "/" + dbconfig.getSchema()
				+ "?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai";
		dbconfig.setDbUrl(dbUrl);
		ParameterManager.putParam(pageName, dbconfig);
	}

	/**
	 * nextPage: 1. save database configuration. 2. close current page(window), open
	 * next page(window) 3. call the function to render the page
	 * 
	 * @throws Exception
	 */
	@FXML
	private void nextPage() throws Exception {
		// 1.update database configuration
		this.saveParameters();
		// 2.close current window, open next window
		Stage nextStage = StageManager.getStage(2);
		nextStage.show();
		Stage dbStage = (Stage) nextPage.getScene().getWindow();
		dbStage.close();
	}
}
