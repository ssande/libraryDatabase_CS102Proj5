package application;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javafx.application.Application.Parameters;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import lib_items.Database;

public class MainControl implements Initializable{

	@FXML ToggleGroup toggle;
	@FXML Button execute;
	@FXML Text welcomeDate;
	@FXML Rectangle hideMenu;
	@FXML TextArea patronDatabase;
	@FXML TextField searchPatronLN;
	@FXML Button searchBtn;
	@FXML TextField textFN;
	@FXML TextField textLN;
	@FXML Button add;
	@FXML TextField textCLN;
	@FXML TextField textISBN;
	@FXML DatePicker textDate;
	@FXML Button cAdd;
	String[] args;
	private Database d = new Database();
	
	public MainControl() {
		// TODO Auto-generated constructor stub
	
	}
	
	public void setInitialDatabase() //uses args gained from setParameters method
	{
		try {
		d.inputFile(this.args[0], 1);
		d.inputFile(this.args[1], 2);
		}
		catch(ArrayIndexOutOfBoundsException exc)
		{
			
		}
	}
	
	//to get args from Main.java file (patron and checkout files)
	public void setParameters(String[] args) 
	{
		this.args = args;
	}
	
	public void displayData(String t)
	{
		patronDatabase.setText(t);
	}
	
	public String getText(TextField t)
	{
		return t.getText();
	}
	
	public void checkoutAdd(ActionEvent event)
	{
		try
		{
			String ln = getText(textCLN);
			String isbn = getText(textISBN);
			Long cISBN = Long.parseLong(isbn);
			LocalDate cDate = textDate.getValue();
			String result = d.getCheckoutInput(ln, cDate, cISBN);
			displayData(result);
			cAdd.setVisible(false);
			execute.setVisible(true);
			textCLN.clear();
			textISBN.clear();
			textDate.getEditor().clear();
		}
		catch(Exception exc)
		{
			displayData("Unable to add checkout. Cancelling operation.");
			cAdd.setVisible(false);
			execute.setVisible(true);
			textCLN.clear();
			textISBN.clear();
			textDate.getEditor().clear();
		}
	}
	
	public void addPatron(ActionEvent event)
	{
		try 
		{
			String fn = getText(textFN);
			String ln = getText(textLN);
			
			if (fn.equals("") || ln.equals("")) //make sure user didn't leave anything blank
			{
				displayData("You must enter in the patron's full name. Cancelling operation.");
				add.setVisible(false);
				execute.setVisible(true);
				textFN.clear();
				textLN.clear();
				return;
			}
			String result = d.getPatronInput(fn, ln);
			displayData(result);
			add.setVisible(false);
			execute.setVisible(true);
			textDate.getEditor().clear();
			textFN.clear();
			textLN.clear();
		}
		catch(Exception e)
		{
			displayData("Unable to add patron. Cancelling operation.");
			add.setVisible(false);
			execute.setVisible(true);
			//textDate.getEditor().clear();
			textFN.clear();
			textLN.clear();
		}
	}
	
	public void search(ActionEvent event)
	{
		try
		{
			String ln = getText(searchPatronLN);			
			String result  = d.searchPatron(ln);
			displayData(result);
			searchBtn.setVisible(false);
			execute.setVisible(true);
			searchPatronLN.clear();
		}
		catch(Exception e)
		{
			displayData("Error occurred.");
			searchBtn.setVisible(false);
			execute.setVisible(true);
			searchPatronLN.clear();
		}
	}
	
