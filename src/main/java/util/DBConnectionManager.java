package util;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBConnectionManager {

	public static final String DBDRIVER = "com.mysql.jdbc.Driver";
	private static final String URL = "jdbc:mysql://rdb3months.mysql.rds.aliyuncs.com/cphoto";
	private static final String USERNAME = "zxm";
	private static final String PASSWORD = "qwerty";

	public static Connection getConnection() {
		Context initContext = null;
		Connection connection = null;
		try {
			initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource) envContext.lookup("jdbc/cphoto");
			connection = ds.getConnection();
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

	public static Connection getConnByJdbc() {
		Driver driver;
		Connection conn = null;
		try {
			driver = (Driver) Class.forName(DBDRIVER).newInstance();
			DriverManager.registerDriver(driver);
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

}
