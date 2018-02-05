import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Overview extends JFrame {


    JPanel overviewPanel = new JPanel();
    JComboBox playerList = null;

    //Array of players
    Player[] players = null;

    //Fields to represent
    private JLabel playerInformation = new JLabel("");



    public Overview(Player[] players){
        super("Overview");

        //Initialise the array of Players.
        this.players = players;

        //Populate ComboBox with Players names
       String[] playersNames = new String[5];
        for(int i=0; i<players.length; i++){
            playersNames[i] = players[i].getName();
        }

        playerList = new JComboBox(playersNames);


        //Add Panel to JFrame
        add(playerInformation);


        ActionListener comboBoxListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String playerToFind = (String) playerList.getSelectedItem();//get the selected item
                organiseInformation(playerToFind);

            }
        };

        playerList.addActionListener(comboBoxListener);
        playerList.setSelectedIndex(0);

        //Add to JFrame
        add(playerList, BorderLayout.SOUTH);



        //Add panel to JFrame
        pack();
        setLocationRelativeTo(null);

        setSize(400,300);
    }

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

        /*
            Possible fonts:
            ms gothic
            Plantagenet Cherokee font
            century gothic font
        * */

        playerInformation.setFont(new Font("Century Gothic", Font.BOLD,15));
        playerInformation.setText("<html><b><span style=\"font-family:Century Gothic;font-size:15px;\">Player</span></b>: "+players[i].getName()+ "<br/>" +
                                  "<b><span style=\"font-family:Century Gothic;font-size:15px;\">Net worth: </span></b>"+players[i].getNetWorth()+ "<br/>" +
                                  "<b><span style=\"font-family:Century Gothic;font-size:15px;\">Properties: </span></b> "+String.join(", ",players[i].getProperties())+ "<br/>"+
                                  "<b><span style=\"font-family:Century Gothic;font-size:15px;\">Monopolies: </span></b> "+String.join(", ",players[i].getMonopolies())+ " <br/>"+
                                  "<b><span style=\"font-family:Century Gothic;font-size:15px;\">Mortgages:  </span></b>"+String.join(", ",players[i].getMortgages())+ " <br/></html>");


    }




}
