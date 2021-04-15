package sait.mms.managers;
import java.sql.*;
import java.util.*;

/**
 * This program demonstrates movie management system using object-oriented
 * design principles. The program will read from database. The database contains
 * information about the movies.
 * 
 * @author Jonghyun Park
 * @version April 06, 2020
 */
public class MovieManageSystem {
	/**
	 * Values needed to get a connection to your DB.
	 */
	private static final String SERVER = "localhost";
	private static final int PORT = 3306;
	private static final String DATABASE = "cprg251";
	private static final String USERNAME = "cprg251";
	private static final String PASSWORD = "password";

	/**
	 * Create a connection to the database.
	 */
	private static Connection conn;

	/**
	 * Create a Scanner object for keyboard input.
	 */
	static Scanner in = new Scanner(System.in);
	
	/**
	 * This method for connecting a MariaDB database using JDBC.
	 * 
	 */
	public static void loaddata() {
		final String DB_URL = String.format("jdbc:mariadb://%s:%d/%s?user=%s&password=%s", SERVER, PORT, DATABASE,
				USERNAME, PASSWORD);
		try {
			conn = DriverManager.getConnection(DB_URL);
			System.out.println("Database Connected");
			System.out.println();
			displayMenu();
		} catch (SQLException e) {
			System.out.println("ERROR: " + e.getMessage());
		}
	}

	/**
	 * This method works for showing display screen and input options. Also, this
	 * method works for other methods.
	 */
	public static void displayMenu() {
		try {
			int numChoice = 0;

			while (numChoice != 5) {
				System.out.println("Movie Manage system");
				System.out.printf("%-6s%-6s%n", "1", "Adds/inserts a new record representing a movie");
				System.out.printf("%-6s%-6s%n", "2", "Retrieves records with movies released in a specific year");
				System.out.printf("%-6s%-6s%n", "3", "Retrieves records with a list of random movies");
				System.out.printf("%-6s%-6s%n", "4", "Deletes a movie given its id");
				System.out.printf("%-6s%-6s%n", "5", "Exit");

				System.out.print("Enter an option: ");
				numChoice = in.nextInt();
				in.nextLine();
				System.out.println();
				if (numChoice == 1) {
					addMovie();
				} else if (numChoice == 2) {
					retrieveMovieInYear();
				} else if (numChoice == 3) {
					retrieveRandomMovie();
				} else if (numChoice == 4) {
					deleteMovie();
				} else if (numChoice == 5) {
					System.out.println("Program is done.");
					conn.close();
					System.out.println("Connection closed.");
				} else {
					System.out.println("Invalid option!\n");
					break;
				}
			}
		} catch (SQLException ex) {
			System.out.println("ERROR: " + ex.getMessage());
		}
	}

	/**
	 * The user will be prompted to enter the duration in minutes, title of the new
	 * movie, and the year the movie was released. When user add a new movie, the
	 * program should update the database by saving new movie at the end of the
	 * movie database.
	 */
	public static void addMovie() {
		int duration;
		String title;
		int year;
		try {
			System.out.println("Enter the Movie Duration: ");
			duration = in.nextInt();
			in.nextLine();
			
			System.out.println("Enter the Movie Title: ");
			title = in.nextLine();
			
			System.out.println("Enter the Movie released year: ");
			year = in.nextInt();
			in.nextLine();

			Statement stmt = conn.createStatement();
			String sqlStatement = "INSERT INTO movies " + "(duration, title, year) " + "VALUES (" + duration + ", '"
					+ title + "', " + year + ")";

			int rows = stmt.executeUpdate(sqlStatement);
			System.out.println(rows + " row(s) added to the table.");
			System.out.println();
		} catch (SQLException ex) {
			System.out.println("ERROR: " + ex.getMessage());
		}
	}

