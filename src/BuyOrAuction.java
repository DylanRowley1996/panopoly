import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        //Check if current selection has changed.
        ActionListener buttonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(e.getSource() == buy){
                    System.out.println("Buy button pressed...\n\n");
                    //currentPlayer.purchaseProperty();
                }
                else if(e.getSource() == auction){
                    System.out.println("Auction button pressed...\n\n");
                    Auction auction = new Auction("Hello");
                }

            }
        };

        buy.addActionListener(buttonListener);
        auction.addActionListener(buttonListener);

        pack();
        setLocationRelativeTo(null);

        setSize(400,300);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }




}
