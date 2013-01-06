/*****************************************************************
This is the main editor of the VI wanna be. All of the commands 
are from the Commands enum. This editor uses a Linked List structure.

@author Thomas Verstraete
@version Fall 2012
 *****************************************************************/
package main;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import utilities.IEditor;

public class DMainEdit implements IEditor{

	/**Determines if Editor is open*/
	private boolean programOpen;

	/**The LinkedList to access data*/
	private DLinkList<String> list;

	/**Keeps track if this has been saved or not*/
	private boolean saved;

	/**Backup history for the undo and redo commands*/
	private DLinkList<DLinkList<String>> history;

	/*****************************************************************
	Main Constructor.
	 *****************************************************************/
	@SuppressWarnings("unchecked")
	public DMainEdit () {
		//sets up the editor
		list = new DLinkList<String>();
		
		//currentIndex = 0;
		history = new DLinkList<DLinkList<String>>();
		
		//have to encapsulate this
		try {
		history.addToHead((DLinkList<String>) list.clone());
		}
		catch (CloneNotSupportedException e) {
			System.out.println("Please see program auther.");
		}
		
		programOpen = true;
		
		saved = true;

		//Set up the commands list
		Commands.setCommands();

		run();
	}

	/*****************************************************************
	reads and processes each command until exit.
	 *****************************************************************/
	@Override
	public void run() {
		Scanner inputScan = new Scanner(System.in);
		String inputString;

		//as long as the user does not type x it will continue to ask
		while (programOpen) {	
			
			//set up the command indicator.
			int size = list.size();
			int line = list.getCurrentIndex() + 1;
			
			if (size == 0)
				System.out.print ("command <" + size + " of " + size + ">: ");
			else
				System.out.print ("command <" + line + " of " + size + ">: ");
			
			
			//scan the input
			inputString = inputScan.nextLine();

			//Split the input
			String[] splitString = inputString.split("\\s+");

			//pass the command and input.
			processCommand(Commands.checkCall(splitString), splitString);

		}

	}

