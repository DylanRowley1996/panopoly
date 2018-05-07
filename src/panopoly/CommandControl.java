package panopoly;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import interfaces.Locatable;
import interfaces.Mortgageable;
import interfaces.Ownable;
import locations.*;

public class CommandControl {

	//Booleans for game control
	private boolean boughtProperty = false;
	private boolean hasHouses = false;

	private HistoryLog history = null;
	private static ArrayList<NamedLocation> locations = (ArrayList<NamedLocation>) SetupGame.getLocationList();
	private static ArrayList<Player> players = SetupGame.getPlayers();
	private static ArrayList<Player> lostPlayers = new ArrayList<Player>();
	private Board board;
	private static Dice normalDice = new Dice();
	private static Random rand = new Random();

	public CommandControl(HistoryLog history, Board board){
		this.history = history;
		this.board = board;
	}
	public void roll(Player player,int currentPlayer,JLabel characterImage) throws InvalidFormatException, IOException {

		if(player.getLocation() instanceof Ownable && !boughtProperty && ((Ownable)player.getLocation()).getOwner() == null ) { // for if rolled doubles. Must buy/Auction before rolling again
			history.getTextArea().append("-> Either 'Buy' or 'Auction' this property before rolling again.\n\n");	
		}
		else {
			int moveCount;
			NamedLocation oldLoc = (NamedLocation) player.getLocation();;
			ArrayList<Integer> diceFaces;
			diceFaces = normalDice.getFaces();
			moveCount = normalDice.rollDice(2, 6);
			moveCount = locations.size()/4;
			history.getTextArea().append("-> You have rolled a "+moveCount+"  "+diceFaces+".\n");	

			if(!normalDice.isDouble()) {
				player.setRolled(true);
			}else {
				history.getTextArea().append("-> You rolled Doubles!\n");	
				boughtProperty=false;
				if(normalDice.getNumberOfRolls()>=3) {
					history.getTextArea().append("-> You have rolled 3 Doubles. \n");
					player.setJail(new Jail(player, history));
					history.getTextArea().append("-> Go to Jail!\n\n");
					player.setLocation(locations.get(locations.size()/4));
					finishTurn(player, currentPlayer, characterImage);
				}
			}
			for(int i=0;i<moveCount;i++) {
				player.setLocation((NamedLocation)player.getNextLoc());
			}
			normalDice.refreshDice();
			history.getTextArea().append("-> You have rolled onto "+player.getLocation().getIdentifier()+".\n\n");

			//After roll
			Locatable location = player.getLocation();
			if(location instanceof PrivateProperty) {
				int rent = 0;
				if(((PrivateProperty)location).getOwner()!=null && ((PrivateProperty)location).getOwner()!=player){
					if(location instanceof Utility) rent = ((Utility)location).getRentalAmount(moveCount);
					else	rent = ((PrivateProperty)location).getRentalAmount();
					player.payPlayer(((PrivateProperty)location).getOwner(), rent);
					history.getTextArea().append("-> You paid $"+rent+ " in rent to "+((PrivateProperty)location).getOwner().getIdentifier()+".\n\n");
				}
			}
			if(location instanceof MCQLocation) {
				MCQ mcq = new MCQ();
				mcq.createMCQPanel(player, history, null);
			}
			if(location instanceof CardLocation) {
				oldLoc=(NamedLocation) player.getLocation();
				CardGenerator.createCard(player, history);
			}
			if(location instanceof CommunismTax) {
				CommunismTax.spreadWealth(players, history);
			}
			if(location instanceof GoToJail) {
				player.setJail(new Jail(player, history));
				history.getTextArea().append("-> Go to Jail!\n\n");
				oldLoc=(NamedLocation) player.getLocation();
				player.setLocation(locations.get(locations.size()/4));

			}
			if(location instanceof TaxableLocation) {
				history.getTextArea().append("-> " + location.getIdentifier() + "\n");
				int tax=0;
				if(rand.nextInt(2)==0) {
					history.getTextArea().append("-> Pay " + ((TaxableLocation) location).getIncomePercentage() + "% of your total net worth in taxes\n");
					tax = (int) (player.getNetWorth()*((TaxableLocation) location).getIncomePercentage());
					history.getTextArea().append("-> "+player.getName()+" paid $"+tax+" in taxes\n\n");
					player.deductFromBalance(tax);
				}
				else {
					tax = ((TaxableLocation) location).getFlatAmount();
					history.getTextArea().append("-> "+player.getName()+" paid $"+tax+" in taxes\n\n");
				}
				player.deductFromBalance(tax);
			}

			board.updateIcons(player, oldLoc);
			board.repaint();
			board.revalidate();
		}		
	}

