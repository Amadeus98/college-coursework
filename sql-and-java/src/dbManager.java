
/* 
 * CS 425 SQL Homework 2
 * Emmanuel Marcha
 * A20212106
 */

import java.sql.*;

public class dbManager {

	String username = null; 
	String password = null; 
	Connection conn; 
	boolean connected = false; 
	
	/* 
	 * Constructor 
	 */
	public dbManager(String username, String password){ 
		this.username = username; 
		this.password = password; 
		this.connected = testConnection(); 
	}
	
	/* 
	 * Test connection with the oracle database on 
	 * the fourier server
	 */
	private boolean testConnection() { 
		
		System.out.println("\nAttempting to establish connection..."); 
		
		try { 
			Class.forName("oracle.jdbc.driver.OracleDriver"); 
			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@fourier.cs.iit.edu:1521:orcl",
					username, password);
			System.out.println("Connected."); 
			return true; 
		} catch (Exception sqle) { 
			System.out.println("Connection failed."); 
			System.out.println("Exception: " + sqle);
			return false; 
		}
	}
	
	/* 
	 * Simple field queries based on user input 
	 */
	public void query(String query, String table) { 
		
		if (!connected) return; 
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery(
					"select " + query + 
					" from " + table);
			while(rset.next()) { 
				System.out.println("- " + rset.getString(query));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	/* 
	 * Handles custom queries passed by strings 
	 */
	public void customQuery(String query) { 
		
		if (!connected) return; 
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery(query); 
			
			ResultSetMetaData rsmd = rset.getMetaData(); 
			int numCols = rsmd.getColumnCount(); 
			
			while(rset.next()) { 
				// Iterate across the number of columns  
				// returned by query
				for (int i = 1; i <= numCols; i++){ 
					System.out.print(rset.getString(i) + " "); 
				}
				System.out.println();
			}
			
			System.out.println(); 
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Checks for existence of entry in table
	 */
	public boolean checkEntry(String query){
		
		if (!connected) return false; 
		
		try { 
			Statement stmt = conn.createStatement(); 
			ResultSet rset = stmt.executeQuery(query); 
			//rset.next(); 
			//rset.getString(1); 
			if (rset.next()) return true; 
			else return false;  
		} catch (SQLException e) { 
			e.printStackTrace(); 
			return false; 
		}
	}
}
