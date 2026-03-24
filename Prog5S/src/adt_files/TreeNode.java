package adt_files;

public class TreeNode<T> 
{
	private TreeNode<T> left;
	private TreeNode<T> right;
	private T data;
	
	public TreeNode(T data)
	{
		this.data = data;	//if wanted to make TreeNode extend Node class then just do super(data);	
	}
	
	public void setData(T d)
	{
		data = d;
	}
	
	public T getData()
	{
		return this.data;
	}
	
	public void setLeft(T leftData)
	{
		if (left != null)
		{
			left.setData(leftData);
		}
		else
		{
			left = new TreeNode<T>(leftData);
		}
	}
	
	public void setRight(T rightData)
	{
		if (right != null)
		{
			right.setData(rightData);
		}
		else
		{
			right = new TreeNode<T>(rightData);
		}
	}
	
	public TreeNode<T> getLeft()
	{
		return this.left;
	}
	
	public TreeNode<T> getRight()
	{
		return this.right;
	}
	
	public boolean hasLeft()
	{
		return this.left != null;
	}
	
	public boolean hasRight()
	{
		return this.right != null;
	}

}
