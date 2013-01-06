package main;

public class RNode<E> {
	/**This is the data this node contains*/
	private E data;

	/**The link to the next not in the list*/
	private RNode<E> nextNode;

	/**The link to the previous not in the list*/
	private RNode<E> prevNode;

	/*****************************************************************
	Blank Constructor with out any variable set.
	 *****************************************************************/
	public RNode () {
		prevNode = this;
		nextNode = this;
		this.data = null;
	}

	/*****************************************************************
	Constructor with all variable to be set with parameters. Will set
	its own neighbors.
	 *****************************************************************/
	public RNode (RNode<E> previous, RNode<E> next, E data) {
		prevNode = previous;
		nextNode = next;
		this.data = data;

		setNeighbors();
	}

	/*****************************************************************
	Sets the neighbors variables automatically from the established 
	links. 
	 *****************************************************************/
	private void setNeighbors() {
		if (prevNode != null)
			prevNode.setNextNode(this);
		
		if (nextNode != null)
			nextNode.setPrevNode(this);
	}

	/*****************************************************************
	Retrieve the data contained in this node.
	
	@return E data stored.
	 *****************************************************************/
	public E getData() {
		return data;
	}

	/*****************************************************************
	Sets the data to be stored in this node.
	
	@param data E to be stored.
	 *****************************************************************/
	public void setData(E data) {
		this.data = data;
	}

	/*****************************************************************
	Retrieve the node next to this one in the list.
	
	Return DNode<E> node next to this.
	 *****************************************************************/
	public RNode<E> getNextNode() {
		return nextNode;
	}

	/*****************************************************************
	Sets the node next to this one in the list.
	
	@param nextNode DNode<E> node to be set next to this.
	 *****************************************************************/
	public void setNextNode(RNode<E> nextNode) {
		this.nextNode = nextNode;
		if (prevNode == null)
			prevNode = nextNode;
	}

	/*****************************************************************
	Retrieve the node prior to this one in the list.
	
	Return DNode<E> node prior to this.
	 *****************************************************************/
	public RNode<E> getPrevNode() {
		return prevNode;
	}

	/*****************************************************************
	Sets the node prior to this one in the list.
	
	@param nextNode DNode<E> node to be set prior to this.
	 *****************************************************************/
	public void setPrevNode(RNode<E> prevNode) {
		this.prevNode = prevNode;
		if (nextNode == null)
			nextNode = prevNode;
	}
	
	/*****************************************************************
	Removes itself from the list by setting its neighbors variables to 
	exclude this. Then returns the data contain within.
	
	@return E data stored.
	 *****************************************************************/
	private E seppuku() {
		if (prevNode != null)
			prevNode.setNextNode(nextNode);
		
		if (nextNode != null)
			nextNode.setPrevNode(prevNode);
		
		return data;

	}
	/*****************************************************************
	Recursively counts the size of the list.
	
	@return int of the current counted size of the list.
	 *****************************************************************/
	public int size(RNode<E> start) {
		if (this.equals(start))
			return 1;
		else
			return nextNode.size(start) + 1;
		
	}
	
	/*****************************************************************
	Recursively gets the information contained
	
	@return int of the current counted size of the list.
	 *****************************************************************/
	public E get(int counter) {
		if (counter == 0)
			return data;
		else
			return nextNode.get(counter - 1);
		
	}
	
	/*****************************************************************
	Recursively gets the node at the given index
	
	@return int of the current counted size of the list.
	 *****************************************************************/
	public RNode<E> getNodeR(int counter) {
		if (counter == 0)
			return this;
		else if (nextNode == null)
			return null;
		else
			return nextNode.getNodeR(counter - 1);
		
	}
	
	/*****************************************************************
	Adds information before this node
	 *****************************************************************/
	public void addBefore (E data) {
		new RNode<E> (prevNode, this, data);
	}
	
	/*****************************************************************
	Adds information after this node
	 *****************************************************************/
	public void addAfter (E data) {
		new RNode<E> (this, nextNode, data);
	}
	
	/*****************************************************************
	Removes this node and returns the node neighbor based on the value
	of the parameter. A positive number returns the next, and negative
	number returns the previous.
	
	@param next boolean to determine the return node. true is for the 
		next node, false the previous one
	@return RNode<E> node user desired.
	 *****************************************************************/
	public RNode<E>	remove (boolean next) {
		seppuku();
		
		if (next)
			return nextNode;
		else
			return prevNode;
	}
	
	/*****************************************************************
	
	 *****************************************************************/
	public void remove(int removeNum) {
		try {
			if (removeNum > 0) {
				nextNode = nextNode.removeR(removeNum - 1, this);
			}
			else if (removeNum < 0) {
				prevNode = prevNode.removeR(removeNum + 1, this);
			}
		}
		catch (IndexOutOfBoundsException e) {
			System.out.println ("If you want to clear the board us \"c\"");
		}
	}
	
	/*****************************************************************
	
	 *****************************************************************/
	public RNode<E> removeR(int removeNum, RNode<E> start) {
		if (this.equals(start)) 
			throw new IndexOutOfBoundsException();
		else if(removeNum > 0) {
			nextNode.setPrevNode(start);
			return nextNode.removeR(removeNum - 1, start);
		}
		else if (removeNum < 0) {
			prevNode.setNextNode(start);
			return prevNode.removeR(removeNum + 1, start);	
		}
		//if removeNum counts zero
		else {
			return this;
		}
	}
	
	/*****************************************************************
	
	 *****************************************************************/
	public RNode<E> move (int move) {
		//move forward in the list
		if (move > 0) {
			if (nextNode == null)
				return null;
			else
				return nextNode.move(move - 1);
		}
		//move back in the list
		else if (move < 0) {
			if (prevNode == null)
				return null;
			else
				return prevNode.move(move + 1);
		}
		
		//if the count reaches 0
		else return this;
	}
	
	
	/*****************************************************************
	
	 *****************************************************************/
	public void printData(RNode<E> start) {
		if (!this.equals(start)) {
			System.out.println (data);
			nextNode.printData(start);
		}
	}
	
	/*****************************************************************
	
	 *****************************************************************/

	public static void main (String[] cheese) {
		
	}
	

}
