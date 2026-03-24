package adt_files;

public class LinkedList<T> implements List<T>
{

	private Node<T> head;

	public LinkedList()
	{
		this.head = null;
	}
	
	public LinkedList(T data)
	{
		this.head = new Node<T>(data);
	}
	
	@Override
	public void append(T data) 
	{
		if (head == null)
		{
			head = new Node<T>(data);
			return;
		}
		
		Node<T> current = head;
		while(current.getNext() !=null)
		{
			current = current.getNext();
		}
		current.setNext(new Node<T>(data));
	}

	@Override
	public void prepend(T data) 
	{
		if (head == null)
		{
			head = new Node<T>(data);
			return;
		}
		head = new Node<T>(data,head);
	}

	@Override
	public void addAt(int pos, T data) 
	{
		if (pos == this.getSize())
		{
			this.append(data);
			return;
		}
		Node<T>  current = head;
		Node<T> previous = null;
		if (current == null && pos != 0) //if list is empty and pos adding to is not 0
		{
			throw new ArrayIndexOutOfBoundsException("There are no nodes in the list.");
		}
		else
		{
			for (int i = 0; i < pos; i++)
			{
				if (current.getNext() == null)
				{
					throw new ArrayIndexOutOfBoundsException("There are less nodes than the specified position.");
				}
				previous = current;
				current = current.getNext();
			}
		}
		Node<T> newNode = new Node<T>(data, current);
		if (previous == null)
		{
			head = newNode;
		}
		else
		{
			previous.setNext(newNode);
		}
	}

	@Override
	public void remove(T data) 
	{
		Node<T> current = head;
		Node<T> previous = null;
		if (current == null)
		{
			throw new ArrayIndexOutOfBoundsException("There are no nodes in the list. None to remove.");
		}
		else
		{ 
			for (int i = 0; i < getSize(); i++)
			{
				if (current.getData() == data)
				{
					previous.setNext(current.getNext());
					return;
				}
				previous = current;
				current = current.getNext();
			}
			System.out.println("No node with given data present in the list.");
		}
	}

	@Override
	public boolean check(T data) 
	{
		Node<T> current = head;
		while (current != null)
		{
			if (current.getData().equals(data))
			{
				return true;
			}
			current = current.getNext();
		}
		return false;
	}

	@Override
	public int getSize() 
	{
		Node<T> current = head;
		int counter = 0;
		while (current != null)
		{
			counter++;
			current = current.getNext();
		}
		return counter;
	}
	
	public T getData(int index)
	{
		Node<T> current = head;
		if (current == null)
		{
			return null;
		}
		else
		{
			for (int i = 0; i < index; i++)
			{
				if (current.getNext() == null)
				{
					throw new ArrayIndexOutOfBoundsException("There are less nodes than the specified position.");
				}
				current = current.getNext();
			}
		}
		return current.getData();
	}
	
	public Node<T> getNode(int index) throws ArrayIndexOutOfBoundsException
	{
		Node<T> current = head;
		if (current == null)
		{
			return null;
		}
		else
		{
			for (int i = 0; i < index; i++)
			{
				if (current.getNext() == null)
				{
					throw new ArrayIndexOutOfBoundsException("There are less nodes than the specified position.");
				}
				current = current.getNext();
			}
		}
		return current;
	}
	
	public void swapElements(int index1, int index2)
	{
		Node<T> node1= getNode(index1);
		Node<T> node2 = getNode(index2);
		T data1 = node1.getData();
		node1.setData(node2.getData());
		node2.setData(data1);
	}

	public String toString() {
		String returnString = "Linked List of Size: " + this.getSize() + "\n";
		
		for(int i = 0; i < this.getSize(); i++) {
			returnString += this.getData(i).toString() + ", ";
		}
		
		return returnString;
	}
}
