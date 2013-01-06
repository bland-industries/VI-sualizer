/*****************************************************************
Enum containing all the possible commands and processes the input
strings. 

@author Thomas Verstraete
@version Fall 2012
 *****************************************************************/
package main;

public enum Commands {

	/*The input string to access the commands are the first parameter
	where # and * are numbers and the $ represents the end of the list
	All commands are as described in the second parameter*/
	
	INSERT_BEFORE ("b", "Insert sentence before the current line; " +
			"make the inserted line \n\t   the current line"),

	INSERT_AFTER ("i", "Insert sentence after the current line; " +
			"make the inserted line \n\t   the current line"),

	MOVE_DOWN ("m", "Move current line indicator down 1 position"),

	MOVE_NUM_DOWN ("m #", "Move current line indicator down # positions"),

	MOVE_UP ("u", "Move current line indicator up 1 position"),

	MOVE_NUM_UP ("u #", "Move current line indicator up # positions"),

	REMOVE ("r", "Remove the current line; make the next line the " +
			"current line, \n\t   if there is a next line; " +
			"otherwise make the previous line \n\t   the current line, " +
			"if there is a previous line"),		

	REMOVE_NUM ("r #", "Remove # lines starting at the current line;" +
			" make the next \n\t   line the current line, if there is " +
			"a next line; otherwise \n\t   make the previous line the " +
			"current line, if there is a \n\t   previous line"),

	DISPLAY ("d", "Display all lines in the buffer/file"),

	DISPLAY_NUMS ("d # *", "Display from line # to * (inclusive) in " +
			"the buffer/file"),
			
	DISPLAY_CURRENT ("d c", "Display the current line"),

	CLEAR ("c", "Clear (remove) all lines in the buffer/file (only if " +
			"saved); \n\t   set the current line indicator to 0"),

	SAVE ("s", "Save the contents to the specified file"),

	LOAD ("l", "Load the contents of the specified file into editor " +
			"buffer \n\t   (only if saved); ; make the first line " +
			"the current line"),

	HELP ("h", "Display a help page of editor commands"),
	
	HELP_COM ("h ?", "Displays the information on the commands " +
			"containing \"?\""),

	EXIT ("x", "Exit the editor"),

	INSERT_END ("e", "Insert sentence after the last line; make the" +
			" inserted \n\t   line the current line"),
			
	MOVE_CURRENT_BACK ("mc $", "Move the current line to the end of" +
			" the buffer/file; make the \n\t   last line the current" +
			" line"),
			
	MOVE_CURRENT_NUM ("mc #", "Move the current line to the line given" +
			" in the buffer/file; \n\t   make the moved line the" +
			" current line"),

	REPLACE ("fr", "Replace all occurrences of string1 with string2"),

	SWITCH_TO_END ("sw $", "Switch the current line and the last line;" +
			" no change to \n\t   current line indicator"),
			
	SWITCH_TO_NUM ("sw #", "Switch the current line and the line" +
			" given by the number: \n\t   no change to line indicator."),

	UNDO ("ud", "Undo the effects of the last operation (excluding" +
			" save and load); \n\t   should support multiple undo" +
			" operations"),

	REDO ("rd", "Redo the effects of the last undo (excluding save);" +
			" should \n\t   support multiple redo operations")

	;


	/**Static list of all the possible commands*/
	private static Commands[] commands;

	/**String list of all the ways to access commands*/
	private String call;

	private String description;

	/*****************************************************************
	Constructor takes in the two string parameters
	
	@param call String input of the command.
	@param description String description of the command.
	 *****************************************************************/
	private Commands (String call, String description) {
		this.call = call;
		this.description = description;
	}

	/*****************************************************************
	Getter for the input String of the command.
	
	@return String input representing the command.
	 *****************************************************************/
	public String getCall () {
		return call;
	}

	/*****************************************************************
	Over-riden toString method returns a String with the input and the
	description of the command.
	 *****************************************************************/
	public String toString () {
		return call + " \t" + description;
	}

	/*****************************************************************
	Creates the static list of all the commands.
	 *****************************************************************/
	public static void setCommands () {
		commands = Commands.values();
	}
	
	/*****************************************************************
	Returns an array of all the commands.
	 *****************************************************************/
	public static Commands[] getAllCommands () {
		return commands;
	}

	/*****************************************************************
	Takes the array of input strings and parses out the command that
	best fits the input.

	@param splitCall String array of the input divided by the space
	@return Commands enum as best fit. Null means not fit.
	 *****************************************************************/
	public static Commands checkCall (String[] splitCall) {

		/*multiple special cases where the first letter is duplicate
			for multiple commands.*/

		//case for the input beginning with m
		if (splitCall[0].equals("m")) {
			return checkM(splitCall);
		}

		//case for the input beginning with u
		if (splitCall[0].equals("u")) {
			return checkU(splitCall);
		}

		//case for the input beginning with d
		if (splitCall[0].equals("d")) {
			return checkD(splitCall);
		}

		//case for the input beginning with r
		if (splitCall[0].equals("r")) {
			return checkR(splitCall);
		}

		//case for the input beginning with mc
		if (splitCall[0].equals("mc")) {
			return checkMC(splitCall);
		}
	
		//case for the input beginning with sw
		if (splitCall[0].equals("sw")) {
			return checkSW(splitCall);
		}
		
		//case for the input beginning with h
		if (splitCall[0].equals("h")) {
			return checkH(splitCall);
		}

		//does not contain any special calls
		for (Commands com: commands) {
			if (com.getCall().startsWith(splitCall[0])) {
				return com;
			}
		}	

		//if no command matches the input
		return null;
	}

