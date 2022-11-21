package org.cityu.group6.generator.controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.cityu.group6.generator.entity.DbTableInfo;
import org.cityu.group6.generator.entity.PageEnum;
import org.cityu.group6.generator.util.ChooseTableUtil;
import org.cityu.group6.generator.util.ParameterManager;
import org.cityu.group6.generator.util.StageManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * Page3: load tables from database, and let user choose
 * 
 * @author Kevin
 *
 */
@SuppressWarnings("restriction")
public class ChooseTableController implements Initializable {
	@FXML
	private TableView<DbTableInfo> dbTable;
	@FXML
	private TableColumn<DbTableInfo, String> tableNameCol, classNameCol, tableRowsCol, createDateCol;
	@FXML
	private TableColumn<DbTableInfo, Boolean> isSelectCol;
	@FXML
	private Button nextPage;
	@FXML
	private Button backPage;
	@FXML
	private CheckBox selectAll;
	private static final String pageName = PageEnum.THIRD_PAGE.getPageName();
	private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			this.renderPage();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * checkBox of selectAll
	 */
	@FXML
	private void selectAll() {
		if (selectAll.isSelected()) {
			// select ALL
			dbTable.getItems().forEach(item -> {
				item.getBox().setSelected(true);
			});
		} else {
			// disSelect ALL
			dbTable.getItems().forEach(item -> {
				item.getBox().setSelected(false);
			});
		}
	}

	private void renderPage() throws Exception {
		// every time load to this page, should load tables from database
		showDbTable(getDbTableData());
	}

	public void showDbTable(ObservableList<DbTableInfo> tables) {
		dbTable.setEditable(true);
		tableNameCol.setCellValueFactory(new PropertyValueFactory<>("tableName"));
		classNameCol.setCellValueFactory(new PropertyValueFactory<>("className"));
		tableRowsCol.setCellValueFactory(new PropertyValueFactory<>("tableRows"));
		createDateCol.setCellValueFactory(new PropertyValueFactory<>("createDate"));
		// checkBox column
		isSelectCol.setCellValueFactory(new PropertyValueFactory<>("box"));
		dbTable.setItems(tables);
	}

	/**
	 * getDbTableData
	 * 
	 * @return
	 * @throws Exception
	 */
	private ObservableList<DbTableInfo> getDbTableData() throws Exception {
		ChooseTableUtil chooseTableUtil = new ChooseTableUtil();
		List<DbTableInfo> tables = chooseTableUtil.loadTables();
		tables.forEach(table -> {
			table.setClassName(this.tableName2ClassName(table.getTableName()));
			table.setCreateDate(df.format(table.getCreateTime()));
		});
		ObservableList<DbTableInfo> list = FXCollections.observableArrayList(tables);
		return list;
	}

	private String tableName2ClassName(String tableName) {
		String className = "";
		if (tableName.length() > 0) {
			String[] list = tableName.split("_");
			for (int i = 0; i < list.length; i++) {
				className = className + list[i].substring(0, 1).toUpperCase() + list[i].substring(1);
			}
		}
		return className;
	}

	private void saveParameters() {
		// get selected rows
		List<DbTableInfo> list = new ArrayList<>();
		dbTable.getItems().forEach(tableInfo -> {
			if (tableInfo.getBox().isSelected()) {
				list.add(tableInfo);
			}
		});
		ParameterManager.putParam(pageName, list);
	}

	@FXML
	private void nextPage() throws Exception {
		this.saveParameters();
		Stage nextStage = StageManager.getStage(4);
		nextStage.show();
		Stage stage = (Stage) nextPage.getScene().getWindow();
		stage.close();
	}

	@FXML
	private void backPage() throws Exception {
		Stage backStage = StageManager.getStage(2);
		backStage.show();
		Stage stage = (Stage) backPage.getScene().getWindow();
		stage.close();
	}

}