	/*****************************************************************
	processes the given editor command
	
	@param command Commands user input based on the Commands enum.
	@param splitCall String[] of input split by spaces.
	 *****************************************************************/
	@Override
	public void processCommand(Commands command, String[] splitCall) {


		try {
			if (splitCall.length == 0)
				throw new NullPointerException();

			switch (command) {

			/*****************************************************************/
			case INSERT_BEFORE:
				
				//Reconnect the inserted line
				String text1 = "";

				for (int i = 1; i < splitCall.length; i ++)
					text1 = text1 + splitCall[i] + " ";

				insertBefore(text1);

				//an action has been performed and is no longer a current save
				saved = false;
				//an action has been performed and is added to history
				addToHistory();

				break;

				/*****************************************************************/
			case INSERT_AFTER:
				
				//Reconnect the inserted line
				String text2 = "";

				for (int i = 1; i < splitCall.length; i ++)
					text2 = text2 + splitCall[i] + " ";

				insertAfter(text2);

				//an action has been performed and is no longer a current save
				saved = false;
				//an action has been performed and is added to history
				addToHistory();

				break;

				/*****************************************************************/
			case MOVE_DOWN:
				moveDown(1);

				//an action has been performed and is added to history
				addToHistory();

				break;

				/*****************************************************************/
			case MOVE_NUM_DOWN:
				try {
					int moveNum1 = Integer.parseInt(splitCall[1]);
					moveDown(moveNum1);
					
					//an action has been performed and is added to history
					addToHistory();
				} 

				catch (NumberFormatException e) {
					System.out.println("Invalid Input");
				}

				break;

				/*****************************************************************/
			case MOVE_UP:
				moveUp(1);

				//an action has been performed and is added to history
				addToHistory();

				break;

				/*****************************************************************/
			case MOVE_NUM_UP:
				try {				
					int moveNum2 = Integer.parseInt(splitCall[1]);
					list.moveCurrentUp(moveNum2);
					
					//an action has been performed and is added to history
					addToHistory();
				} 

				catch (NumberFormatException e) {
					System.out.println("Invalid Input");
				}

				break;

				/*****************************************************************/
			case REMOVE:
				remove(1);

				//an action has been performed and is no longer a current save
				saved = false;
				//an action has been performed and is added to history
				addToHistory();

				break;

				/*****************************************************************/
			case REMOVE_NUM:
				try {
					remove(Integer.parseInt(splitCall[1]));

					//an action has been performed and is no longer a current save
					saved = false;
					//an action has been performed and is added to history
					addToHistory();
				}

				catch (NumberFormatException e) {
					System.out.println("Invalid Input");
				}
					
				break;

				/*****************************************************************/
			case DISPLAY:
				
				displayAll();

				break;

				/*****************************************************************/
			case DISPLAY_CURRENT:

				System.out.println(list.get());

				break;

				/*****************************************************************/
			case DISPLAY_NUMS:
				try {
					String[] lines = getLines(Integer.parseInt(splitCall[1]), Integer.parseInt(splitCall[2]));
					for (String line: lines)
						System.out.println (line);
				} 

				catch (IndexOutOfBoundsException e) {
					System.out.println("Invalid Input");
				} 

				catch (NumberFormatException e) {
					System.out.println("Invalid Input");
				}

				break;

				/*****************************************************************/
			case CLEAR:
				
				if (checkToClear()) {
					clear();
					saved = true;
					
					//an action has been performed and is added to history
					addToHistory();
				}
				
				break;

				/*****************************************************************/
			case SAVE:
				try {
					save(splitCall[1]);
				}

				catch (IOException e) {
					System.out.println("Invalid Input");
				}

				//file saved
				saved = true;

				break;

				/*****************************************************************/
			case LOAD:
				try {
					load(splitCall[1]);
					
					//an action has been performed and is added to history
					addToHistory();
				}

				catch (IOException e) {
					System.out.println("Invalid entry (load command)");

				}

				break;

				/*****************************************************************/
			case HELP:
				
				Commands.displayCommands();
				
				break;

				/*****************************************************************/
			case HELP_COM:
				
				Commands.displaySelectCommands(splitCall[1]);
				
				break;
				
				/*****************************************************************/
			case EXIT:
				
				if (checkToClear()) {
					programOpen = false;
					System.out.println("Now back to the 21st century!");
				}
				
				break;

				/*****************************************************************/
			case INSERT_END:
				
				//Reconnect the inserted line
				String text3 = "";

				for (int i = 1; i < splitCall.length; i ++)
					text3 = text3 + splitCall[i] + " ";

				insertEnd(text3);

				//an action has been performed and is no longer a current save
				saved = false;
				//an action has been performed and is added to history
				addToHistory();

				break;

				/*****************************************************************/
			case MOVE_CURRENT_BACK:
				
				String data = list.removeCurrent();

				list.addToTail(data);

				list.setCurrentToTail();

				//an action has been performed and is no longer a current save
				saved = false;
				//an action has been performed and is added to history
				addToHistory();

				break;

				/*****************************************************************/
			case MOVE_CURRENT_NUM:				
				try {
					moveCurrentLineTo(Integer.parseInt(splitCall[1]));

					//an action has been performed and is no longer a current save
					saved = false;
					//an action has been performed and is added to history
					addToHistory();
				}

				catch (NumberFormatException e) {
					System.out.println("Invalid Input");
				}



				break;

				/*****************************************************************/
			case REPLACE:
				
				int count = findReplace(splitCall[1], splitCall[2]);

				System.out.println("Replaced " + count + " parts");

				//an action has been performed and is no longer a current save
				saved = false;
				//an action has been performed and is added to history
				addToHistory();

				break;

				/*****************************************************************/
			case SWITCH_TO_END:
				
				switchCurrentLineWith(list.size());

				//an action has been performed and is no longer a current save
				saved = false;
				//an action has been performed and is added to history
				addToHistory();

				break;

				/*****************************************************************/
			case SWITCH_TO_NUM:			
				try {
					switchCurrentLineWith(Integer.parseInt(splitCall[1]));

					//an action has been performed and is no longer a current save
					saved = false;
					//an action has been performed and is added to history
					addToHistory();
				}

				catch (NumberFormatException e) {
					System.out.println("Invalid Input");
				}

				break;

				/*****************************************************************/
			case UNDO:
				undo();

				//an action has been performed and is no longer a current save
				saved = false;

				break;

				/*****************************************************************/
			case REDO:
				redo();

				//an action has been performed and is no longer a current save
				saved = false;

				break;

				/*****************************************************************/
			default:
				System.out.println("Invalid Input");

				break;
			}
		}

		catch (NullPointerException e ) {
			System.out.println("Invalid Input");
		}

		catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Invalid Input");
		}
		
