package panopoly;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.WindowConstants;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class MCQ {
	
	private int noOfQuestions = 10;
	
	//Path to NOC List and line count to bound random line selection. 
    private static final String NOC_LIST_PATH = "Veale's NOC List/Veale's The NOC List.xlsx";
    private static final int NOC_LIST_LINE_COUNT = 805;

    //Path to Vehicle fleet.
    private static final String VEHICLE_FLEET_PATH = "Veale's NOC List/Veale's vehicle fleet.xlsx";
    
    private static final String WEAPON_ARSENAL = "Veale's NOC List/Veale's weapon arsenal.xlsx";
    
    //Path to the Clothing Line.
    private static final String CLOTHING_LINE_PATH = "Veale's NOC List/Veale's clothing line.xlsx";
    
    //By using List, we can take advantage of Collections.shuffle(list);
    private List<String> answers = new ArrayList<String>();// // TODO - Remove 'static' if we're going to create several questions from same object
    private String answer = ""; // TODO - Remove 'static' if we're going to create several questions from same object
    private String question = "";    
    private JFrame mcqFrame = new JFrame("MCQ QUESTION");

	public void createGenderWeaponQuestion() throws IOException, InvalidFormatException{
		
		ZipSecureFile.setMinInflateRatio(0.005);
		
		//Stores potential answers for question.
		//String[] names = new String[4];
		String weapon = "";
		String gender = "";
		int rowOfAnswer = 0;
		
		//Generate a random number that will be used for finding a row from NOC List.
		Random rand = new Random();
		int randomRowNumber = rowOfAnswer = rand.nextInt(805);
		
		// Creating a Workbook from an Excel file (.xls or .xlsx)
    	//Workbook: A workbook is the high-level representation of a Spreadsheet.
        Workbook workbook = WorkbookFactory.create(new File(NOC_LIST_PATH));
		
        //Obtain the sheet and the random row.
        Sheet sheet = workbook.getSheetAt(0);
        Row row = sheet.getRow(randomRowNumber);
        Cell cell;
        String cellValue;
        DataFormatter dataFormatter = new DataFormatter();

        //For Iterating over the columns of the current row
        Iterator<Cell> cellIterator = row.cellIterator();

        //Find the information for answer.
        while (cellIterator.hasNext()) {
        	
        	//Get the next column.
            cell = cellIterator.next();
            
            //Get the value in that column.
            cellValue = dataFormatter.formatCellValue(cell);
            
            //Columns in NOC list are labelled A-X, Y and Z are null.
            String currentColumnName = CellReference.convertNumToColString(cell.getColumnIndex());
            
            switch(currentColumnName){
            
            //Column A is canonical name.
            case "A":
            	answer = cellValue.trim();
            	answers.add(answer);
            	break;
            //Column C is gender of character.
            case "C":
            	gender = cellValue;
            	break;
            //Column L is weapon of choice for current character.
            case "L":
            	weapon = cellValue;
            	break;
            }
            
        }
        
        int i = 0;
        //Find three more names to present as answers.
        while(i < 3){
        	//Generate a random row number again.
        	randomRowNumber = rand.nextInt(805);
        	//If it's not the same as row we got answer from, continue.
        	if(randomRowNumber != rowOfAnswer){
        		row = sheet.getRow(randomRowNumber);
        		cell = row.getCell(1);
        		cellValue = dataFormatter.formatCellValue(cell);
        		answers.add(cellValue);
        		i++;
        	}
        }
        	
        
        Collections.shuffle(answers);
        
        //Format the question.
        question = "You see a "+gender+" with a "+weapon+". Is it:";
    		
	}
	
	public void createVehicleQuestion() throws EncryptedDocumentException, InvalidFormatException, IOException{
		
		answers.clear();
		
		//Prevent ZIP BOMB
		ZipSecureFile.setMinInflateRatio(0.005);
		
		String[] potentialQuestions = {"You see a", "You just got run over by a", "You're offered a lift by a"};
		
		//Stores potential answers for question.
		String vehicle = "";
		String gender = "";
		String determiner = "";
		String affordance = "";
		
		int rowOfAnswer = 0;
		
		//Generate a random number that will be used for finding a row from NOC List.
		Random rand = new Random();
		int randomRowNumber = rowOfAnswer = rand.nextInt(NOC_LIST_LINE_COUNT);
		
		// Creating a Workbook from an Excel file (.xls or .xlsx)
    	//Workbook: A workbook is the high-level representation of a Spreadsheet.
        Workbook workbook = WorkbookFactory.create(new File(NOC_LIST_PATH));
		
        //Obtain the sheet and the random row.
        
        /* Get name of character.
         * Get gender of character.
         * Get a list of characters vehciles.
         * Choose random vehicle.
         */
     
        //Find a row that doesn't have a null value for vehicle
        while(workbook.getSheetAt(0).getRow(rowOfAnswer).getCell(10).toString().equals("")){
        	rowOfAnswer = rand.nextInt(NOC_LIST_LINE_COUNT);
        }
        
        answer = workbook.getSheetAt(0).getRow(rowOfAnswer).getCell(0).toString();
        answers.add(answer);
        gender =  workbook.getSheetAt(0).getRow(rowOfAnswer).getCell(2).toString();
        List<String> listOfVehicles = Arrays.asList(workbook.getSheetAt(0).getRow(rowOfAnswer).getCell(10).toString().split(", "));
        vehicle =  listOfVehicles.get(rand.nextInt(listOfVehicles.size())).trim();
        
        int i = 1;
        //Find three more names to present as answers.
        while(i < 4){
        	//Generate a random row number again.
        	randomRowNumber = rand.nextInt(NOC_LIST_LINE_COUNT);
        	//If it's not the same as row we got answer from, continue.
        	if(randomRowNumber != rowOfAnswer){
        		answers.add(workbook.getSheetAt(0).getRow(randomRowNumber).getCell(0).toString());
        		i++;
        	}
        }
        /* Find the characters vehicle in Veales Vehicle Fleet.
         * Get Determiner for vehicle.
         * Get Affordances of vehcile.
         */
        Workbook workbook1 = WorkbookFactory.create(new File(VEHICLE_FLEET_PATH));

        //For Iterating over the rows of current file.
        Row row;
        Iterator<Row> rowIterator = workbook1.getSheetAt(0).rowIterator();

        boolean vehicleFound = false;

        //Iterate through rows of Vehicle fleet until we find the correct one.
        while (rowIterator.hasNext() && !vehicleFound ) {
        	
        	//Get the next row.
            row = rowIterator.next();
            
            //If the current row contains the car we're looking for, get the necessary info to form a question.
            if(row.getCell(1).toString().trim().equals(vehicle)){
	            	determiner = row.getCell(0).toString();
	            	List<String> listOfAffordances = Arrays.asList(row.getCell(2).toString().split(", "));
	            affordance =  listOfAffordances.get(rand.nextInt(listOfAffordances.size()));
	            	vehicleFound = true;
            }
            
        }
        
        //Shuffle List
        Collections.shuffle(answers);
          
       question = potentialQuestions[rand.nextInt(potentialQuestions.length)]+" "+gender+" "+affordance+" "+determiner+" "+vehicle+". Is it:";
    	
	}
	
	//Question format: You're at <address> and you see a <positive/negative talking point> <gender>. Who is it?
	public void createAddressAndTalkingPointQuestion() throws EncryptedDocumentException, InvalidFormatException, IOException{
		
		answers.clear();
		
		//Prevent ZIP BOMB
		ZipSecureFile.setMinInflateRatio(0.005);
				
		//Stores potential answers for question.
		//String[] names = new String[4];
		String address = "";
		String gender = "";
		String talkingPoint = "";
		
		int rowOfAnswer = 0;
		int randomRowNumber = 0;
					
		// Creating a Workbook from an Excel file (.xls or .xlsx)
    	//Workbook: A workbook is the high-level representation of a Spreadsheet.
        Workbook workbook = WorkbookFactory.create(new File(NOC_LIST_PATH));
        
		//Generate a random number that will be used for finding a row from NOC List.
		Random rand = new Random();
		rowOfAnswer = rand.nextInt((NOC_LIST_LINE_COUNT-1)+1)+1;
		
		//Set answer
		answer = workbook.getSheetAt(0).getRow(rowOfAnswer).getCell(0).toString();
		answers.add(answer);
    	
		//Get gender of character
		gender = workbook.getSheetAt(0).getRow(rowOfAnswer).getCell(2).toString();
       
        //Get characters address from columns 3,4 and 5
        for(int i=3;i<6;i++){
            	address += workbook.getSheetAt(0).getRow(rowOfAnswer).getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString().trim()+" ";
        }
        
        //Get a random talking point (positive or negative), columns 22 and 23 from NOC List.
        int column = rand.nextInt(((23-22)+1))+22;
        List<String> listOfTalkingPoints = Arrays.asList(workbook.getSheetAt(0).getRow(rowOfAnswer).getCell(column, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString().split(", "));
        talkingPoint = listOfTalkingPoints.get(rand.nextInt(listOfTalkingPoints.size())).trim();
        
        int i = 0;
        //Find three more names to present as answers.
        while(i < 3){
        	//Generate a random row number again.
        	randomRowNumber = rand.nextInt((NOC_LIST_LINE_COUNT-1)+1)+1;
        	//If it's not the same as row we got answer from, continue.
        	if(randomRowNumber != rowOfAnswer){
        		answers.add(workbook.getSheetAt(0).getRow(randomRowNumber).getCell(0).toString());
        		i++;
        	}
        }
        
        //Shuffle List of answers
        Collections.shuffle(answers);
        
        question = "You're at "+address+"and you see a "+talkingPoint+" "+gender+".\nIs it:";

	}
	
	//Column j is typical activity
	//Question format: You see someone <typical activity>
	public void createTypicalActivityQuestion() throws EncryptedDocumentException, InvalidFormatException, IOException{
		
		answers.clear();
		
		//Prevent ZIP BOMB
		ZipSecureFile.setMinInflateRatio(0.005);
		
		String activity = "";
		
		int rowOfAnswer = 0;
		int randomRowNumber = 0;
					
		// Creating a Workbook from an Excel file (.xls or .xlsx)
    	//Workbook: A workbook is the high-level representation of a Spreadsheet.
        Workbook workbook = WorkbookFactory.create(new File(NOC_LIST_PATH));
        
		//Generate a random number that will be used for finding a row from NOC List.
		Random rand = new Random();
		rowOfAnswer = rand.nextInt((NOC_LIST_LINE_COUNT-1)+1)+1;
		
		//Set answer
		answer = workbook.getSheetAt(0).getRow(rowOfAnswer).getCell(0).toString();
		answers.add(answer);
		
		//Get a list of typical activities for the character
		//Pick a random activity from this list
        List<String> listOfTypicalActivities = Arrays.asList(workbook.getSheetAt(0).getRow(rowOfAnswer).getCell(9).toString().split(", "));
        activity = listOfTypicalActivities.get(rand.nextInt(listOfTypicalActivities.size())).trim();
        
        int i = 0;
        //Find three more names to present as answers.
        while(i < 3){
        	//Generate a random row number again.
        	randomRowNumber = rand.nextInt((NOC_LIST_LINE_COUNT-1)+1)+1;
        	//If it's not the same as row we got answer from, continue.
        	if(randomRowNumber != rowOfAnswer){
        		answers.add(workbook.getSheetAt(0).getRow(randomRowNumber).getCell(0).toString());
        		i++;
        	}
        }
        
        //Shuffle List of answers
        Collections.shuffle(answers);
    	
        question = "You see someone "+activity+". Is it:";

	}
	
	//Question: You see someone shooting <arch nemesis> with a <weapon>
	//Question: You see someone <affordance> <arch nemesis> with a <weapon>
	//Determiner: a	Weapon: .22 caliber Colt	Affordances: shooting with, pistol-whipping with
	public void createWeaponArchNemesisQuestion() throws EncryptedDocumentException, InvalidFormatException, IOException{
		
		answers.clear();
		
		//Prevent ZIP BOMB
		ZipSecureFile.setMinInflateRatio(0.005);
				
		//Stores potential answers for question.
		//String[] names = new String[4];
		String weapon = "";
		String archNemesis = "";
		String determiner = "";
		String affordance = "";
		//String talkingPoint = "";
		
		int rowOfAnswer = 0;
		int randomRowNumber = 0;
					
		// Creating a Workbook from an Excel file (.xls or .xlsx)
    	//Workbook: A workbook is the high-level representation of a Spreadsheet.
        Workbook workbook = WorkbookFactory.create(new File(NOC_LIST_PATH));
        
		//Generate a random number that will be used for finding a row from NOC List.
		Random rand = new Random();
		rowOfAnswer = rand.nextInt((NOC_LIST_LINE_COUNT-1)+1)+1;
		
		//Find a row where the character has an arch nemesis and a weapon of choice.
		while(workbook.getSheetAt(0).getRow(rowOfAnswer).getCell(8, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString() == "" ||
			  workbook.getSheetAt(0).getRow(rowOfAnswer).getCell(11, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString() == ""){
			  
			  rowOfAnswer = rand.nextInt((NOC_LIST_LINE_COUNT-1)+1)+1;
		}
		
		//Set answer
		answer = workbook.getSheetAt(0).getRow(rowOfAnswer).getCell(0).toString();
		answers.add(answer);
		
		//Get the characters list of weapons then pick a random one.
        List<String> listOfWeapons = Arrays.asList(workbook.getSheetAt(0).getRow(rowOfAnswer).getCell(11).toString().split(", "));
        weapon = listOfWeapons.get(rand.nextInt(listOfWeapons.size())).trim();
        
        //TODO - Find the weapons affordance
        Workbook workbook1 = WorkbookFactory.create(new File(WEAPON_ARSENAL));

        //For Iterating over the rows of current file.
        Row row;
        Iterator<Row> rowIterator = workbook1.getSheetAt(0).rowIterator();

        boolean weaponFound= false;

        //Iterate through rows of weapon arsenal fleet until we find the correct one.
        while (rowIterator.hasNext() && !weaponFound ) {
        	
        	//Get the next row.
            row = rowIterator.next();
            
            //If the current row contains the weapon we're looking for, get the necessary info to form a question.
            if(row.getCell(1).toString().trim().equals(weapon)){
            	if(row.getCell(0) != null){
	            	determiner = row.getCell(0).toString().trim();
            	}
            	
	            	List<String> listOfWeaponAffordances = Arrays.asList(row.getCell(2).toString().split(", "));
	            affordance =  listOfWeaponAffordances.get(rand.nextInt(listOfWeaponAffordances.size()));
	            	weaponFound = true;
            }
            
        }

        //Get the characters list of arch nemesis then pick a random one.
        List<String> listOfArchNemesis = Arrays.asList( workbook.getSheetAt(0).getRow(rowOfAnswer).getCell(8).toString().split(", "));
		archNemesis = listOfArchNemesis.get(rand.nextInt(listOfArchNemesis.size())).trim();
		
		//This splits the affordances into different parts.
		/*E.G -> Affordance: beating black and blue
		  This is split so we can insert character name between sentence:
		   beating <character name> black and blue
		*/
		List<String> affordancesParts = Arrays.asList(affordance.split(" "));

		 int i = 0;
	     //Find three more names to present as answers.
	     while(i < 3){
	      	//Generate a random row number again.
	       	randomRowNumber = rand.nextInt((NOC_LIST_LINE_COUNT-1)+1)+1;
	       	//If it's not the same as row we got answer from, continue.
	       	if(randomRowNumber != rowOfAnswer){
	       		answers.add(workbook.getSheetAt(0).getRow(randomRowNumber).getCell(0).toString());
        		i++;
        	}
	     }  
	     
	     //Shuffle List of answers
	     Collections.shuffle(answers);
	     
	     //Begin to form the question
	     question = "You see someone ";
	     if(affordancesParts.size() == 1){
	    	 question += affordancesParts.get(0)+" "+archNemesis+" "+determiner+" "+weapon.trim();
	     }
	     
	     /*
	      * This deals with longer than normal affordances i.e ass kicking with instead of shooting with
	      */
	     else{
	    	 question += affordancesParts.get(0)+" "+archNemesis+" ";//+ affordancesParts.get(affordancesParts.size()-1)+" "+determiner+" "+weapon;
	    	 i = 1;
	    	 while(i<affordancesParts.size()){
	    		 question += affordancesParts.get(i)+" ";
	    		 i++;
	    	 }
	    	 question += determiner+" "+weapon;
	     }
	    		    
	    question = question+"\nIs it:";
				
	}
	
	// Question: You see someone fighting <opponent>. Is it:
	// Answer = Character (cell 1), Opponent = Opponent (cell 9)
	public void createOpponentQuestion() throws EncryptedDocumentException, InvalidFormatException, IOException
	{    
		//Prevent ZIP BOMB
		ZipSecureFile.setMinInflateRatio(0.005);
		
		question = "You see someone wresting and fighting with ";
		String opponent = "";
		
		List<String> listOfOpponents;
		
		answers.clear();
		
		int rowOfAnswer = 0;
		
		Random rand = new Random();
		int randomRowNumber = rowOfAnswer = rand.nextInt(NOC_LIST_LINE_COUNT);
		
		Workbook workbook = WorkbookFactory.create(new File(NOC_LIST_PATH));
		
		while(workbook.getSheetAt(0).getRow(rowOfAnswer).getCell(8, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString().equals(""))
		{
			rowOfAnswer = rand.nextInt(NOC_LIST_LINE_COUNT);
		}
		
		answer = workbook.getSheetAt(0).getRow(rowOfAnswer).getCell(0).toString().trim();
		answers.add(answer);
		
		if(!workbook.getSheetAt(0).getRow(rowOfAnswer).getCell(8, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString().equals(""))
		{
			listOfOpponents = Arrays.asList(workbook.getSheetAt(0).getRow(rowOfAnswer).getCell(8).toString().split(", "));
			opponent = listOfOpponents.get(rand.nextInt(listOfOpponents.size()));
		}
		
		int i = 1;
		
		while(i < 4)
		{
			randomRowNumber = rand.nextInt(NOC_LIST_LINE_COUNT);
        	
        	if(randomRowNumber != rowOfAnswer)
        	{
           		answers.add(workbook.getSheetAt(0).getRow(randomRowNumber).getCell(0).toString());
           		i++;
           	}
		}
		
		Collections.shuffle(answers);
		
		question += opponent + ". Is it:";
	}
	
	public void createSeenWearingGenreQuestion() throws EncryptedDocumentException, InvalidFormatException, IOException
	{
		ZipSecureFile.setMinInflateRatio(0.005);

		question = "You're envious of someone strutting down the street wearing";
		String clothing = "";
		String determiner = "";
		String genre = "";

		List<String> listOfClothing;
		List<String> listOfGenres;

		answers.clear();

		int rowOfAnswer = 0;

		Random rand = new Random();
		int randomRowNumber = rowOfAnswer = rand.nextInt(NOC_LIST_LINE_COUNT);

		Workbook workbook = WorkbookFactory.create(new File(NOC_LIST_PATH));

		while(workbook.getSheetAt(0).getRow(rowOfAnswer).getCell(12, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString().equals("") &&
				workbook.getSheetAt(0).getRow(rowOfAnswer).getCell(14, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString().equals(""))
		{
			rowOfAnswer = rand.nextInt(NOC_LIST_LINE_COUNT);
		}

		answer = workbook.getSheetAt(0).getRow(rowOfAnswer).getCell(0).toString().trim();
		answers.add(answer);

		Workbook workbook1 = WorkbookFactory.create(new File(CLOTHING_LINE_PATH));

		Row row;
		Iterator<Row> rowIterator = workbook1.getSheetAt(0).rowIterator();

		boolean clothingFound = false;

		if(!workbook.getSheetAt(0).getRow(rowOfAnswer).getCell(12, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString().equals(""))
		{
			listOfClothing = Arrays.asList(workbook.getSheetAt(0).getRow(rowOfAnswer).getCell(12).toString().split(", "));
			clothing = listOfClothing.get(rand.nextInt(listOfClothing.size()));

			if(listOfClothing.size() == 1)
			{
				while (rowIterator.hasNext() && !clothingFound ) 
				{
					//Get the next row.
					row = rowIterator.next();

					//If the current row contains the weapon we're looking for, get the necessary info to form a question.
					if(row.getCell(1).toString().trim().equals(clothing))
					{
						if(row.getCell(0) != null)
						{
							determiner = row.getCell(0).toString().trim();

							question += " " + determiner;
						}
						clothingFound = true;
					}
				}

				question += " " + clothing;
			}
			else
			{
				for(int i = 0; i < listOfClothing.size(); i++)
				{
					while (rowIterator.hasNext() && !clothingFound ) 
					{

						//Get the next row.
						row = rowIterator.next();

						//If the current row contains the weapon we're looking for, get the necessary info to form a question.
						if(row.getCell(1).toString().trim().equals(listOfClothing.get(i)))
						{
							if(row.getCell(0) != null)
							{
								determiner = row.getCell(0).toString().trim();

								question += " " + determiner;
								clothingFound = true;
							}
						}
					}

					question += " " + listOfClothing.get(i);
				}
			}
		}
		if(!workbook.getSheetAt(0).getRow(rowOfAnswer).getCell(14, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString().equals(""))
		{
			listOfGenres = Arrays.asList(workbook.getSheetAt(0).getRow(rowOfAnswer).getCell(14).toString().split(", "));
			genre = listOfGenres.get(rand.nextInt(listOfGenres.size()));
		}

		int i = 0;

		while(i < 3)
		{
			randomRowNumber = rand.nextInt(NOC_LIST_LINE_COUNT);

			if(randomRowNumber != rowOfAnswer)
			{
				answers.add(workbook.getSheetAt(0).getRow(randomRowNumber).getCell(0).toString());
				i++;
			}
		}

		Collections.shuffle(answers);

		question += " who looks like they're involved in "+ genre;

		question += ". Is it";
	}

	public void createPortrayedBy() throws EncryptedDocumentException, InvalidFormatException, IOException
	{
		//Prevent ZIP BOMB
		ZipSecureFile.setMinInflateRatio(0.005);
		
		question = "What actor portrays ";
		String character = "";
		
		List<String> listOfCharacters;
		
		answers.clear();
		
		int rowOfAnswer = 0;
		
		Random rand = new Random();
		int randomRowNumber = rowOfAnswer = rand.nextInt(NOC_LIST_LINE_COUNT);
		
		Workbook workbook = WorkbookFactory.create(new File(NOC_LIST_PATH));
		
		while(workbook.getSheetAt(0).getRow(rowOfAnswer).getCell(16, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString().equals(""))
		{
			rowOfAnswer = rand.nextInt(NOC_LIST_LINE_COUNT);
		}
		
		answer = workbook.getSheetAt(0).getRow(rowOfAnswer).getCell(16).toString().trim();
		answers.add(answer);
		
		if(!workbook.getSheetAt(0).getRow(rowOfAnswer).getCell(16, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString().equals(""))
		{
			listOfCharacters = Arrays.asList(workbook.getSheetAt(0).getRow(rowOfAnswer).getCell(0).toString().split(", "));
			character = listOfCharacters.get(rand.nextInt(listOfCharacters.size()));
		}
		
		int i = 1;
		
		while(i < 4)
		{
			randomRowNumber = rand.nextInt(NOC_LIST_LINE_COUNT);
			
			if(randomRowNumber != rowOfAnswer && !workbook.getSheetAt(0).getRow(rowOfAnswer).getCell(16, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString().equals(""))
			{
				answers.add(workbook.getSheetAt(0).getRow(randomRowNumber).getCell(0).toString());
				i++;
			}
		}
		
		Collections.shuffle(answers);
		
		question = "\nQuestion: "+question + character+". Is it:";
	}
	
	public void creatorAndCreation() throws EncryptedDocumentException, InvalidFormatException, IOException{
		
		//Prevent ZIP BOMB
		ZipSecureFile.setMinInflateRatio(0.005);
				
		String character = "";
		
		answers.clear();
		
		
		int rowOfAnswer = 0;
		
		//Generate a random number that will be used for finding a row from NOC List.
		Random rand = new Random();
		int randomRowNumber = rowOfAnswer = rand.nextInt(NOC_LIST_LINE_COUNT);
		
		Workbook workbook = WorkbookFactory.create(new File(NOC_LIST_PATH));
		
		boolean foundAcceptableRow = false;
		
		while(!foundAcceptableRow){
			
			//Is row fictional? Check to ensure it has a creator/creation
			if(workbook.getSheetAt(0).getRow(rowOfAnswer).getCell(16,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString().equals("fictional")){
				
				//If the fictional character has a creator AND creation, randomly choose one of these to form a question
				//Cell 17 is creator, Cell 18 is creation
				if(!workbook.getSheetAt(0).getRow(rowOfAnswer).getCell(17,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString().equals("") &&
				   !workbook.getSheetAt(0).getRow(rowOfAnswer).getCell(18,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString().equals("")	){
					
					//Store characters name
					character = workbook.getSheetAt(0).getRow(rowOfAnswer).getCell(0).toString();
					
					//If 0, use creator. If 1, use creation
					int decideQuestion = rand.nextInt(2);
					
					//Creator of character
					if(decideQuestion == 0){
						answer = workbook.getSheetAt(0).getRow(rowOfAnswer).getCell(17).toString().trim();
						
						question += "Who created "+character;
						foundAcceptableRow = true;
					}
					
					//Creation of character
					else if(decideQuestion == 1){
				        List<String> listOfCreations = Arrays.asList(workbook.getSheetAt(0).getRow(rowOfAnswer).getCell(18).toString().split(", "));
						answer =  listOfCreations.get(rand.nextInt(listOfCreations.size()));
						question += "What did "+character+" create?";
						foundAcceptableRow = true;
					}
				}
				
				
				//Else, if character has no creation and only a creator, use this to form a question
				else if(!workbook.getSheetAt(0).getRow(rowOfAnswer).getCell(17, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString().equals("") &&
						workbook.getSheetAt(0).getRow(rowOfAnswer).getCell(18, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString().equals("") ){
					
					answer = workbook.getSheetAt(0).getRow(rowOfAnswer).getCell(17).toString().trim();
					question += "Who created "+character;
					foundAcceptableRow = true;
				}
			}
			
			//If character has no fictional status, check to see if they have a creation.
			else if(!workbook.getSheetAt(0).getRow(rowOfAnswer).getCell(18,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString().equals("")){
					List<String> listOfCreations = Arrays.asList(workbook.getSheetAt(0).getRow(rowOfAnswer).getCell(18).toString().split(", "));
					String creation =  listOfCreations.get(rand.nextInt(listOfCreations.size()));
					question += "Who created "+creation+"?";
					answer = workbook.getSheetAt(0).getRow(rowOfAnswer).getCell(0).toString().trim();
					foundAcceptableRow = true;
				}
			
			//No suitable row found, generate another.
			else{
				randomRowNumber = rowOfAnswer = rand.nextInt(NOC_LIST_LINE_COUNT);
			}
			
		}
		
		answers.add(answer);
		
		//Get 3 more random characters as options for answers
		int i=0;
		while(i < 3){
	        	randomRowNumber = rand.nextInt(NOC_LIST_LINE_COUNT);
	        	
	        	if(randomRowNumber != rowOfAnswer){
	           		answers.add(workbook.getSheetAt(0).getRow(randomRowNumber).getCell(0).toString());
	           		i++;
	           	}
	    }
		
		Collections.shuffle(answers);
		
	}
	
	/*Question: You're transported into the world of <domain>
	and you see a <negative talking point> but <postive talking point> <category>. Is it.....*/
	public void domainCategoryTalkingPointQuestion() throws EncryptedDocumentException, InvalidFormatException, IOException{
		
		//Prevent ZIP BOMB
		ZipSecureFile.setMinInflateRatio(0.005);
						
		//Question and it's parts.
		question = "You're transported into the world of ";
		String domain = "";
		String positiveTalkingPoint = "";
		String negativeTalkingPoint = "";
		String category = "";
		
		List<String> listOfDomains;
		List<String> listOfCategories;
		List<String> listOfPositiveTalkingPoints;
		List<String> listOfNegativeTalkingPoints;
				
		answers.clear();
				
		int rowOfAnswer = 0;
				
		//Generate a random number that will be used for finding a row from NOC List.
		Random rand = new Random();
		int randomRowNumber = rowOfAnswer = rand.nextInt(NOC_LIST_LINE_COUNT);
				
		Workbook workbook = WorkbookFactory.create(new File(NOC_LIST_PATH));
				
		//Find a row that has a domain, category and a postive and negative talking point
		while(workbook.getSheetAt(0).getRow(rowOfAnswer).getCell(13,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString().equals("") &&
			  workbook.getSheetAt(0).getRow(rowOfAnswer).getCell(21,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString().equals("") &&
			  workbook.getSheetAt(0).getRow(rowOfAnswer).getCell(22,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString().equals("") &&
			  workbook.getSheetAt(0).getRow(rowOfAnswer).getCell(23,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString().equals("") ) {
			 //Generate new row numbers
			 rowOfAnswer = rand.nextInt(NOC_LIST_LINE_COUNT);
		}
		
		
		answer = workbook.getSheetAt(0).getRow(rowOfAnswer).getCell(0).toString().trim();
		answers.add(answer);
		
		//Gather the choices of information for forming the question and randomly select from them.
		if(!workbook.getSheetAt(0).getRow(rowOfAnswer).getCell(13,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString().equals("")){
			listOfDomains = Arrays.asList(workbook.getSheetAt(0).getRow(rowOfAnswer).getCell(13).toString().split(", "));
			domain = listOfDomains.get(rand.nextInt(listOfDomains.size()));
		}
		if(!workbook.getSheetAt(0).getRow(rowOfAnswer).getCell(21,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString().equals("")){
			listOfCategories = Arrays.asList(workbook.getSheetAt(0).getRow(rowOfAnswer).getCell(21).toString().split(", "));
			category = listOfCategories.get(rand.nextInt(listOfCategories.size()));

		}
		if(!workbook.getSheetAt(0).getRow(rowOfAnswer).getCell(22,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString().equals("")){
			listOfPositiveTalkingPoints = Arrays.asList(workbook.getSheetAt(0).getRow(rowOfAnswer).getCell(22).toString().split(", "));
			positiveTalkingPoint = listOfPositiveTalkingPoints.get(rand.nextInt(listOfPositiveTalkingPoints.size()));

		}
		if(!workbook.getSheetAt(0).getRow(rowOfAnswer).getCell(23,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString().equals("")){
			listOfNegativeTalkingPoints = Arrays.asList(workbook.getSheetAt(0).getRow(rowOfAnswer).getCell(23).toString().split(", "));
			negativeTalkingPoint = listOfNegativeTalkingPoints.get(rand.nextInt(listOfNegativeTalkingPoints.size()));

		}


		//Get 3 more random characters as options for answers
		int i=0;
		while(i < 3){
	        	randomRowNumber = rand.nextInt(NOC_LIST_LINE_COUNT);
	        	
	        	if(randomRowNumber != rowOfAnswer){
	           		answers.add(workbook.getSheetAt(0).getRow(randomRowNumber).getCell(0).toString());
	           		i++;
	           	}
	    }
		
		//Form the question.
		question += domain+" and you see a "+negativeTalkingPoint+" but "+positiveTalkingPoint+" "+category;
				
		question += ". Is it";
						
	}
	
	public void createMCQPanel(Player player, HistoryLog history, Jail jail) throws InvalidFormatException, IOException{
		
		Random rand = new Random();
		player.setAnsweringMCQ(true);
		int mcqAmount = rand.nextInt(301)+200;
		if(jail==null) {
			history.getTextArea().append("-> Answer the following question to win or lose $"+mcqAmount+"\n\n");
		}
		
		int questionChoiceNumber = rand.nextInt(noOfQuestions);
		
		switch(questionChoiceNumber){
		case 0:
			createGenderWeaponQuestion();
			break;
		case 1:
			createVehicleQuestion();
			break;
		case 2:
			createAddressAndTalkingPointQuestion();
			break;
		case 3:
			createTypicalActivityQuestion();
			break;
		case 4:
			createWeaponArchNemesisQuestion();
			break;
		case 5:
			createOpponentQuestion();
			break;
		case 6:
			createSeenWearingGenreQuestion();
			break;
		case 7:
			createPortrayedBy();
			break;
		case 8: 
			creatorAndCreation();
			break;
		case 9: 
			domainCategoryTalkingPointQuestion();
			break;
		}
		
		JLabel questionLabel = new JLabel(question);
		
		//JPanel and it's Components of used to present the question
		JPanel mcqPanel = new JPanel();
	    ButtonGroup group = new ButtonGroup();
	    JRadioButton firstChoice = new JRadioButton("A: "+answers.get(0));
	    JRadioButton secondChoice = new JRadioButton("B: "+answers.get(1));
	    JRadioButton thirdChoice = new JRadioButton("C: "+answers.get(2));
	    JRadioButton fourthChoice = new JRadioButton("D: "+answers.get(3));
	    
	    JRadioButton choices[] = {firstChoice, secondChoice, thirdChoice, fourthChoice}; 
	    
	    //Determine which button has the correct answer.
	    int i=0;
	    boolean answerFound = false;
	    while(i < 4 && !answerFound){
	    	if(answer.equals(answers.get(i))){
	    		answerFound = true;
	    	}
	    	else{
	    		i++;
	    	}
	    }
	    
	    //Create reference of the correct button
	    JRadioButton correctButton = choices[i];
	    
	    //Group the radio buttons
	    group.add(firstChoice);
	    group.add(secondChoice);
	    group.add(thirdChoice);
	    group.add(fourthChoice);
	    
	    //Create the button for choice confirmation.
	    JButton confirmationButton = new JButton("Confirm");
		
	    //Will confirm if user has selected the correct answer
		 confirmationButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	boolean jailCorrect = false;
	            	player.setAnsweringMCQ(false);
	                if (correctButton.isSelected()) {
	                	history.getTextArea().append("-> Correct!\n");
	                	if(jail==null) {
	                		history.getTextArea().append("-> You won $"+mcqAmount+"\n\n");
		        			player.addToBalance(mcqAmount);
	                	}
	                	else {
	                		jail.correctAnswer();
	                		if(jail.getQsToAnswer()==0) {
	                			history.getTextArea().append("-> All questions answered! You're free to go!\n\n");
	                			player.setInJail(false);
	                			player.setRolled(false);
	                		}
	                		else {
		                		try {
		                			jailCorrect = true;
		                			mcqFrame.dispose();
		                			Thread.sleep(10);
		                			MCQ mcq = new MCQ();
									mcq.createMCQPanel(player, history, jail);
								} catch (InvalidFormatException | IOException | InterruptedException e1) {
									e1.printStackTrace();
								}
	                		}
	                	}
	                } else {
	                	history.getTextArea().append("-> Incorrect!\n");
	                	if(jail==null) {
	                		history.getTextArea().append("-> You lost $"+mcqAmount+"\n\n");
		        			player.deductFromBalance(mcqAmount);
	                	}
	                	else {
	                		jail.turnInJail();
	                	}
	                }
	              
	                if(!jailCorrect) mcqFrame.dispose();
	            }
	        });
	    
	    
	    //Put the radio buttons in a column in a panel.
        JPanel radioPanel = new JPanel(new GridLayout(0, 1));
        radioPanel.add(questionLabel);
        radioPanel.add(firstChoice);
        radioPanel.add(secondChoice);
        radioPanel.add(thirdChoice);
        radioPanel.add(fourthChoice);
        radioPanel.add(confirmationButton);
 
        mcqPanel.add(radioPanel, BorderLayout.LINE_START);
        mcqPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        mcqPanel.setVisible(true);
        
        mcqFrame.add(mcqPanel);
        mcqFrame.setVisible(true);
        mcqFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        mcqFrame.pack();
        mcqFrame.setLocationRelativeTo(null);
        
        return;
	}

}