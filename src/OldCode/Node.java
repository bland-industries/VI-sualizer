/*****************************************************************


@author Thomas Verstraete
@version Fall 2012
 *****************************************************************/

package OldCode;

import utilities.INode;

public class Node<V> implements INode<V>{

	/***/
	V data;

	/***/
	Node<V> nextNode;

	/*****************************************************************
	
	 *****************************************************************/
	public Node() {
		super();
	}
	
	/*****************************************************************
	
	 *****************************************************************/
	public Node(V data) {
		super();
		this.data = data;
	}

	/*****************************************************************
	
	 *****************************************************************/
	public Node(V data, Node<V> nextNode) {
		super();
		this.data = data;
		this.nextNode = nextNode;
	}

	/*****************************************************************
	Returns the data contained in this node.
	
	@return String data contained in this node.
	 *****************************************************************/
	@Override
	public V getData() {
		return data;
	}

	/*****************************************************************
	
	 *****************************************************************/
	@Override
	public void setData(V data)  {
		this.data = data;
	}

	/*****************************************************************

	 *****************************************************************/
	@Override
	public INode<V> getNext() {
		return nextNode;
	}

	/*****************************************************************

	 *****************************************************************/
	@Override
	public void setNext(INode<V> next) {
		nextNode = (Node<V>) next;
	}
}