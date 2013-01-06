/*****************************************************************
Node for a Linked list. This contains a link to the next node and
the previous node. Upan instantiation it will set its neighbors
so you don't have to.

@author Thomas Verstraete
@version Fall 2012
 *****************************************************************/
package main;

public class DNode<E> {

	/**This is the data this node contains*/
	private E data;

	/**The link to the next not in the list*/
	private DNode<E> nextNode;

	/**The link to the previous not in the list*/
	private DNode<E> prevNode;
	

	/*****************************************************************
	Blank Constructor with out any variable set.
	 *****************************************************************/
	public DNode () {}

	/*****************************************************************
	Constructor with all variable to be set with parameters. Will set
	its own neighbors.
	 *****************************************************************/
	public DNode (DNode<E> previous, DNode<E> next, E data) {
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
	public DNode<E> getNextNode() {
		return nextNode;
	}

	/*****************************************************************
	Sets the node next to this one in the list.
	
	@param nextNode DNode<E> node to be set next to this.
	 *****************************************************************/
	public void setNextNode(DNode<E> nextNode) {
		this.nextNode = nextNode;
	}

	/*****************************************************************
	Retrieve the node prior to this one in the list.
	
	Return DNode<E> node prior to this.
	 *****************************************************************/
	public DNode<E> getPrevNode() {
		return prevNode;
	}

	/*****************************************************************
	Sets the node prior to this one in the list.
	
	@param nextNode DNode<E> node to be set prior to this.
	 *****************************************************************/
	public void setPrevNode(DNode<E> prevNode) {
		this.prevNode = prevNode;
	}
	
	/*****************************************************************
	Removes itself from the list by setting its neighbors variables to 
	exclude this. Then returns the data contain within.
	
	@return E data stored.
	 *****************************************************************/
	public E seppuku() {
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
	public int sizeR() {
		if (nextNode == null)
			return 1;
		else
			return nextNode.sizeR() + 1;
		
	}
	
	/*****************************************************************
	Recursively gets the information contained
	
	@return int of the current counted size of the list.
	 *****************************************************************/
	public E getR(int counter) {
		if (counter == 0)
			return data;
		else if (nextNode == null)
			return null;
		else
			return nextNode.getR(counter - 1);
		
	}
	
	/*****************************************************************
	Recursively gets the node at the given index
	
	@return int of the current counted size of the list.
	 *****************************************************************/
	public DNode<E> getNodeR(int counter) {
		if (counter == 0)
			return this;
		else if (nextNode == null)
			return null;
		else
			return nextNode.getNodeR(counter - 1);
		
	}

}
