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

public class MCQ {
	
    public static final String SAMPLE_XLSX_FILE_PATH = "C:/Users/Rowley/git/panopoly/Veale's NOC List/Veale's The NOC List.xlsx";

	public String createMCQ() throws IOException, InvalidFormatException{
		
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
        Workbook workbook = WorkbookFactory.create(new File(SAMPLE_XLSX_FILE_PATH));
		
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
	
}
