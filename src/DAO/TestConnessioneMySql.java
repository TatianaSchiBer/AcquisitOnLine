package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestConnessioneMySql {
	final static String dataBaseType = "MYSQL";                    	    // Tipologia database MSACCESS/MYSQL
//	final static String dataBaseName = "DbAmrita";                      // Nome database
	final static String dataBaseName = "acquistionline";                  // Nome database
	final static String dataBaseUser = "root";                      	// User
	final static String dataBasePwd = "";                     // Pwd
	final static String dataBaseDriver = "com.mysql.cj.jdbc.Driver";     // Driver
	final static String dataBaseAccessType = "LOCAL";                 	// Accesso LOCAL/REMOTE
//	final static String dataBaseUrl = "jdbc:mysql://localhost:3306/Amrita?autoReconnect=true&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";			 	
	final static String dataBaseUrl = "jdbc:mysql://localhost:3306/MYSQL";			 	

	public TestConnessioneMySql() {  
	}
	
	public static void main(String[] args) throws ClassNotFoundException {
		
		//////////////////////////////////////////////////////////////////
		// Caricamento driver 
		//////////////////////////////////////////////////////////////////
        
			try {
				Class.forName(dataBaseDriver);
				Connection dbConn = DriverManager.getConnection(dataBaseUrl, dataBaseUser, dataBasePwd);
			    System.out.println(dbConn.toString());
			    System.out.println("Connessione a " + dataBaseName + " Stabilita Correttamente");
			    System.out.println("URL di connessione = " + dataBaseUrl);
			} catch (Exception e) {
				System.out.println("Errore di connessione al database");
				e.printStackTrace();
			}
             
	}
}
