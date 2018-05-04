package panopoly;
import javax.swing.*;

import interfaces.Groupable;
import interfaces.Improvable;
import interfaces.Mortgageable;
import interfaces.Ownable;
import locations.NamedLocation;
import locations.PrivateProperty;
import locations.PropertyGroup;
import interfaces.Rentable;

import java.awt.*;

import static java.awt.FlowLayout.CENTER;

public class Auction extends JFrame{
	

    //All necessary components for displaying property info.
    private JPanel propertyPanel = new JPanel(new GridLayout(5,0));
    private JLabel[] propertyComponents = new JLabel[5];

    //All necessary components for bidding/withdrawing.
    private JPanel biddingProcessPanel = new JPanel();
    private JPanel buttonPanel = new JPanel();
    private JPanel inputBidPanel = new JPanel();
    private JTextArea bidInputArea = new JTextArea("Enter your bid here!");
    private JButton bidButton = new JButton("Bid");
    private JButton withdrawButton = new JButton("Withdraw");

    //All necessary components to display bidding history
    private JPanel biddingHistoryPanel = new JPanel();
    private JLabel biddingHistoryTitle = new JLabel("Bidding History");
    private JTextArea biddingHistoryArea = new JTextArea("ONLY THE RECENT HISTORY OF BIDS WILL BE ENTERED HERE. Not all of them");

    private JSplitPane propertyAndBiddingPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    private JSplitPane splitBiddingPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT);


    public Auction(Ownable propertyToAuction){
    	    	
    	formatPropertyComponents(propertyToAuction ,propertyPanel);

        biddingHistoryArea.setEditable(false);

        buttonPanel.setLayout(new FlowLayout(CENTER));
        buttonPanel.add(bidButton);
        buttonPanel.add(withdrawButton);

        inputBidPanel.add(bidInputArea);

        //Create bidding history panel
        biddingHistoryPanel.setLayout(new BorderLayout());
        biddingHistoryArea.setLineWrap(true);
        biddingHistoryTitle.setHorizontalAlignment(JLabel.CENTER);
        biddingHistoryTitle.setVerticalAlignment(JLabel.CENTER);
        biddingHistoryPanel.add(biddingHistoryTitle, BorderLayout.PAGE_START);
        biddingHistoryPanel.add(biddingHistoryArea, BorderLayout.CENTER);

        //Add bid and withdraw buttons to bidding JPanel.
        biddingProcessPanel.setLayout(new FlowLayout());
        biddingProcessPanel.add(inputBidPanel);
        biddingProcessPanel.add(buttonPanel);

        //Add property panel to frame.
        propertyPanel.setVisible(true);
        

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
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setTitle("Auction House");
        pack();

    }
    
    private void addPropertyInformationToPanel(JPanel propertyPanel){
    	for(int i=0;i<propertyComponents.length;i++){
    		propertyPanel.add(propertyComponents[i]);
    	}
     }
    
    private void formatPropertyComponents(Ownable property, JPanel propertyPanel){
    	
    	//Get name of property
    	if(property instanceof NamedLocation){
        	propertyComponents[0] = new JLabel("Property Name: "+((NamedLocation)property).getIdentifier());
        	propertyComponents[0].setSize(propertyPanel.getWidth(), propertyPanel.getHeight());
    	}
    	
    	//Get price of property
    	if(property instanceof Ownable){
    		propertyComponents[1] = new JLabel("Price: "+((Ownable)property).getPrice());
        	propertyComponents[1].setSize(propertyPanel.getWidth(), propertyPanel.getHeight());
    	}
    	
    	//Get rental prices of property
    	if(property instanceof Rentable){
        	String rentals = "";
        	
        	//Retrive rental amounts from this property, format to string
        	int[] rentalAmounts = ((Rentable)property).getAllRents();
        	for(int i=0;i<rentalAmounts.length;i++){
        		rentals += rentalAmounts[i]+" ";
        	}
        	
        	propertyComponents[2] = new JLabel("Property Rent: "+rentals);
        	propertyComponents[2].setSize(propertyPanel.getWidth(), propertyPanel.getHeight());
    	}
    	
    	//Get Mortgage value of property
    	if(property instanceof Mortgageable){
    		propertyComponents[3] = new JLabel("Mortgage Value: "+((Mortgageable)property).getMortgageAmount());
        	propertyComponents[3].setSize(propertyPanel.getWidth(), propertyPanel.getHeight());
    	}
    	
    	//Get Group of Property
    	if(property instanceof Groupable){
        	propertyComponents[4] = new JLabel("Group: "+((Groupable)property).getGroup());
        	propertyComponents[4].setSize(propertyPanel.getWidth(), propertyPanel.getHeight());
    	}
    	
    	addPropertyInformationToPanel(propertyPanel);
    	
    }
}
