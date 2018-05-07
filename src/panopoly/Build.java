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

public class Build {

	private JFrame frame = new JFrame("Build Houses/Hotel");
	private HistoryLog history;
	private Player player;
	
	private Border border = BorderFactory.createLineBorder(Color.BLACK);
	

	private JPanel mainPanel = new JPanel(new BorderLayout());
	
	//Top Panel
	private JLabel userDirection = new JLabel("      Choose the properties and enter the number of houses you"
			+ " want build on property selected. 5 Houses = Hotel      ");
	
	//Middle Panel
	private JPanel propertyHouseSelectionPanel = new JPanel(new GridLayout(0,2));
	private ArrayList<JTextArea> houseNumberInput = new ArrayList<JTextArea>();
	private ArrayList<JRadioButton> propertyButtons = new ArrayList<JRadioButton>();
	
	//Holds label and button panel
	private JPanel buttonPanel = new JPanel(new FlowLayout());
	private JButton confirmationButton = new JButton("Confirm");
	private JButton cancelationButton = new JButton("Cancel");
		
	public Build(Player player, HistoryLog history){
		
		this.history = history;
		this.player = player;
		
		createMainPanel();
		addConfirmationButtonListner();
		addCancelButtonActionListener();
		
		frame.setLocationRelativeTo(null);
	    frame.setVisible(true);
	    frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	    frame.setResizable(true);
		
	}
	
	private void createMainPanel(){
		
		frame.remove(mainPanel);
		mainPanel.removeAll();
		propertyHouseSelectionPanel.removeAll();
		buttonPanel.removeAll();
		propertyButtons.clear();
		houseNumberInput.clear();
		
		for(int i=0;i<player.getProperties().size();i++){
			if(player.getProperties().get(i) instanceof ImprovableProperty){
				ImprovableProperty prop = ((ImprovableProperty)player.getProperties().get(i));
				if(player.getMonopolies().contains(prop.getGroup()) && prop.getNumHouses()<5) {
					//Adding to buttons text areas to a list will make access easier later on
					propertyButtons.add(new JRadioButton(prop.getIdentifier()+"  |  Houses: "+prop.getNumHouses()+"  |  Build Cost: $"+prop.getBuildCost()));
					houseNumberInput.add(new JTextArea());
				}
			}
		}
				
		if(propertyButtons.size() == 0){
			frame.dispose();
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
		mainPanel.add(propertyHouseSelectionPanel, BorderLayout.NORTH);
		mainPanel.add(userDirection, BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		frame.add(mainPanel);
	    frame.pack();
	    frame.repaint();
	    frame.revalidate();
	}
	
	private void addConfirmationButtonListner(){
		confirmationButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				for(int i=0;i<propertyButtons.size();i++) {
					
					if(propertyButtons.get(i).isSelected()){
						
						try{
							
							int noOfHousesToBuy = Integer.parseInt(houseNumberInput.get(i).getText());
							String propName = propertyButtons.get(i).getText().split("  |  ")[0];
							int noOfHousesOnProperty =  getNumberOfHousesOnProperty(propName);
							
							//Find the number of houses on the property by name
							if(noOfHousesToBuy+noOfHousesOnProperty>5){
								history.getTextArea().append("Error: Too many houses being built on "+propName+"\n\n");
							}
							
							else{	
								/*
								 * Valid number of houses. Place the property name and the number 
								 * in a hash map.
								 * Update JFrame.
								 */
								buyHouses(propName, noOfHousesToBuy);
								
							}
							
						}catch(Exception exception){
							
							houseNumberInput.get(i).setText("Not a valid input. Enter an Integer.");

						}
					}
				}
				createMainPanel();
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
	
	private void buyHouses(String property, int noOfHousesToBuy){
		System.out.println(property);
		//Iterate through all properties
		for(int i=0;i<player.getProperties().size();i++){
			
			if(player.getProperties().get(i) instanceof ImprovableProperty &&
			   player.getProperties().get(i).getIdentifier().equals(property)){
				//Get build price of this property
				ImprovableProperty prop = ((ImprovableProperty)player.getProperties().get(i));
				int cost = prop.getBuildCost()*noOfHousesToBuy;
				
				if(player.getNetWorth()>cost) {
					//Add the amount to players balance.
					player.deductFromBalance(cost);
					
					//build the houses from the property
					prop.buildHouses(noOfHousesToBuy);
					
					//Update history on GUI.
					history.getTextArea().append("-> "+player.getIdentifier()+" has built "+noOfHousesToBuy+
							" house(s) on "+player.getProperties().get(i).getIdentifier()+" for $"+cost+"\n\n");
					
				}
				else {
					history.getTextArea().append("-> Error: insufficient funds to build "+noOfHousesToBuy+
							" house(s) on "+player.getProperties().get(i).getIdentifier()+"\n\n");
				}
				
			}
			
			
		}
	}
	
	
}

