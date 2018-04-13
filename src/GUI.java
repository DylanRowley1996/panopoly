import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

public class GUI {
	
	private JFrame frame = new JFrame();
	private JSplitPane boardAndGameInformationPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	private JSplitPane buttonsDetailsAndPropInfo = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
	private JSplitPane detailsAndHistoryLog = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	
	private JSplitPane imageDetailsAndHistory = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
	
	private HistoryLog history = new HistoryLog();
	private PropertyInformationPanel propertyInformationPanel = new PropertyInformationPanel();
	private BoardExample board = new BoardExample();
	private JPanel boardPanel = board.getBoard();
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private ButtonPanel buttonPanel = new ButtonPanel();
	private int currentPlayer = 0;

	private JPanel characterImagePanel = new JPanel();
	private JLabel characterImage = new JLabel();
	


	GUI(ArrayList<Player> players) throws IOException{	
					
		BoardExample.loadBoard();
		

		detailsAndHistoryLog.setDividerLocation(.2);
		detailsAndHistoryLog.setTopComponent(propertyInformationPanel);
		detailsAndHistoryLog.setBottomComponent(history);
		
		//Retrieve path for image and scale it.
		BufferedImage myPicture = ImageIO.read(new File(players.get(currentPlayer).getPathForImageIcon()));
		
		//Add the scaled image to Jlabel and add this to the necessary panel
		characterImage.setIcon(new ImageIcon(myPicture));
		characterImagePanel.add(characterImage);
		characterImage.setSize(new Dimension(5,5));
		
		imageDetailsAndHistory.setDividerLocation(.5);
		imageDetailsAndHistory.setTopComponent(characterImagePanel);
		imageDetailsAndHistory.setBottomComponent(detailsAndHistoryLog);
		
		buttonsDetailsAndPropInfo.setDividerLocation(.2);
		buttonsDetailsAndPropInfo.setTopComponent(imageDetailsAndHistory);
		buttonsDetailsAndPropInfo.setBottomComponent(buttonPanel);
		
		boardAndGameInformationPane.setDividerLocation(.3);
		boardAndGameInformationPane.setLeftComponent(boardPanel);
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
     
     buttonPanel.getFinishTurnButton().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				 SwingUtilities.invokeLater(new Runnable() {
			            @Override
			            public void run() {
			            	if(currentPlayer == 5){
			            		currentPlayer = 0;
			            	}
			            	else{
			            		currentPlayer++;
			            	}
			        		try {
			        			BufferedImage myPicture = ImageIO.read(new File(players.get(currentPlayer).getPathForImageIcon()));
			        			characterImage.setIcon(new ImageIcon(myPicture));
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
			            }
			        });

			}
});

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
        	
     //test();
     
	}

	public void makeGuiVisible() {
		this.frame.setVisible(true);
	}
	
}

