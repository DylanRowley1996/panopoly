package panopoly;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

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
	
	private HashMap<String, Integer> propertyHousesToSell = new HashMap<String, Integer>();
	
	public Sell(Player player, HistoryLog history){
		
		this.history = history;
		this.player = player;
		
		createMainPanel();
		addActionListners();
		
		frame.add(mainPanel);
	    frame.pack();
		frame.setLocationRelativeTo(null);
	    frame.setVisible(true);
	    frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	    frame.setResizable(true);
		
	}
	
	private void createMainPanel(){
		
		for(int i=0;i<player.getProperties().size();i++){
			if(player.getProperties().get(i).getNumHouses() > 0){
				
				//Adding to buttons text areas to a  list will make access easier later on
				propertyButtons.add(new JRadioButton(player.getProperties().get(i).getIdentifier()));
				houseNumberInput.add(new JTextArea());
				
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
	
	private void addActionListners(){
		confirmationButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				
			}
		});
		
	}
	
	
}
