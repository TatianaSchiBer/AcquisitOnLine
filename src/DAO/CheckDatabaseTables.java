package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class CheckDatabaseTables {
	public static void checkData(String schema) {
		try {

//    	    Class.forName("org.hsqldb.jdbcDriver");
//		    Connection conn = DriverManager.getConnection("jdbc:hsqldb:mem:mydatabase", "sa", "");
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/acquistionline",
					"root", "");
			Statement st = conn.createStatement();

			ResultSet mrs = conn.getMetaData().getTables(schema, null, null, new String[] { "TABLE" });
			while (mrs.next()) {
				String tableName = mrs.getString(3);
				System.out.println("Table Name: " + tableName);

				ResultSet rs = st.executeQuery("select * from " + tableName);
				ResultSetMetaData metadata = rs.getMetaData();
				while (rs.next()) {
					System.out.println(" Row:");
					for (int i = 0; i < metadata.getColumnCount(); i++) {
						System.out.println("    Column Name: " + metadata.getColumnLabel(i + 1) + ",  ");
						System.out.println("    Column Type: " + metadata.getColumnTypeName(i + 1) + ":  ");
						Object value = rs.getObject(i + 1);
						System.out.println("    Column Value: " + value + "\n");
					}
				}
			}
		} catch (Exception e) { 
			e.printStackTrace();
		}
	}
	
	public static void listTables(String schema) {
		try {

			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/acquistionline",
					"root", "");

			ResultSet mrs = conn.getMetaData().getTables(schema, null, null, new String[] { "TABLE" });
			while (mrs.next()) {
				String tableName = mrs.getString(3);
				System.out.println("Table Name: " + tableName );
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