	/**
	 * The user will input a year and the program will display a list of all the
	 * movies released in that year along with the duration (in minutes) of all the
	 * movies. There is no need to specify a value for the ID column of a new movie
	 * record. This will be automatically set to the next available ID by the
	 * database.
	 */
	public static void retrieveMovieInYear() {
		int found;
		try {
			System.out.print("Enter the Movie released year: ");
			found = in.nextInt();
			in.hasNextLine();
			
			Statement stmt = conn.createStatement();
			String sqlStatement = "SELECT * FROM movies " + "WHERE year ='" + found + "'";
			ResultSet result = stmt.executeQuery(sqlStatement);

			System.out.printf("%-9s%-6s%s%n", "Duration", "Year", "Title");

			while (result.next()) {
				System.out.printf("%-9d%-6d%s%n", result.getInt("Duration"), result.getInt("Year"),
						result.getString("Title"));
			}
			System.out.println();
		} catch (SQLException ex) {
			System.out.println("ERROR: " + ex.getMessage());
		}
	}

	/**
	 * The program will read from the supplied data file into a single array list.
	 * The database contains information about the movies. Each movie record has the
	 * following attributes: Duration (in minutes), Title, Year of release
	 */
	public static void retrieveRandomMovie() {
		int Random;
		try {
			System.out.print("Enter number of movies: ");
			Random = in.nextInt();
			in.nextLine();
			
			if (Random > 0) {
				Statement stmt = conn.createStatement();

				String sqlStatement = "SELECT * FROM movies " + "ORDER BY RAND() " + "LIMIT " + Random;
				ResultSet result = stmt.executeQuery(sqlStatement);
				System.out.printf("%-9s%-6s%s%n", "Duration", "Year", "Title");

				while (result.next()) {
					System.out.printf("%-9d%-6d%s%n", result.getInt("Duration"), result.getInt("Year"),
							result.getString("Title"));
				}
				System.out.println();
			} else {
				System.out.println("Wrong number! Try Again!");
				System.out.println();
				retrieveRandomMovie();
			}
		} catch (SQLException ex) {
			System.out.println("ERROR: " + ex.getMessage());
		}
	}

	/**
	 * This method lets the user delete a movie from the cprg251 database's movies
	 * table.
	 */
	public static void deleteMovie() {
		int deletedId;
		try {
			System.out.print("Enter number of id which you want delete: ");
			deletedId = in.nextInt();
			in.nextLine();

			Statement stmt = conn.createStatement();

			if (findAndDisplayId(stmt, deletedId)) {
				System.out.print("Are you sure you want to delete " + "this Movie? (y/n): ");
				String sure = in.nextLine();

				if (Character.toUpperCase(sure.charAt(0)) == 'Y') {
					removeMovie(stmt, deletedId);
				} else {
					System.out.println("The movie was not deleted.");
					displayMenu();
				}
			} else {
				System.out.println("That id was not found.");
				displayMenu();
			}
		} catch (SQLException ex) {
			System.out.println("ERROR: " + ex.getMessage());
		}
	}

	/**
	 * The findAndDisplayId method finds a specified movie's data and displays it.
	 * 
	 * @param stmt      A Statement object for the database.
	 * @param deletedId The deleted Id for the desired movie.
	 * @return true/false to indicate whether the movie was found
	 * @throws SQLException When there is an exception from the executeQuery method.
	 */
	public static boolean findAndDisplayId(Statement stmt, int deletedId) throws SQLException {
		boolean movieFound;

		String sqlStatement = "SELECT * FROM movies WHERE id = '" + deletedId + "'";
		ResultSet result = stmt.executeQuery(sqlStatement);

		if (result.next()) {
			System.out.println("Duration: " + result.getInt("Duration"));
			System.out.println("Title: " + result.getString("Title"));
			System.out.println("Year: " + result.getInt("Year"));

			movieFound = true;
		} else {
			movieFound = false;
		}
		return movieFound;
	}

	/**
	 * The removeMovie method deletes a specified movie.
	 * 
	 * @param stmt      A Statement object for the database.
	 * @param deletedId deletedId The deleted Id for the desired movie.
	 * @throws SQLException hen there is an exception from the executeQuery method.
	 */
	public static void removeMovie(Statement stmt, int deletedId) throws SQLException {
		String sqlStatement = "DELETE FROM movies " + "WHERE id = '" + deletedId + "'";
		int rows = stmt.executeUpdate(sqlStatement);

		System.out.println(rows + " row(s) deleted.");
		displayMenu();
	}

	
}
