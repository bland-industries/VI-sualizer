/*****************************************************************
Double link list with the nodes containing next and previous. 

@author Thomas Verstraete
@version Fall 2012
 *****************************************************************/
package main;

public class DLinkList<E> implements Cloneable{

	/**The beginning node of the list*/
	private DNode<E> head;

	/**The ending node of the list*/
	private DNode<E> tail;

	/**The node to perform actions on*/
	private DNode<E> current;

	/**Index of the current node to access*/
	private int currentIndex;

	/*****************************************************************
	Constructor sets the top, tail and current to null.
	 *****************************************************************/
	public DLinkList () {
		head = null;
		tail = null;
		current = null;
		currentIndex = 0;
	}

	/*****************************************************************
	Adds a node to the top of the list.

	@param element Data type of the node to add
	 *****************************************************************/
	public void addToHead(E element) {
		//checks for an empty list and if true adds the new node.
		if (!addToEmpty(element)) { //else
			//adds to head and shifts the head.
			head = new DNode<E> (null, head, element);

			//adjust the currentIndex to keep in line.
			if (currentIndex != 0)
				currentIndex ++;
		}
	}

	/*****************************************************************
	Adds a node to the end of the list.

	@param element Data type of the node to add
	 *****************************************************************/
	public void addToTail(E element) {
		//checks for an empty list and if true adds the new node.
		if (!addToEmpty(element)) { //else
			//adds to the end of the list and shifts tail.
			tail = new DNode<E> (tail, tail.getNextNode(), element);
		}
	}
	
	/*****************************************************************
	Adds a node to an empty list

	@param element Data type of the node to add
	@return boolean if the list was empty and the node was added.
	 *****************************************************************/
	private boolean addToEmpty (E element) {
		//checks if the list is empty and then adds to the list
		if (current == null) {
			head = tail = current = new DNode<E> (null, null, element);
			currentIndex = 0;
			return true;
		}
		else {
			return false;
		}
	}

	/*****************************************************************
	Adds a new node to the given index with the given data. 

	@param index int location to add the node to
	@param element Data type of the node to add
	 *****************************************************************/
	public void add(int index, E element) {

		//if the index to add is at the beginning of the list.
		if (index == 0)
			addToHead(element);

		//Add the new node anywhere in the list below the top.
		else {

			//gets the node at the index location.
			DNode<E> temp = getNode(index);

			//sets the new node at the index passed.
			new DNode<E> (temp.getPrevNode(), temp, element);

			//if the item was added to the end of the list.
			if (tail == temp)
				tail = temp.getNextNode();
		}
		
		//need to shift the number of currentIndex if inserting before
		if (currentIndex >= index)
			currentIndex ++;
	}

	/*****************************************************************
	Adds a new node above the current node. Does not move the current 
	node

	@param element Data type of the node to add
	 *****************************************************************/
	public void addAboveCurrent(E element) {
		//checks for an empty list and if true adds the new node.
		if (!addToEmpty(element)) { //else
			
			new DNode<E> (current.getPrevNode(), current, element);
			currentIndex ++; //shifts index to compensate for the add

			//if the new node was added before the head, move the head
			if (head.getPrevNode() != null)
				head = head.getPrevNode();
		}
	}

	/*****************************************************************
	Adds a new node after the current node. Does not move the current 
	node

	@param element Data type of the node to add
	 *****************************************************************/
	public void addBelowCurrent(E element) {

		//checks for an empty list and if true adds the new node.
		if (!addToEmpty(element)) { //else
			
			new DNode<E> (current, current.getNextNode(), element);

			//if the new node was added after the tail
			if (tail.getNextNode() != null)
				tail = tail.getNextNode();
		}
	}

