import java.io.IOException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import panopoly.CardGenerator;
import panopoly.MCQ;

public class Main {
	
	public static void main(String args[]) throws InvalidFormatException, IOException {
		new GUI();
	
		/*NocListReader nocListReader = new NocListReader();
		nocListReader.readExcelFile();*/
		
		MCQ mcq = new MCQ();
		mcq.createMCQ();
		
		CardGenerator cardGenerator = new CardGenerator();
		cardGenerator.createCard();
		
	}

}
