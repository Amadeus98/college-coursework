
/* 
 * CS 425 SQL Homework 2
 * Emmanuel Marcha
 * A20212106
 */

import java.io.*;

public class mainFile {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); 
	static String username = null; 
	static String password = null; 
	static String userInput = ""; 
	static dbManager db; 
	
	/* 
	 * Main function
	 */
	public static void main(String[] args) {
		
		System.out.println("Welcome to the Rec Center Manager!\n"); 
		
		// Obtain user information for connectivity  
		try {
			System.out.print("Username: "); 
			username = br.readLine(); 
			System.out.print("Password: "); 
			password = br.readLine(); 
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		// Test connection with database 
		db = new dbManager(username, password);
		
		// Once connected, clear local info and begin  
		if (db.connected) { 
			username = null; 
			password = null; 
			beginQuery();  
		}
	}
	
	/* 
	 * Begin queries based on user input 
	 */
	private static void beginQuery(){
		
		System.out.println("\nAvailable queries");
		System.out.println("-----------------");
		System.out.println("- Classes\n- People\n- Discounts\n"); 
		System.out.println("(Type 'Exit' to quit)"); 
		
		// Recycle prompt until quit 
		while(userInput.compareToIgnoreCase("Exit") != 0) { 
			try { 
				System.out.print(">");
				userInput = br.readLine(); 
			} catch (IOException e) {
				e.printStackTrace(); 
			}
			
			// Evaluating user input 
			if (userInput.compareToIgnoreCase("Classes") == 0) classSearchMenu(); 
			if (userInput.compareToIgnoreCase("People") == 0) peopleSearchMenu(); 
		}
		
	}
	
	/* 
	 * Menu for searching people based on different criteria 
	 */
	private static void peopleSearchMenu() { 
		System.out.println("Search for a person by typing their name in the format \n" + 
						   "'FIRST LAST' or their ID number.");
		
		System.out.println("(Type 'back' to return to previous menu)");
		
		String delims = "[ ]"; 
		
		while(userInput.compareToIgnoreCase("back") != 0) {
			try { 
				System.out.print(">");
				userInput = br.readLine();
				String[] inputs = userInput.split(delims);
				
				// Check the databases to see if entry is present 
				if (inputs.length == 2) {
					if (db.checkEntry(
						"select * " + 
						"from Instructor " + 
						"where f_name = '" + inputs[0] + "' AND " + 
						"l_name = '" + inputs[1] + "'")) 
					
						System.out.println("Is an instructor."); 
					else System.out.println("Not an instructor."); 
				
					if (db.checkEntry(
							"select * " + 
							"from RecCenterMember " + 
							"where f_name = '" + inputs[0] + "' AND " + 
							"l_name = '" + inputs[1] + "'")) { 
						
						System.out.println("Is a member.");
						System.out.println("\nContact Information"); 
						System.out.println("---------------------------"); 
						db.customQuery(
								"select address, phone " +  
								"from (select * from RecCenterMember LEFT OUTER JOIN " +
								"FamilyPackage on RecCenterMember.family_id = FamilyPackage.id) " + 
								"where f_name = '" + inputs[0] + "' AND l_name = '" + inputs[1] + "'");
					} else System.out.println("Not a member."); 
				} 
				
			} catch (IOException e) {
				e.printStackTrace(); 
			}
		}
	}
	
	/*
	 * Menu for searching classes based on different criteria
	 */
	private static void classSearchMenu() { 
		System.out.println("\nSearch classes by one of the following criteria"); 
		System.out.println("-----------------------------------------------");
		System.out.println("- Type\n- Season\n- Year\n");
		System.out.println("(Type 'back' to return to previous menu)");
		
		while(userInput.compareToIgnoreCase("back") != 0) {
			try { 
				System.out.print(">");
				userInput = br.readLine(); 
			} catch (IOException e) {
				e.printStackTrace(); 
			}
			
			// Evaluating user input 
			if (userInput.compareToIgnoreCase("Type") == 0) classTypeSearch(); 
			else if (userInput.compareToIgnoreCase("Season") == 0 ||
					 userInput.compareToIgnoreCase("Year") == 0) classOtherSearch(); 
		}
	}
	
	/*
	 * User selects the 'types' option 
	 */
	private static void classTypeSearch() { 
		
		System.out.println("\nAvailable class types"); 
		System.out.println("---------------------");
		db.query("type","Type");
		System.out.println("\n(Type 'back' to return to previous menu)");
		
		while(userInput.compareToIgnoreCase("back") != 0) {
			try { 
				System.out.print(">");
				userInput = br.readLine();
				System.out.println("\nAll classes of type " + userInput); 
				System.out.println("--------------------------------------"); 
				
				// Selects class title and instructor first and last name 
				db.customQuery(
						"select title, f_name, l_name " +  
						"from (select * from Class LEFT OUTER JOIN " +
						"Instructor on Class.instructor = Instructor.id) " + 
						"where type = " + "'" + userInput + "'");
				
				System.out.println("--------------------------------------"); 
				System.out.print("Total revenue for classes of type " +  
						userInput + ": "); 
				
				// Selects total revenue for given type 
				db.customQuery(
						"select sum(cost) " +  
						"from (select * from Class LEFT OUTER JOIN " +
						"Enrollment on Class.id = Enrollment.class_id) " + 
						"where type = " + "'" + userInput + "'"); 
			} catch (IOException e) {
				e.printStackTrace(); 
			}
		}
		 
	}
	
	/* 
	 * Evaluating searches based on season and year 
	 */
	private static void classOtherSearch() { 
		System.out.println("\nYou may search by typing in a season, a year, or both in" + 
						   "the format 'SEASON YEAR'");
		System.out.println("(Type 'back' to return to the previous menu)"); 
		
		String delims = "[ ]"; 
		
		while(userInput.compareToIgnoreCase("back") != 0) {
			try { 
				System.out.print(">");
				userInput = br.readLine();
				String[] inputs = userInput.split(delims);
				String search = "*";  
				if (inputs.length == 1) {
					if (isInteger(inputs[0])) search = "year = " + inputs[0]; 
					else search = "season = '" + inputs[0] + "'";  
				} else search = "season = '" + inputs[0] + "' AND " +  
								"year = " + inputs[1]; 
			
				System.out.println("\nList of selected courses"); 
				System.out.println("----------------------"); 
				
				db.customQuery(
						"select title " +  
						"from Class " + 
						"where " + search);
				
			} catch (IOException e) {
				e.printStackTrace(); 
			}
				
		}
	}
	
	/* 
	 * Check a string for integer 
	 */
	private static boolean isInteger(String input) { 
		
		try { 
			Integer.parseInt(input); 
			return true; 
		} catch (Exception e) { 
			return false; 
		}
	}

}
