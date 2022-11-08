package org.cityu.group6.generator.entity;

import lombok.Data;

/**
 * @author Kevin LIN
 *
 */
@Data
public class GlobalConfig {

	private String author;

	private String tableName;

	private String className;

	private String packageName;

	private boolean controller;

	private String controllerName;

	private String controllerPath;

	private boolean service;

	private String serviceName;

	private String servicePath;

	private boolean interf;

	private String interfaceName;

	private String interfacePath;

	private boolean dao;

	private String daoName;

	private String daoPath;

	private boolean entity;

	private String entityName;

	private String entityPath;

	private boolean mapper;

	private String mapperName;

	private String mapperPath;

	private boolean mybatisPlusEnable;

	private boolean jpaEnable;

	private boolean swaggerEnable;

	private boolean lombokEnable;

	private boolean idStrategy;

	private boolean fileOverride;

	private boolean mapperUnderSource;

}
