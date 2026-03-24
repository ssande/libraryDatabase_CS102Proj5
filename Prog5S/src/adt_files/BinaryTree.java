package adt_files;

public class BinaryTree<T extends Comparable<T>>
{
	protected TreeNode<T> root; //protected to give access to sub classes
	
	public void insert(T data)
	{
		root = insert(root, data);
	}
	
	private TreeNode<T> insert(TreeNode<T> r, T data)
	{
		if (r == null)
		{
			r = new TreeNode<T>(data);
			return r;
		}
		
		if (data.compareTo(r.getData()) <= 0)
		{
			if (r.hasLeft())
			{
				insert(r.getLeft(),data);
			}
			else
				r.setLeft(data);
		}
		
		else
		{
			if (r.hasRight())
			{
				insert(r.getRight(),data);
			}
			else
				r.setRight(data);
		}
			
		return r;
	}
	
	public void removeAll()
	{
		root = null;
	}
	
	public String printInOrder()
	{
		if (root != null)
		{
			String fullList = inOrder(root,"");
			return fullList;
		}
		return "Unable to print. The database is empty.";
	}
	
	private String inOrder(TreeNode<T> r, String list)
	{
		String left = "", right = "";
		if (r.getLeft() != null)
		{
			//list+=inOrder(r.getLeft(), list);
			left = inOrder(r.getLeft(),list);
		}
		String center =r.getData() + " ";
		if (r.getRight() != null)
		{
			//list+=inOrder(r.getRight(), list);
			right = inOrder(r.getRight(),list);
		}
		list = left + center + right;
		return list;
	}
}
