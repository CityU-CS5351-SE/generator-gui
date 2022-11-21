package org.cityu.group6.generator.entity;

public enum FileTypeEnum {
	Controller("Controller"),
	Service("Service"),
	Interface("Interface"),
	Dao("Dao"),
	Entity("Entity"),
	Mapper("Mapper");

	private String name;
	
	private FileTypeEnum(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

}
