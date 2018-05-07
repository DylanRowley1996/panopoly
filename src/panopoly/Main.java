package panopoly;
import java.io.IOException;
import java.net.URISyntaxException;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

public class Main {
	public static void main(String[] args) throws EncryptedDocumentException, InvalidFormatException, IOException, URISyntaxException, InterruptedException {

	
		StartingScreen startScreen = new StartingScreen();
		
		while(!startScreen.getNumSelected()){
			Thread.sleep(5);
		}
		
		@SuppressWarnings("unused")
		SetupGame gameSetup = new SetupGame(startScreen.getNoOfPlayers());
		
	}
}