	public void buy(Player player) {
		if(player.getLocation() instanceof PrivateProperty) {
			if(((PrivateProperty)player.getLocation()).getOwner()==null) {
				if(player.getNetWorth()>=(((PrivateProperty)player.getLocation()).getPrice())) {
					player.buyProperty((PrivateProperty)player.getLocation());
					((PrivateProperty)player.getLocation()).setOwner(player);
					history.getTextArea().append("-> You have bought "+player.getLocation().getIdentifier()+" for $"+(((PrivateProperty)player.getLocation()).getPrice())+".\n\n");
					boughtProperty = true;
				}else {
					history.getTextArea().append("-> This property is too expensive.\n\n");	
				}
			}
			else {
				history.getTextArea().append("-> This property is already purchased.\n\n");
			}

		}else {
			history.getTextArea().append("-> You cannot purchase this property.\n\n");
		}
	}

	public void sell(Player player) {
		// TODO

		//Check if a player even has houses to sell.
		for(PrivateProperty property : player.getProperties() ){
			if(property instanceof ImprovableProperty){
				if(((ImprovableProperty)property).getNumHouses() != 0){
					hasHouses = true;
				}
			}
		}

		//Take corresponding action.
		if(!hasHouses){
			history.getTextArea().append("-> You don't have any houses to sell.\n\n");
		}
		
		else{
			new Sell(player, history);
			hasHouses = false;
		}
		
		

	}

