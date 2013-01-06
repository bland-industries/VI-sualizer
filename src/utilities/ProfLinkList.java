package utilities;

import OldCode.Node;

public class ProfLinkList {

	private Node<String> top;

	/*****************************************************************

	 *****************************************************************/
	public ProfLinkList() {
		top = null;
	}

	/*****************************************************************

	 *****************************************************************/
	public void addAtTop (String s) {
		Node<String> temp;
		temp = new Node<String>();
		temp.setData(s);
		temp.setNext(top);
		top = temp;
	}

	/*****************************************************************

	 *****************************************************************/
	public void display () {
		Node<String> temp = top;
		while (temp != null) {
			System.out.println (temp.getData());
			temp = (Node<String>) temp.getNext();
		}
	}

	/*****************************************************************

	 *****************************************************************/
	public void addAtEnd(String s) {

		Node<String> temp = top;
		while (temp.getNext() != null) {
			temp = (Node<String>) temp.getNext();
		}

		Node<String> newNode = new Node<String>();
		newNode.setData(s);

		temp.setNext(newNode);

		// incomplete.
		//temp.setData(s);

	}


	/*****************************************************************

	 *****************************************************************/
	public String get(int index) {

		Node<String> temp = top;
		for(int i = 0; i < index; i ++) {
			temp = (Node<String>) temp.getNext();
		}
		return temp.getData();
	}

	


	/*****************************************************************

	 *****************************************************************/
//	public static void main (String[] args){
//		MainLinkList<String> list = new MainLinkList<String>();
//
//		list.add("1");
//		list.add("2");
//		list.add("3");
//		list.add("4");
//		list.add("5");
//		list.add("6");
//		list.add("7");
//		list.add("8");
//		list.add("9");
//
//
//		//list.display();
//
//		//		System.out.println();
//		//		list.addAtEnd("10");
//		//		list.display();
//
//		//		System.out.println();
//		//		System.out.println(list.get(2));
//
//		//		System.out.println();
//		//		System.out.println("The list size: " + list.size());
//
//		//		System.out.println();
//		//		System.out.println(list.remove(5));
//
//		//		list.add(5, "10");
//		//		
//		//		System.out.println();
//		//		list.display();
//
//
//		
//	}


}



