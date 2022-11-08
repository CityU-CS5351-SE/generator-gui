package org.cityu.group6.generator.util;

import org.cityu.group6.generator.entity.DatabaseConnectionConfig;

/**
 * DbConfigUtil
 * @author Kevin LIN
 *
 */
public class DbConfigUtil {

	private static volatile DatabaseConnectionConfig dbConfig;

	public static void setDbConfig(DatabaseConnectionConfig dbConfig) {
		DbConfigUtil.dbConfig = dbConfig;
	}

	public static DatabaseConnectionConfig getDbConfig() {
		return DbConfigUtil.dbConfig;
	}
}
