package org.cityu.group6.generator.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.cityu.group6.generator.entity.DatabaseConnectionConfig;
import org.cityu.group6.generator.entity.DbTableInfo;
import org.cityu.group6.generator.entity.PageEnum;

import com.greedystar.generator.db.DataBaseFactory;

/**
 * load table names from database
 * 
 * @author Kevin
 *
 */
public class ChooseTableUtil {

	private Connection connection;

	/**
	 * initial database connection
	 * 
	 * @return
	 */
	private boolean initConnection() {
		try {
			// get database configuration from first page
			DatabaseConnectionConfig dbConfig = (DatabaseConnectionConfig) ParameterManager
					.getParam(PageEnum.FIRST_PAGE.getPageName());
			Class.forName(DataBaseFactory.getDriver(dbConfig.getDbUrl()));
			String username = dbConfig.getUsername();
			String password = dbConfig.getPassword();
			Properties properties = new Properties();
			properties.put("user", username);
			properties.put("password", password == null ? "" : password);
			properties.setProperty("remarks", "true");
			properties.setProperty("useInformationSchema", "true");
			properties.setProperty("nullCatalogMeansCurrent", "true");
			connection = DriverManager.getConnection(dbConfig.getDbUrl(), properties);
			return true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * loadTables
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<DbTableInfo> loadTables() throws Exception {
		DatabaseConnectionConfig dbConfig = (DatabaseConnectionConfig) ParameterManager
				.getParam(PageEnum.FIRST_PAGE.getPageName());
		if (!initConnection()) {
			throw new Exception("Failed to connect to database at url:" + dbConfig.getDbUrl());
		}
		List<DbTableInfo> tables = new ArrayList<>();
		String sql = "SELECT TABLE_NAME,TABLE_ROWS,CREATE_TIME from information_schema.TABLES WHERE TABLE_schema = 'vueblog2' AND table_type = 'BASE TABLE'";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			DbTableInfo table = new DbTableInfo();
			table.setTableName(resultSet.getString("TABLE_NAME"));
			table.setTableRows(String.valueOf(resultSet.getInt("TABLE_ROWS")));
			table.setCreateTime(resultSet.getDate("CREATE_TIME"));
			tables.add(table);
		}
		// TODO 
		connection.close();
		return tables;
	}
	
}
