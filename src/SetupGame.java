import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;
import java.util.List;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class SetupGame {

    private static final String NOC_LIST_FILE_PATH = "Veale's NOC List/Veale's The NOC List.xlsx";
    private static final String DOMAIN_FILE_PATH = "Veale's NOC List/Veale's domains.xlsx";
    
    private static final int DOMAIN_LINE_TOTAL = 614;
    
    private static final int noOfPlayers = 6;
    private ArrayList<ArrayList<String>> charactersAndThemes = new ArrayList<ArrayList<String>>(noOfPlayers);
    
    public SetupGame(){
    	
    }

    //Randomly generate a list of unique themes depending on the number of players.
    public ArrayList<String> findThemes() throws EncryptedDocumentException, InvalidFormatException, IOException{
    	
    	
    	ArrayList<String> listOfThemes = new ArrayList<String>();
    	boolean themesFound = false;
    	boolean newThemeFound = false;
    	
    	Random rand = new Random();
    	
    	Workbook locationListingWb = WorkbookFactory.create(new File(DOMAIN_FILE_PATH));
		Sheet sheet = locationListingWb.getSheetAt(0);
	   // DataFormatter dataFormatter = new DataFormatter();
	    
	    while(!themesFound){
	    	
	    	//Retrieve a random row.
	    	 Row row = sheet.getRow(rand.nextInt(DOMAIN_LINE_TOTAL));
	    	 
	    	 //Retrieve the specific domain column from that row.
	    	 Cell cell = row.getCell(0);
	    	 
	    	 //Get the specific domain as a string.
	    	 List<String> themes = Arrays.asList(cell.getStringCellValue().split(", "));
	    	 
	    	 for(int k=0;k<themes.size();k++){
					themes.set(k, themes.get(k).trim());
			 }
	    	 
	    	 int i = 0;
	    	 while(!newThemeFound && i < themes.size()){
	    		 System.out.println("Current theme: "+themes.get(i)+"\n");
	    		 if(!listOfThemes.contains(themes.get(i))){
	    			 listOfThemes.add(themes.get(i));
	    			 newThemeFound = true;
	    		 }
	    		 else{
	    			 i++;
	    		 }
	    	 }
	    	 
	    	 newThemeFound = false;
	    	 
	    	 //If we've enough unique themes then make sure loop exits.
	    	 if(listOfThemes.size() == noOfPlayers) themesFound = true;
	    		 
	    }
	    
	    locationListingWb.close();
	    
        return listOfThemes;
   }
    
    //Creates new .xlsx files that will store characters from certain themes. 
    public void createAndPopulateFiles(ArrayList<String> themes) throws EncryptedDocumentException, InvalidFormatException, IOException{
    	
    	//Add themes to list.
    	for(int i=0;i<themes.size();i++){
    		charactersAndThemes.add(new ArrayList<String>());
    		charactersAndThemes.get(i).add(themes.get(i));
    	}
    	

    	//Create new workbooks that correspond to the themes found.
    	for(int i=0;i<themes.size();i++){
  
    		//Open the NOC list for reading.
    		ZipSecureFile.setMinInflateRatio(0.005);
    		Workbook nocListWb = WorkbookFactory.create(new File(NOC_LIST_FILE_PATH));
    		Sheet nocListSheet = nocListWb.getSheetAt(0);
    		Iterator<Row> rowIterator = nocListSheet.iterator();
    		
    		/*
    		 * Iterate through all rows of the NOC list.
    		 * If a row matches a domain we've chosen, place that character into
    		 * an .xlsx file composing of characters from that domain
    		 * 
    		 */
    		while(rowIterator.hasNext()){
    			
    			Row row = rowIterator.next();
    			
    			//System.out.println("On row: "+l++);
    			
    			List<String> domainsOfCurrentRow = null;
    			
    			if(row.getCell(13) != null){
    				
    				domainsOfCurrentRow = Arrays.asList(row.getCell(13).getStringCellValue().split(", "));
    				
    				for(int k=0;k<domainsOfCurrentRow.size();k++){
    					domainsOfCurrentRow.set(k, domainsOfCurrentRow.get(k).trim());
    				}
    				
    				/*
        			 * If one of the current rows domains equals one of the current domains we've chosen,
        			 * add the character to the given .xlsx file
        			 */
    				if(domainsOfCurrentRow.contains(themes.get(i))){
        				charactersAndThemes.get(i).add(row.getCell(0).getStringCellValue());
        			}
    				
    			}
    			    			 			
    		}

    	}
    	
    	System.out.println("Size of characters and themes: "+charactersAndThemes.size()+"\n");
		for(int k=0;k<charactersAndThemes.size();k++){
			for(int x=0;x<charactersAndThemes.get(k).size()-1;x++){
				//if((x+1) < charactersAndThemes.get(k).size()){
					System.out.println("k: "+k+", x: "+x+"\n");
					System.out.println("Current Theme: "+charactersAndThemes.get(k).get(0));
					System.out.println(" Current Character: "+charactersAndThemes.get(k).get(x+1)+"\n\n");	
				//}
    		}
    	}
    }
    
   
}
