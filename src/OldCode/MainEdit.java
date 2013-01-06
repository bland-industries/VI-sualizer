package OldCode;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import main.Commands;

import utilities.IEditor;

public class MainEdit implements IEditor{

	/**Determines if Editor is open*/
	boolean programOpen;

	/**The LinkedList to access data*/
	MainLinkList<String> list;

	/**Index of the current node to access*/
	int currentIndex;

	/**Keeps track if this has been saved or not*/
	boolean saved;

	/**Keeps track of where in the history list we are*/
	int historyIndex;

	/**Backup history for the undo and redo commands*/
	MainLinkList<MainLinkList<String>> history;

	/*****************************************************************

	 *****************************************************************/
	public MainEdit () {
		//sets up the editor
		list = new MainLinkList<String>();
		currentIndex = 0;
		history = new MainLinkList<MainLinkList<String>>();
		historyIndex = 0;
		programOpen = true;
		saved = true;

		//Set up the commands list
		Commands.setCommands();

		run();

	}

	/*****************************************************************
	reads and processes each command until exit

	command - 
	 *****************************************************************/
	@Override
	public void run() {
		Scanner inputScan = new Scanner(System.in);
		String inputString;

		//as long as the user does not type x it will continue to ask
		while (programOpen) {
			System.out.print ("command <" + historyIndex + " of " + history.size() + ">: ");

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

	command - 
	 *****************************************************************/
	@Override
	public void processCommand(Commands command, String[] splitCall) {


		try {
			switch (command) {

			/*****************************************************************/
			case INSERT_BEFORE:
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
				String text2 = "";

				for (int i = 1; i < splitCall.length; i ++)
					text2 = text2 + splitCall[i] + " ";

				insertAfter(text2);

				currentIndex ++;

				//an action has been performed and is no longer a current save
				saved = false;
				//an action has been performed and is added to history
				addToHistory();

				break;

				/*****************************************************************/
			case MOVE_DOWN:
				if (currentIndex < list.size())
					currentIndex ++;

				break;

				/*****************************************************************/
			case MOVE_NUM_DOWN:
				try {
					moveDown(Integer.parseInt(splitCall[1]));
				} 

				catch (NumberFormatException e) {
					System.out.println("Invalid Input");
				}

				break;

				/*****************************************************************/
			case MOVE_UP:
				if (currentIndex > 1)
					currentIndex --;

				break;

				/*****************************************************************/
			case MOVE_NUM_UP:
				try {				
					moveUp(Integer.parseInt(splitCall[1]));
				} 

				catch (NumberFormatException e) {
					System.out.println("Invalid Input");
				}

				break;

				/*****************************************************************/
			case REMOVE:
				remove(1);

				if (currentIndex > list.size())
					currentIndex = list.size();

				//an action has been performed and is no longer a current save
				saved = false;
				//an action has been performed and is added to history
				addToHistory();

				break;

				/*****************************************************************/
			case REMOVE_NUM:
				try {
					remove(Integer.parseInt(splitCall[1]));

					if (currentIndex > list.size())
						currentIndex = list.size();

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
				try {
					String[] lines = getLines(1,getNbrLines());
					for (int i = 1; i <= lines.length; i ++) {
						if (i == currentIndex)
							System.out.println ("))=3\t" + lines[i-1]);
						else 
							System.out.println ("\t" + lines[i-1]);
					} 
				}

				catch (IndexOutOfBoundsException e) {
					System.out.println("Invalid line numbers");
				} 

				catch (NullPointerException e) {
					System.out.println ("There are no lines");
					e.printStackTrace();
				}

				break;

				/*****************************************************************/
			case DISPLAY_CURRENT:

				System.out.println(getLine(currentIndex));

				break;

				/*****************************************************************/
			case DISPLAY_NUMS:
				try {
					String[] lines = getLines(Integer.parseInt(splitCall[1]), Integer.parseInt(splitCall[2]));
					for (String line: lines)
						System.out.println (line);
				} 

				catch (IndexOutOfBoundsException e) {
					System.out.println("Invalid line numbers");
				} 

				catch (NumberFormatException e) {
					System.out.println("Invalid line numbers");
				}

				break;

				/*****************************************************************/
			case CLEAR:
				Scanner scan1 = new Scanner(System.in);

				if (saved)
					clear();
				else {
					System.out.println("Do you want to clear without saving? (yes/no) ");
					String answer = scan1.next();
					if (answer.equalsIgnoreCase("yes"))
						clear();
				}

				//don't need to say to save if it is clear.
				saved = true;
				//an action has been performed and is added to history
				addToHistory();

				break;

				/*****************************************************************/
			case SAVE:
				System.out.println(command);

				try {
					save(splitCall[1]);
				}

				catch (IOException e) {
					System.out.println("Invalid entry (save command)");
				}

				//file saved
				saved = true;

				break;

				/*****************************************************************/
			case LOAD:
				System.out.println(command);

				try {
					load(splitCall[1]);
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
			case EXIT:
				Scanner scan2 = new Scanner(System.in);

				if (saved)
					programOpen = false;
				else {
					System.out.println("Do you want to exit without saving? (yes/no) ");
					String answer = scan2.next();
					if (answer.equalsIgnoreCase("yes"))
						programOpen = false;
				}
				break;

				/*****************************************************************/
			case INSERT_END:
				String text3 = "";

				for (int i = 1; i < splitCall.length; i ++)
					text3 = text3 + splitCall[i] + " ";

				insertEnd(text3);

				currentIndex = list.size();

				//an action has been performed and is no longer a current save
				saved = false;
				//an action has been performed and is added to history
				addToHistory();

				break;

				/*****************************************************************/
			case MOVE_CURRENT_BACK:
				System.out.println(command);

				moveCurrentLineTo(list.size());

				//an action has been performed and is no longer a current save
				saved = false;
				//an action has been performed and is added to history
				addToHistory();

				break;

				/*****************************************************************/
			case MOVE_CURRENT_NUM:
				System.out.println(command);				
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
				System.out.println(command);

				int count = findReplace(splitCall[1], splitCall[2]);

				System.out.println("Replaced " + count + " parts");

				//an action has been performed and is no longer a current save
				saved = false;
				//an action has been performed and is added to history
				addToHistory();

				break;

				/*****************************************************************/
			case SWITCH_TO_END:
				System.out.println(command);

				switchCurrentLineWith(list.size());


				//an action has been performed and is no longer a current save
				saved = false;
				//an action has been performed and is added to history
				addToHistory();

				break;

				/*****************************************************************/
			case SWITCH_TO_NUM:
				System.out.println(command);				
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
				System.out.println(command);
				
				undo();

				//an action has been performed and is no longer a current save
				saved = false;

				break;

				/*****************************************************************/
			case REDO:
				System.out.println(command);
				
				redo();

				//an action has been performed and is no longer a current save
				saved = false;

				break;

				/*****************************************************************/
			default:
				System.out.println("default switch");

				break;
			}
		}

		catch (NullPointerException e ) {
			System.out.println("(Null command) Invalid command, try again...");
			e.printStackTrace();
		}

		catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("(Array bounds command) Invalid command, try again...");
		}

	}

	/*****************************************************************
	returns number of lines in the editor buffer
	 *****************************************************************/
	@Override
	public int getNbrLines() {
		return list.size();
	}

	/*****************************************************************
	returns the position of the current line
	 *****************************************************************/
	@Override
	public int getCurrentLineNbr() {
		return currentIndex;
	}

	/*****************************************************************
	returns the line at the given line number

	command -  d c
	 *****************************************************************/
	@Override
	public String getLine(int lineNbr) {

		if (lineNbr <= 0 || lineNbr > list.size())
			throw new IndexOutOfBoundsException();


		return list.get(lineNbr - 1);
	}

	/*****************************************************************
	returns an array of lines from beginPos to endPos

	command - d, d #
	 *****************************************************************/
	@Override
	public String[] getLines(int beginPos, int endPos) {
		//checks the first index.
		if (beginPos < 1 || beginPos > list.size())
			throw new IndexOutOfBoundsException();

		//checks the second index.
		if (endPos < 1 || endPos > list.size())
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
				data[i] = list.get(i);
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
	 *****************************************************************/
	@Override
	public void insertBefore(String line) {
		//when the line indicator is at the beginning

		if (currentIndex <= 1) {
			list.add(line);
		}
		else {
			try{
				list.add(currentIndex - 1, line);
			} catch (IndexOutOfBoundsException e) {
				System.out.println("Insert Before Index out of bounds");
			}
		}

	}

	/*****************************************************************
	inserts line after the current line

	command - i <>
	 *****************************************************************/
	@Override
	public void insertAfter(String line) {
		//when the line indicator is at the beginning
		if (currentIndex == 0) {
			list.add(line);
		}
		else if (currentIndex == list.size()) {
			list.addAtEnd(line);
		}
		else {
			try{
				list.add(currentIndex - 1, line);
			} catch (IndexOutOfBoundsException e) {
				System.out.println("Insert After Index out of bounds");
				e.printStackTrace();
			} catch (NullPointerException e) {
				System.out.println("Insert after null pointer");
				e.printStackTrace();
			}
		}
	}

	/*****************************************************************
	inserts line as the last line

	command - e <>
	 *****************************************************************/
	@Override
	public void insertEnd(String line) {
		list.addAtEnd(line);
	}

	/*****************************************************************
	moves current line indicator up nbrOfPositions and returns new position

	command - u, u #
	 *****************************************************************/
	@Override
	public void moveUp(int nbrOfPositions) {

		if (currentIndex > 1)
			currentIndex -= nbrOfPositions;

		if (currentIndex < 1)
			currentIndex = 1;
	}

	/*****************************************************************
	moves current line indicator down nbrOfPositions and returns new position

	command - m, m #
	 *****************************************************************/
	@Override
	public void moveDown(int nbrOfPositions) {

		if (currentIndex < list.size())
			currentIndex += nbrOfPositions;

		if (currentIndex > list.size())
			currentIndex = list.size();
	}

	/*****************************************************************
	removes given number of lines from the current line

	command - r, r #
	 *****************************************************************/
	@Override
	public void remove(int nbrLinesToRemove) {
		try {
			for ( int i = nbrLinesToRemove; i > 0; i --)
				list.remove(currentIndex + i - 2);
		}
		catch (IndexOutOfBoundsException e ) {
			//don't do anything. this means there are no more left
		}

	}

	/*****************************************************************
	removes all lines in the buffer

	command - c
	 *****************************************************************/
	@Override
	public void clear() {
		list.clear();
		currentIndex = 0;

	}

	/*****************************************************************
	saves contents to the file with filename

	command - s <>
	 *****************************************************************/
	@Override
	public void save(String filename) throws IOException {
		try {
			File bbfile = new File (filename + ".txt");
			PrintWriter myoutput = new PrintWriter (bbfile);

			String[] lines = getLines(1,getNbrLines());
			for (int i = 1; i <= lines.length; i ++) {
				myoutput.println(lines[i-1]);
			}

			myoutput.close(); 
		}
		catch (Exception e)
		{
			System.out.println ("Invalid entry (save)");
		}

	}

	/*****************************************************************
	clears the current contents and loads the contents of the specified file

	command - l <>
	 *****************************************************************/
	@Override
	public void load(String filename) throws IOException {
		//find the file
		Scanner scan;
		try {
			scan = new Scanner(new File (filename + ".txt"));

			list.add(scan.nextLine());

			while (scan.hasNextLine()) {
				list.addAtEnd(scan.nextLine());
			}
			currentIndex = 1;
		} 
		catch (Exception e) {
			System.out.println("Invalid entry (load)");
		}
	}

	/*****************************************************************
	finds and replaces all occurrences of str1 with str2 and return the count

	command - fr <> <>
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
	moves the current line to the lineNbr

	command - mc $, mc #
	 *****************************************************************/
	@Override
	public void moveCurrentLineTo(int lineNbr) {
		try{
			String movedItem = list.remove(currentIndex - 1);

			//need to compensate for the changed index when removed
			if (lineNbr > currentIndex)
				list.add(lineNbr - 2, movedItem);

			//changed index does not effect this
			else
				list.add(lineNbr - 1, movedItem);
		} 

		catch (IndexOutOfBoundsException e) {
			System.out.println("Move to Index out of bounds");
		}
	}

	/*****************************************************************
	switches the current line with the lineNbr

	command - sw $, sw #
	 *****************************************************************/
	@Override
	public void switchCurrentLineWith(int lineNbr) {
		try{
			String currentMove;
			String lineMove;

			lineMove = list.get(lineNbr - 1);
			currentMove = list.get(currentIndex - 1);

			list.setData(currentIndex - 1, lineMove);
			list.setData(lineNbr - 1, currentMove);

		} 

		catch (IndexOutOfBoundsException e) {
			System.out.println("Move to Index out of bounds");
		}


	}

	/*****************************************************************
	undo the effects of the last edit operation

	command - ud
	 *****************************************************************/
	@Override
	public void undo() {
		
		if (historyIndex < history.size()-1) {
		historyIndex ++;
		list = history.get(historyIndex);
		}
		
		
		if (currentIndex > list.size())
			currentIndex = list.size();

	}
	
	/*****************************************************************
	undo the effects of the last edit operation

	command - ud
	 *****************************************************************/
	
	public void redo() {
		
		if (historyIndex > 0) {
		historyIndex --;
		list = history.get(historyIndex);
		}
		
		if (currentIndex < 0)
			currentIndex = list.size();

	}

	/*****************************************************************
	Accesses the time machine to travel back into the past

	command - time machine
	 *****************************************************************/

	public void timeMachine () {
		//while 



	}

	/*****************************************************************
	Adds actions to history
	 *****************************************************************/
	@SuppressWarnings("unchecked")
	public void addToHistory() {

		//String saveData = "";
		
		//for (int i = 1; i <= list.size(); i ++) {
			
			
			
		//}

		//current version is in the past.

		if (historyIndex == 0) {
			try {
				history.add( (MainLinkList<String>) list.clone());
			} catch (CloneNotSupportedException e) {
				System.out.println("not cloneing");
				e.printStackTrace();
			}
			System.out.println("index = 0");
		}
		else {
			try{
				System.out.println("if in the middle of the list");
				history.add(historyIndex, (MainLinkList<String>) list.clone());
				historyIndex++;

				history.removeTop(historyIndex);


			} catch (IndexOutOfBoundsException e) {
				System.out.println("Insert After Index out of bounds");
				e.printStackTrace();
			} catch (NullPointerException e) {
				System.out.println("Insert after null pointer");
				e.printStackTrace();
			} catch (CloneNotSupportedException e) {
				System.out.println("not cloneing");
				e.printStackTrace();
			}
		}






	}


}
