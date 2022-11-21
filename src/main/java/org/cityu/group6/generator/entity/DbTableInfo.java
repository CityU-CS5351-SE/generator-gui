package org.cityu.group6.generator.entity;

import java.util.Date;

import javafx.scene.control.CheckBox;
import lombok.Data;

@Data
@SuppressWarnings("restriction")
public class DbTableInfo {
	private String tableName;

	private String className;
	
	private String tableRows;
	
	private Date createTime;
	
	private String createDate;
	
	private CheckBox box = new CheckBox();
}