	/*****************************************************************
	Removes the node at the given index and returns the information 
	contained within. 

	@param index of the node to remove
	@return the data contain in the removed node
	 *****************************************************************/
	public E remove(int index) {
		//if the index to remove is the current node
		if (currentIndex == index) {
			return removeCurrent();
		}
		
		//if the node to remove is not the current node
		else {
			DNode<E> temp = getNode(index);

			//shift head down before deletion
			if (temp.equals(head))
				head = head.getNextNode();
			
			//shift tail up before deletion
			if (temp.equals(tail))
				tail = tail.getPrevNode();
			
			//node removes itself
			temp.seppuku();

			//adjusts the index as needed
			if (currentIndex > index)
				currentIndex --;

			//returns the data from the removed node
			return temp.getData();
		}
	}

	/*****************************************************************
	Removes the current node from the list. If the removed current 
	is not at the end the index number will not change. If it is at
	the end then the new end index is the current index

	@return the data contain in the removed node
	 *****************************************************************/
	public E removeCurrent() {
		if (current == null)
			return null;
		
		//collects the data upon removal
		E returnData = current.seppuku();
		
		//if the list had only one item
		if (tail.equals(head))
			clear();
		
		//if the tail was removed
		else if (current.equals(tail)) {
			tail = current = tail.getPrevNode();
			if (currentIndex > 0)
				currentIndex --;
		}
		
		//if the head was removed
		else if (current.equals(head)) {
			current = current.getNextNode();
			head = current;
		}
		
		//if an item somewhere in the middle was removed
		else {
			current = current.getNextNode();
		}
		
		return returnData;
	}

	/*****************************************************************
	Gets the current node's data
	
	@return the data desired from the current index
	 *****************************************************************/
	public E get() {
		return current.getData();
	}

	/*****************************************************************
	Gets the data at the given index

	@param index of the given information
	@return the data desired at the index
	 *****************************************************************/
	public E get(int index) {
		return getNode(index).getData();
	}
	
	/*****************************************************************
	Recursively gets the data at the given index

	@param index of the given information
	@return the data desired at the index
	 *****************************************************************/
	public E getR(int index) {
		return head.getR(index);
	}

	/*****************************************************************
	Gets the node at the given index

	@param index of the given information
	@return the node desired at the index
	 *****************************************************************/
	public DNode<E> getNode(int index) {
		//checks the if within the possible indexes.
		if (index < 0)
			throw new IndexOutOfBoundsException("Must be a positive " +
					"number");


		DNode<E> temp = head;

		/*Moves through the list until it stops on the index 
		 * or moves out of the list and is out of bounds*/
		try {
			/*Moves down the list until temp is at the given index*/
			for(int i = 0; i <= index; i ++) {
				if (i == index)
					return temp;
				else 
					temp = (DNode<E>) temp.getNextNode();
			}
		}

		//if the temp moves past the last index
		catch (NullPointerException e) {
			throw new IndexOutOfBoundsException("Number to high");
		}

		return temp;
	}

	/*****************************************************************
	Checks to see if the list is empty
	
	@return true if the list is empty, false if not.
	 *****************************************************************/
	public boolean isEmpty() {
		return (current == null);
	}

	/*****************************************************************
	Gets the index for the current node.

	@return index integer of the current node
	 *****************************************************************/
	public int getCurrentIndex() {
		return currentIndex;
	}
	
	/*****************************************************************
	Gets the index for the current node.

	@return index integer of the current node
	 *****************************************************************/
	public int matchCurrentIndex() {

		DNode<E> temp = head;
		int count = 0;

		/*Moves through the list until it finds the current node 
		 * or moves out of the list and is out of bounds*/
		try {
			for(; temp != current; count ++) {
				temp = temp.getNextNode();
			}
		}

		//if the temp moves past the last index
		catch (NullPointerException e) {
			throw new IndexOutOfBoundsException("Current not in list2");
		}

		return count;
	}

	/*****************************************************************
	Sets the current node to a given index.

	@return index integer to set the current node
	 *****************************************************************/
	public void setCurrent (int index) {
		//sets the node
		current = getNode(index);
		//sets the index
		currentIndex = index;
	}
	
