/*****************************************************************


@author Thomas Verstraete
@version Fall 2012
 *****************************************************************/
package OldCode;

import utilities.ILinkedList;

public class MainLinkList<V> implements ILinkedList<V>, Cloneable{

	/**The beginning of the list.*/
	Node<V> top;

	/*****************************************************************
	Constructor sets the top to null.
	 *****************************************************************/
	public MainLinkList () {
		top = null;
	}


	/*****************************************************************
	Adds new node to the top of the linked list.

	@param element String to add to list.
	 *****************************************************************/
	@Override
	public void add(V element) {
		Node<V> temp = new Node<V>(element, top);
		top = temp;
	}

	/*****************************************************************
	Adds a new note with the given data at the given index.

	@param index int location in the list to add new.
	@param element String to add to list.
	 *****************************************************************/
	@Override
	public void add(int index, V element) {

		//checks the if within the possible indexes.
		if (index < 0 || index > size())
			throw new IndexOutOfBoundsException();

		//adds the new node to the top of the list
		if (index == 0) {
			Node<V> temp = new Node<V>(element, top);
			top = temp;
		}

		//adds the new node to the given index
		else {
			Node<V> temp = top;
			//Node<V> beforeAdd = null;
			Node<V> addNode = new Node<V>();
			addNode.setData(element);

			/*move down the list till the temp reaches the node just 
				just before the index*/
			for(int i = 0; i < index; i ++) {
				//beforeAdd = temp;
				temp = (Node<V>) temp.getNext();
			}

			addNode.setNext(temp.getNext());
			temp.setNext(addNode);
			//beforeAdd.setNext(addNode);
		}
	}

	/*****************************************************************

	 *****************************************************************/
	public void addAtEnd(V s) {

		if (top == null) {
			Node<V> temp = new Node<V>(s, top);
			top = temp;
		}

		else {
			Node<V> temp = top;
			while (temp.getNext() != null) {
				temp = (Node<V>) temp.getNext();
			}

			Node<V> newNode = new Node<V>(s, null);

			temp.setNext(newNode);
		}
	}

	/*****************************************************************
	Removes and gets the data at the given index.

	@param index int location of the data.
	@return String of the data.
	 *****************************************************************/
	@Override
	public V remove(int index) {

		//TODO check to see if there is a null list
		//TODO check to see if param index is 0 (might be covered by code)

		//checks the if within the possible indexes.
		if (index < 0 || index > size())
			throw new IndexOutOfBoundsException();

		if (index == 0) {
			Node<V> removed = top;
			top = (Node<V>) removed.getNext();
			return removed.getData(); 
		}


		Node<V> removed = top;
		Node<V> beforeRemoved = top;

		/*Moves down the list until temp is the node to remove*/
		for(int i = 0; i < index; i ++) {
			beforeRemoved = removed;
			removed = (Node<V>) removed.getNext();
		}

		beforeRemoved.setNext(removed.getNext());

		return removed.getData();
	}

	/*****************************************************************
	Gets the data at the given index.

	@param index int location of the data.
	@return String of the data.
	 *****************************************************************/
	@Override
	public V get(int index) {

		//checks the if within the possible indexes.
		if (index < 0 || index >= size())
			throw new IndexOutOfBoundsException("attempted indes: "+ index);

		Node<V> temp = top;

		/*Moves down the list until temp is the node to get*/
		for(int i = 0; i < index; i ++) {
			temp = (Node<V>) temp.getNext();
		}

		return temp.getData();
	}

	/*****************************************************************
	Checks if the list is empty.

	@return boolean true if the linked list is empty
	 *****************************************************************/
	@Override
	public boolean isEmpty() {

		if (top == null)
			return true;
		else
			return false;
	}

	/*****************************************************************
	Returns the size of the linked list

	@return int size of the list.
	 *****************************************************************/
	@Override
	public int size() {
		if (top == null)
			return 0;

		Node<V> temp = top;
		int count = 0;

		//moves down the list counting each step
		while (temp != null) {
			temp = (Node<V>) temp.getNext();
			count ++;
		}

		return count;
	}

	/*****************************************************************
	Clears the Linked List.
	 *****************************************************************/
	@Override
	public void clear() {
		top = null;

	}

	/*****************************************************************
	Displays all the String data for each of the Elements.
	 *****************************************************************/
	public void display () {

		Node<V> temp = top;

		while (temp != null) {
			System.out.println (temp.getData());
			temp = (Node<V>) temp.getNext();
		}
	}

	/*****************************************************************
	Gets the node at the given index.

	@param index int location of the data.
	@return Node at the given index.
	 *****************************************************************/
	public void setData(int index, V data) {

		//checks the if within the possible indexes.
		if (index < 0 || index >= size())
			throw new IndexOutOfBoundsException();

		Node<V> temp = top;

		/*Moves down the list until temp is the node to get*/
		for(int i = 0; i < index; i ++) {
			temp = (Node<V>) temp.getNext();
		}

		temp.setData(data);
	}

	/*****************************************************************

	 *****************************************************************/
	public void removeTop (int lastIndex) {

		Node<V> temp = top;

		for(int i = 0; i <= lastIndex; i ++) {
			temp = (Node<V>) temp.getNext();
		}

		top = temp;
	}

	protected Object clone() throws CloneNotSupportedException {
		MainLinkList<V> copy = new MainLinkList<V>();

		Node<V> temp = top;
		while (temp != null) {
			copy.addAtEnd(temp.getData());

			temp = (Node<V>) temp.getNext();
		}






		return (Object) copy;

	}
}
