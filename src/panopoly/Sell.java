package panopoly;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

import locations.ImprovableProperty;

public class Sell {

	private JFrame frame = new JFrame("Sell Houses");
	private HistoryLog history;
	private Player player;
	
	private Border border = BorderFactory.createLineBorder(Color.BLACK);
	

	private JPanel mainPanel = new JPanel(new BorderLayout());
	
	//Top Panel
	private JLabel userDirection = new JLabel("Choose the properties and enter the number of houses you"
			+ " want to sell for each property selected.");
	
	//Middle Panel
	private JPanel propertyHouseSelectionPanel = new JPanel(new GridLayout(0,2));
	private ArrayList<JTextArea> houseNumberInput = new ArrayList<JTextArea>();
	private ArrayList<JRadioButton> propertyButtons = new ArrayList<JRadioButton>();
	
	//Holds label and button panel
	private JPanel buttonPanel = new JPanel(new FlowLayout());
	private JButton confirmationButton = new JButton("Confirm");
	private JButton cancelationButton = new JButton("Cancel");
	
	private ArrayList<Integer> indicesToRemove = new ArrayList<Integer>();
		
	public Sell(Player player, HistoryLog history){
		
		this.history = history;
		this.player = player;
		
		createMainPanel();
		addConfirmationButtonListner();
		addCancelButtonActionListener();
		
		frame.add(mainPanel);
	    frame.pack();
		frame.setLocationRelativeTo(null);
	    frame.setVisible(true);
	    frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	    frame.setResizable(true);
		
	}
	
	private void createMainPanel(){
		
		for(int i=0;i<player.getProperties().size();i++){
			if(player.getProperties().get(i) instanceof ImprovableProperty){
				if(((ImprovableProperty)player.getProperties().get(i)).getNumHouses() > 0){
					
					//Adding to buttons text areas to a  list will make access easier later on
					propertyButtons.add(new JRadioButton(player.getProperties().get(i).getIdentifier()));
					houseNumberInput.add(new JTextArea());
					
				}
			}
		}
				
		for(int i=0;i<houseNumberInput.size();i++){
			
			//Format JTextArea
			houseNumberInput.get(i).setBorder(BorderFactory.createCompoundBorder(border,
					BorderFactory.createEmptyBorder(10, 10, 10, 10)));


			//Add property button and corresponding text area
			propertyHouseSelectionPanel.add(propertyButtons.get(i));
			propertyHouseSelectionPanel.add(houseNumberInput.get(i));
		}
		
		
		//Add buttons to same panel.
		buttonPanel.add(confirmationButton);
		buttonPanel.add(cancelationButton);
		
		//Add all components to the main panel
		//mainPanel.add(userDirectionHolder);
		mainPanel.add(propertyHouseSelectionPanel, BorderLayout.NORTH);
		mainPanel.add(userDirection, BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
	}
	
	private void addConfirmationButtonListner(){
		confirmationButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				for(int i=0;i<propertyButtons.size();i++){
					
					if(propertyButtons.get(i).isSelected()){
						
						try{
							
							int noOfHousesToSell = Integer.parseInt(houseNumberInput.get(i).getText());
							int noOfHousesOnProperty =  getNumberOfHousesOnProperty(propertyButtons.get(i).getText());
							
							//Find the number of houses on the property by name
							if(noOfHousesToSell > noOfHousesOnProperty){
								houseNumberInput.get(i).setText("There is only "+noOfHousesOnProperty+
										" on this property");
							}
							
							else{	
								/*
								 * Valid number of houses. Place the property name and the number 
								 * in a hash map.
								 * Update JFrame.
								 */
								sellHousesUpdateBalance(propertyButtons.get(i).getText(),noOfHousesToSell);
								indicesToRemove.add(i);
								
							}

						}catch(Exception exception){
							
							houseNumberInput.get(i).setText("Not a valid input. Enter an Integer.");

						}
					}
				}
				
				for(int i=0;i<indicesToRemove.size();i++){
					System.out.println("Removing "+indicesToRemove.get(i));
					updateFrame(indicesToRemove.get(i));
				}
				
				indicesToRemove.clear();
				
			}
		});
		
		
	}
	
	private void addCancelButtonActionListener(){
		cancelationButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
	}
	
	private int getNumberOfHousesOnProperty(String propertyName){
		
		int noOfHouses = 0;
		int i=0;
		boolean propertyFound = false;
		
		while(!propertyFound){
			if(player.getProperties().get(i).getIdentifier().equals(propertyName)){
				noOfHouses = player.getProperties().get(i).getNumHouses();
				propertyFound = true;
			}
			else{
				i++;
			}
		
		}
			
		
		return noOfHouses;
	}
	
	private void updateFrame(int i){
		
		propertyHouseSelectionPanel.remove(propertyButtons.get(i));
		propertyHouseSelectionPanel.remove(houseNumberInput.get(i));
		propertyHouseSelectionPanel.repaint();
		propertyHouseSelectionPanel.revalidate();
		
		System.out.println("Removing Button: "+propertyButtons.get(i).getText());

		propertyButtons.remove(i);
		houseNumberInput.remove(i);
		propertyButtons.trimToSize();
		houseNumberInput.trimToSize();
		
		//No choices left. Dispose of frame.
		if(propertyButtons.size() == 0){
			frame.dispose();
		}
		
		//Choices left, update.
		else{
			frame.revalidate();
			frame.repaint();
		}
				
	}
	
	private void sellHousesUpdateBalance(String property, int noOfHousesToSell){
		
		//Iterate through all properties
		for(int i=0;i<player.getProperties().size();i++){
			
			if(player.getProperties().get(i) instanceof ImprovableProperty &&
			   player.getProperties().get(i).getIdentifier().equals(property)){
				
				//Get building cost of this property
				int cost = ((ImprovableProperty)player.getProperties().get(i)).getBuildCost()/2;
				
				//Add the amount to players balance.
				player.addToBalance(cost*noOfHousesToSell);
				
				//Remove the houses from the property
				((ImprovableProperty)player.getProperties().get(i)).removeHouse(noOfHousesToSell);
				
				//Update history on GUI.
				history.getTextArea().append("-> "+player.getIdentifier()+" has sold "+noOfHousesToSell+
						" house(s) on "+player.getProperties().get(i).getIdentifier()+"\n\n");
				
			}
			
			
		}
	}
	
	
}
