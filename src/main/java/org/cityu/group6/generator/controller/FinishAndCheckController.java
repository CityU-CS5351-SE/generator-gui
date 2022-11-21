package org.cityu.group6.generator.controller;

import java.awt.Desktop;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ResourceBundle;

import org.cityu.group6.generator.entity.FileGenerationInfo;
import org.cityu.group6.generator.entity.PageEnum;
import org.cityu.group6.generator.util.AlertUtil;
import org.cityu.group6.generator.util.ParameterManager;
import org.cityu.group6.generator.util.StageManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * final page: code genertion and preview
 * 
 * @author Kevin
 *
 */
@SuppressWarnings("restriction")
public class FinishAndCheckController implements Initializable {
	@FXML
	private TableView<FileGenerationInfo> resultTable;
	@FXML
	private TableColumn<FileGenerationInfo, String> fileNameCol, filePathCol;
	@FXML
	private Button finish;
	@FXML
	private Button backPage;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		renderPage();
	}

	/**
	 * renderPage
	 */
	private void renderPage() {
		showFileTable(getResultList());
	}

	public void showFileTable(ObservableList<FileGenerationInfo> files) {
		resultTable.setEditable(true);
		fileNameCol.setCellValueFactory(new PropertyValueFactory<>("fileName"));
		filePathCol.setCellValueFactory(new PropertyValueFactory<>("filePath"));
		this.addTableEvents();
		resultTable.setItems(files);
	}

	private ObservableList<FileGenerationInfo> getResultList() {
		LinkedHashMap<String, FileGenerationInfo> fileMap = (LinkedHashMap<String, FileGenerationInfo>) ParameterManager
				.getParam(PageEnum.FOURTH_PAGE.getPageName());
		// get files
		List<FileGenerationInfo> files = new ArrayList<>();
		for (FileGenerationInfo file : fileMap.values()) {
			files.add(file);
		}
		ObservableList<FileGenerationInfo> list = FXCollections.observableArrayList(files);
		return list;
	}

	/**
	 * addTableEvents
	 */
	private void addTableEvents() {
		// open the file
		resultTable.setRowFactory(tv -> {
			TableRow<FileGenerationInfo> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				FileGenerationInfo rowData = row.getItem();
//				if (event.getClickCount() == 1) {
//					// open the folder
//					try {
//						Desktop.getDesktop().browse(new File(rowData.getFilePath()).toURI());
//					} catch (Exception e) {
//						AlertUtil.showErrorAlert("Open Folder Fail! Please Check Path!" + e.getMessage());
//					}
//				}
				if (event.getClickCount() == 2) {
					// open the file
					try {
						Desktop.getDesktop().open(new File(rowData.getFilePath()));
					} catch (Exception e) {
						AlertUtil.showErrorAlert("Open Folder Fail! Please Check Path!" + e.getMessage());
					}
				}
			});
			return row;
		});
	}

	@FXML
	private void finish() throws Exception {
		Stage stage = (Stage) finish.getScene().getWindow();
		stage.close();
	}

	@FXML
	private void backPage() throws Exception {
		Stage backStage = StageManager.getStage(4);
		backStage.show();
		Stage stage = (Stage) backPage.getScene().getWindow();
		stage.close();
	}

}
