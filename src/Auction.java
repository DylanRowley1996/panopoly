import javax.swing.*;
import java.awt.*;

import static java.awt.FlowLayout.CENTER;

public class Auction extends JFrame{

    //All necessary components for displaying property info.
    private JPanel propertyPanel = new JPanel();
    private JTextArea propertyInformationArea = new JTextArea();

    //All necessary components for bidding/withdrawing.
    private JPanel biddingProcessPanel = new JPanel();
    private JPanel buttonPanel = new JPanel();
    private JPanel inputBidPanel = new JPanel();
    private JTextArea bidInputArea = new JTextArea("Enter your bid here!");
    private JButton bidButton = new JButton("Bid");
    private JButton withdrawButton = new JButton("Withdraw");

    //All necessary components to display bidding history
    private JPanel biddingHistoryPanel = new JPanel();
    private JLabel biddingHistoryTitle = new JLabel("Bidding History");
    private JTextArea biddingHistoryLog = new JTextArea("ONLY THE RECENT HISTORY OF BIDS WILL BE ENTERED HERE. Not all of them");

    private JSplitPane propertyAndBiddingPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    private JSplitPane splitBiddingPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT);


    public Auction(String propertyName){
        propertyInformationArea.setLineWrap(true);

        buttonPanel.setLayout(new FlowLayout(CENTER));
        buttonPanel.add(bidButton);
        buttonPanel.add(withdrawButton);

        inputBidPanel.add(bidInputArea);

        //Create bidding history panel
        biddingHistoryPanel.setLayout(new BorderLayout());
        biddingHistoryLog.setLineWrap(true);
        biddingHistoryTitle.setHorizontalAlignment(JLabel.CENTER);
        biddingHistoryTitle.setVerticalAlignment(JLabel.CENTER);
        biddingHistoryPanel.add(biddingHistoryTitle, BorderLayout.PAGE_START);
        biddingHistoryPanel.add(biddingHistoryLog, BorderLayout.CENTER);

        //Add bid and withdraw buttons to bidding JPanel.
        biddingProcessPanel.setLayout(new FlowLayout());
        biddingProcessPanel.add(inputBidPanel);
        biddingProcessPanel.add(buttonPanel);

        //Add property panel to frame.
        propertyPanel.setLayout(new BorderLayout());
        propertyInformationArea.setText("THIS IS WHERE THE PROPERTY INFORMATION WILL GO");
        propertyPanel.add(propertyInformationArea);

        setSize(400,300);

        /* Split the bidding process into two parts across the middle.
         * Top is entering bids or withdrawing from auction.
         * Bottom is log of bids.*/
        splitBiddingPanel.setPreferredSize(new Dimension(getWidth()/2,getHeight()));
        splitBiddingPanel.setDividerLocation(getHeight()/2);
        splitBiddingPanel.setTopComponent(biddingProcessPanel);
        splitBiddingPanel.setBottomComponent(biddingHistoryPanel);

        /*
        Split the two parts of the panel down the middle.
        Left side is property info.
        Right side is bidding info.
         */
        propertyAndBiddingPanel.setPreferredSize(new Dimension(getWidth(),getHeight()));
        propertyAndBiddingPanel.setDividerLocation(getWidth()/2);
        propertyAndBiddingPanel.setLeftComponent(propertyPanel);
        propertyAndBiddingPanel.setRightComponent(splitBiddingPanel);

        add(propertyAndBiddingPanel);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setTitle("Auction House");
        pack();

    }
}