	/*****************************************************************
	Sets the current node location to the currentIndex value.
	 *****************************************************************/
	public void setCurrentIndex () {
		
		DNode<E> temp = head;
		int count = 0;

		//moves down the list counting each step
		while (!temp.equals(current)) {
			temp = (DNode<E>) temp.getNextNode();
			count ++;
		}
		
		currentIndex = count;
	}
	
	/*****************************************************************
	Sets the current node and index to the head.
	 *****************************************************************/
	public void setCurrentToHead () {
		current = head;

		currentIndex = 0;
	}
	
	/*****************************************************************
	Sets the current node and index to the tail.
	 *****************************************************************/
	public void setCurrentToTail () {
		current = tail;

		currentIndex = size() - 1;
	}


	/*****************************************************************
	Moves the current node up on the list the given number of spots

	@param moveNum int value of spots to move the current node
	 *****************************************************************/
	public void moveCurrentUp (int moveNum) {
		
		//count down the number of moves to make
		for (; moveNum > 0; moveNum --) {
			
			//move until no more space to move
			if(current.getPrevNode() != null) {
				
				//shifts currents
				current = current.getPrevNode();
				currentIndex --;
			}
		}
	}

	/*****************************************************************
	Moves the current node down on the list the given number of spots

	@param moveNum int value of spots to move the current node
	 *****************************************************************/
	public void moveCurrentDown(int moveNum) {
		
		//count down the number of moves to make
		for (; moveNum > 0; moveNum --) {
			
			//move until no more space to move
			if(current.getNextNode() != null) {
				
				//shifts currents
				current = current.getNextNode();
				currentIndex ++;
			}
		}
	}

	/*****************************************************************
	Gets the size of the link list

	@return int size of the list (+1 of the last index)
	 *****************************************************************/
	public int size() {
		
		DNode<E> temp = head;
		int count = 0;

		//moves down the list counting each step, if there is a list
		while (temp != null) {
			temp = (DNode<E>) temp.getNextNode();
			count ++;
		}

		return count;
	}

	/*****************************************************************
	This is a recursive way of check the size of the Link List.

	@return int size of the list (+1 of the last index)
	 *****************************************************************/
	public int sizeR() {
		if (head == null)
			return 0;

		else return head.sizeR();
	}

	/*****************************************************************
	Clears the link list of all information
	 *****************************************************************/
	public void clear() {
		head = null;
		tail = null;
		current = null;
		currentIndex = 0;

	}

	/*****************************************************************
	Sets the data at the given index

	@param index location to add data.
	@param data to store in node.
	 *****************************************************************/
	public void setData(int index, E data) {

		DNode<E> temp = getNode(index);
		
		temp.setData(data);
	}

	/*****************************************************************
	Removes everything on the list before the current node
	 *****************************************************************/
	public void removeAllBefore() {
		if (current != null) {
			head = current;
			current.setPrevNode(null);
			currentIndex = 0;
		}
	}

	/*****************************************************************
	Removes everything on the list after the current node
	 *****************************************************************/
	public void removeAllAfter() {
		if (current != null) {
			tail = current;
			current.setNextNode(null);
		}
	}
	
	/*****************************************************************
	Checks to see if the given integer is within the bounds of the list
	
	@param value int value to check for
	@return true if value is in the bounds, false otherwise
	 *****************************************************************/
	public boolean inBounds (int value) {
		return (value >= 0 && value <= size()-1);
	}

	/*****************************************************************
	This is a terrible way of making a copy of the list.
	 *****************************************************************/
	protected Object clone() throws CloneNotSupportedException {
		DLinkList<E> copy = new DLinkList<E>();

		DNode<E> temp = head;
		while (temp != null) {
			copy.addToTail(temp.getData());

			temp = (DNode<E>) temp.getNextNode();
		}

		copy.setCurrent(currentIndex);
		
		return (Object) copy;
	}
}