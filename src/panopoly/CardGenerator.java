package panopoly;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Random;

import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class CardGenerator {
	
    public static final String NOC_LIST_FILE_PATH = "C:/Users/Rowley/git/panopoly/Veale's NOC List/Veale's The NOC List.xlsx";
    public static final String LOCATION_LISTING_FILE_PATH = "C:/Users/Rowley/git/panopoly/Veale's NOC List/Veale's location listing.xlsx";


  /*  Sample card format:
   *  "You enter <determiner> <location>. It's <ambience>. You're <interactions> <character from NOC List>.
   *  <They/you> hit <you/them> with <props>. <enter reward or loss>. "
   *  NOTE: If 'They hit you....' this is possibly a loss.
   *  		If 'You hit them....' this is possibly a gain.
*/	public void createCard() throws IOException, InvalidFormatException{
		String determiner, location, interactions, character, ambience, props, finalOutcome;
		determiner = location = interactions = character = ambience = props = finalOutcome = "";
		
		//Note 'location listings' has approx. 220 lines, but only up to line 28 is filled in for all cells.
		final int locationListingLineTotal = 28;
		final int nocListLineTotal = 805;
		
		
		final String rewardPrecondition= "You hit them";
		final String reward = "You've gained 200 million Rwandan francs.";
		
		final String penaltyPrecondition = "They hit you";
		final String penalty = "You've lost all your BTC due to North Korean hackers.";
				
		Random rand = new Random();
		
		Workbook locationListingWb = WorkbookFactory.create(new File(LOCATION_LISTING_FILE_PATH));
		
		Sheet sheet = locationListingWb.getSheetAt(0);
	    Row row = sheet.getRow(rand.nextInt(locationListingLineTotal));
	    Cell cell;
	    String cellValue;
	    DataFormatter dataFormatter = new DataFormatter();
	    
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
            case "A":
            	location = cellValue;
            	break;
            case "C":
            	determiner = cellValue;
            	break;
            case "F":
            	ambience = cellValue;
            	break;
            case "G":
            	interactions = cellValue;
            	break;
            case "H":
            	props = cellValue;
            	break;
            }
                        
        }
        
		Workbook nocListWb = WorkbookFactory.create(new File(NOC_LIST_FILE_PATH));
		sheet = nocListWb.getSheetAt(0);
		
		//Generate a random row number.
    	int randomNumber = rand.nextInt(nocListLineTotal);
    	
    	//Get sheet and get name (column 1) for character name
    	row = sheet.getRow(randomNumber);
    	cell = row.getCell(1);
   		character = dataFormatter.formatCellValue(cell);
   		
   		//Begin to build final outcome of generation.
   		finalOutcome += "You enter "+determiner+" "+location+".\nIt's "+ambience+
	            "\nYou're "+interactions+" "+character+".\n";
   		
   	
   		
   		/*	Determine if 'They' or 'You' comes first using random number between 0-1.
   		If 'They' comes first 'You' comes second and vice versa
   		 */
   		randomNumber = (int) Math.round(Math.random());
   		
   		if(randomNumber == 0){
   			finalOutcome += rewardPrecondition+" with "+props+".\n"+reward;
   		}
   		else{
   			finalOutcome += penaltyPrecondition+" with "+props+".\n" + penalty;
   		}
   		
    
        System.out.println(finalOutcome);
		
}
}
