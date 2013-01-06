/*****************************************************************


@author Professor
@version Fall 2012
 *****************************************************************/
package OldCode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import main.Commands;

public class MainEditor {//implements IEditor {

	/**Determines if Editor is open*/
	boolean programOpen;

	/***/
	ArrayList<Commands> commands;

	/**The LinkedList to access data*/
	MainLinkList<String> list;

	/**Index of the current node to access*/
	int currentIndex;

	/***/
	//Scanner inputScan;


	/*ASSUMEING THE "LINE" DESIGNATION STARTS WITH 1*/
	// Need to throw index out of bounds exceptions for getters

	/*****************************************************************

	 *****************************************************************/
	public MainEditor () {
		list = new MainLinkList<String>();
		currentIndex = 1;
		programOpen = true;

		Commands.setCommands();

		run();
		
	}
	
	private void run() {
		
		Scanner inputScan = new Scanner(System.in);
		String inputString;

		while (programOpen) {
			System.out.print ("command: ");
			inputString = inputScan.nextLine();
			String[] splitString = inputString.split("\\s+");
			processCommand(Commands.checkCall(splitString), splitString);
			//System.out.println(splitString[0]);
		}
		
	}


	/*****************************************************************
	This takes the command String from the interface and determines the
	course of action to take.

	@param command String of command to perform
	 *****************************************************************/
	//@Override
	public void processCommand(Commands command, String[] splitCall) {
		//  
		//System.out.println(command);

		
		try {
		switch (command) {

		case INSERT_BEFORE:
			String text1 = "";
			for (int i = 1; i < splitCall.length; i ++)
				text1 = text1 + splitCall[i] + " ";
			insertBefore(text1);
			
			//System.out.println(command);
			break;

		case INSERT_AFTER:
			String text2 = "";
			for (int i = 1; i < splitCall.length; i ++)
				text2 = text2 + splitCall[i] + " ";
			insertAfter(text2);
			
			System.out.println(command);
			break;

		case MOVE_DOWN:
			System.out.println(command);

			break;

		case MOVE_NUM_DOWN:
			System.out.println(command);

			break;

		case MOVE_UP:
			System.out.println(command);

			break;

		case MOVE_NUM_UP:
			System.out.println(command);

			break;

		case REMOVE:
			System.out.println(command);

			break;

		case REMOVE_NUM:
			System.out.println(command);

			break;

		case DISPLAY:
			//System.out.println(command);
			
			try {
			String[] lines = getLines(0,getNbrLines()-1);
			for (String line: lines)
				System.out.println (line);
			} catch (IndexOutOfBoundsException e) {
				System.out.println("Invalid line numbers");
			}

			break;

		case DISPLAY_NUMS:
			System.out.println(command);

			try {
				String[] lines = getLines(Integer.parseInt(splitCall[1]), Integer.parseInt(splitCall[2]));
				for (String line: lines)
					System.out.println (line);
				} catch (IndexOutOfBoundsException e) {
					System.out.println("Invalid line numbers");
				} catch (NumberFormatException e) {
					System.out.println("Invalid line numbers");
				}

			
			break;

		case CLEAR:
			System.out.println(command);

			break;

		case SAVE:
			System.out.println(command);

			break;

		case LOAD:
			System.out.println(command);

			break;

		case HELP:
			Commands.displayCommands();
			break;

		case EXIT:
			System.out.println(command);


			programOpen = false;
			break;

		case INSERT_END:
			System.out.println(command);

			break;



		case MOVE_CURRENT_BACK:
			System.out.println(command);

			break;

		case REPLACE:
			System.out.println(command);

			break;


		case SWITCH_TO_END:
			System.out.println(command);

			break;

		case UNDO:
			System.out.println(command);

			break;

		case REDO:
			System.out.println(command);

			break;

		default:
			System.out.println("default switch");

			break;
		}
		} catch (NullPointerException e ) {
			System.out.println("Invalid command, try again...");
			
			
		}
	}

	/*****************************************************************
	Gets the size of the Linked List.

	@return Int size of the linked list.
	 *****************************************************************/
	//@Override
	public int getNbrLines() {
		return list.size();
	}

	/*****************************************************************
	Gets the current index indicator.

	@return Int value of the current index indicator.
	 *****************************************************************/
	//@Override
	public int getCurrentLineNbr() {
		return currentIndex;
	}

	/*****************************************************************
	Gets the data from the passed index.

	@param lineNbr int index to get data from.
	@return String data from the passed location
	 *****************************************************************/
	//@Override
	public String getLine(int lineNbr) {

		if (lineNbr < 0 || lineNbr >= list.size())
			throw new IndexOutOfBoundsException();


		return list.get(lineNbr);
	}

