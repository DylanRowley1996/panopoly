package panopoly;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import interfaces.Mortgageable;
import locations.*;

public class PartyLeader {
	private static ArrayList<NamedLocation> locations = (ArrayList<NamedLocation>) SetupGame.getLocationList();
	private static ArrayList<Player> players = SetupGame.getPlayers();
	
	public void roll(Player player) throws InvalidFormatException, IOException {
		// TODO
		
		//after roll
		if(!(player.getLocation() instanceof MCQLocation)) { // TODO get rid of 
			MCQ mcq = new MCQ();
			mcq.createMCQPanel();
		}
	}
	
	public void buy(Player player) {
		// TODO
	}
	
	public void sell(Player player) {
		// TODO
	}
	
	public void mortgage(Player player){
		//TODO
		//Get list of players properties.
		//Check what properties are mortgageable
		//Pass information to JPanel
		//Allow them to select which properties to mortgage.
		//Ensure balance is updated accordingly
		ArrayList<PrivateProperty> mortgageableProperties = new ArrayList<PrivateProperty>();
		JFrame mortgageFrame = new JFrame("Mortgage Choices");
		JLabel userDirections = new JLabel("Choose which properties to mortgage then hit 'Confirm'");

		
		
		for(int i=0;i<player.getProperties().size();i++){
			//If property is mortgageable and is not already mortgaged
			if(player.getProperties().get(i) instanceof Mortgageable && player.getProperties().get(i).isMortgaged() == false){
				mortgageableProperties.add(player.getProperties().get(i));
			}
		}
			
		/*
		 * Create an array list of buttons. One for each mortgageable property.
		 */
		ArrayList<JRadioButton> mortgageableRadioButtons = new ArrayList<JRadioButton>();
		for(int i=0;i<mortgageableProperties.size();i++){
			mortgageableRadioButtons.add(new JRadioButton("Property: "+mortgageableProperties.get(i).getIdentifier()
					+" Mortgage Amount: "+mortgageableProperties.get(i).getMortgageAmount()));
		}
		
		//Create the button for choice confirmation.
	    JButton confirmationButton = new JButton("Confirm");
	    
	    /* 1. Check which buttons are selected
	     * 2. Count the total amount of mortgages selected.
	     * 3. Add this to players account
	     * 4. Set isMortgaged to true for all properties that are mortgaged
	    */
		 confirmationButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	               
	                    try {	               
	                		double totalMortgageValue = 0;
	                    	int j=0;
	                    	while(j < mortgageableRadioButtons.size()){
	                    		if(mortgageableRadioButtons.get(j).isSelected()){
	                    			
	                    			//Start totalling value of mortgages.
	                    			totalMortgageValue += mortgageableProperties.get(j).getMortgageAmount();
	                    			
	                    			//Find index of property to mortgage then mortgage this property in players list.
	                    			int indexToMortgage = player.getProperties().indexOf(mortgageableProperties.get(j));
	                    			player.getProperties().get(indexToMortgage).mortagage();
	                    		}
	                    		j++;
	                    	}
                    		//Add the total value to the players balance.
                    		player.addToBalance(totalMortgageValue);
	                    	mortgageFrame.dispose();//Exit JFrame, player has selected the properties they want to redeem
						} 
	                    catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}	
	                } 
	            
	      });
		 
		 
		 //Add all choices to JPanel
		 JPanel radioPanel = new JPanel(new GridLayout(0, 1));
		 
		 //Add label that contains directions for user onto panel
		 radioPanel.add(userDirections);
		 
		 for(int i=0;i<mortgageableProperties.size();i++){
		     radioPanel.add(mortgageableRadioButtons.get(i));
		 }
		 
		 //Add button below choices
		 radioPanel.add(confirmationButton);
		 
		 radioPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
	     mortgageFrame.add(radioPanel);
	     mortgageFrame.setVisible(true);
	     mortgageFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	     mortgageFrame.pack();
	     mortgageFrame.setLocationRelativeTo(null);
	     
		
	}
	
	public void redeem(Player player){
		//TODO
	}
	
	public void auction(Player player){
		//TODO
	}
	
	public void finishTurn(Player player){
		//TODO
		// check for in jail too long, unpaid rent, etc.
	}
}
