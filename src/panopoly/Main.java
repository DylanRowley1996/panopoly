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
    	String[] properties = {"EMPTY PROP"};
    	String[] monopolies = {"EMPTY MONOP"};
    	String[] mortgages = {"EMPTY MORT"};
    	ArrayList<Player> players = new ArrayList<Player>();
    	for(int i=0;i<5;i++){
    		//String name,int netWorth, String[] properties, String[] monopolies, String[] mortgages, String pathToIcon
    		players.add(new Player("Dylan",0,properties,monopolies,mortgages,""));
    	}
    	
    	//SelectionPanel panel = new SelectionPanel(players);
    	

         
         
        SetupGame gameSetup = new SetupGame();
        
         
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
