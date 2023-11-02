package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	Connection conn = null;
	
	String driverClassName = "com.mysql.cj.jdbc.Driver";
	String connectionUrl = "jdbc:mysql://localhost:3306/acquistionline";
	String dbUser = "root";
	String dbPwd = ""; 

	private static ConnectionFactory connectionFactory = null;

	private ConnectionFactory() throws ClassNotFoundException {
		try {
			Class.forName(driverClassName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public Connection getConnection() throws SQLException {
		conn = DriverManager.getConnection(connectionUrl, dbUser, dbPwd);
		conn.setAutoCommit(false);
		return conn;
	}

	public void commit() throws SQLException {
		conn.commit();
		return;
	}

	public void rollback() throws SQLException {
		conn.rollback();
		return;
	}

	public void close() throws SQLException {
		conn.close();;
		return;
	}

	public static ConnectionFactory getInstance() throws ClassNotFoundException {
		if (connectionFactory == null) {
			connectionFactory = new ConnectionFactory();
		}
		return connectionFactory;
	}
}