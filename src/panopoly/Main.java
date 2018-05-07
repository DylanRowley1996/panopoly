package panopoly;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

public class Main {
	public static void main(String[] args) throws EncryptedDocumentException, InvalidFormatException, IOException, URISyntaxException {

		// Finds images from web using the chosen characters.
		// FindImages findImages = new FindImages(gameSetup.getCharacters());
		// findImages.Search();

		//SelectionPanel panel = new SelectionPanel(players);
		int noOfPlayers = 6;
		SetupGame gameSetup = new SetupGame(noOfPlayers);
//		StartingScreen startScreen = new StartingScreen();

		/*gameSetup.findCharactersFromThemes(gameSetup.findThemes(0, 0));
         gameSetup.compileChoiceOfCharacters();
         gameSetup.launchSelectionPanel();
		 */
		/* //TODO Remove.
         //Just checking paths to image icons are set correctly.
         Player[] players = gameSetup.createPlayers();
         for(int i=0;i<players.length;i++){
        	System.out.println(players[i].getPathForImageIcon());
         }
		 */





		/*Finds images from web using the chosen characters.
         gameSetup.setUpLocations(gameSetup.findThemes(1, 1));
         gameSetup.testLocations();
         gameSetup.launchSelectionPanel();
>>>>>>> refs/remotes/origin/master

        // CharacterSelection characterSelect = new CharacterSelection(players);

       // ButtonPanel buttons = new ButtonPanel();
       //buttons.showButton();

        // BuyOrAuction bOrA = new BuyOrAuction("WEST-END");




/*
        // set look and feel to the system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }*/

		/*     SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Overview(players).setVisible(true);
            }
        });

        // set look and feel to the system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		 */

		/* SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BuyOrAuction("UCD").setVisible(true);
            }
        });*/
	}
}
