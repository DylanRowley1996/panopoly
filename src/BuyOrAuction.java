import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BuyOrAuction extends JFrame{

    private JPanel propertyInformation = new JPanel();
    private JPanel buttonPanel = new JPanel(new GridLayout(1,2));
    private JSplitPane propertyInformation1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    private JTextPane propertyName = new JTextPane();
    private JTextArea propertyPrices = new JTextArea("Prices will go here.\n\n\n\n\n\n\n");
    private JButton buy = new JButton("Buy");
    private JButton auction = new JButton("Auction");
    private String property = "";

    //Change parameter to Property property when class is created and implemented
    public BuyOrAuction(String property){

        StyledDocument doc = propertyName.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        propertyName.setBackground(Color.decode("#E53C00"));
        propertyName.setText("Property Name");
        propertyName.setEditable(false);

        propertyPrices.setLineWrap(true);
        propertyPrices.setEditable(false);

        propertyInformation.setLayout(new BorderLayout());
        propertyInformation.add(propertyPrices);

        //Implement necessary fields in split pane.
        propertyInformation1.setTopComponent(propertyName);

        //Add JPanel with property prices to SplitPane
        propertyInformation1.setBottomComponent(propertyInformation);

        this.property = property;

        //Add Panel and Buttons to JFrame
        add(propertyInformation1);

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

        setSize(250,300);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }




}
