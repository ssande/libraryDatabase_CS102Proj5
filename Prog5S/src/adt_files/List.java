package adt_files;

interface List<T> {
	
  public void append(T data);
  
  public void prepend(T data);
  
  public void addAt(int pos, T data);
  
  public void remove(T data);
  
  public boolean check(T data);
  
  public int getSize();
}
