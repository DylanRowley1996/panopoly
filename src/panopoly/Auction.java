package panopoly;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import interfaces.Ownable;
import locations.NamedLocation;
import locations.PrivateProperty;
import interfaces.Rentable;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import static java.awt.FlowLayout.CENTER;

public class Auction extends JFrame{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private HistoryLog history;
	private Ownable propertyToAuction;

	//Information needed for players.
	private int currentPlayerNumber = 0;
	private ArrayList<Player> players;
	private ArrayList<Integer> indicesOfWithdrawnPlayers = new ArrayList<Integer>();

	//HashMap of players and their bids.
	private HashMap<String, Integer> playersAndBids = new HashMap<String, Integer>();

	//All necessary components for displaying property info.
	private JPanel propertyPanel = new JPanel(new GridLayout(0,1));

	private JTextPane propertyInformationPanel = new JTextPane();

	//All necessary components for bidding/withdrawing.
	private JPanel biddingProcessPanel = new JPanel();
	private JPanel buttonPanel = new JPanel();
	private JPanel inputBidPanel = new JPanel();
	private JTextArea bidInputArea = new JTextArea(2,10);
	private JButton bidButton = new JButton("Bid");
	private JButton withdrawButton = new JButton("Withdraw");

	//All necessary components to display bidding history
	private JPanel biddingHistoryPanel = new JPanel();
	private JLabel biddingHistoryTitle = new JLabel("Bidding History");
	private JTextArea biddingHistoryArea = new JTextArea();
	private JScrollPane biddingHistoryScrollable = new JScrollPane(biddingHistoryArea);


