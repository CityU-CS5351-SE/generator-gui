package org.cityu.group6.generator.entity;

import java.util.List;

import lombok.Data;

@Data
public class ChooseTableConfig {
	private List<String> tableNameList;

	private List<String> classNameList;
}
