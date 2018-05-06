package panopoly;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

public class Trade extends JFrame{

	JLabel userDirections = new JLabel("Choose which properties you want to trade then hit 'Confirm'");

	JPanel buttonPanel = new JPanel(new FlowLayout());
	JButton confirmationButton = new JButton("Confirm");
	JButton cancelButton = new JButton("Cancel");
	
	JPanel radioButtonPanel = new JPanel(new GridLayout(0,1));
	
	ArrayList<JRadioButton> propertyRadioButtons = new ArrayList<JRadioButton>();
	ArrayList<String> propertiesWishingToTrade = new ArrayList<String>();

	public Trade(Player player, ArrayList<Player> players, HistoryLog history){
		
		createPropertyPanel(player);
		
		add(radioButtonPanel);
		setLayout(new GridBagLayout());    
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setTitle("Auction House");
        pack();
		
		
	}
	
	private void createPropertyPanel(Player player){
		
		radioButtonPanel.add(userDirections);
		//player.getProperties().size();
		for(int i=0;i<player.getProperties().size();i++){
			propertyRadioButtons.add(new JRadioButton(player.getProperties().get(i).getIdentifier()));
			radioButtonPanel.add(propertyRadioButtons.get(i));
		}
		
		buttonPanel.add(confirmationButton);
		buttonPanel.add(cancelButton);
		radioButtonPanel.add(buttonPanel);
		
	}
	
	
	
}
