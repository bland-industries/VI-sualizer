/*****************************************************************
It has begun!! This starts the program.

@author Thomas Verstraete
@version Fall 2012
 *****************************************************************/
package main;

public class Ignition {

	public static void main(String[] cheese) {
		
		System.out.println("Welcome to the boring command line program.");
		System.out.println("Use the archaic method of typeing what you want.");
		System.out.println("Type h and you will get a list of commands you can use.");
		
		try {
			new DMainEdit();
		} catch (Exception e) {
			System.out.println("Please see program author.");
		}
		
	}
	
	
}
