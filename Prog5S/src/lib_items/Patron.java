package lib_items;
import adt_files.LinkedList;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Patron implements Comparable<Patron>
{
  private LinkedList<CheckoutInfo> checkout = new LinkedList<CheckoutInfo>();
  private String familyName, firstName;
  private int iD;
  private double fine = 0.00;
  DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("M/d/YYYY.\n\n"); //formats date. eg. 10/10/2018.
  String dateNow = dateFormat.format(LocalDate.now());
  
  public void calculateFine()
  {
	  for (int j = 0; j < checkout.getSize(); j++) //goes through checkout list
      {
          CheckoutInfo tempInfo = checkout.getData(j);
          LocalDate date1 = LocalDate.now();
          LocalDate date2 = tempInfo.getDateObj();
          int days = (int) ChronoUnit.DAYS.between(date2, date1); //gets number of days between two dates
          if (days > 90) //if executes means book is overdue
          {
            double f = (days-90) *.25; //determine fine value
            this.setFine(f); //sets fine to patron 
          }
        }
  }
  public LinkedList<Long> getISBNS() //gets list of isbns
  {
	  LinkedList<Long> iSBNS = new LinkedList<Long>();
	  for (int i = 0; i < checkout.getSize(); i++)
	  {
		  long isbn = checkout.getData(i).getISBN();
		  iSBNS.append(isbn);
	  }
	  return iSBNS;
  }

  public LinkedList<CheckoutInfo> getCheckoutList()
  {
	  return this.checkout;
  }
  
  public int compareTo(Patron patron2) //patron1 is famName in calling
  {
	  return String.CASE_INSENSITIVE_ORDER.compare(this.getFamilyName(), patron2.getFamilyName());
  }
  
  public CheckoutInfo getCheckoutInfoOfISBN(long isbn) //returns checkoutinfo for specific isbn
  {
	  for (int i = 0; i < checkout.getSize(); i++)
	  {
		  if (checkout.getData(i).getISBN() == isbn)
		  {
			  return checkout.getData(i);
		  }
	  }
	  return null;
  }
  
  public int insertCheckoutWhere(CheckoutInfo inputCheckout)
  {
	  int index = 0;
	  if (this.checkout.getNode(0) == null)
	  {
		  return index;
	  }
	  try 
	  {
		  while (this.checkout.getNode(index) != null)
		  {
			  if (this.checkout.getData(index).compareDateDesc(inputCheckout) >= 0)
			  {
				  break;
			  }
			  index++;
		  }
	  }
	  catch(ArrayIndexOutOfBoundsException exc)
	  {
		  
	  }
	  return index;
  }
  
  public void addCheckoutData(CheckoutInfo data) //append checkout object to checkoutlist
  {
	  int index = insertCheckoutWhere(data);
	  this.checkout.addAt(index, data);
	  this.calculateFine();
  }

  public void setId(int i) //set id
  {
    this.iD = i;
  }
  
  public void setFine(double f) //set fine
  {
    this.fine = this.fine + f; //adds double f to fine since each checkout index (that matches to corresponding patron) uses this
  }
  
  public void setFamilyName(String f) //sets family name
  {
    this.familyName = f;
  }
  
  public void setFirstName(String n) //sets first name
  {
    this.firstName = n;
  }
  
  public int getID() //gets id
  {
    return this.iD;
  }
  
  public String getFirstName() //gets first name
  {
    return this.firstName;
  }
  
  public String getFamilyName() //gets family name
  {
    return this.familyName;
  }
  
  public String toString() //to print to console
  {
	  String tempPatronString = printPatron();
	  for (int i = 0; i < this.getCheckoutList().getSize(); i++)
	  {
		  tempPatronString += this.getCheckoutList().getData(i).toString();
	  }
	  tempPatronString += printFine();
	  //System.out.println(tempPatronString);
	  return tempPatronString;
	  
  }
  
  public String checkoutToString() // put in specific format to write to file
  {
	  String checkoutData = "";
	  for (int i = 0; i < this.getCheckoutList().getSize(); i++)
	  {
		  checkoutData += this.getCheckoutList().getData(i).toFileString();
	  }
	  return checkoutData;
  }
  
  public String printPatron() //prints patron data
  {
    return (this.firstName + " " + this.familyName + " (" + this.iD + ")\n");
  }
  
  public String printFine() //prints patron fine
  {
    return "        Fine Total: $" + String.format("%.2f", this.fine) + "\n";
  }
}