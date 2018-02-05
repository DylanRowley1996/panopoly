import javax.swing.*;
import java.awt.*;

public class BuyOrAuction extends JFrame{

    private JPanel propertyInformation = new JPanel();
    private JPanel buttonPanel = new JPanel(new GridLayout(1,2));
    private JButton buy = new JButton("Buy");
    private JButton auction = new JButton("Auction");
    private String property = "";

    //Change parameter to Property property when class is created and implemented
    public BuyOrAuction(String property){
        this.property = property;

        //Add Panel and Buttons to JFrame
        add(propertyInformation,BorderLayout.NORTH);


        //Add buttons to button panel
        buttonPanel.add(buy);
        buttonPanel.add(auction);

        //Add button panel to JFrame
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);

        setSize(400,300);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }


}
