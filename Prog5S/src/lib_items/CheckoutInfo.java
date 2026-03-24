package lib_items;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.LocalDate;


class CheckoutInfo
{
  private long iSBN;
  private int patronID;
  private String date;
  private LocalDate tempDateObj;
  
  public int getID() //gets patron id
  {
    return this.patronID;
  }
  
  public long getISBN() //gets book isbn num
  {
   return this.iSBN;
  }
  
  public String getDate() //gets date in string format (used for welcome and whenever patron data is printed
  {
    return this.date;
  }
  
  public LocalDate getDateObj() //gets date object (used for determining fine)
  {
    return this.tempDateObj;
  }
  
  public void setID(int i) //sets patron id
  {
    this.patronID = i;
  }
  
  public void setISBN(long i) //sets isbn
  {
    this.iSBN = i;
  }
 
  public int compareISBN(CheckoutInfo i)
  {
	  if (i.getISBN() < this.getISBN())
	  {
		  return -1;
	  }
	  else if (i.getISBN() > this.getISBN())
	  {
		  return 1;
	  }
	  else
		  return 0;
  }
  
  public void setDate(String d) //sets date
  {
	  try {
	    String temp = String.valueOf(d); //in order to get d into correct format
	    String y = temp.substring(0,4);
	    String m = temp.substring(4,6);
	    String day = temp.substring(6,8);
	    d = m + "/" + day + "/" + y;
	    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("M/d/yyyy");
	    this.tempDateObj = LocalDate.parse(d, dateFormat);
	    String tempDate = dateFormat.format(tempDateObj);
	    this.date = tempDate;
	  }
	  catch(DateTimeParseException exc)
	  {
		  System.out.println("The date entered in was not of the correct format or was not valid. Cancelling operation...");
		  return;
	  }
  }
  
  public int compareDateAsc(CheckoutInfo check)
  {
	  return this.getDateObj().compareTo(check.getDateObj());
  }
  
  public int compareDateDesc(CheckoutInfo check)
  {
	  return check.getDateObj().compareTo(this.getDateObj());
  }
  
  public void print() //prints checkout info
  {
    System.out.println("        ISBN: " + this.iSBN);
    System.out.println("        Checkout Date: " + this.date);
  }
  
  public String toString() // for printing to console
  {
	  return "        ISBN: " + this.iSBN + "\n        Checkout Date: " + this.date + "\n";
  }
  
  public String toFileString() //for writing to file
  {
	  LocalDate tempDateObj = this.getDateObj();
	  String tempDate = tempDateObj.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
	  return this.patronID + "/" + this.iSBN + "/" + tempDate + "\r\n";
  }
}