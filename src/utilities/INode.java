/*****************************************************************
Interface for the projects Node. Given to me from the professor.

@author Professor
@version Fall 2012
 *****************************************************************/
package utilities;

public interface INode<V> {

	/* returns the data stored in this node */
	V getData();

	/* sets the data to the argument value */
	void setData(V data); 

	/* returns the value of the next field of this node */
	INode<V> getNext();

	/* sets the next field of this node to the argument value */
	void setNext(INode<V> next);

}
