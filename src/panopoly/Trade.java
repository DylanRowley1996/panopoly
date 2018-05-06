package panopoly;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

/* 
 * This class works by iterating through panels
 * on the same JFrame.
 * 1. Player is presented with property choices to trade, panel is disposed of.
 * 2. Player then selects the asset they want to trade for, panel is disposed of.
 * 3. Player then selects the player they want to trade with, panel is disposed of.
 * 4. The chosen player has to accept the trade. If rejected, return to step 3, if accepted 
 * dispose of panel and continue
 * 5. Chosen player then enters the amount/property they'll trade for. If accepted by current player
 * the trading of assets takes place and trading is complete. If rejected continue.
 * 6. Return to step 3.
 */
public class Trade extends JFrame{
	
	private JFrame tradingHouse = new JFrame("Trading house");
	private Player player;
	private ArrayList<Player> players;

	//Components for first panel
	private JPanel propertyPanel = new JPanel(new GridLayout(0,1));
	private JLabel propertyDirections = new JLabel("Choose which properties you want to trade then hit 'Confirm'");
	private JPanel propertyRadioButtonPanel = new JPanel(new GridLayout(0,3));
	private ArrayList<JRadioButton> propertyRadioButtons = new ArrayList<JRadioButton>();
	private ArrayList<String> propertiesWishingToTrade = new ArrayList<String>();

	//Components for second panel
	private JPanel assetsPanel = new JPanel(new GridLayout(0,1));
	private JLabel assetsDirections = new JLabel("Choose what asset you'd like to trade for.");
	private JRadioButton propertyChoice = new JRadioButton("Property");
	private JRadioButton cashChoice = new JRadioButton("Cash");
	private ButtonGroup assetButtonGrouping = new ButtonGroup();
	private boolean cashChosen = false;
	private boolean propertyChosen = false;
	
	//Components for third panel
	private JPanel playerChoicesPanel = new JPanel(new GridLayout(0,1));
	private JLabel playerChoiceDirections = new JLabel("Choose what player you'd like to trade with.");
	private JPanel playerRadioButtonPanel = new JPanel(new GridLayout(0,1));
	private ArrayList<JRadioButton> playerRadioButtons = new ArrayList<JRadioButton>();
	private ButtonGroup playerButtonGroup = new ButtonGroup();
	
	//Components for fourth panel.
	private JLabel acceptOrDeclineQuestion = new JLabel();
	private JPanel playerOfferPanel = new JPanel(new GridLayout(0,1));
	
	//Components for fifth panel.
	private JLabel bidQuestion = new JLabel("How much do you want to offer for these properties?");
	private JPanel bidPanel = new JPanel(new GridLayout(0,1));
	private JTextArea bidArea = new JTextArea();

	
	//Button panel is common to all panels.
	private JPanel buttonPanel = new JPanel(new FlowLayout());
	private JButton confirmationButton = new JButton("Confirm");
	private JButton cancelButton = new JButton("Cancel");
	
	//Control flow of panels using booleans.
	private boolean propertiesChosen = false;
	private boolean assetsChosen = false;
	private boolean playerChosen = false;
	private boolean playerAccepted = false;
	private boolean offerGiven = false;
	private boolean offerAccepted = false;
	
	//Information to complete trade.
	String playerToTradeWith = "";
	

	public Trade(Player player, ArrayList<Player> players, HistoryLog history){
				
		this.player = player;
		this.players = players;
		
		addActionListeners();
		
		createPropertyPanel(player);
		
		/* 
		 * Add the initial property panel.
		 * This will be swapped out later
		 * for the other panels described above.
		 */
		tradingHouse.add(propertyPanel);
		//tradingHouse.setLayout(new GridBagLayout());    
        tradingHouse.setLocationRelativeTo(null);
        tradingHouse.setVisible(true);
        tradingHouse.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        tradingHouse.setResizable(true);
        tradingHouse.pack();
		
		
	}
	
	private void createPropertyPanel(Player player){
		
		//Add question for user.
		propertyPanel.add(propertyDirections);
		
		//Add a radio button for each property.
		for(int i=0;i<5;i++){
			//player.getProperties().size()
			//player.getProperties().get(i).getIdentifier()
			propertyRadioButtons.add(new JRadioButton("Property"));
			propertyRadioButtonPanel.add(propertyRadioButtons.get(i));
		}
		
		//Create panel of buttons.
		buttonPanel.add(confirmationButton);
		buttonPanel.add(cancelButton);
		
		//Add the remaining components
		propertyPanel.add(propertyRadioButtonPanel);
		propertyPanel.add(buttonPanel);
		
	}
	
	private void createAssetsPanel(){
		
		//Add user directions.
		assetsPanel.add(assetsDirections);
		
		//Add buttons to group so only one is selectable
		assetButtonGrouping.add(propertyChoice);
		assetButtonGrouping.add(cashChoice);
		
		//Add the buttons to the panel
		assetsPanel.add(propertyChoice);
		assetsPanel.add(cashChoice);
		
		
		//Add the final component to the button panel
		assetsPanel.add(buttonPanel);
		
	}
	
