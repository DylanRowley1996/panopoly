package panopoly;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Overview extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JComboBox<?> playerList;
    private ArrayList<Player> players;
    private JLabel playerInformation = new JLabel("");

	public Overview(ArrayList<Player> players){
        super("Overview");

        this.players = players;

        //Populate ComboBox with Players names
        String[] playersNames = new String[6];
        for(int i=0; i<players.size(); i++){
            playersNames[i] = players.get(i).getName();
        }

        playerList = new JComboBox(playersNames);


        //Add Panel to JFrame
        add(playerInformation);

        //Check if current selection has changed.
        ActionListener comboBoxListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String playerToFind = (String) playerList.getSelectedItem();//get the selected item
                organiseInformation(playerToFind);

            }
        };

        //Add the action listener to the combo box.
        playerList.addActionListener(comboBoxListener);
        playerList.setSelectedIndex(0);

        //Add combo box to JFrame
        add(playerList, BorderLayout.SOUTH);

        //Add panel to JFrame
        pack();
        setLocationRelativeTo(null);
        setSize(400,300);
        getContentPane().setBackground(Color.decode("#71AE6F"));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    //Organises the currently selected players information and places it in the JLabel.
    public void organiseInformation(String playerToFind){
    	
        boolean playerFound = false;
        int i = 0;

        //Find the correct player
        while(!playerFound){
            if(players.get(i).getName().equals(playerToFind)){
                playerFound = true;
            }
            else{
                i++;
            }
        }
        
    	String balance = buildBalanceString(players.get(i));
    	String properties = buildPropertiesString(players.get(i));;
    	String mortgages = buildMortgagesString(players.get(i));
    	String monopolies = "";
        
        

       //Format the data.
        playerInformation.setFont(new Font("Rockwell", Font.BOLD,15));
        playerInformation.setText("<html><b><span style=\"font-family:Rockwell;font-size:15px;\">Player</span></b>: "+players.get(i).getIdentifier()+ "<br/>" +
                "<b><span style=\"font-family:Rockwell;font-size:15px;\">Net worth: </span></b>"+balance+ "<br/>" +
                "<b><span style=\"font-family:Rockwell;font-size:15px;\">Properties: </span></b> "+properties+ "<br/>"+
                "<b><span style=\"font-family:Rockwell;font-size:15px;\">Mortgages: </span></b> "+mortgages+" <br/>"+
                "<b><span style=\"font-family:Rockwell;font-size:15px;\">Monopolies:  </span></b>"+monopolies+ " <br/></html>");


    }
  
    
    
    private String buildBalanceString(Player player){
    	return Integer.toString(player.getNetWorth());
    }
    
    private String buildPropertiesString(Player player){
    	String properties = "";
    	
    	if(player.getProperties().size() == 0){
    		properties += "No Properties Owned.";
    	}
    	else{
    		for(int i=0;i<player.getProperties().size();i++){
    			properties += player.getProperties().get(i).getIdentifier()+" ";
    		}
    	}
    	
    	
    	return properties;
    }
    
    private String buildMortgagesString(Player player){
    	String mortgages = "";
    	
    	if(player.getMortgages().size() == 0){
    		mortgages += "None.";
    	}
    	else{
    		for(int i=0;i<player.getProperties().size();i++){
    			if(player.getProperties().get(i).isMortgaged()){
    				mortgages += player.getProperties().get(i).getIdentifier()+" ";
    			}
    		}
    	}
    	
    	
    	return mortgages;
    }
    
   /* private String buildMonopoliesString(Player player){
    	String monopolies = "MONOPOLIES: ";
	    	if(player.getMonopolies().size() == 0){
	    		monopolies += "None.";
	    	}
	    	else{
	    		player.getMonopolies().
	    	}
	    	return monopolies;
    }
*/
}
