/*****************************************************************
Interface for the projects LinkedList. Given to me from the
professor.

@author Professor
@version Fall 2012
 *****************************************************************/
package utilities;

public interface ILinkedList<V> {

    /* adds the given element to the end of the list */
    void add(V element);

    /* inserts the given element at the specified position in the list */
    void add(int index, V element);

    /* removes the element at the specified position in the list */
    V remove(int index);
    
    /* returns the element at the specified position in the list */
    V get(int index);

    /* returns true if the list is empty; false otherwise */
    boolean isEmpty();

    /* returns the number of elements in the list */
    int size();

    /* removes all the elements from the list */
    void clear();
}
