package lib_items;

import adt_files.BinaryTree;
import adt_files.TreeNode;

public class PatronTree extends BinaryTree<Patron> 
{

	public PatronTree() 
	{
		// TODO Auto-generated constructor stub
	}
	
	public Patron searchByID(int iD)
	{
		return searchByID(root, iD);
	}
	
	private Patron searchByID(TreeNode<Patron> r, int iD) //searching
	{
		if (r != null)
		{
			if (r.getData() != null)
			{
				if (r.getData().getID() == iD)
				{
					return r.getData();
				}
				Patron left = null;
				Patron right = null;
				if (r.hasLeft())
				{
					left = searchByID(r.getLeft(), iD);
					if (left != null)
					{
						return left;
					}
				}
				if (r.hasRight())
				{
					right = searchByID(r.getRight(), iD);
					if (right != null)
					{
						return right;
					}
				}
			}
		}
		return null;
	}
	
	public Patron searchByName(String name)
	{
		return searchByName(root, name);
	}
	
	private Patron searchByName(TreeNode<Patron> r, String name) //searching using in order
	{
		if (r != null)
		{
			if (r.getData() != null)
			{
				int comparison = String.CASE_INSENSITIVE_ORDER.compare(name, r.getData().getFamilyName());
				if (r.getLeft() != null && comparison < 0)
				{
					return searchByName(r.getLeft(), name);
				}
				if (comparison == 0)
				{
					return r.getData();
				}
				if (r.getRight() != null && comparison > 0)
				{
					return searchByName(r.getRight(), name);
				}
			}
		}
		return null;
	}
	
	public String filePreOrder() //returns string to write to file
	{
		String data = "";
		if (root != null)
		{
			data = filePreOrder(root, data);
		}
		return data;
	}
	
	private String filePreOrder(TreeNode<Patron> r, String d) //needs to be in preorder in order to put back in tree
	{
		String temp = "";
		d += r.getData().getID() + "/" + r.getData().getFamilyName() + "/" + r.getData().getFirstName() + "\r\n";
		if (r.getLeft() != null)
		{
			d+= filePreOrder(r.getLeft(), temp);
		}
		if (r.getRight() != null)
		{
			d+= filePreOrder(r.getRight(), temp);
		}
		return d;
	}
	
	public String checkoutFilePreOrder() //returns string to write to file
	{
		String data = "";
		if (root != null)
		{
			data = checkoutFilePreOrder(root, data);
		}
		return data;
	}
	
	private String checkoutFilePreOrder(TreeNode<Patron> r, String d) //needs to be in preorder in order to put back in tree
	{
		String temp = "";
		d += r.getData().checkoutToString();
		if (r.getLeft() != null)
		{
			d+= checkoutFilePreOrder(r.getLeft(), temp);
		}
		if (r.getRight() != null)
		{
			d+= checkoutFilePreOrder(r.getRight(), temp);
		}
		return d;
	}
}
