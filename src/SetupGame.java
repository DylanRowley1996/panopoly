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
    
    private static final int NOC_LIST_LINE_TOTAL = 805;
    private static final int DOMAIN_LINE_TOTAL = 614;
    
    private static final int noOfPlayers = 6;
    
    public SetupGame(){
    	
    }

    //Randomly generate a list of unique themes depending on the number of players.
    public ArrayList<String> findThemes() throws EncryptedDocumentException, InvalidFormatException, IOException{
    	
    	
    	ArrayList<String> listOfThemes = new ArrayList<String>();
    	String theme = "";
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
	    	 
	    	 //System.out.println("This theme is "+theme+"\n");
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
	    	 
	    	 //Make sure the theme hasn't already been added.
	    	// if(!listOfThemes.contains(theme)) listOfThemes.add(theme);
	    	 
	    	 //If we've enough unique themes then make sure loop exits.
	    	 if(listOfThemes.size() == noOfPlayers) themesFound = true;
	    		 
	    }
	    
	    locationListingWb.close();
	    
        return listOfThemes;
   }
    
    //Creates new .xlsx files that will store characters from certain themes. 
    public void createAndPopulateFiles(ArrayList<String> themes) throws EncryptedDocumentException, InvalidFormatException, IOException{
    	

    	//Create new workbooks that correspond to the themes found.
    	for(int i=0;i<themes.size();i++){
    		
    		int j = 0;
    		int l = 0;
    		
    		Workbook wb = new XSSFWorkbook();
    		Sheet sheet = wb.createSheet("Character Sheet");
    		//CreationHelper createHelper = wb.getCreationHelper();
    		
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
    					//System.out.println(domainsOfCurrentRow.get(k));
    				}
    				
    				/*
        			 * If one of the current rows domains equals one of the current domains we've chosen,
        			 * add the character to the given .xlsx file
        			 */
    				//sheet.createRow(j++).createCell(0).setCellValue(row.getCell(0).getStringCellValue());

					//System.out.println("We about to attempt to match a domain");
    				if(domainsOfCurrentRow.contains(themes.get(i))){
    					System.out.println("We have found a matching domain.");
        				sheet.createRow(j++).createCell(0).setCellValue(row.getCell(0).getStringCellValue());
        						//createHelper.createRichTextString(row.getCell(0).getStringCellValue()));
        			//	System.out.println("The value of this column is: "+sheet.getRow(j-1).getCell(0).getStringCellValue());
        			}
    				
    			}
    			
    			//domainsOfCurrentRow.clear();
    			 			
    		}
    		
    	    FileOutputStream fileOut = new FileOutputStream("C:/Users/Rowley/git/panopoly/Resources/"+themes.get(i)+".xlsx");
    	    wb.write(fileOut);
    	    fileOut.close(); 
    	    nocListWb.close();
    	}
    	
    }
    
    
    
    //Finds a list of characters from a given set of themes.
    public ArrayList<String> createListsOfCharactersFromThemes(ArrayList<String> themes) throws EncryptedDocumentException, InvalidFormatException, IOException{
    	    	
    	ArrayList<String> characters = new ArrayList<String>();
    	boolean charactersFound = false;
    	String theme = "";
    	
    	Random rand = new Random();
    	
    	Workbook locationListingWb = WorkbookFactory.create(new File(NOC_LIST_FILE_PATH));
    	
		Sheet sheet = locationListingWb.getSheetAt(0);
		
    	Iterator<Row> rowIterator = sheet.iterator();
    	
    	while(rowIterator.hasNext()){
    		Row row = rowIterator.next();
    		row.getCell(13);
    	}

		
		while(!charactersFound){
			
			System.out.println("Entered while loop\n");
			
			//Retrieve a random row.
	    	 Row row = sheet.getRow(rand.nextInt(DOMAIN_LINE_TOTAL));
	    	 
	    	 //Retrieve the 'Specific domain' column from that row.
	    	 Cell cell = row.getCell(13);
	    	 
	    	 //Get the specific domain as a string.
	    	 theme = cell.getStringCellValue();
	    	 
	    	 List<String> listOfThemes =  Arrays.asList(theme.split(","));	    	 
	    	 
	    	 
	    	 /*
	    	  * If we find a row where the theme matches one of the given themes,
	    	  * find the character from that row and add to a list.
	    	  */
	    	 for(int i=0;i<listOfThemes.size();i++){
	    		 
	    		 theme = listOfThemes.get(i);
	    		 
	    		 if(themes.contains(theme)){
		 	    		
	 	    		//Add character to the list of characters
	 	    		characters.add(row.getCell(0).getStringCellValue());
	 	    		
	 	    		System.out.println("Found character name: "+row.getCell(0).getStringCellValue()+
	 	    							"From theme "+ theme+"\n");

	 	    		
	 	    		//Remove the theme from the list so we don't find another character from that theme.
	 	    		themes.remove(theme);
	 	    	 }
	    	 }
	    	 
	    	 
	    	 if(characters.size() == noOfPlayers) charactersFound = true;
			
		}
		
		return characters;
    	
    }
    
}