	public void mortgage(Player player) {
		// Get list of players properties.
		// Check what properties are mortgageable
		// Pass information to JPanel
		// Allow them to select which properties to mortgage.
		// Ensure balance is updated accordingly
		if (player.getProperties().size() == 0) {
			history.getTextArea().append("-> You don't own any properties. Nothing to Mortgage.\n");
		} else {
			ArrayList<PrivateProperty> mortgageableProperties = new ArrayList<PrivateProperty>();
			JFrame mortgageFrame = new JFrame("Mortgage Choices");
			JLabel userDirections = new JLabel("Choose which properties to mortgage then hit 'Confirm'");

			for (int i = 0; i < player.getProperties().size(); i++) {
				// If property is mortgageable and is not already mortgaged
				if (player.getProperties().get(i) instanceof Mortgageable
						&& player.getProperties().get(i).isMortgaged() == false) {
					mortgageableProperties.add(player.getProperties().get(i));
				}
			}

			/*
			 * Create an array list of buttons. One for each mortgageable
			 * property.
			 */
			ArrayList<JRadioButton> mortgageableRadioButtons = new ArrayList<JRadioButton>();
			for (int i = 0; i < mortgageableProperties.size(); i++) {
				mortgageableRadioButtons
				.add(new JRadioButton("Property: " + mortgageableProperties.get(i).getIdentifier()
						+ " Mortgage Amount: $" + mortgageableProperties.get(i).getMortgageAmount()));
			}

			// Create the button for choice confirmation.
			JButton confirmationButton = new JButton("Confirm");

			/*
			 * 1. Check which buttons are selected 2. Count the total amount of
			 * mortgages selected. 3. Add this to players account 4. Set
			 * isMortgaged to true for all properties that are mortgaged
			 */
			confirmationButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {

					try {
						int totalMortgageValue = 0;
						int j = 0;
						// This list is used to create a string that will be
						// appended to text area on the GUI
						ArrayList<String> mortgageableIdentifiers = new ArrayList<String>();
						ArrayList<String> unmortgagableIdentifiers = new ArrayList<String>();

						while (j < mortgageableRadioButtons.size()) {

							if (mortgageableRadioButtons.get(j).isSelected()) {

								// Check that the user can mortgage the property
								if (mortgageableProperties.get(j) instanceof ImprovableProperty) {
									if (((ImprovableProperty) mortgageableProperties.get(j)).getNumHouses() == 0) {
										totalMortgageValue += mortgageableProperties.get(j).getMortgageAmount();
										mortgageableIdentifiers.add(mortgageableProperties.get(j).getIdentifier());
									}

									// Can't be mortgaged. Add to list.
									else {
										unmortgagableIdentifiers.add(mortgageableProperties.get(j).getIdentifier());
									}
								}

							}
							j++;
						}

						// No unmortgageable properties were selected. Allow
						// them to mortgage.
						if (unmortgagableIdentifiers.size() == 0) {

							// Mortgage the correct properties.
							for (int x = 0; x < mortgageableIdentifiers.size(); x++) {
								for (int y = 0; y < player.getProperties().size(); y++) {
									if (mortgageableIdentifiers.get(x)
											.equals(player.getProperties().get(y).getIdentifier())) {
										player.mortgageProperty(player.getProperties().get(y));
									}
								}
							}

							// Add the total value to the players balance.
							history.getTextArea().append("-> Properties Mortgaged: " + buildString(mortgageableIdentifiers)
							+ " for total: $" + totalMortgageValue + " \n\n");
							mortgageFrame.dispose();// Exit JFrame, player has
							// selected the properties
							// they want to redeem

						}

						else {
							history.getTextArea().append("-> Can't mortgage the following properties"
									+ " due to houses on locations: " + buildString(unmortgagableIdentifiers) + "\n\n");
						}

					} catch (Exception e1) {		
						e1.printStackTrace();
					}
				}

			});

			// Add all choices to JPanel
			JPanel radioPanel = new JPanel(new GridLayout(0, 1));

			// Add label that contains directions for user onto panel
			radioPanel.add(userDirections);

			for (int i = 0; i < mortgageableProperties.size(); i++) {
				radioPanel.add(mortgageableRadioButtons.get(i));
			}

			// Add button below choices
			radioPanel.add(confirmationButton);

			radioPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
			mortgageFrame.add(radioPanel);
			mortgageFrame.setVisible(true);
			mortgageFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			mortgageFrame.pack();
			mortgageFrame.setLocationRelativeTo(null);
		}

	}

	public void redeem(Player player){

		if(player.getMortgages().size() == 0){
			history.getTextArea().append("-> You have not mortgaged any properties. Nothing to redeem.\n\n");

		}
		else{
			ArrayList<PrivateProperty> redeemableProperties = new ArrayList<PrivateProperty>();
			JFrame redeemFrame = new JFrame("Mortgage Redemption Choices");
			JLabel userDirections = new JLabel("Choose which mortgages to redeem then hit 'Confirm'");
			JLabel userAlert = new JLabel("You currently have enough to redeem these mortgages.");

			for(int i=0;i<player.getProperties().size();i++){
				//If property is mortgageable and is not already mortgaged
				if(player.getProperties().get(i) instanceof Mortgageable && player.getProperties().get(i).isMortgaged() == true){
					redeemableProperties.add(player.getProperties().get(i));	
				}

			}

			/*
			 * Create an array list of buttons. One for each mortgageable property.
			 */
			ArrayList<JRadioButton> redeemableRadioButtons = new ArrayList<JRadioButton>();
			for(int i=0;i<redeemableProperties.size();i++){
				redeemableRadioButtons.add(new JRadioButton("Property: "+redeemableProperties.get(i).getIdentifier()
						+" Redemption Amount: $"+redeemableProperties.get(i).getMortgageAmount()));
			}

			//Create the button for choice confirmation.
			JButton confirmationButton = new JButton("Confirm");

			/* 1. Check which buttons are selected
			 * 2. Count the total redemption amount of mortgages selected.
			 * 3. Remove this from players account if player can afford it
			 * 4. Unmortgage all properties that are selected.
			 */
			confirmationButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					//This list is used to create a string that will be appended to text area on the GUI
					ArrayList<String> redeemedPropertiesToPrint = new ArrayList<String>();
					boolean mortgagedSelected = false;
					try {	               

						int totalRedemptionValue = 0;

						int j=0;
						ArrayList<PrivateProperty> propertiesToRedeem = new ArrayList<PrivateProperty>();
						while(j < redeemableRadioButtons.size()){
							if(redeemableRadioButtons.get(j).isSelected()){

								mortgagedSelected = true;
								if(player.getNetWorth()>redeemableProperties.get(j).getRedeemAmount()) {	

									//Start totalling value of mortgages.
									totalRedemptionValue += redeemableProperties.get(j).getRedeemAmount();

									//Find index of property to unmortgage then unmortgage this property in players list.
									int indexToRedeem = player.getProperties().indexOf(redeemableProperties.get(j));
									propertiesToRedeem.add(player.getProperties().get(indexToRedeem));
									//Add property name to list of Stringst that we'll print
									redeemedPropertiesToPrint.add(player.getProperties().get(indexToRedeem).getIdentifier());
								}
							}
							j++;
						}

						//Make sure player has enough funds to redeem mortgages and has actually chosen one.
						if(player.getNetWorth() >= totalRedemptionValue && mortgagedSelected){
							player.deductFromBalance(totalRedemptionValue);
							for(int i=0; i<propertiesToRedeem.size(); i++) {
								//Unmortgage the correct properties
								player.redeemProperty(propertiesToRedeem.get(i));
							}
							history.getTextArea().append("-> Redeeemed Mortgages: "+buildString(redeemedPropertiesToPrint)+" for total: $"+totalRedemptionValue+"\n\n");
							redeemFrame.dispose();//Exit JFrame, player has selected the properties they want to redeem	
						}
						else if(player.getNetWorth() < totalRedemptionValue && mortgagedSelected){
							userAlert.setForeground(Color.red);
							userAlert.setText("Insufficient funds. Choose Less Mortgages.");
						}
					} 
					catch (Exception e1) {
						e1.printStackTrace();
					}	
				} 

			});

			//Add all choices to JPanel
			JPanel radioPanel = new JPanel(new GridLayout(0, 1));

			//Add label that contains directions for user onto panel
			radioPanel.add(userDirections);

			for(int i=0;i<redeemableProperties.size();i++){
				radioPanel.add(redeemableRadioButtons.get(i));
			}

			//Tell user if they have enough money to mortgage the property.
			radioPanel.add(userAlert);

			//Add button below choices
			radioPanel.add(confirmationButton);

			radioPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
			redeemFrame.add(radioPanel);
			redeemFrame.setVisible(true);
			redeemFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			redeemFrame.pack();
			redeemFrame.setLocationRelativeTo(null);

		}


	}

	public void auction(Player player){
		if(player.getLocation() instanceof Ownable && ((Ownable)player.getLocation()).getOwner() == null){
			new Auction((Ownable)player.getLocation(),players,history);
		}
	}

	public void trade(Player player){
		new Trade(player, players, history);
	}

	public int finishTurn(Player player, int currentPlayerNumber, JLabel characterImage){
		// check for in jail too long, unpaid rent, etc.
		//If player is on an ownable property that is unowned and hasn't bought it
		//Force them to auction.
		if(player.getNetWorth()<0) {
			history.getTextArea().append("You are in debt! Sell houses and cards or mortgage properties to pay your debt!\n");
		} 
		else if(player.getLocation() instanceof Ownable && !boughtProperty && ((Ownable)player.getLocation()).getOwner() == null ){
			history.getTextArea().append("Either 'Buy' or 'Auction' this property before finishing your turn.\n");
		} 
		else if(!player.hasRolled() && !player.isInJail()){
			history.getTextArea().append("-> You must roll before finishing your turn.\n\n");
		}
		else if(player.isAnsweringMCQ()) {
			history.getTextArea().append("-> Cannot end turn while answering MCQ.\n\n");
		}
		else{
			boughtProperty = false;
			player.setRolled(false);

			if (currentPlayerNumber == players.size()-1) {
				currentPlayerNumber = 0;
			} else {
				currentPlayerNumber++;
			}
			try {
				BufferedImage myPicture = ImageIO
						.read(new File(players.get(currentPlayerNumber).getPathForImageIcon()));
				characterImage.setIcon(new ImageIcon(myPicture));
				history.getTextArea()
				.append("-> Current Player is now: " + players.get(currentPlayerNumber).getName() + "\n\n");
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return currentPlayerNumber;
	}

	private String buildString(ArrayList<String> properties){
		String stringToBuild = "";

		if(properties.size() == 1){
			stringToBuild = properties.get(0);
		}

		else if(properties.size() > 2){
			for(int i=0;i<properties.size()-2;i++){
				stringToBuild += properties.get(i)+",";
			}

			//Insert 'and' between the last two properties
			stringToBuild += properties.get(properties.size()-2)+" and "+properties.get(properties.size()-1);
		}

		else if(properties.size() == 2){
			stringToBuild += properties.get(properties.size()-2)+" and "+properties.get(properties.size()-1);

		}

		return stringToBuild;
	}

	public void declareBankruptcy(Player player, int currentPlayerNumber, JLabel characterImage, JFrame frame) throws IOException {
		history.getTextArea().append(player.getIdentifier()+" has declared bankruptcy and drops out.\n");
		board.removeCharacter(player);
		lostPlayers.add(player);
		players.remove(player);
		try {
			BufferedImage myPicture = ImageIO
					.read(new File(players.get(currentPlayerNumber).getPathForImageIcon()));
			characterImage.setIcon(new ImageIcon(myPicture));
			history.getTextArea()
			.append("-> Current Player is now: " + players.get(currentPlayerNumber).getName() + "\n\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(players.size()==1) {
			lostPlayers.add(players.get(0));
			players.clear();
			declareWinner(frame);
		}
	}	
	public void QuitEarly(JFrame frame) throws IOException {
		Collections.sort(players, new Comparator<Player>(){
			public int compare(Player o1, Player o2){
				if(o1.getTotalWorth() == o2.getTotalWorth())
					return 0;
				return o1.getTotalWorth() < o2.getTotalWorth() ? -1 : 1;
			}
		});
		Collections.reverseOrder();
		
		Iterator<Player> it = players.iterator();

		while(it.hasNext()) {
			lostPlayers.add(it.next());
			//players.remove(it.next());	
		}
		declareWinner(frame);

	}
	public void declareWinner(JFrame frame) throws IOException  {
		int position;
		Player curr;
		for(int i=lostPlayers.size()-1;i>=0;i--) {
			curr = lostPlayers.get(i);
			position = lostPlayers.size()-i;
			if(position == 1) {
				history.getTextArea().append(curr.getIdentifier()+" wins the game!\n");
			}
			else if(position == 2) {
				history.getTextArea().append(position+"nd: "+curr.getIdentifier()+"\n");
			}else if(position == 3) {
				history.getTextArea().append(position+"rd: "+curr.getIdentifier()+"\n");
			}else {
				history.getTextArea().append(position+"th: "+curr.getIdentifier()+"\n");
			}
		}

		new EndGamePanel(lostPlayers, board, frame);	
	}

	public void build(Player player) {
		if(player.getMonopolies().size() == 0){
			history.getTextArea().append("-> You have no monopolies or one with a mortgaged property. Unable to build.\n\n");

		}
		else{
			new Build(player, history);
		}


	}
}
