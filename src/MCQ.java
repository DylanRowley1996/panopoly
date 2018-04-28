import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
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
   

	public String createMCQ() throws IOException, InvalidFormatException{
		
		ZipSecureFile.setMinInflateRatio(0.005);
		
		//Stores potential answers for question.
		String[] names = new String[4];
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
            	names[0] = cellValue;
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
        		names[i+1] = cellValue;
        		i++;
        	}
        }
        	
        
        //Format the question.
        System.out.println("Question: You see a "+gender+" with a "+weapon+". Is it:");
        System.out.println("A. "+names[0]+"\n"+
        				   "B. "+names[1]+"\n"+
        				   "C. "+names[2]+"\n"+
        				   "D. "+names[3]+"\n");
		
		
		
		return "";
	}
	
	public String createVehicleQuestion() throws EncryptedDocumentException, InvalidFormatException, IOException{
		
		answers.clear();
		
		//Prevent ZIP BOMB
		ZipSecureFile.setMinInflateRatio(0.005);
		
		String[] potentialQuestions = {"You see a", "You just got run over by a", "You're offered a lift by a"};
		
		//Stores potential answers for question.
		//String[] names = new String[4];
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
                  
        return "Answer is: "+answer+"\nQuestion: "+potentialQuestions[rand.nextInt(potentialQuestions.length)]+" "+gender+" "+affordance+" "+determiner+" "+vehicle+". Is it: \nA. "+answers.get(0)+"\n"+
				  "B. "+answers.get(1)+"\n"+
				  "C. "+answers.get(2)+"\n"+
				  "D. "+answers.get(3)+"\n";
    	
	}
	
	//Question format: You're at <address> and you see a <positive/negative talking point> <gender>. Who is it?
	public String createAddressAndTalkingPointQuestion() throws EncryptedDocumentException, InvalidFormatException, IOException{
		
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
        
        System.out.println("Answer: "+answer);
        return "You're at "+address+"and you see a "+talkingPoint+" "+gender+".\nIs it: \n"
        		+ "A: "+answers.get(0) + "\n"
        		+ "B: "+answers.get(1) + "\n"
        		+ "C: "+answers.get(2) + "\n"
         		+ "D: "+answers.get(3) + "\n";
	}
	
	//Column j is typical activity
	//Question format: You see someone <typical activity>
	public String createTypicalActivityQuestion() throws EncryptedDocumentException, InvalidFormatException, IOException{
		
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
    	
        System.out.println("Answer: "+answer);
		return "You see someone "+activity+"\nIs it: \n"
		+ "A: "+answers.get(0) + "\n"
		+ "B: "+answers.get(1) + "\n"
		+ "C: "+answers.get(2) + "\n"
 		+ "D: "+answers.get(3) + "\n";
	}
	
	//Question: You see someone shooting <arch nemesis> with a <weapon>
	//Question: You see someone <affordance> <arch nemesis> with a <weapon>
	//Determiner: a	Weapon: .22 caliber Colt	Affordances: shooting with, pistol-whipping with
	public String createWeaponArchNemesisQuestion() throws EncryptedDocumentException, InvalidFormatException, IOException{
		
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
		
		List<String> affordancesParts = Arrays.asList(affordance.split(" "));

		//TODO - REmove when tested
		/*System.out.println("Arch nemesis: "+archNemesis);
		System.out.println("Weapon of choice: "+weapon);
		System.out.println("Determiner: "+determiner);
		System.out.println("Affordance: "+affordance);*/
		//System.out.println("You see someone "+affordancesParts.get(0)+" "+archNemesis+" "+ affordancesParts.get(1)+" "+determiner+" "+weapon);
		
		
    	
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
	     String question = "";
	     if(affordancesParts.size() == 1){
	    	 question = "You see someone "+affordancesParts.get(0)+" "+archNemesis+" "+determiner+" "+weapon;
	     }
	     else{
	    	 question = "You see someone "+affordancesParts.get(0)+" "+archNemesis+" "+ affordancesParts.get(1)+" "+determiner+" "+weapon;
	     }
	    	
	    System.out.println("Answer: "+answer);
		return question+"\nIs it: \n"
			+ "A: "+answers.get(0) + "\n"
			+ "B: "+answers.get(1) + "\n"
			+ "C: "+answers.get(2) + "\n"
	 		+ "D: "+answers.get(3) + "\n";
				
	}
	
	// Question: You see someone fighting <opponent>. Is it:
	// Answer = Character (cell 1), Opponent = Opponent (cell 9)
	public String createOpponentQuestion() throws EncryptedDocumentException, InvalidFormatException, IOException
	{
		answers.clear();
		
		//Prevent ZIP BOMB
		ZipSecureFile.setMinInflateRatio(0.005);
				
		String[] potentialQuestions = {"You see someone fighting"}; // Need to think of other ways to phrase this
				
		String opponent = "";
						
		int rowOfAnswer = 0;
		
		//Generate a random number that will be used for finding a row from NOC List.
		Random rand = new Random();
		int randomRowNumber = rowOfAnswer = rand.nextInt(NOC_LIST_LINE_COUNT);
		
		Workbook workbook = WorkbookFactory.create(new File(NOC_LIST_PATH));
		
		while(workbook.getSheetAt(0).getRow(rowOfAnswer).getCell(8).toString().equals("")) // Makes sure there is no empty string
		{
			rowOfAnswer = rand.nextInt(NOC_LIST_LINE_COUNT);
        }
		
		answer = workbook.getSheetAt(0).getRow(rowOfAnswer).getCell(0).toString();
        answers.add(answer);
        
        List<String> listOfOpponents = Arrays.asList(workbook.getSheetAt(0).getRow(rowOfAnswer).getCell(8).toString().split(", "));
        opponent =  listOfOpponents.get(rand.nextInt(listOfOpponents.size())).trim();
        
        int i = 1;
        
        while(i < 4)
        {
        		randomRowNumber = rand.nextInt(NOC_LIST_LINE_COUNT);

        		if(randomRowNumber != rowOfAnswer){
            		answers.add(workbook.getSheetAt(0).getRow(randomRowNumber).getCell(0).toString());
            		i++;
            	}
        }
		
        Workbook workbook1 = WorkbookFactory.create(new File(VEHICLE_FLEET_PATH));
        
        Row row;
        Iterator<Row> rowIterator = workbook1.getSheetAt(0).rowIterator();
        
        boolean opponentFound = false;
        
		while (rowIterator.hasNext() && !opponentFound )
		{
        	
        	//Get the next row.
            row = rowIterator.next();
            
            //If the current row contains the car we're looking for, get the necessary info to form a question.
            if(row.getCell(1).toString().trim().equals(opponent))
            {
            		opponentFound = true;
            }
		}        
        
		Collections.shuffle(answers);
            
        return "Answer is: "+answer+"\nQuestion: "+potentialQuestions[rand.nextInt(potentialQuestions.length)]+" "+opponent+". Is it: \nA. "+answers.get(0)+"\n"+
	  	  "B. "+answers.get(1)+"\n"+
	  	  "C. "+answers.get(2)+"\n"+
	  	  "D. "+answers.get(3)+"\n";
	}
	
	public String createSeenWearingQuestion() throws EncryptedDocumentException, InvalidFormatException, IOException
	{
		answers.clear();
		
		//Prevent ZIP BOMB
		ZipSecureFile.setMinInflateRatio(0.005);
				
		String[] potentialQuestions = {"You see someone wearing ", "You see someone looking fantastic in ", "You're envious of someone wearing "}; // Need to think of other ways to phrase this
				
		String seenWearing = "";
		String determiner = "";
						
		int rowOfAnswer = 0;
		
		//Generate a random number that will be used for finding a row from NOC List.
		Random rand = new Random();
		int randomRowNumber = rowOfAnswer = rand.nextInt(NOC_LIST_LINE_COUNT);
		
		Workbook workbook = WorkbookFactory.create(new File(NOC_LIST_PATH));
		
		while(workbook.getSheetAt(0).getRow(rowOfAnswer).getCell(12).toString().equals("")) // Makes sure there is no empty string
		{
			rowOfAnswer = rand.nextInt(NOC_LIST_LINE_COUNT);
        }
		
		answer = workbook.getSheetAt(0).getRow(rowOfAnswer).getCell(0).toString();
        answers.add(answer);
        
        List<String> listOfOutfits = Arrays.asList(workbook.getSheetAt(0).getRow(rowOfAnswer).getCell(12).toString());
        seenWearing =  listOfOutfits.get(rand.nextInt(listOfOutfits.size())).trim();
        
        int i = 1;
        
        while(i < 4)
        {
        		randomRowNumber = rand.nextInt(NOC_LIST_LINE_COUNT);

        		if(randomRowNumber != rowOfAnswer){
            		answers.add(workbook.getSheetAt(0).getRow(randomRowNumber).getCell(0).toString());
            		i++;
            	}
        }
		
        Workbook workbook1 = WorkbookFactory.create(new File(CLOTHING_LINE_PATH));
        
        Row row;
        Iterator<Row> rowIterator = workbook1.getSheetAt(0).rowIterator();
        
        boolean outfitFound = false;
        
		while (rowIterator.hasNext() && !outfitFound )
		{
			//Get the next row.
            row = rowIterator.next();
            
            //If the current row contains the car we're looking for, get the necessary info to form a question.
            if(row.getCell(1).toString().trim().equals(seenWearing))
            {
            		determiner = row.getCell(0).toString();
            		outfitFound = true;
            }
		}        
        
		Collections.shuffle(answers);
        
        return "Answer is: "+answer+"\nQuestion: "+potentialQuestions[rand.nextInt(potentialQuestions.length)]+determiner+" "+seenWearing+". Is it: \nA. "+answers.get(0)+"\n"+
	  	  "B. "+answers.get(1)+"\n"+
	  	  "C. "+answers.get(2)+"\n"+
	  	  "D. "+answers.get(3)+"\n";
	}
	
	public String createPortrayedBy() throws EncryptedDocumentException, InvalidFormatException, IOException
	{
		answers.clear();
		
		//Prevent ZIP BOMB
		ZipSecureFile.setMinInflateRatio(0.005);
				
		String[] potentialQuestions = {"Who plays ", "What actor portrays "}; // Need to think of other ways to phrase this
				
		String character = "";
						
		int rowOfAnswer = 0;
		
		//Generate a random number that will be used for finding a row from NOC List.
		Random rand = new Random();
		int randomRowNumber = rowOfAnswer = rand.nextInt(NOC_LIST_LINE_COUNT);
		
		Workbook workbook = WorkbookFactory.create(new File(NOC_LIST_PATH));
		
		while(workbook.getSheetAt(0).getRow(rowOfAnswer).getCell(0).toString().equals("")) // Makes sure there is no empty string
		{
			rowOfAnswer = rand.nextInt(NOC_LIST_LINE_COUNT);
        }
		
		answer = workbook.getSheetAt(0).getRow(rowOfAnswer).getCell(16).toString();
        answers.add(answer);
        
        List<String> listOfCharacters = Arrays.asList(workbook.getSheetAt(0).getRow(rowOfAnswer).getCell(0).toString().split(", "));
        character =  listOfCharacters.get(rand.nextInt(listOfCharacters.size())).trim();
        
        int i = 1;
        
        while(i < 4)
        {
        		randomRowNumber = rand.nextInt(NOC_LIST_LINE_COUNT);

        		if(randomRowNumber != rowOfAnswer){
            		answers.add(workbook.getSheetAt(0).getRow(randomRowNumber).getCell(0).toString());
            		i++;
            	}
        }
		
        Workbook workbook1 = WorkbookFactory.create(new File(VEHICLE_FLEET_PATH));
        
        Row row;
        Iterator<Row> rowIterator = workbook1.getSheetAt(0).rowIterator();
        
        boolean characterFound = false;
        
		while (rowIterator.hasNext() && !characterFound )
		{     	
        	//Get the next row.
            row = rowIterator.next();
            
            //If the current row contains the character we're looking for, get the necessary info to form a question.
            if(row.getCell(1).toString().trim().equals(character))
            {
            		characterFound = true;
            }
		}        
        
		Collections.shuffle(answers);
            
        return "Answer is: "+answer+"\nQuestion: "+potentialQuestions[rand.nextInt(potentialQuestions.length)]+character+". Is it: \nA. "+answers.get(0)+"\n"+
	  	  "B. "+answers.get(1)+"\n"+
	  	  "C. "+answers.get(2)+"\n"+
	  	  "D. "+answers.get(3)+"\n";
	}
	
	
}
