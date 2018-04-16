import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

import locations.NamedLocation;

public class GUI {
	
	private JFrame frame = new JFrame();
	private JSplitPane boardAndGameInformationPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	private JSplitPane buttonsDetailsAndPropInfo = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
	private JSplitPane detailsAndHistoryLog = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
	private HistoryLog history = new HistoryLog();
	private PropertyInformationPanel propertyInformationPanel = new PropertyInformationPanel();
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private ButtonPanel buttonPanel = new ButtonPanel();
	private Board board;
//	ArrayList<NamedLocation> locations;


	GUI(Player[] players, int squares, ArrayList<NamedLocation> locs) {		
		
		board = new Board(squares, locs);
		
		detailsAndHistoryLog.setDividerLocation(.2);
		detailsAndHistoryLog.setTopComponent(propertyInformationPanel);
		detailsAndHistoryLog.setBottomComponent(history);
		
		buttonsDetailsAndPropInfo.setDividerLocation(.2);
		buttonsDetailsAndPropInfo.setTopComponent(detailsAndHistoryLog);
		buttonsDetailsAndPropInfo.setBottomComponent(buttonPanel);
		
		boardAndGameInformationPane.setDividerLocation(.3);
		boardAndGameInformationPane.setLeftComponent(board);
		boardAndGameInformationPane.setRightComponent(buttonsDetailsAndPropInfo);
		
		frame.add(boardAndGameInformationPane);
		frame.setTitle("Interdimensional Panopoly");
		frame.setSize(screenSize.width, screenSize.height);
	    frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	 buttonPanel.getRollButton().addActionListener(e ->
		 	history.getTextArea().setText("Roll button clicked")
     );

    buttonPanel.getSellButton().addActionListener(e ->
    	history.getTextArea().setText("Sell button clicked.")
     );
    
    buttonPanel.getBuyButton().addActionListener(e ->
    	history.getTextArea().setText("Buy Button Clicked")
    );
    
    buttonPanel.getAuctionButton().addActionListener(e ->
		history.getTextArea().setText("Auction Button Clicked")
    );

     buttonPanel.getCollectRentButton().addActionListener(e ->
     	history.getTextArea().setText("Collect rent button clicked.")
     );

     buttonPanel.getMortgageButton().addActionListener(e ->
     	history.getTextArea().setText("Mortgage button clicked.")

     );

     buttonPanel.getRedeemMortgageButton().addActionListener(e ->
     	history.getTextArea().setText("Redeem mortgage button clicked.")
     );

     buttonPanel.getTradeButton().addActionListener(e ->
     	history.getTextArea().setText("Trade button clicked.")
     );

     buttonPanel.getOverviewButton().addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					 SwingUtilities.invokeLater(new Runnable() {
				            @Override
				            public void run() {
				                new Overview(players).setVisible(true);
				            }
				        });

				}
			 });
    
     buttonPanel.getAssetsButton().addActionListener(e ->
     	history.getTextArea().setText("Assets button clicked.")
     );
		
		//test();
	}
	
}

