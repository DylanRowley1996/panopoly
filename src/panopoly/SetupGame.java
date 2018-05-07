package panopoly;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.imageio.ImageIO;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
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

	private int noOfPlayers;
	private ArrayList<ArrayList<String>> charactersAndThemes = new ArrayList<ArrayList<String>>(noOfPlayers);
	private ArrayList<String> characters = new ArrayList<String>();
	private static ArrayList<NamedLocation> locationList = new ArrayList<NamedLocation>(); // TODO maybe change to ArrayList of ArrayLists for multiple boards
	private static ArrayList<Player> players = new ArrayList<Player>();
	
	Random rand = new Random();
	private String[] pathsToIcons = null;

	public SetupGame(int noOfPlayers) throws EncryptedDocumentException, InvalidFormatException, IOException, URISyntaxException{

		this.noOfPlayers = noOfPlayers;
		pathsToIcons = new String[noOfPlayers];
		int noBoardRows = rand.nextInt(5) + 11;
		int noLocations = (noBoardRows-3)*4; //total number of squares on the board
		int noGroups = (int) ((noLocations*0.8)-8)/3;


		findCharactersFromThemes(findThemes(0, 0, this.noOfPlayers/*noCharacters*/));
		compileChoiceOfCharacters();
		
		//TODO - Uncomment code below when we need queries working
		FindImages imageRetriever = new FindImages(characters, this.noOfPlayers);
		imageRetriever.searchForCharacterImages();
		imageRetriever.resizeAllImages();
		
		//resizeAllImages();
		createPlayers();
		setUpLocations(findThemes(1, 1, noGroups), noLocations, noBoardRows);
		addRandomPropertiesToEachPlayer();

		new GUI(players, noBoardRows, locationList);
	}

	//Randomly generate a list of unique themes depending on the number of players.
	public ArrayList<String> findThemes(int c, int wB, int noOfThemes) throws EncryptedDocumentException, InvalidFormatException, IOException{

		ArrayList<String> listOfThemes = new ArrayList<String>();
		boolean themesFound = false;
		boolean newThemeFound = false;
		Workbook locationListingWb;


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
			List<String> themes = Arrays.asList(cell.toString().split(", "));

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
			if(listOfThemes.size() == noOfThemes) themesFound = true;

		}

		locationListingWb.close();

		if(listOfThemes.size() == 0) System.out.println("\nBROKEN!\n");
		System.out.println("Themes: " + listOfThemes.size());
		return listOfThemes;
	}

	//Creates new .xlsx files that will store characters from certain themes. 
	public void findCharactersFromThemes(ArrayList<String> themes) throws EncryptedDocumentException, InvalidFormatException, IOException {

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
					 * add the character to the given list
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

		while(characters.size() != this.noOfPlayers){
			int choice = rand.nextInt(charactersAndThemes.get(i).size());

			if(choice == 0) choice  += 1;
			if(!characters.contains(charactersAndThemes.get(i).get(choice))){
				System.out.println("Current Theme: "+charactersAndThemes.get(i).get(0)+" Current Character:"+charactersAndThemes.get(i).get(choice)+"\n");
				characters.add(charactersAndThemes.get(i).get(choice));
				i++;
			}
		}
	}


	public ArrayList<String> getCharacters(){
		return characters;
	}

	/*
	 * Take list of themes based on amount of locations.
	 * Read Fictional Worlds file and retrieves 3 locations based on each theme.
	 * 
	 */
	public void setUpLocations(ArrayList<String> themes, int noLocations, int noBoardRows) throws EncryptedDocumentException, InvalidFormatException, IOException {
		ArrayList<PropertyGroup> groups = setUpGroups(themes);

		Random rand = new Random();
		ArrayList<ArrayList<String>> locationsByTheme = new ArrayList<ArrayList<String>>();
		int noLocsAdded = 0;

		//Open the Fictional Worlds list for reading.
		ZipSecureFile.setMinInflateRatio(0.005);
		Workbook worldsListWb = WorkbookFactory.create(new File(WORLDS_FILE_PATH));
		Sheet worldsListSheet = worldsListWb.getSheetAt(0);
		Row row;
		//Iterator<Row> rowIterator = worldsListSheet.iterator();

		for(int i=0;i<themes.size();i++) {
			int locationsSelected = 0;
			locationsByTheme.add(new ArrayList<String>());
			locationsByTheme.get(i).add(themes.get(i));

			/*
			 * If you keep finding duplicates,
			 * just accept the unique properties you've
			 * already retreived and continue*/
			int duplicateError = 0;
			while(locationsSelected<3 && duplicateError<10) {
				row = worldsListSheet.getRow(rand.nextInt(WORLDS_LINE_TOTAL));

				if(row.getCell(1).toString().contains(themes.get(i))) {
					if(!nestedContains(locationsByTheme, row.getCell(0).toString())) {
						locationsByTheme.get(i).add(row.getCell(0).toString());
						noLocsAdded++;
						locationsSelected++;
					}
					else duplicateError++;
				}
			}

		}

		//Creating Stations
		//Stations have no theme, they're a random fictional world from the NOC List
		locationsByTheme.add(new ArrayList<String>());
		locationsByTheme.get(themes.size()).add("Station");
		PropertyGroup stationGroup = new PropertyGroup("Station", new int[]{100, 500}, Color.white);
		int stationsSelected=0;
		while(stationsSelected<4) {
			row = worldsListSheet.getRow(rand.nextInt(WORLDS_LINE_TOTAL));
			//Make sure other themes don't contain the name chosen for the station.
			if(!nestedContains(locationsByTheme, row.getCell(0).toString())) {
				locationsByTheme.get(themes.size()).add(row.getCell(0).toString());
				noLocsAdded++;
				stationsSelected++;
			}
		}

		//Other locations are MCQ, Utilities, Cards etc
		ArrayList<NamedLocation> otherLocationList = createRandomLocationList((noLocations-4) - noLocsAdded);
		noLocsAdded = noLocations-4;

		worldsListWb.close();
		

		/* 
		 * All property names have been gathered.
		 * Now create actual instances of property
		 * and store in location list.
		 */
		while(noLocsAdded>0) {
			int listNo = rand.nextInt(locationsByTheme.size());
			if(locationsByTheme.get(listNo).size()>1) { 
				if(listNo==locationsByTheme.size()-1) { // add Station
					locationList.add(new Station(locationsByTheme.get(listNo).get(1) + " Station", stationGroup));
					locationsByTheme.get(listNo).remove(1);
					noLocsAdded--;
				}
				else { // Add Property
					locationList.add(new ImprovableProperty(locationsByTheme.get(listNo).get(1), groups.get(listNo)));
					locationsByTheme.get(listNo).remove(1);
					noLocsAdded--;
				}
			}
			else { // Add other Location
				if(otherLocationList.size()>0) {
					locationList.add(otherLocationList.get(0));
					otherLocationList.remove(0);
					noLocsAdded--;
				}
			}
		}

		// shuffles if stations are too close to each other
		boolean stationCheck = false;
		while(!stationCheck) { 
			Collections.shuffle(locationList);
			stationCheck = checkStationLocations(noBoardRows);
		}

		//Add Properties that need to be present every time to the corners.
		locationList.add(0, new NamedLocation("Go"));
		locationList.add(noBoardRows-3, new NamedLocation("Jail"));
		locationList.add((noLocations-1)-(noBoardRows-3), new GoToJail("Go to Jail"));
		locationList.add(noLocations-1, new CommunismTax("Communism Spread the Wealth", noLocations-1));

		// set next and prev locations
		for(int i=1; i<noBoardRows-3; i++) { // top row (without corner squares)
			NamedLocation loc = locationList.get(i);
			loc.setNextLoc(locationList.get(i+1));
			loc.setPrevLoc(locationList.get(i-1));
		}
		locationList.get(0).setNextLoc(locationList.get(1)); // top left corner
		locationList.get(0).setPrevLoc(locationList.get(noBoardRows-2)); 
		locationList.get(noBoardRows-3).setNextLoc(locationList.get(noBoardRows-1)); // top right corner
		locationList.get(noBoardRows-3).setPrevLoc(locationList.get(noBoardRows-4));
		
		int boardSide = 0;
		for(int i=noBoardRows-1; i<noLocations-(noBoardRows-1); i++) { // board sides
			boardSide++;
			NamedLocation loc = locationList.get(i);
			if(boardSide%2==0) {
				loc.setNextLoc(locationList.get(i-2));
				loc.setPrevLoc(locationList.get(i+2));
			}
			else {
				loc.setNextLoc(locationList.get(i+2));
				loc.setPrevLoc(locationList.get(i-2));
			}
		}
		locationList.get(noBoardRows-2).setNextLoc(locationList.get(0)); // under top left corner
		locationList.get(noBoardRows-2).setPrevLoc(locationList.get(noBoardRows));
		locationList.get(noLocations-(noBoardRows-1)).setNextLoc(locationList.get(noLocations-1)); // above bottom right corner
		locationList.get(noLocations-(noBoardRows-1)).setPrevLoc(locationList.get(noLocations-(noBoardRows+1)));
		
		for(int i=noLocations-(noBoardRows-3); i<noLocations-1; i++) { // bottom row
			NamedLocation loc = locationList.get(i);
			loc.setNextLoc(locationList.get(i-1));
			loc.setPrevLoc(locationList.get(i+1));
		}
		locationList.get(noLocations-1).setNextLoc(locationList.get(noLocations-2)); // bottom right corner
		locationList.get(noLocations-1).setPrevLoc(locationList.get(noLocations-(noBoardRows-1)));
		locationList.get(noLocations-(noBoardRows-2)).setNextLoc(locationList.get(noLocations-(noBoardRows))); // bottom left corner
		locationList.get(noLocations-(noBoardRows-2)).setPrevLoc(locationList.get(noLocations-(noBoardRows-3)));

	}

	public static ArrayList<NamedLocation> getLocationList() {
		return locationList;
	}


	private ArrayList<NamedLocation> createRandomLocationList(int size) {
		ArrayList<String> taxNames = new ArrayList<>(Arrays.asList("Income Tax", "Property Tax", "Luxury Tax"));
		ArrayList<String> utilityNames = new ArrayList<>(Arrays.asList("Coal Mines", "The Gulag", "Nuclear Power Facility", "Advanced Weapons Facility", "Experimental Sciences Lab"));
		PropertyGroup utilityGroup = new PropertyGroup("Utilities", new int[]{50, 250}, Color.WHITE);

		ArrayList<NamedLocation> locList = new ArrayList<NamedLocation>();

		int noLocsAdded = 0;
		while(noLocsAdded < size) {
			NamedLocation loc = null;
			int locType = rand.nextInt(4);
			switch(locType) {
			case 0:
				if(taxNames.size()>1) {
					Collections.shuffle(taxNames);
					String name = taxNames.get(0);
					taxNames.remove(0);
					double iP = ThreadLocalRandom.current().nextDouble(0.05, 0.51);
					int flatAmount = rand.nextInt(300)+50;
					int fARoundToFive = 5*(Math.round(flatAmount/5));
					loc = new TaxableLocation(name, iP, fARoundToFive);
				}
				break;
			case 1:
				if(rand.nextInt(2)==0)	loc = new CardLocation("Card");
				else					loc = new MCQLocation("MCQ");
				break;
			case 2:
				if(utilityNames.size()>1) {
					Collections.shuffle(utilityNames);
					String name = utilityNames.get(0);
					utilityNames.remove(0);
					loc = new Utility(name, utilityGroup);
				}
				break;
			}

			if(loc!=null) 	{
				locList.add(loc);
				noLocsAdded++;
			}
		}

		return locList;
	}


	private boolean checkStationLocations(int noBoardRows) {
		int distanceBetweenStations = (int) (noBoardRows*.8);
		for(NamedLocation loc : locationList) {
			if(loc instanceof Station) {
				if(distanceBetweenStations < (int) (noBoardRows*.8)) {
					return false;
				}
				distanceBetweenStations = 0;
			}
			distanceBetweenStations++;
		}
		return true;
	}

	public ArrayList<PropertyGroup> setUpGroups(ArrayList<String> themes) {
		ArrayList<PropertyGroup> groups = new ArrayList<PropertyGroup>();
		ArrayList<Color> colors = new ArrayList<>(Arrays.asList(Color.red, Color.green, Color.blue, Color.cyan, Color.magenta, Color.yellow, Color.pink, Color.lightGray, Color.orange, Color.gray));
		ArrayList<int[]> prices = new ArrayList<>(Arrays.asList(new int[]{50, 80}, new int[]{80, 160}, new int[]{160, 250}, new int[]{100, 280},
				new int[]{250, 400}, new int[]{400, 675}, new int[]{320, 435}, new int[]{550, 875}, new int[]{800, 1100}, new int[]{1200, 2000}));
		int totalGroups = themes.size();

		for(int i=0; i<totalGroups; i++) {
			int x = rand.nextInt(totalGroups-i);
			int y = rand.nextInt(totalGroups-i);
			groups.add(new PropertyGroup(themes.get(i), prices.get(x), colors.get(y)));
			prices.remove(x);
			colors.remove(y);
		}

		return groups;
	}



	private boolean nestedContains(ArrayList<?> outer, Object obj) { // used to check if any ArrayList in an ArrayList of ArrayLists contains an object
		for (Object inner : outer) {
			if (((ArrayList<?>) inner).contains(obj)) {
				return true;
			}
		}
		return false;
	}

	private void createPlayers() throws EncryptedDocumentException, InvalidFormatException, IOException{

		String[] pathsToIcons = getPathsToIcons();

		//Instantiate all information for players
		for(int i=0;i<noOfPlayers;i++){

			/*	getBaseName removes full path and returns file name.
			This is the character name*/
			String characterName = FilenameUtils.getBaseName(pathsToIcons[i]);
			players.add(new Player(characterName,pathsToIcons[i]));
			System.out.println("Path to icon for player "+players.get(i).getName()+" PATH:"+players.get(i).getPathForImageIcon());
		}
	}
	
	public static ArrayList<Player> getPlayers() {
		return players;
	}

	public void setPathToIcon(String filePath, int i){
		this.pathsToIcons[i] = filePath;
	}

	public String[] getPathsToIcons(){
		return this.pathsToIcons;
	}

	private static BufferedImage resizeImage(BufferedImage originalImage, int type, int IMG_WIDTH, int IMG_HEIGHT) {
		BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
		g.dispose();
		return resizedImage;
	}

	//Scales all images so they don't have to be constantly rescaled during the game
	public void resizeAllImages() throws IOException{

		File dir = new File("savedImages");
		File[] children = dir.listFiles();

		//TODO - Change so it only loops on number of players
		for(int i=0;i<pathsToIcons.length;i++){
			System.out.println("Trying to read: "+children[i].toString());
			BufferedImage originalImage = ImageIO.read(new File(children[i].toString()));//change path to where file is located
			int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();

			BufferedImage resizeImageJpg = resizeImage(originalImage, type, 150, 150);
			ImageIO.write(resizeImageJpg, "jpg", new File(children[i].toString())); //change path where you want it saved
		}

	}

	//TODO - Remove when testing of Mortgaging is complete.
	//This just adds a random amount of properties to each player
	//so checking if Mortgaging/Redeeming etc is working correctly.
	public void addRandomPropertiesToEachPlayer(){
		int j=0;
		for(int i =0;i<players.size();i++){
			while(j < 30){
				if(locationList.get(j) instanceof PrivateProperty){
					System.out.println(locationList.get(j).getIdentifier());
					players.get(i).buyProperty((PrivateProperty)locationList.get(j));
				}
				j++;
			}
			j=0;
		}
	}
}