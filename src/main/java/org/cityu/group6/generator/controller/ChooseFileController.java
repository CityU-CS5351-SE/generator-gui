package org.cityu.group6.generator.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ResourceBundle;

import org.cityu.group6.generator.entity.DbTableInfo;
import org.cityu.group6.generator.entity.FileGenerationInfo;
import org.cityu.group6.generator.entity.FileTypeEnum;
import org.cityu.group6.generator.entity.GlobalConfig;
import org.cityu.group6.generator.entity.PageEnum;
import org.cityu.group6.generator.util.ParameterManager;
import org.cityu.group6.generator.util.StageManager;

import com.greedystar.generator.entity.Constant;
import com.greedystar.generator.invoker.SingleInvoker;
import com.greedystar.generator.invoker.base.Invoker;
import com.greedystar.generator.utils.ConfigUtil;
import com.greedystar.generator.utils.StringUtil;

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
 * @author Kevin
 *
 */
@SuppressWarnings("restriction")
public class ChooseFileController implements Initializable {
	@FXML
	private TableView<FileGenerationInfo> fileTable;
	@FXML
	private TableColumn<FileGenerationInfo, String> fileNameCol, tableNameCol, fileTypeCol, filePathCol;
	@FXML
	private TableColumn<DbTableInfo, Boolean> isSelectCol;
	@FXML
	private Button nextPage;
	@FXML
	private Button backPage;
	@FXML
	private CheckBox selectAll;
	private static final String pageName = PageEnum.FOURTH_PAGE.getPageName();

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
			fileTable.getItems().forEach(item -> {
				item.getBox().setSelected(true);
			});
		} else {
			// disSelect ALL
			fileTable.getItems().forEach(item -> {
				item.getBox().setSelected(false);
			});
		}
	}

	private void renderPage() {
		// render page
		showFileTable(getFileList());
	}

	public void showFileTable(ObservableList<FileGenerationInfo> files) {
		fileTable.setEditable(true);
		fileNameCol.setCellValueFactory(new PropertyValueFactory<>("fileName"));
		tableNameCol.setCellValueFactory(new PropertyValueFactory<>("tableName"));
		fileTypeCol.setCellValueFactory(new PropertyValueFactory<>("fileType"));
		filePathCol.setCellValueFactory(new PropertyValueFactory<>("filePath"));
		isSelectCol.setCellValueFactory(new PropertyValueFactory<>("box"));
		fileTable.setItems(files);
	}

	/**
	 * Get files for users to choose, depends on tables chosen in step3
	 * 
	 * @return
	 * @throws Exception
	 */
	private ObservableList<FileGenerationInfo> getFileList() {
		List<FileGenerationInfo> files = new ArrayList<>();
		GlobalConfig config = (GlobalConfig) ParameterManager.getParam(PageEnum.SECOND_PAGE.getPageName());
		List<DbTableInfo> tables = (List<DbTableInfo>) ParameterManager.getParam(PageEnum.THIRD_PAGE.getPageName());
		tables.forEach(table -> {
			// get related files
			for (FileTypeEnum file : FileTypeEnum.values()) {
				files.add(this.getFile(file.getName(), table, config));
			}
		});
		ObservableList<FileGenerationInfo> list = FXCollections.observableArrayList(files);
		return list;
	}

	/**
	 * getFile
	 * 
	 * @param type
	 * @param table
	 * @param config
	 * @return
	 */
	private FileGenerationInfo getFile(String type, DbTableInfo table, GlobalConfig config) {
		FileGenerationInfo file = new FileGenerationInfo();
		String fileName = "";
		String filePath = "";
		if (type.equals(FileTypeEnum.Controller.getName())) {
			fileName = config.getControllerName().replace(Constant.PLACEHOLDER, table.getClassName()) + ".java";
			filePath = config.getProjectFolderPath() + "\\" + StringUtil.package2Path(config.getControllerPath());

		} else if (type.equals(FileTypeEnum.Service.getName())) {
			fileName = config.getServiceName().replace(Constant.PLACEHOLDER, table.getClassName()) + ".java";
			filePath = config.getProjectFolderPath() + "\\" + StringUtil.package2Path(config.getServicePath());

		} else if (type.equals(FileTypeEnum.Interface.getName())) {
			fileName = config.getInterfaceName().replace(Constant.PLACEHOLDER, table.getClassName()) + ".java";
			filePath = config.getProjectFolderPath() + "\\" + StringUtil.package2Path(config.getInterfacePath());

		} else if (type.equals(FileTypeEnum.Dao.getName())) {
			fileName = config.getDaoName().replace(Constant.PLACEHOLDER, table.getClassName()) + ".java";
			filePath = config.getProjectFolderPath() + "\\" + StringUtil.package2Path(config.getDaoPath());

		} else if (type.equals(FileTypeEnum.Entity.getName())) {
			fileName = config.getEntityName().replace(Constant.PLACEHOLDER, table.getClassName()) + ".java";
			filePath = config.getProjectFolderPath() + "\\" + StringUtil.package2Path(config.getEntityPath());

		} else if (type.equals(FileTypeEnum.Mapper.getName())) {
			fileName = config.getMapperName().replace(Constant.PLACEHOLDER, table.getClassName()) + ".xml";
			if (config.isMapperUnderSource()) {
				filePath = config.getProjectFolderPath() + "\\" + StringUtil.package2Path(config.getMapperPath());
			} else {
				String name = config.getPackageName().replace(".", "\\");
				filePath = config.getProjectFolderPath().replace(name, "")
						+ StringUtil.package2Path(config.getMapperPath());
			}
		}
		file.setFileName(fileName);
		file.setFilePath(filePath);
		file.setFileType(type);
		file.setTableName(table.getTableName());
		return file;
	}

	private void saveParameters() {
		LinkedHashMap<String, FileGenerationInfo> fileMap = new LinkedHashMap<>();
		// get selected rows
		fileTable.getItems().forEach(file -> {
			if (file.getBox().isSelected()) {
				fileMap.put(file.getFileName(), file);
			}
		});
		ParameterManager.putParam(pageName, fileMap);
	}

	private void generateCode() {
		List<DbTableInfo> tableList = (List<DbTableInfo>) ParameterManager.getParam(PageEnum.THIRD_PAGE.getPageName());
		if (tableList != null && tableList.size() > 0) {
			ConfigUtil.readConfigurationFromUI();
			for (DbTableInfo table : tableList) {
				Invoker invoker = new SingleInvoker.Builder().setTableName(table.getTableName())
						.setClassName(table.getClassName()).build();
				invoker.execute();
			}
		}
	}

	@FXML
	private void nextPage() throws Exception {
		this.saveParameters();
		// generate codes
		this.generateCode();
		Stage nextStage = StageManager.getStage(5);
		nextStage.show();
		Stage stage = (Stage) nextPage.getScene().getWindow();
		stage.close();
	}

	@FXML
	private void backPage() throws Exception {
		Stage backStage = StageManager.getStage(3);
		backStage.show();
		Stage stage = (Stage) backPage.getScene().getWindow();
		stage.close();
	}

}
