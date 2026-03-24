package lib_items;
import adt_files.*;
import java.io.*;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

public class Database
{
  boolean welcome = true; //only says welcome message once
  LocalDate date = LocalDate.now();
  Scanner input = new Scanner(System.in);
  private PatronTree patronTree;
  DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("M/d/yyyy"); //formats date. eg. 10/10/2018.
  String dateNow = dateFormat.format(LocalDate.now()); //allows for quick usage of date in multiple methods
  
  public Database() //constructor
  {
	  patronTree = new PatronTree();
  }
  
  public int splitCheckout(LinkedList<CheckoutInfo> list, int start, int end)
  {
	  CheckoutInfo date1 = list.getData(end);
	  int index = (start-1);
	  for (int i = start; i < end; i++)
	  {
		  CheckoutInfo date2 = list.getData(i);
		  if (date2.compareDateDesc(date1) <= 0)
		  {
			  index++;
			  list.swapElements(index, i);
		  }
	  }
	  
	  list.swapElements(index+1, end);
	  return index + 1;
  }
  
  public void sortListCheckout(LinkedList<CheckoutInfo> list, int start, int end)
  {
	  if (start < end)
	  {
		  int index = splitCheckout(list, start, end);
	  
		  sortListCheckout(list, start, index-1);
		  sortListCheckout(list, index+1, end);
	  }
  }
  
  public String addPatron(int iD, String fam, String first) //adds a patron to the patron list
  {
	  if (patronTree.searchByID(iD) != null)
	  {
		  return "Patron already exists with given ID.";
	  }
	  
	  Patron patronTemp = new Patron(); //creates a patron and sets properties and appends to list of patrons
	  patronTemp.setId(iD);
      patronTemp.setFamilyName(fam);
      patronTemp.setFirstName(first);
      
      patronTree.insert(patronTemp);
      return "Patron added successfully.";
      
  }
  
  public boolean addCheckout(int iD, long iSBN, String date) //adds checkout data to patron with corresponding id
  {
	  Patron tempPatron = patronTree.searchByID(iD); //finds patron with associated patron if one exists
	  CheckoutInfo tempCheckout = new CheckoutInfo();
	  tempCheckout.setID(tempPatron.getID());
      tempCheckout.setISBN(iSBN);
      tempCheckout.setDate(date);
      
      if (tempPatron != null && tempCheckout.getDate() != null)// if patron was found and date of checkout was successfully set
      {
    	  boolean found = false; //won't allow a patron to check out a book they already checked out
    	  for (int i = 0; i < tempPatron.getCheckoutList().getSize(); i++)//checks to see if user has already checked book out
    	  {
    		  if (tempPatron.getCheckoutList().getData(i).getISBN() == iSBN)
    		  {
    			  found = true;
    		  }
    	  }
    	  if (!found)
    	  {
    		  tempPatron.addCheckoutData(tempCheckout);
    	  }
    	  else
    	  {
    		  return false;//("Patron cannot checkout a book that they've already checked out.");
    	  }
      }
      return true;
  }
  
  public boolean validDate(String date) //validates date
  {
	  try
	  {
		  String temp = String.valueOf(date); //in order to get d into correct format
		  if (date.length() != 8)
			  return false;
		    String y = temp.substring(0,4);
		    String m = temp.substring(4,6);
		    String day = temp.substring(6,8);
		    date = m + "/" + day + "/" + y;
		  LocalDate.parse(date, dateFormat);
		  return true;
	  }
	  catch(DateTimeParseException exc) //exception thrown if date could not be parsed (aka not in correct format)
	  {
		  return false;
	  }
  }
  
  public String getCheckoutInput(String name, LocalDate cDate, long iSBN) //gets user input for checkout data
  {
	  try
	  {
		 
		  Patron tempPatron = patronTree.searchByName(name);
		  if (tempPatron == null) //no patron found with given id
		  {
			  return ("There is no patron with given family name.");
			 
		  }
		  String date = cDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		  if (validDate(date)) //checks to see if date is valid
		  {		  
			  boolean result = addCheckout(tempPatron.getID(), iSBN, date);
			  
			  if (result)
			  {
				  //adding fine to existing patron if applicable
				  String d = dateNow;
		          LocalDate date1 = LocalDate.parse(d, dateFormat);
		          CheckoutInfo tempCheckout = new CheckoutInfo();
		          tempCheckout.setDate(date);
		          int days = (int) ChronoUnit.DAYS.between(tempCheckout.getDateObj(), date1); //gets number of days between two dates
		          if (days > 90) //if executes means book is overdue
		          {
		            double f = (days-90) *.25; //gets fine
		            tempPatron.setFine(f); //sets fine to patron 
		          }
		          return "Checkout added successfully.";
			  }
			  else
			  {
				  return "Patron cannot checkout a book that they've already checked out.";
			  }
		  }
		  else
		  {
			  return("Invalid date. Terminating command.");
		  }
	  }
	  catch(InputMismatchException exc)
	  {
		  return("Input was of an invalid type. Cancelling Operation.");
	  }
  }
  
