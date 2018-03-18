import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import panopoly.MCQ;
import panopoly.NocListReader;

public class Main {
	
	public static void main(String args[]) throws InvalidFormatException, IOException {
		//new GUI();
		
		/*NocListReader nocListReader = new NocListReader();
		nocListReader.readExcelFile();*/
		
		MCQ mcq = new MCQ();
		mcq.createMCQ();
	}

}