	/*****************************************************************
	Gets all the data from the given location to the given location
	inclusive.

	@param beginPos int index of the first location to get data from.
	@param endPos int index of the last location to get data from.
	@return String[] Array of the data collected.
	 *****************************************************************/
	//@Override
	public String[] getLines(int beginPos, int endPos) {

		//checks the first index.
		if (beginPos < 0 || beginPos >= list.size())
			throw new IndexOutOfBoundsException();

		//checks the first index.
		if (endPos < 0 || endPos >= list.size())
			throw new IndexOutOfBoundsException();

		//The array that collects the data
		String[] data;

		//if the first parameter is smaller the second
		//run throw the list from beginPos to endPos forward.
		if (beginPos <= endPos){

			//instantiates the array to collect the data.
			data = new String[(endPos - beginPos) + 1];

			//Collects the data in order
			for (int i = 0; i + beginPos <= endPos; i++){
				data[i] = list.get(i + beginPos);
			}
		}

		//if the first parameter is larger then the second
		//run thru the list from endPos to beginPos backwards
		else {

			//instantiates the array to collect the data.
			data = new String[(beginPos - endPos) + 1];

			//Collects the data in reverse order
			for (int i = 0; i + beginPos >= endPos; i--){
				data[Math.abs(i)] = list.get(i + beginPos);
			}
		}
		return data;
	}

	/*****************************************************************
	Adds passed data before the current location.

	@param line String to add to list.
	@return boolean true if add to the list.
	 *****************************************************************/
	//@Override
	public boolean insertBefore(String line) {
		//when the line indicator is at the beginning
		if (currentIndex == 1) {
			list.add(line);
			return true;
		}
		else {
			try{
				list.add(currentIndex, line);
				return true;
			} catch (IndexOutOfBoundsException e) {
				return false;
			}
		}
	}

	/*****************************************************************
	Adds passed data after the current location.

	@param line String to add to list.
	@return boolean true if add to the list.
	 *****************************************************************/
	//@Override
	public boolean insertAfter(String line) {
		//when the line indicator is at the beginning
		if (currentIndex == list.size()-1)
			list.addAtEnd(line);
		
		//if ()

		list.add(currentIndex, line);
		return true;

	}

	/*****************************************************************
	Adds passed data to the end of the list.

	@param line String to add to list.
	@return boolean true if add to the list.
	 *****************************************************************/
	//@Override
	public boolean insertEnd(String line) {
		list.addAtEnd(line);
		return true;
	}

	/*****************************************************************
	Moves the line indicator up passed number of nodes.

	@param nbrOfPositions int value to move location indicator.
	@return int value of the location indicator.
	 *****************************************************************/
	//@Override
	public int moveUp(int nbrOfPositions) {
		currentIndex -= nbrOfPositions;
		if (currentIndex < 0)
			return 0;
		else
			return currentIndex;
	}

	/*****************************************************************
	Moves the line indicator down ther paramater number of nodes.

	@param nbrOfPositions int value to move location indicator.
	@return int value of the location indicator.
	 *****************************************************************/
	//@Override
	public int moveDown(int nbrOfPositions) {
		currentIndex += nbrOfPositions;
		if (currentIndex >= list.size())
			return list.size() - 1;
		else
			return currentIndex;
	}

	/*****************************************************************

	 *****************************************************************/
	//@Override
	public boolean remove(int nbrLines) {
		//  Auto-generated method stub
		return false;
	}

	/*****************************************************************
	Clears the list.
	 *****************************************************************/
	//@Override
	public void clear() {
		list.clear();
	}

	/*****************************************************************

	 *****************************************************************/
	//@Override
	public void save(String filename) throws IOException {
	

	}

	/*****************************************************************

	 *****************************************************************/
	//@Override
	public void load(String filename) throws IOException {
		

	}

	/*****************************************************************

	 *****************************************************************/
	//@Override
	public int findReplace(String str1, String str2) {
		// 
		//		int count = 0;
		//		
		//		Node temp;
		//		while (temp.getNext() != null) {
		//			temp = (Node) temp.getNext();
		//		}
		//		
		//		
		//		
		return 0;
	}

	/*****************************************************************
	Moves the location indicator to the beginning.
	 *****************************************************************/
	//@Override
	public void moveCurrentLineToFront() {
		currentIndex = 0;

	}

	/*****************************************************************
	Moves the location indicator to the end.
	 *****************************************************************/
	//@Override
	public void moveCurrentLineToEnd() {
		currentIndex = list.size() - 1;
	}

	/*****************************************************************
	This switches the current line with the 
	 *****************************************************************/
	//@Override
	public void switchCurrentLineWithFirst() {

		String first = list.remove(0);
		String current = list.remove(currentIndex);

		list.add(current);
		list.add(currentIndex, first);
	}

	/*****************************************************************

	 *****************************************************************/
	//@Override
	public void switchCurrentLineWithLast() {
		String last = list.remove(list.size()-1);
		String current = list.remove(currentIndex);

		list.addAtEnd(current);
		list.add(currentIndex, last);

	}

	/*****************************************************************

	 *****************************************************************/
	//@Override
	public void undo() {
		

	}
}