  public String inputFile(String s, int whichFile) //opens patron and checkout info files and sets data to correct arrays
  {
    try { //tries to open file
          Scanner inFile = new Scanner(new File(s));
          if (whichFile == 1) //checks to see what file data is being read from
          {
            while (inFile.hasNext())
            {
                String data = inFile.next();
                Scanner individData = new Scanner(data);
                individData.useDelimiter("/");
                
                int iD = Integer.parseInt(individData.next());
		        String famName = individData.next();
                String firstName = individData.next();
                
                addPatron(iD, famName, firstName);
                
                individData.close();
            }
          }
          else //checkoutfile
          {
            while (inFile.hasNext())
            {
                String data = inFile.next();
                Scanner individData = new Scanner(data);
                individData.useDelimiter("/");
                
                int iD = Integer.parseInt(individData.next());
                long iSBN = Long.parseLong(individData.next());
                String date = individData.next();
                
                if (patronTree.searchByID(iD) != null)
                	addCheckout(iD, iSBN, date);
                
                individData.close();
            }
          }
          inFile.close();
          return "Database successfully re-initalized with data from given files.";
        }
             catch (ArrayIndexOutOfBoundsException exc) {
            // Only happens if file not specified as parameter
//               System.out.println(exc);
//               exc.printStackTrace();
               return exc.toString();
            }
             catch (FileNotFoundException exc) {
            // Only happens if file cannot be opened 
               String error1 =  "File could not be opened. Please check your spelling and"
               + " whether you put the correct extension in.";
               return error1;
            }
            catch(NumberFormatException exc)
            {
              String error2 = "There was a number format exception that occurred when inputing data from a file. Exiting...";
              return error2;
            }
  }
  
  public String getPatronInput(String first, String fam) //gets user input for patron data (to add new patron)
  {	  
	  boolean exists = false;
	  int iD;
	  do //will loop if id generated is already in use by another patron in list
	  {
		  iD = (int) Math.round(Math.random()*Integer.MAX_VALUE);
		  exists = patronTree.searchByID(iD) != null;
		  
	  }while(exists);
	  
	  String result = addPatron(iD, fam, first);
	  return result;
  }
   
  public String searchPatron(String patron) //searches for patron given patron id
   {
     try
     {
       Patron tempPatron = patronTree.searchByName(patron);
       if (tempPatron != null) //if patron was found with given id
       {
    	   return tempPatron.toString();
       }
       else
       {
    	  String error = "No patron with last name of " + patron;// + patron;
    	  return error; //if no such patron exists
       }
     }
     catch(InputMismatchException exc)
     {
       return "Input was of an invalid type. Cancelling operation...";
     }
   }
   
  public String printPatrons() //prints all patrons
   {
	  String list = patronTree.printInOrder();
	  return list;
   }
   
  public String userInputFiles(String pFileName, String cFileName) //let user choose patron and checkout input files and replace all of stored data with data from files
  {	
	 File patronFile = new File(pFileName);
	 boolean patronExists = patronFile.exists();
	 File checkoutFile = new File(cFileName);
	 boolean checkoutExists = checkoutFile.exists();
	 
	 if (patronExists != true && checkoutExists != true)
	 {
		 return ("One or more files was not viable. Cancelling execution.");
	 }
	 
	 patronTree.removeAll();
	 String result = "Patron file: " + inputFile(pFileName, 1);
	 String result2 = "\nCheckout file: " + inputFile(cFileName, 0);
	 return result + result2;
	 
  }
  
  public String printToFile(String pFile, String cFile) //prints data to user selected files
  {
	  try 
	  {
		  PrintStream patronFile = new PrintStream(new File(pFile));
		  PrintStream checkoutFile = new PrintStream(new File(cFile));
		  
		  String patronData = patronTree.filePreOrder();
		  String checkoutData = patronTree.checkoutFilePreOrder();
		  patronFile.print(patronData);
		  checkoutFile.print(checkoutData);
		  
		  patronFile.close();
		  checkoutFile.close();
		  return "Files successfully written.";
		  
	  }
	  catch(FileNotFoundException exc)
	  {
		  return ("File to write to could not be found.");
	  } 
  }
}