	private void createPlayerChoicesPanel(){
		
		//Add user directions
		playerChoicesPanel.add(playerChoiceDirections);
		
		//Add a players name to each radio button
		for(int i=0;i<players.size();i++){
			
			//Make sure we don't add the player wishing to trade.
			if(!players.get(i).getIdentifier().equals(player.getIdentifier())){
				playerRadioButtons.add(new JRadioButton(players.get(i).getIdentifier()));
			}
		}
		
		//Add radio buttons to their panel and to a group so only one can be chosen.
		for(int i=0;i<playerRadioButtons.size();i++){
			playerRadioButtonPanel.add(playerRadioButtons.get(i));
			playerButtonGroup.add(playerRadioButtons.get(i));
		}
		
		playerChoicesPanel.add(playerRadioButtonPanel);
		
		playerChoicesPanel.add(buttonPanel);
			
	}
	
	private void createPresentOfferPanel(){
		
		String assetWanted = "";
		
		if(cashChosen){
			assetWanted = "cash";
		}
		else if(propertyChosen){
			assetWanted = "property";
		}
	
		
		acceptOrDeclineQuestion.setText(playerToTradeWith+", "+player.getIdentifier()+" wants to trade the following for "+assetWanted);
		//Add Question
		playerOfferPanel.add(acceptOrDeclineQuestion);
		
		//Present player with properties the player wants to trade.
		for(int i=0;i<propertiesWishingToTrade.size();i++){
			playerOfferPanel.add(new JLabel(propertiesWishingToTrade.get(i)));
		}
		
		//Change button text for clarity
		confirmationButton.setText("ACCEPT");
		cancelButton.setText("REJECT");
		
		playerOfferPanel.add(buttonPanel);
		
	}
	
	/*
	 * If the player accepts the offer they now say
	 * how much they'll offer the player wanting to trade.
	 */
	private void createPlayerBidPanel(){
		
		bidPanel.add(bidQuestion);
		bidPanel.add(bidArea);
		
		confirmationButton.setText("Confirm");
		cancelButton.setText("Cancel");
		
		bidPanel.add(buttonPanel);
	}
	
	private void updateFrame(){
		if(!offerAccepted){
			if(!offerGiven){
				if(!playerAccepted){
					if(!playerChosen){
						if(!assetsChosen){
							if(!propertiesChosen){
							}else{//Properties chosen, set up assets
								
								/*
								 * Create a list of properties they want to trade.
								 */
								System.out.println("In properties");
								createAssetsPanel();
								tradingHouse.remove(propertyPanel);
								tradingHouse.add(assetsPanel);
								tradingHouse.revalidate();
								tradingHouse.repaint();
								tradingHouse.pack();
								
								
							}
						}else{//Assets chosen, set up player to offer to
							
							/*
							 * Create a list of players to choose from
							 * that a player wants to trade with.
							 */
							createPlayerChoicesPanel();
							tradingHouse.remove(assetsPanel);
							tradingHouse.add(playerChoicesPanel);
							tradingHouse.revalidate();
							tradingHouse.repaint();
							tradingHouse.pack();
						}
					}else{//Player chosen, ask player to accept
						
						createPresentOfferPanel();
						tradingHouse.remove(playerChoicesPanel);
						tradingHouse.add(playerOfferPanel);
						tradingHouse.revalidate();
						tradingHouse.repaint();
						tradingHouse.pack();
						
					}
				}else{//Player has accepted or declined, proceed to players offer or return to choosing another
					
					createPlayerBidPanel();
					tradingHouse.remove(playerOfferPanel);
					tradingHouse.add(bidPanel);
					tradingHouse.revalidate();
					tradingHouse.repaint();
					tradingHouse.pack();
					
				}
			}else{//Chosen player has given offer, proceed to accepting/declining
				
			}
		}else{//Offer has been accepted/declined. Accept -> dispose of frame. Decline -> Return to choosing another player
			
		}
	}
	
	
	
	private void addActionListeners(){
		//TODO Add action listeners to buttons
		confirmationButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//Check player has chosen properties and update list and JFrame.
				if(!propertiesChosen) {
					if(findPropertiesWishingToTrade()){
						propertiesChosen = true;
						updateFrame();
					}
				}
				
				else if(!assetsChosen){
					if(assetChoiceSelected()){
						assetsChosen = true;
						updateFrame();
					}
				}
				
				else if(!playerChosen){
					if(playerButtonSelected()){
						playerChosen = true;
						updateFrame();
					}
				}
				
				else if(!playerAccepted){
						playerAccepted = true;
						updateFrame();
				}
			}
		});
	}
	
	private boolean findPropertiesWishingToTrade(){
		for(int i=0;i<propertyRadioButtons.size();i++){
			if(propertyRadioButtons.get(i).isSelected()){
				propertiesWishingToTrade.add(propertyRadioButtons.get(i).getText());
			}
		}
		
		return propertiesWishingToTrade.size() > 0;
	}
	
	private boolean assetChoiceSelected(){
		boolean chosenAnAsset = false;
		
		if(propertyChoice.isSelected()){
			propertyChosen = true;
			chosenAnAsset = true;
		}
		
		else if(cashChoice.isSelected()){
			cashChosen = true;
			chosenAnAsset = true;
		}
		
		return chosenAnAsset;
		
	}
	
	private boolean playerButtonSelected(){
		
		boolean playerChosen = false;
		int i=0;
		
		/*
		 * If a player is selected, get their name.
		 */
		while(i<playerRadioButtons.size() && !playerChosen){
			if(playerRadioButtons.get(i).isSelected()){
				playerChosen  = true;
				playerToTradeWith = playerRadioButtons.get(i).getText();
			}
			else{
				i++;
			}
			
		}
		
		return playerChosen;
	}
	
	
	
}