		catch (Exception e) {
			System.out.println("Invalid Input");
		}
	}

	/*****************************************************************
	returns number of lines in the editor buffer.
	
	@return int value of the size of the list.
	 *****************************************************************/
	@Override
	public int getNbrLines() {
		return list.size();
	}

	/*****************************************************************
	returns the position of the current line.
	
	@return int value of the current index of the list.
	 *****************************************************************/
	@Override
	public int getCurrentLineNbr() {
		return list.getCurrentIndex();
	}

	/*****************************************************************
	returns the line at the given line number

	command -  d c
	
	@param lineNbr int value of the index to get from the list.
	@return String stored in the given index of the list.
	 *****************************************************************/
	@Override
	public String getLine(int lineNbr) {
		
		return list.get(lineNbr - 1);
		
	}

	/*****************************************************************
	returns an array of lines from beginPos to endPos

	command - d, d # *
	
	@param beginPos int value of the begin index to get data from list.
	@param beginPos int value of the last index to get data from list.
	@return String [] of the information requested from the list.
	 *****************************************************************/
	@Override
	public String[] getLines(int beginPos, int endPos) {
//		//checks the first index.
//		if (beginPos < 1 || beginPos > list.size())
//			throw new IndexOutOfBoundsException();
//
//		//checks the second index.
//		if (endPos < 1 || endPos > list.size())
//			throw new IndexOutOfBoundsException();

		//The array that collects the data
		String[] data;

		//if the first parameter is smaller the second
		//run throw the list from beginPos to endPos forward.
		if (beginPos <= endPos){

			//instantiates the array to collect the data.
			data = new String[(endPos - beginPos) + 1];

			//Collects the data in order
			for (int i = 0; i + beginPos <= endPos; i++){
				data[i] = list.get(i + beginPos - 1);
			}
		}

		//if the first parameter is larger then the second
		//run thru the list from endPos to beginPos backwards
		else {

			//instantiates the array to collect the data.
			data = new String[(beginPos - endPos) + 1];

			//Collects the data in reverse order
			for (int i = 0; i + beginPos >= endPos; i--){
				data[Math.abs(i)] = list.get(i + beginPos - 1);
			}
		}
		return data;
	}

	/*****************************************************************
	inserts line before the current line

	command - b <>
	
	@param line String data to insert into the list
	 *****************************************************************/
	@Override
	public void insertBefore(String line) {
		list.addAboveCurrent(line);
		list.moveCurrentUp(1);
	}

	/*****************************************************************
	inserts line after the current line

	command - i <>
	
	@param line String data to insert into the list
	 *****************************************************************/
	@Override
	public void insertAfter(String line) {
		list.addBelowCurrent(line);
		list.moveCurrentDown(1);
	}

	/*****************************************************************
	inserts line as the last line

	command - e <>
	
	@param line String data to insert into the list
	 *****************************************************************/
	@Override
	public void insertEnd(String line) {
		list.addToTail(line);
		list.setCurrentToTail();
	}
	
	/*****************************************************************
	Displays all the lines in the list.

	command - d
	 *****************************************************************/
	public void displayAll() {
		try {
			String[] lines = getLines(1,getNbrLines());
			for (int i = 1; i <= lines.length; i ++) {
				if (i == list.getCurrentIndex() + 1)
					System.out.println ("==>\t" + lines[i-1]);
				else 
					System.out.println ("\t" + lines[i-1]);
			} 
		}

		catch (IndexOutOfBoundsException e) {
			System.out.println("No lines to display");
		} 

		catch (NullPointerException e) {
			System.out.println ("No lines to display");
		}

	}
	
	

	/*****************************************************************
	moves current line indicator up nbrOfPositions and returns 
	new position.

	command - u, u #
	
	@param nbrOfPositions int value to move cursor.
	 *****************************************************************/
	@Override
	public void moveUp(int nbrOfPositions) {
		list.moveCurrentUp(nbrOfPositions);
	}

	/*****************************************************************
	moves current line indicator down nbrOfPositions and returns 
	new position.

	command - m, m #
	
	@param nbrOfPositions int value to move cursor.
	 *****************************************************************/
	@Override
	public void moveDown(int nbrOfPositions) {
		list.moveCurrentDown(nbrOfPositions);
	}

	/*****************************************************************
	removes given number of lines from the current line.

	command - r, r #
	
	@param nbrLinesToRemove int value of lines to remove from list.
	 *****************************************************************/
	@Override
	public void remove(int nbrLinesToRemove) {
		try {
			for (int i = nbrLinesToRemove; i > 0; i --)
				list.removeCurrent();
		}
		catch (IndexOutOfBoundsException e ) {
			//don't do anything. this means there are no more left
			System.out.println("Remove index");
		}
		
		catch (NullPointerException e) {
			//this gets caught when we run out of things to delete
		}
		
	}

	/*****************************************************************
	removes all lines in the buffer

	command - c
	 *****************************************************************/
	@Override
	public void clear() {
		list.clear();
	}

	/*****************************************************************
	saves contents to the file with filename

	command - s <>
	
	@param filename String name of file.
	 *****************************************************************/
	@Override
	public void save(String filename) throws IOException {
		try {
			File bbfile = new File (filename + ".txt");
			PrintWriter myoutput = new PrintWriter (bbfile);

			myoutput.println("" + list.getCurrentIndex());

			String[] lines = getLines(1,getNbrLines());
			for (int i = 1; i <= lines.length; i ++) {
				myoutput.println(lines[i-1]);
			}

			myoutput.close(); 
		}
		catch (Exception e)
		{
			System.out.println ("Invalid entry");
		}

	}

	/*****************************************************************
	clears the current contents and loads the contents of the specified file

	command - l <>
	
	@param filename String name of file.
	 *****************************************************************/
	@Override
	public void load(String filename) throws IOException {
		//find the file
		Scanner scan;
		try {
			scan = new Scanner(new File (filename + ".txt"));

			int currentIndex = 0;

			currentIndex = Integer.parseInt(scan.nextLine());


			while (scan.hasNextLine()) {
				//System.out.println("loading a line");
				list.addToTail(scan.nextLine());
			}
			list.setCurrent(currentIndex);
		} 

		catch (NumberFormatException e) {
			System.out.println("The file format not acceptable");
		}

		catch (Exception e) {
			System.out.println("Invalid entry");
		}
	}

	/*****************************************************************
	finds and replaces all occurrences of str1 with str2 
	and return the count.

	command - fr <> <>
	
	@param str1 String to be replaced.
	@param str2 String to do the replacing.
	@return int Number of times there has been a replacement.
	 *****************************************************************/
	@Override
	public int findReplace(String str1, String str2) {
		int count = 0;

		for (int i = 0; i < list.size(); i ++){
			String data = list.get(i);
			while (data.contains(str1)) {
				data = data.replaceFirst(str1, str2);
				list.setData(i, data);
				count ++;
			}
		}

		return count;
	}

	/*****************************************************************
	moves the current line to the lineNbr.

	command - mc #
	
	@param lineNbr int index to move current to.
	 *****************************************************************/
	@Override
	public void moveCurrentLineTo(int lineNbr) {

		//Attempting to move current to the end of the list.
		if (lineNbr == list.size()) {

			String data = list.removeCurrent();

			list.addToTail(data);

			list.setCurrentToTail();
		}

		else if (list.inBounds(lineNbr - 1)) {
			try{
				String movedItem = list.removeCurrent();

				list.setCurrent(lineNbr - 1);
				list.addAboveCurrent(movedItem);
				list.moveCurrentUp(1);
			} 

			catch (IndexOutOfBoundsException e) {
				System.out.println("Move to Index out of bounds");
			}
		}
		//not in bounds of the list
		else
			throw new IndexOutOfBoundsException();
	}

	/*****************************************************************
	switches the current line with the lineNbr.

	command - sw $, sw #
	
	@param lineNbr int index to switch current with.
	 *****************************************************************/
	@Override
	public void switchCurrentLineWith(int lineNbr) {
		if (list.inBounds(lineNbr - 1)) {
			try{
				String currentMove;
				String lineMove;

				lineMove = list.get(lineNbr - 1);
				currentMove = list.get();

				list.setData(list.getCurrentIndex(), lineMove);
				list.setData(lineNbr - 1, currentMove);
				//list.setCurrent(lineNbr - 1);
			} 

			catch (IndexOutOfBoundsException e) {
				System.out.println("Move to Index out of bounds");
			}
		}
		else 
			throw new IndexOutOfBoundsException();
	}
	
	/*****************************************************************
	Checks with the user if the want to clear.
	
	@return boolean if the user wants to clear the list.
	 *****************************************************************/
	private boolean checkToClear () {

		Scanner scan1 = new Scanner(System.in);

		if (saved) 
			return true;

		else {
			System.out.println("This action will clear your current " +
					"information, do you want to clear it (yes/no) ");
			String answer = scan1.next();

			if (answer.equalsIgnoreCase("yes")) 
				return true;

			else
				return false;
		}

	}

	/*****************************************************************
	undo the effects of the last edit operation

	command - ud
	 *****************************************************************/
	@Override
	public void undo() {

		try {
		history.moveCurrentDown(1);
		list = history.get();
		}
		catch (IndexOutOfBoundsException e) {
		}

	}

	/*****************************************************************
	undo the effects of the last edit operation

	command - rd
	 *****************************************************************/

	public void redo() {

		try {
			history.moveCurrentUp(1);
			list = history.get();
			}
			catch (IndexOutOfBoundsException e) {
				
			}		
	}

	/*****************************************************************
	Adds the current list to the history list.
	 *****************************************************************/
	@SuppressWarnings("unchecked")
	public void addToHistory() {

		try {
			history.removeAllBefore();
			history.addToHead((DLinkList<String>) list.clone());
			history.setCurrentToHead();

		}

		catch (Exception e) {
			System.out.println("add to history");
			e.printStackTrace();
		}
	}
}
