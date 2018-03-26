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
	private JComboBox playerList = null;
    private Player[] players = null;
    private JLabel playerInformation = new JLabel("");

    public Overview(Player[] players){
        super("Overview");

        this.players = players;

        //Populate ComboBox with Players names
        String[] playersNames = new String[5];
        for(int i=0; i<players.length; i++){
            playersNames[i] = players[i].getName();
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
            if(players[i].getName().equals(playerToFind)){
                playerFound = true;
            }
            else{
                i++;
            }
        }

       //Format the data.
        playerInformation.setFont(new Font("Rockwell", Font.BOLD,15));
        playerInformation.setText("<html><b><span style=\"font-family:Rockwell;font-size:15px;\">Player</span></b>: "+players[i].getName()+ "<br/>" +
                "<b><span style=\"font-family:Rockwell;font-size:15px;\">Net worth: </span></b>"+players[i].getNetWorth()+ "<br/>" +
                "<b><span style=\"font-family:Rockwell;font-size:15px;\">Properties: </span></b> "+String.join(", ",players[i].getProperties())+ "<br/>"+
                "<b><span style=\"font-family:Rockwell;font-size:15px;\">Monopolies: </span></b> "+String.join(", ",players[i].getMonopolies())+ " <br/>"+
                "<b><span style=\"font-family:Rockwell;font-size:15px;\">Mortgages:  </span></b>"+String.join(", ",players[i].getMortgages())+ " <br/></html>");


    }

}
