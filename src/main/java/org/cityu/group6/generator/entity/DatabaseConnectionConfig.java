package org.cityu.group6.generator.entity;

import lombok.Data;

@Data
public class DatabaseConnectionConfig {
	private String saveName;

	private String hostIp;

	private String port;

	private String username;

	private String password;

	private String schema;

	private String encoding;

	private String dbUrl;
}