	private JSplitPane propertyAndBiddingPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	private JSplitPane splitBiddingPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT);


	public Auction(Ownable propertyToAuction, ArrayList<Player> players, HistoryLog history){

		this.players = players;
		this.history = history;
		this.propertyToAuction = propertyToAuction;

		//Add all players to hash map with default bids of 0.
		populatePlayersAndBids();

		//Add property panel to frame.
		propertyPanel.setVisible(true);
		propertyPanel.setSize(new Dimension(200,300));


		formatPropertyComponents(propertyToAuction ,propertyPanel);

		biddingHistoryArea.setEditable(false);

		addActionListeners(this);
		buttonPanel.setOpaque(true);
		buttonPanel.setBackground(Color.BLACK);
		buttonPanel.setLayout(new FlowLayout(CENTER));
		buttonPanel.add(bidButton);
		buttonPanel.add(withdrawButton);

		inputBidPanel.setOpaque(true);
		inputBidPanel.setBackground(Color.BLACK);
		inputBidPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		inputBidPanel.add(bidInputArea);

		//Create bidding history panel
		biddingHistoryArea.setText("-> Player: "+players.get(currentPlayerNumber).getIdentifier()+".\n\n-> Enter Bid or Withdraw from Auction.\n");
		biddingHistoryPanel.setLayout(new BorderLayout());
		biddingHistoryArea.setLineWrap(true);
		biddingHistoryTitle.setHorizontalAlignment(JLabel.CENTER);
		biddingHistoryTitle.setVerticalAlignment(JLabel.CENTER);
		biddingHistoryPanel.add(biddingHistoryTitle, BorderLayout.PAGE_START);
		biddingHistoryPanel.add(biddingHistoryScrollable, BorderLayout.CENTER);

		//Add bid and withdraw buttons to bidding JPanel.
		biddingProcessPanel.setOpaque(true);
		biddingProcessPanel.setBackground(Color.BLACK);
		biddingProcessPanel.setLayout(new FlowLayout());
		biddingProcessPanel.add(inputBidPanel);
		biddingProcessPanel.add(buttonPanel);

		//Set size before add split panes. Rlse, they won't show.
		setSize(400,300);

		/* Split the bidding process into two parts across the middle.
		 * Top is entering bids or withdrawing from auction.
		 * Bottom is log of bids.*/
		splitBiddingPanel.setPreferredSize(new Dimension(getWidth()/2,getHeight()));
		splitBiddingPanel.setDividerLocation(getHeight()/2);
		splitBiddingPanel.setTopComponent(biddingProcessPanel);
		splitBiddingPanel.setBottomComponent(biddingHistoryPanel);

		/*
        Split the two parts of the panel down the middle.
        Left side is property info.
        Right side is bidding info.
		 */
		propertyAndBiddingPanel.setPreferredSize(new Dimension(getWidth(),getHeight()));
		propertyAndBiddingPanel.setDividerLocation(getWidth()/2);
		propertyAndBiddingPanel.setLeftComponent(propertyPanel);
		propertyAndBiddingPanel.setRightComponent(splitBiddingPanel);

		getContentPane().add(propertyAndBiddingPanel);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setResizable(false);
		setTitle("Auction House");
		pack();

	}


	//Take the property, format all it's information and add to property panel.
	private void formatPropertyComponents(Ownable property, JPanel propertyPanel){

		String propertyInformation = "Property:\n";


		//Get name of property
		if(property instanceof NamedLocation){
			propertyInformation += "Name: "+((NamedLocation)property).getIdentifier()+"\n";
		}

		//    	//Get price of property
		//    	if(property instanceof Ownable){
		//    		propertyInformation += "Price: $"+((Ownable)property).getPrice()+"\n";
		//    	}

		//Get rental prices of property
		if(property instanceof Rentable){

			propertyInformation += property.toString()+"\n";

		}

		//    	//Get Mortgage value of property
		//    	if(property instanceof Mortgageable){
		//    		propertyInformation += "Mortgage Value: "+((Mortgageable)property).getMortgageAmount()+"\n";
		//    	}
		//    	
		//    	//Get Group of Property
		//    	if(property instanceof Groupable){
		//    		propertyInformation += "Group: "+((Groupable)property).getGroup()+"\n";
		//    	}

		//Set up header of property card.
		StyledDocument doc = propertyInformationPanel.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		propertyInformationPanel.setBackground(Color.decode("#E53C00"));
		propertyInformationPanel.setText(propertyInformation);
		propertyInformationPanel.setEditable(false);


		//Finally, add the JTextPane to the panel
		propertyPanel.add(propertyInformationPanel);


	}

	//Add functionality to bidding and withdraw buttons
	private void addActionListeners(JFrame auctionHouse){

		//Add functionality to Bidding Button
		bidButton.addActionListener(new ActionListener() {
			int maxBid=0;
			@Override
			public void actionPerformed(ActionEvent e) {

				try{
					int bid = Integer.parseInt(bidInputArea.getText().trim());

					//Make sure player has enough to bid
					if(bid > players.get(currentPlayerNumber).getNetWorth()){
						biddingHistoryArea.append("-> Player: "+players.get(currentPlayerNumber).getIdentifier()+" does not have "
								+ "enough to place this bid.\n-> Your balance is: "+players.get(currentPlayerNumber).getNetWorth()+"\n\n");
					}
					else if(bid<=maxBid) {
						biddingHistoryArea.append("-> Bid must be greater than the current highest bid of $"+maxBid+"\n\n");
					}

					else{

						//Tell users what the bid was
						biddingHistoryArea.append("-> Player: "+players.get(currentPlayerNumber).getIdentifier()+" has bid $"
								+bid+"\n\n");
						
						if(bid>maxBid) maxBid=bid;

						//Place players name and their bid in hash map
						playersAndBids.put(players.get(currentPlayerNumber).getIdentifier(), bid);

						//Some have withdrawn from the bidding, make sure we don't allow them to bid again.
						//By looping through until we find a currentPlayer number not in withdrawn indices
						if(indicesOfWithdrawnPlayers.size() != 0){

							if(currentPlayerNumber == players.size()-1){
								currentPlayerNumber = 0;
							}
							else{
								currentPlayerNumber ++;
							}

							while(indicesOfWithdrawnPlayers.contains(currentPlayerNumber)){
								if(currentPlayerNumber == players.size()-1){
									currentPlayerNumber = 0;
								}
								else{
									currentPlayerNumber ++;
								}
							}
						}

						//No one has withdrawn from the bidding yet, loop through players as normal.
						else{
							if(currentPlayerNumber == players.size()-1){
								currentPlayerNumber = 0;
							}
							else{
								currentPlayerNumber ++;
							}
						}

					}



					biddingHistoryArea.append("-> Player: "+players.get(currentPlayerNumber).getIdentifier()+". Enter Bid or Withdraw from Auction.\n\n");

				}catch(NumberFormatException ex){ // handle your exception

					biddingHistoryArea.append("-> Not an integer. Enter another bid.\n\n");

				}

			}
		});

		//Add functionality to Withdraw Button
		withdrawButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				indicesOfWithdrawnPlayers.add(currentPlayerNumber);

				//Remove player that has just withdrawn from the map
				playersAndBids.remove(players.get(currentPlayerNumber).getIdentifier());

				//Tell users who has withdrawn
				biddingHistoryArea.append("-> "+players.get(currentPlayerNumber).getIdentifier()+" has withdrawn from the Auction.\n\n");

				if(playersAndBids.size() == 1){
					while(indicesOfWithdrawnPlayers.contains(currentPlayerNumber)){
						if(currentPlayerNumber == players.size()-1){
							currentPlayerNumber = 0;
						}
						else{
							currentPlayerNumber ++;
						}
					}

					//Add the property to the players properties and deduct their bid from their balance
					players.get(currentPlayerNumber).addProperty((PrivateProperty)propertyToAuction);
					players.get(currentPlayerNumber).deductFromBalance(playersAndBids.get(players.get(currentPlayerNumber).getIdentifier())); 
					propertyToAuction.setOwner(players.get(currentPlayerNumber));

					//Tell users who has won the auction on the property and dispose of JFrame.
					history.getTextArea().append("-> "+players.get(currentPlayerNumber).getIdentifier()+" has won the auction for "
							+ ((PrivateProperty)propertyToAuction).getIdentifier()+" for $"
							+playersAndBids.get(players.get(currentPlayerNumber).getIdentifier())+"!\n\n");


					auctionHouse.dispose();
				}

				else{
					while(indicesOfWithdrawnPlayers.contains(currentPlayerNumber)){
						if(currentPlayerNumber == players.size()-1){
							currentPlayerNumber = 0;
						}
						else{
							currentPlayerNumber++;
						}
					}


				}

				biddingHistoryArea.append("-> It is now: "+players.get(currentPlayerNumber).getIdentifier()+" turn.\n\n");

			}
		});
	}

	//Populate the hashmap with default bids of 0.
	private void populatePlayersAndBids(){
		for(int i=0;i<players.size();i++){
			playersAndBids.put(players.get(i).getIdentifier(), 0);
		}
	}

}
