package sait.mms.appication;
import sait.mms.managers.MovieManageSystem;

/**
 * This program demonstrates movie management system using object-oriented
 * design principles. The program will read from database. The database contains
 * information about the movies.
 * 
 * @author Jonghyun Park
 * @version April 06, 2020
 */
public class AppDriver {

	public static void main(String[] args) {
		MovieManageSystem.loaddata();
	}

}
