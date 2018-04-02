import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextArea;

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

	@SuppressWarnings("unchecked")
	public void launchSelectionPanel(){
		JFrame selectionPanel = new JFrame();
		JTextArea currentPlayer = new JTextArea();
		JComboBox<String> comboBox = new JComboBox(characters.toArray());
		JButton confirmButton = new JButton("Confirm");

		/*
    	for(int i=0;i<characters.size();i++){
    		comboBox.add(characters.get(i));
    	}*/

		selectionPanel.setLayout(new GridLayout(3,1));
		selectionPanel.setSize(new Dimension(10,20));

		comboBox.setSelectedIndex(0);
		//comboBox.addActionListener();

		currentPlayer.setText("Select character for player 1");
		selectionPanel.add(currentPlayer);
		selectionPanel.add(comboBox);
		selectionPanel.add(confirmButton);

		//frame.add(boardAndGameInformationPane);
		selectionPanel.setTitle("Selection panel\n");
		selectionPanel.setSize(100, 100);
		selectionPanel.pack();
		selectionPanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		selectionPanel.setVisible(true);
		//selectionPanel.setDefaultCloseOperation(Jframe.);

	}

	public ArrayList<String> getCharacters(){
		return characters;
	}

	@SuppressWarnings("deprecation")
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