	/*****************************************************************
	Special case if the input call begins with m. Determines which of
	the two commands it will be.

	@param splitCall String array of the input divided by the space 
	@return Commands enum as best fit. Null means not fit.
	 *****************************************************************/
	private static Commands checkM (String[] splitCall) {

		//m commands do not have any input larger then 2 sections.
		if (splitCall.length > 2)
			return null;

		//if m only
		if (splitCall.length == 1)
			return Commands.MOVE_DOWN;
	
		//if m and a number 		
		else if (splitCall[1].matches("[0-9]+"))
			return Commands.MOVE_NUM_DOWN;
		
		//if m and not a number, not a command
		else
			return null;
	}

	/*****************************************************************
	Special case if the input call begins with u. Determines which of
	the two commands it will be.

	@param splitCall String array of the input divided by the space 
	@return Commands enum as best fit. Null means not fit.
	 *****************************************************************/
	private static Commands checkU (String[] splitCall) {
		
		if (splitCall.length > 2)
			return null;

		if (splitCall.length == 1)
			return Commands.MOVE_UP;
		
		else if (splitCall[1].matches("[0-9]+")) {
			if (splitCall[1].matches("2"))
				System.out.println("Still haven't found what I'm looking for!!!");
			return Commands.MOVE_NUM_UP;
		}
			
		else
			return null;
	}

	/*****************************************************************
	Special case if the input call begins with d. Determines which of
	the three commands it will be.

	@param splitCall String array of the input divided by the space 
	@return Commands enum as best fit. Null means not fit.
	 *****************************************************************/
	private static Commands checkD (String[] splitCall) {
		
		if (splitCall.length > 3)
			return null;

		if (splitCall.length == 1)
			return Commands.DISPLAY;
		
		else if (splitCall.length == 2 && splitCall[1].equals("c"))
			return Commands.DISPLAY_CURRENT;
		
		else if (splitCall[1].matches("[0-9]+") && splitCall[2].matches("[0-9]+"))
			return Commands.DISPLAY_NUMS;
		
		else
			return null;
	}

	/*****************************************************************
	Special case if the input call begins with r. Determines which of
	the two commands it will be.

	@param splitCall String array of the input divided by the space 
	@return Commands enum as best fit. Null means not fit.
	 *****************************************************************/
	private static Commands checkR (String[] splitCall) {
		
		if (splitCall.length > 2)
			return null;

		if (splitCall.length == 1)
			return Commands.REMOVE;
		
		else if (splitCall[1].matches("[0-9]+")) {
			if (splitCall[1].matches("2"))
				System.out.println("These aren't the droids you are " +
						"looking for");
			return Commands.REMOVE_NUM;
		}
			
		else
			return null;
	}

	/*****************************************************************
	Special case if the input call begins with mc. Determines which of
	the two commands it will be.

	@param splitCall String array of the input divided by the space 
	@return Commands enum as best fit. Null means not fit.
	 *****************************************************************/
	private static Commands checkMC (String[] splitCall) {
		
		if (splitCall.length > 2)
			return null;
		
		if (splitCall[1].equals("$"))
			return Commands.MOVE_CURRENT_BACK;
		
		else if (splitCall[1].matches("[0-9]+")) {
			if (splitCall[1].matches("5"))
				System.out.println("PERSONALITY CRISIS!!!!!");
			return Commands.MOVE_CURRENT_NUM;
		}
		
		else
			return null;
	}

	/*****************************************************************
	Special case if the input call begins with sw. Determines which of
	the two commands it will be.

	@param splitCall String array of the input divided by the space 
	@return Commands enum as best fit. Null means not fit.
	 *****************************************************************/
	private static Commands checkSW (String[] splitCall) {
		
		if (splitCall.length > 2 || splitCall.length < 2)
			return null;

		if (splitCall[1].equals("$"))
			return Commands.SWITCH_TO_END;
		
		else if (splitCall[1].matches("[0-9]+"))
			return Commands.SWITCH_TO_NUM;
		
		else
			return null;
	}
	
	/*****************************************************************
	Special case if the input call begins with h. Determines which of
	the two commands it will be.

	@param splitCall String array of the input divided by the space 
	@return Commands enum as best fit. Null means not fit.
	 *****************************************************************/
	private static Commands checkH (String[] splitCall) {
		
		if (splitCall.length == 1)
			return Commands.HELP;
		
		else 
			return Commands.HELP_COM;
	}

	/*****************************************************************
	Prints out the help display for the user to see the possible 
	commands and their description.
	 *****************************************************************/
	public static void displayCommands() {
		
		System.out.println ("*****************************************" +
				"*****************************");
		
		System.out.println ("The following are all the commands that " +
				"are available:");
		System.out.println ();

		//loops through each command.
		for (Commands com: commands) {
			System.out.println (com);
			System.out.println ();
		}

		System.out.println ("*****************************************" +
				"*****************************");
	}
	
	/*****************************************************************
	Prints out the help display for the user to see the possible 
	commands and their description that contain the given string.
	
	@param str String to search for in command calls.
	 *****************************************************************/
	public static void displaySelectCommands(String str) {
		
		System.out.println ("*****************************************" +
				"*****************************");
		
		System.out.println ("The following are all the commands that " +
				"contain \"" + str + "\":");
		
		System.out.println ();
		//loops through each command.
		for (Commands com: commands) {
			if (com.getCall().contains(str)) {
				System.out.println (com);
				System.out.println ();
			}
			
		}
			System.out.println ("*****************************************" +
					"*****************************");
		
		
	}
}