	public void importFile()
	{
		String pFileName, cFileName;
		FileChooser pImportChooser = new FileChooser();
		pImportChooser.setTitle("Choose Patron Import File");
		pImportChooser.getExtensionFilters().add(new ExtensionFilter("Text File","*.txt"));
		File pFile = pImportChooser.showOpenDialog(null);
		if (pFile != null)
		{
			//pFileName  = pFile.getName();
			pFileName = pFile.getAbsolutePath(); //allows for files outside of project
		}
		else
		{
			displayData("Since you pressed cancel, operation will abort.");
			execute.setVisible(true);
			return;
		}
		FileChooser cImportChooser = new FileChooser();
		cImportChooser.setTitle("Choose Checkout Import File");
		cImportChooser.getExtensionFilters().add(new ExtensionFilter("Text File", "*.txt"));
		File cFile = cImportChooser.showOpenDialog(null);
		if (cFile != null)
		{
			//cFileName = cFile.getName();
			cFileName = cFile.getAbsolutePath(); //allows for files outside of project
		}
		else
		{
			displayData("Since you pressed cancel, operation will abort.");
			execute.setVisible(true);
			return;
		}
		
		//
		String warning = "\nPlease note, if the patron file does not work, the database will not be initialized.";
		String result = d.userInputFiles(pFileName, cFileName) + warning;
		displayData(result);
		execute.setVisible(true);
	}
	
	public void exportFile()
	{
		String pExportFileName, cExportFileName;
		FileChooser exportChooser = new FileChooser();
		exportChooser.setTitle("Choose Patron Export File");
		exportChooser.getExtensionFilters().add(new ExtensionFilter("Text File" , "*.txt"));
		File pFile= exportChooser.showSaveDialog(null); //creates file in location
		if (pFile != null)
		{
			//pExportFileName = pFile.getName();
			pExportFileName = pFile.getAbsolutePath();
		}
		else //if user cancels option in the filechooser dialog
		{
			displayData("Since you pressed cancel, operation will abort.");
			execute.setVisible(true);
			return;
		}
		FileChooser exportChooser2 = new FileChooser();
		exportChooser2.setTitle("Choose Checkout Export File");
		exportChooser2.getExtensionFilters().add(new ExtensionFilter("Text File","*.txt"));
		File cFile = exportChooser2.showSaveDialog(null); //creates file in location
		if (cFile != null)
		{
			//cExportFileName = cFile.getName(); 
			cExportFileName = cFile.getAbsolutePath();
		}
		else
		{
			displayData("Since you pressed cancel, operation will abort.");
			execute.setVisible(true);
			return;
		}
		
		//do the d.printToFile command with both files being brought in.
		String result = d.printToFile(pExportFileName,  cExportFileName);
		displayData(result);
		execute.setVisible(true);
	}
	
	public void mainMenu(ActionEvent event)
	{
		try {
			RadioButton selected = (RadioButton) toggle.getSelectedToggle();
			String selectedText = selected.getText();
			
			int choice; 
			switch(selectedText)
			{
			case "Print Patrons":
				choice = 1;
				String patrons = d.printPatrons();
				displayData(patrons);
				//showWindows(choice);
				break;
			case "Search Patron":
				choice = 2;
				searchPatronLN.clear();
				execute.setVisible(false);
				searchBtn.setVisible(true);
				break;
			case "Add a Patron":
				choice = 3;
				textFN.clear();
				textLN.clear();
				execute.setVisible(false);
				add.setVisible(true);				
				break;
			case "Add a Checkout":
				choice = 4;
				textCLN.clear();
				textISBN.clear();
				textDate.getEditor().clear();
				execute.setVisible(false);
				cAdd.setVisible(true);
				//hideMenu();
				break;
			case "Export to File":
				choice = 5;
				execute.setVisible(false);
				exportFile();
				//hideMenu();
				break;
			case "Load from File":
				choice = 6;
				execute.setVisible(false);
				importFile();
				//hideMenu();
				break;
			default:
				choice = 0;
			}
		}catch(Exception e)
		{
			//System.out.println(e);
		    //e.printStackTrace();
			Alert empty = new Alert(AlertType.WARNING);
			empty.setHeaderText("User Error");
			empty.setContentText("You must choose an option to execute.");
			empty.showAndWait();
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("M/d/yyyy"); //formats date. eg. 10/10/2018.
		welcomeDate.setText(dateFormat.format(LocalDate.now()));
	}
}
