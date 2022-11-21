package org.cityu.group6.generator.entity;

import javafx.scene.control.CheckBox;
import lombok.Data;

@Data
@SuppressWarnings("restriction")
public class FileGenerationInfo {
	private String fileName;
	private String tableName;
	private String fileType;
	private String filePath;
	private CheckBox box = new CheckBox();
}
