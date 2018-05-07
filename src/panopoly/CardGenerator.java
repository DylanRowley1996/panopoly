package panopoly;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import locations.*;

public class CardGenerator {


	private static final String NOC_LIST_FILE_PATH = "Veale's NOC List/Veale's The NOC List.xlsx";
	private static final String LOCATION_LISTING_FILE_PATH = "Veale's NOC List/Veale's location listing.xlsx";
	private static ArrayList<NamedLocation> locations = SetupGame.getLocationList();
	private static ArrayList<Player> players = SetupGame.getPlayers();

	/*  Sample card format:
	 *  "You enter <determiner> <location>. It's <ambience>. You're <interactions> <character from NOC List>.
	 *  <They/you> hit <you/them> with <props>. <enter reward or loss>. "
	 *  NOTE: If 'They hit you....' this is possibly a loss.
	 *  		If 'You hit them....' this is possibly a gain.
	 */	
	public static void createCard(Player player, HistoryLog history) throws IOException, InvalidFormatException{
		 String determiner, location, interactions, character, ambience, props, finalOutcome, gender;
		 determiner = location = interactions = character = ambience = props = finalOutcome = gender = "";		
		 //Note 'location listings' has approx. 220 lines, but only up to line 28 is filled in for all cells.
		 final int locationListingLineTotal = 27;		// if null then draw from Person file (action)
		 final int nocListLineTotal = 805;
		 ZipSecureFile.setMinInflateRatio(0.0000001);  // THIS FIXED A LOT OF ERRORS BUT NOT SURE WHAT ITS DONE

		 Random rand = new Random();

		 Workbook locationListingWb = WorkbookFactory.create(new File(LOCATION_LISTING_FILE_PATH));
		 Sheet sheet = locationListingWb.getSheetAt(0);
		 Row row = sheet.getRow(rand.nextInt(locationListingLineTotal+1));
		 Cell cell;
		 String cellValue;
		 DataFormatter dataFormatter = new DataFormatter();

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
			 case "A":
				 location = cellValue;
				 break;
			 case "C":
				 determiner = cellValue;
				 break;
			 case "F":
				 for (int i = 0; i < cellValue.length(); i++){
					 if(cellValue.charAt(i) == ',') {
						 break;
					 }
					 ambience += cellValue.charAt(i);  
				 }
				 break;
			 case "G":
				 for (int i = 0; i < cellValue.length(); i++){
					 if(cellValue.charAt(i) == ',') {
						 break;
					 }
					 interactions += cellValue.charAt(i);  
				 }
				 break;
			 case "H":
				 char firstLetter = cellValue.charAt(0);
				 if(firstLetter == 'a' || firstLetter == 'e' || firstLetter == 'i' || firstLetter == 'o'|| firstLetter == 'u'){
					 props += "an ";
				 }else {
					 props += "a ";
				 }
				 for (int i = 0; i < cellValue.length(); i++){
					 if(cellValue.charAt(i) == ',') {
						 break;
					 }
					 props += cellValue.charAt(i);  
				 }
				 break;
			 }

		 }

		 Workbook nocListWb = WorkbookFactory.create(new File(NOC_LIST_FILE_PATH));
		 sheet = nocListWb.getSheetAt(0);

		 //Generate a random row number.
		 int randomNumber = rand.nextInt(nocListLineTotal+1);

		 //Get sheet and get name (column 1) for character name
		 row = sheet.getRow(randomNumber);
		 cell = row.getCell(1);
		 character = dataFormatter.formatCellValue(cell);
		 cell = row.getCell(2);
		 gender = dataFormatter.formatCellValue(cell);


		 String genderDeterminer = "";
		 String genderPossesive = "";
		 String genderPossesive2 = "";
		 if(gender.equals("male")) {
			 genderDeterminer = "He";
			 genderPossesive = "him";
			 genderPossesive2 = "his";
		 }else if( gender.equals("female")) {
			 genderDeterminer = "She";
			 genderPossesive = "her";
			 genderPossesive2 = "her";
		 }else {
			 genderDeterminer = gender;
			 genderPossesive = gender;
			 genderPossesive = gender;
		 }

		 final String rewardPrecondition= "You hit " + genderPossesive;
		 final String penaltyPrecondition = genderDeterminer + " hit you";


		 //Begin to build final outcome of generation.
		 finalOutcome += "You enter "+determiner+" "+location+".\nIt's "+ambience+
				 "\nYou're "+interactions+" "+character+".\n";

		 // Determine the Effect of the card

		 int effectDecider = (int) (Math.random() * (100 - 0));			//random number between 0 and 100

		 final String reward;
		 final String penalty;
		 int randomAmount;
		 if(effectDecider<27) {		//win or lose a set amount
			 /*	Determine if 'They' or 'You' comes first using random number between 0-1.
	   		If 'They' comes first 'You' comes second and vice versa
			  */			
			 randomAmount = (int) (Math.random() * (500 - 100)) + 100;			
			 reward = "You steal $"+randomAmount+" from "+genderPossesive+".";
			 penalty = genderDeterminer+" takes $"+randomAmount+ " from you";

			 randomNumber = (int) Math.round(Math.random());   		
			 if(randomNumber == 0){
				 finalOutcome += rewardPrecondition+" with "+props+".\n"+reward;
				 player.addToBalance(randomAmount);
			 }
			 else{
				 finalOutcome += penaltyPrecondition+" with "+props+".\n" + penalty;
				 player.deductFromBalance(randomAmount);
			 }   		

		 }else if(effectDecider<37) {			//win or lose a % amount		
			 randomAmount = (int) (Math.random() * (20 - 5)) + 5;	
			 double percent = randomAmount/100;
			 percent = Math.round(randomAmount*100.0)/100.0;
			 reward = "You take all of "+genderPossesive2+ " cash, increasing your wallet by "+percent+"%!";
			 penalty = genderDeterminer+" takes "+percent+ "% of your money!";

			 randomNumber = (int) Math.round(Math.random());   		
			 if(randomNumber == 0){
				 finalOutcome += rewardPrecondition+" with "+props+".\n"+reward+"\nWallet increased by $"+(int) (player.getNetWorth()*percent);
				 player.addToBalance((int) (player.getNetWorth()*percent));
			 }
			 else{
				 finalOutcome += penaltyPrecondition+" with "+props+".\n" + penalty+"\nYou lost $"+(int) (player.getNetWorth()*percent);
				 player.deductFromBalance((int) (player.getNetWorth()*percent));
			 }   		
	
		 }else if(effectDecider>=37 && effectDecider <= 41) {
			 int loc = rand.nextInt(locations.size());
			 reward = " robbed "+genderPossesive+" car";			
			 finalOutcome += rewardPrecondition+" with "+props+"and "+reward+"\nTravel to "+locations.get(loc).getIdentifier();
			 player.setLocation(locations.get(loc));

			 //		}else if(effectDecider<46){
			 //				//coin toss chance
			 //				/* split 5 ways
			 //				 * SET MONEY
			 //				 * % MONEY
			 //				 * GAIN OR LOSE ASSET
			 //				 * GAIN POWER OR LOSE MONEY
			 //				 * PERMANENT RENT INCREASE OR PEMANENT RENT DECREASE ON RANDOM PROPERTY
			 //				 */
			 //			history.getTextArea().append("Coin Toss\n");
			 //		}else if(effectDecider<51){
			 //		}else if(effectDecider<56){
			 //		}else if(effectDecider<61){

			 //also add a card that moves your location on the board
		 }else if(effectDecider<66){
			 history.getTextArea().append("Coin Toss\n");


		 }else if(effectDecider<76) {
			 /*
			  * SPLIT 2 WAYS
			  * 2 OUTPUTS LEAD TO RENT AVOID CARD
			  * 10 OUTPUTS LEAD TO GET OUT OF JAIL CARD
			  */
			 reward = "You looted a GET OUT OF JAIL FREE card from "+genderPossesive2+" corpse";			
			 finalOutcome += rewardPrecondition+" with "+props+".\n"+reward;
			 // TODO add Jail Card
			 	
			 //new retainableCard object get out of jail
			 //player.addCard(GetOutOfJailFree);
			 //history.getTextArea().append("Retainable Card\n");	
		 }else if(effectDecider<82) { // TODO
		
			 reward = "Police find you standing over "+genderPossesive+" corpse";			
			 finalOutcome += rewardPrecondition+" with "+props+".\n"+reward+"\nYou are arrested for murder.\n Go directly to jail";
			 player.setLocation(locations.get(locations.size()/4));
			 player.setJail(new Jail(player, history));
		 }
		 /*else if(effectDecider<78) {

			 reward = "You looted a RENT AVOID card from "+genderPossesive2+" corpse";			
			 finalOutcome += rewardPrecondition+" with "+props+".\n"+reward;
			 
			 //TODO add card to player inventory
			 //rent avoid

		 }else if(effectDecider<82) { // TODO
			 
			  * up to 82 leads to boost of rent intake
			  * up to 85 leads to cheaper prices on properties/buildings??
			  
			 reward = "You looted a RENT POWER UP from "+genderPossesive+" corpse";			
			 finalOutcome += rewardPrecondition+" with "+props+".\n"+reward+"\nAll your properties' rents have increased by 15%!";
			 
			 // set boolean to true within player class that can be searched for when calculating rent , must have a timer so gets revoked after x turns
			 
		 }else if(effectDecider<85) {
			 reward = "You looted a PROPERTY COST POWER UP from "+genderPossesive+" corpse";			
			 finalOutcome += rewardPrecondition+" with "+props+".\n"+reward+"\nAll properties now cost 10% less for you to purchase!";
			

		 }*/else if(effectDecider<90) {
			 
			 int amount = rand.nextInt(131) + 20;
			 penalty = genderDeterminer+" makes you pay $"+amount+" for each of your Properties";
			 finalOutcome += penaltyPrecondition+" with "+props+".\n" + penalty;   		
			 player.deductFromBalance(amount*player.getProperties().size());
			 	

		 }else if(effectDecider<95) {
			 int amount = rand.nextInt(131) + 20;
			 penalty = genderDeterminer+" makes you pay $"+amount+" for each of your Improvements";
			 finalOutcome += penaltyPrecondition+" with "+props+".\n" + penalty;  
			 player.deductFromBalance(amount*player.getNumImprovements());

			 	
		 }else if(effectDecider<=100) {
			 randomAmount = (int) (Math.random() * (20 - 5)) + 5;			
			 reward = "Word gets round that you're a hard ladT like Seán Grant so each player pays you $" + randomAmount;
			 penalty = genderDeterminer+" makes you pay each player $" + randomAmount;

			 randomNumber = (int) Math.round(Math.random());   		
			 if(randomNumber == 0){
				 finalOutcome += rewardPrecondition+" with "+props+".\n"+reward;
				 for(Player otherPlayer: players) {
					 if(otherPlayer!=player) {
						 otherPlayer.deductFromBalance(randomAmount);
						 player.addToBalance(randomAmount);
					 }
				 }
			 }
			 else{
				 finalOutcome += penaltyPrecondition+" with "+props+".\n" + penalty;
				 for(Player otherPlayer: players) {
					 if(otherPlayer!=player) {
						 player.deductFromBalance(randomAmount);
						 otherPlayer.addToBalance(randomAmount);
					 }
				 }
			 }   
			 
		 } else {
			 history.getTextArea().append("Error, random integer outside boundary for card selection\n");
		 }
		 
		 history.getTextArea().append(finalOutcome+"\n");
	 }
}

