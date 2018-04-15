import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

public class Main {
    public static void main(String[] args) throws EncryptedDocumentException, InvalidFormatException, IOException, URISyntaxException {
    	
    	 String[] player1Properties = {"UCD", "TRINITY", "DCU"};
         String[] player1Monopolies = {"Red"};
         String[] player1Mortgages = {"TRINITY"};

         String[] player2Properties = {"DIT", "ITCarlow", "SomeOtherShitHole"};
         String[] player2Monopolies = {"Blue"};
         String[] player2Mortgages = {"ITCarlow"};

         String[] player3Properties = {"Dublin", "Monaghan", "Cork"};
         String[] player3Monopolies = {"Green"};
         String[] player3Mortgages = {"Cork"};

         Player player1 = new Player("Dylan", 100, player1Properties,player1Monopolies,player1Mortgages );
         Player player2 = new Player("Enna", 100, player2Properties,player2Monopolies,player2Mortgages );
         Player player3 = new Player("Sean", 100, player3Properties,player3Monopolies,player3Mortgages );

         Player[] players = {player1,player2,player3};
         
         new GUI(players);
         
         SetupGame gameSetup = new SetupGame();
//         gameSetup.findCharactersFromThemes(gameSetup.findThemes(0, 0));
//         gameSetup.compileChoiceOfCharacters();
         
     
//         gameSetup.setUpLocations(gameSetup.findThemes(1, 1));
//         gameSetup.testLocations();
         
        /*Finds images from web using the chosen characters.
         FindImages findImages = new FindImages(gameSetup.getCharacters());
         findImages.Search();
         */
         
         //gameSetup.launchSelectionPanel();
         
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
