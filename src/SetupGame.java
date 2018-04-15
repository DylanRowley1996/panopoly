import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.util.List;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import locations.*;

public class SetupGame {
	
	private static int currentPlayerNumber = 1;

	private static final String NOC_LIST_FILE_PATH = "Veale's NOC List/Veale's The NOC List.xlsx";
	private static final String DOMAIN_FILE_PATH = "Veale's NOC List/Veale's domains.xlsx";
	private static final String WORLDS_FILE_PATH = "Veale's NOC List/Veale's Fictional Worlds.xlsx";

	private static final int DOMAIN_LINE_TOTAL = 614;
	private static final int WORLDS_LINE_TOTAL = 242;

	private static final int noOfPlayers = 6;
	private ArrayList<ArrayList<String>> charactersAndThemes = new ArrayList<ArrayList<String>>(noOfPlayers);
	private ArrayList<String> characters = new ArrayList<String>();
	private ArrayList<NamedLocation> locationList = new ArrayList<NamedLocation>(); // TODO maybe change to ArrayList of ArrayLists for multiple boards


	public SetupGame(){

	}

	//Randomly generate a list of unique themes depending on the number of players.
	public ArrayList<String> findThemes(int c, int wB) throws EncryptedDocumentException, InvalidFormatException, IOException{


		ArrayList<String> listOfThemes = new ArrayList<String>();
		boolean themesFound = false;
		boolean newThemeFound = false;
		Workbook locationListingWb;

		Random rand = new Random();

		if(wB==0)	locationListingWb = WorkbookFactory.create(new File(DOMAIN_FILE_PATH));
		else		locationListingWb = WorkbookFactory.create(new File(WORLDS_FILE_PATH));	
		Sheet sheet = locationListingWb.getSheetAt(0);
		Row row;
		// DataFormatter dataFormatter = new DataFormatter();

		while(!themesFound){

			//Retrieve a random row.
			if(wB==0)	row = sheet.getRow(rand.nextInt(DOMAIN_LINE_TOTAL));
			else		row = sheet.getRow(rand.nextInt(WORLDS_LINE_TOTAL));

			//Retrieve the specific domain column from that row.

			Cell cell = row.getCell(c);

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

		if(listOfThemes.size() == 0) System.out.println("\nBROKEN!\n");
		return listOfThemes;
	}

	//Creates new .xlsx files that will store characters from certain themes. 
	public void findCharactersFromThemes(ArrayList<String> themes) throws EncryptedDocumentException, InvalidFormatException, IOException{

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

		//System.out.println("Size of characters and themes: "+charactersAndThemes.size()+"\n");
		for(int k=0;k<charactersAndThemes.size();k++){
			for(int x=0;x<charactersAndThemes.get(k).size()-1;x++){
				//if((x+1) < charactersAndThemes.get(k).size()){
				/*System.out.println("k: "+k+", x: "+x+"\n");
					System.out.println("Current Theme: "+charactersAndThemes.get(k).get(0));
					System.out.println(" Current Character: "+charactersAndThemes.get(k).get(x+1)+"\n\n");	*/
				//}
    		}
    	}
    }
    
    public void compileChoiceOfCharacters(){    	
    	Random rand = new Random();
    	int i = 0;
    	
    	while(characters.size() != noOfPlayers){
    		  int choice = rand.nextInt(charactersAndThemes.get(i).size());
    		  
    		  if(choice == 0) choice  += 1;
    		  if(!characters.contains(charactersAndThemes.get(i).get(choice))){
    			  System.out.println("Current Theme: "+charactersAndThemes.get(i).get(0)+" Current Character:"+charactersAndThemes.get(i).get(choice)+"\n");
    			  characters.add(charactersAndThemes.get(i).get(choice));
    			  i++;
    		  }
    	}
   }
    
	public void launchSelectionPanel() throws IOException{
    	
    	JPanel characterPanel = new JPanel(new GridBagLayout());
    	JButton[] imageButtons = new JButton[noOfPlayers];
    	JFrame selectionPanel = new JFrame();
    	JLabel informationArea = new JLabel("The label",SwingConstants.CENTER);
    	
    	selectionPanel.setLayout(new GridBagLayout());
    	GridBagConstraints c = new GridBagConstraints();

    	//https://stackoverflow.com/questions/3360255/how-to-get-a-single-file-from-a-folder-in-java
        File dir = new File("C:/Users/Rowley/git/panopoly/savedImages");
        File[] children = dir.listFiles();
        
        //Ensures all images are resized evenly.
        c.weightx = .5;
        c.weighty = .5;
        
        /*
         * Images are obtained from /savedImages.
         * These are then resized and added to the buttons.
         * Each button added to the JPanel.
         */
    	for(int i=0;i<noOfPlayers;i++){
    		c.fill = GridBagConstraints.HORIZONTAL;
    		c.gridx = i;
    		c.gridy = 0;
    		imageButtons[i] = new JButton();
    		BufferedImage myPicture = ImageIO.read(new File(children[i].toString()));
    		Image myResizedPicture = myPicture.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        	imageButtons[i].setIcon(new ImageIcon(myResizedPicture));
        	characterPanel.add(imageButtons[i],c);
    	}
    	
    	//Constraints for JLabel that presents character selection info.
        c.fill = GridBagConstraints.BOTH;
    	c.gridx = 0;
    	c.gridy = 1;
    	
    	//Ensures the JLabel spans all columns when beneath the images.
    	c.gridwidth = noOfPlayers;
    
    	informationArea.setText("Click an image to select a character for player: "+(currentPlayerNumber));
    	
    	//Add action listeners to all images.
    	for(int i=0;i<noOfPlayers;i++){
    		
    		//https://stackoverflow.com/questions/33799800/java-local-variable-mi-defined-in-an-enclosing-scope-must-be-final-or-effective
    	    final Integer innerI = new Integer(i);
    	    
    		imageButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	
                	informationArea.setText("Click an image to select a character for player: "+(currentPlayerNumber+1));
                	currentPlayerNumber++;
                	
                	//Remove the corresponding button if character is chosen.
                	characterPanel.remove(imageButtons[innerI]);
                	
                	//Repaint JFrame so removed button is present to user.
                	selectionPanel.repaint();
                	
                	//When all characters are chosen, close JFrame.
                	if(currentPlayerNumber == 7){
                		selectionPanel.dispose();
                	}
                }
    		});
       }
    	
    	characterPanel.add(informationArea,c);//Add information about selecting characters under buttons with images
    	selectionPanel.add(characterPanel);//Add this to JFrame.
    	selectionPanel.setPreferredSize(new Dimension(1125,150));
    	selectionPanel.pack();
    	selectionPanel.setLocationRelativeTo(null);//Centers JFrame on users screen.
    	selectionPanel.setVisible(true);
    	selectionPanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
 }
		    
    public ArrayList<String> getCharacters(){
    	return characters;
    }
    
	public void setUpLocations(ArrayList<String> themes) throws EncryptedDocumentException, InvalidFormatException, IOException {
		Random rand = new Random();
		ArrayList<ArrayList<String>> locationsByTheme = new ArrayList<ArrayList<String>>();

		//Open the Fictional Worlds list for reading.
		ZipSecureFile.setMinInflateRatio(0.005);
		Workbook worldsListWb = WorkbookFactory.create(new File(WORLDS_FILE_PATH));
		Sheet worldsListSheet = worldsListWb.getSheetAt(0);
		//Iterator<Row> rowIterator = worldsListSheet.iterator();

		for(int i=0;i<themes.size();i++) {
			//System.out.println("Enter loop 1");
			int locationsSelected = 0;
			locationsByTheme.add(new ArrayList<String>());
			locationsByTheme.get(i).add(themes.get(i));

			while(locationsSelected<3) {
				//System.out.println("Enter loop 2");
				Row row = worldsListSheet.getRow(rand.nextInt(WORLDS_LINE_TOTAL));

				if(row.getCell(1).getStringCellValue().contains(themes.get(i))) {
					locationsByTheme.get(i).add(row.getCell(0).getStringCellValue());
					locationsSelected++;
				}
			}
			//System.out.println("Exit loop 2");

		}
		//System.out.println("Exit loop 1");
		worldsListWb.close();

		locationList.add(new NamedLocation("Go", 0));
		locationList.add(new ImprovableProperty(locationsByTheme.get(0).get(1), 1, 150, 75, 35, locationsByTheme.get(0).get(0)));
		locationList.add(new ImprovableProperty(locationsByTheme.get(0).get(2), 2, 170, 80, 40, locationsByTheme.get(0).get(0)));
		locationList.add(new TaxableLocation("Communism Spread The Wealth Tax", 2, 0.2, 250));
		locationList.add(new ImprovableProperty(locationsByTheme.get(0).get(3), 3, 165, 75, 30, locationsByTheme.get(0).get(0)));
		locationList.add(new ImprovableProperty(locationsByTheme.get(1).get(1), 4, 300, 135, 65, locationsByTheme.get(1).get(0)));
		locationList.add(new ImprovableProperty(locationsByTheme.get(1).get(2), 5, 280, 100, 80, locationsByTheme.get(1).get(0)));
		locationList.add(new Station(locationsByTheme.get(5).get(1) + " Station", 6, 200, 90, 25, null, locationsByTheme.get(5).get(0)));
		locationList.add(new ImprovableProperty(locationsByTheme.get(1).get(3), 7, 1650, 75, 30, locationsByTheme.get(1).get(0)));
		locationList.add(new ImprovableProperty(locationsByTheme.get(2).get(1), 8, 300, 135, 65, locationsByTheme.get(2).get(0)));
		locationList.add(new ImprovableProperty(locationsByTheme.get(2).get(2), 9, 280, 100, 80, locationsByTheme.get(2).get(0)));
		locationList.add(new Utility(locationsByTheme.get(5).get(2) + " Utility", 10, 200, 90, 25, null, locationsByTheme.get(5).get(0)));
		locationList.add(new ImprovableProperty(locationsByTheme.get(2).get(3), 11, 1650, 75, 30, locationsByTheme.get(2).get(0)));
		locationList.add(new ImprovableProperty(locationsByTheme.get(3).get(1), 12, 300, 135, 65, locationsByTheme.get(3).get(0)));
		locationList.add(new ImprovableProperty(locationsByTheme.get(3).get(2), 13, 280, 100, 80, locationsByTheme.get(3).get(0)));
		locationList.add(new Station(locationsByTheme.get(5).get(3) + " Station", 14, 200, 90, 25, null, locationsByTheme.get(5).get(0)));
		locationList.add(new ImprovableProperty(locationsByTheme.get(3).get(3), 15, 1650, 75, 30, locationsByTheme.get(3).get(0)));
		locationList.add(new Shop("Marketplace", 16));

		//set left and right locations
		for(int i=1; i<locationList.size()-1; i++) {
			NamedLocation loc = locationList.get(i);
			loc.setLeft(locationList.get(i+1));
			loc.setRight(locationList.get(i-1));
		}
		locationList.get(0).setLeft(locationList.get(1));
		locationList.get(0).setRight(locationList.get(locationList.size()-1));
		locationList.get(locationList.size()-1).setLeft(locationList.get(0));
		locationList.get(locationList.size()-1).setRight(locationList.get(locationList.size()-2));

	}

	public void testLocations() { // TODO Remove
		System.out.println("\n");
		for(NamedLocation loc : locationList) {
			if(loc instanceof PrivateProperty ) System.out.print(((PrivateProperty) loc).getGroup() + ": ");
			System.out.println(loc.getIdentifier());
		}
		System.out.println("\n");
	}
	
}

