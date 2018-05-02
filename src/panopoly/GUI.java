<<<<<<< HEAD:src/GUI.java
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

import locations.NamedLocation;

public class GUI {

	private JFrame frame = new JFrame();
	private JSplitPane boardAndGameInformationPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	private JSplitPane buttonsDetailsAndPropInfo = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
	private JSplitPane detailsAndHistoryLog = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	private JSplitPane imageDetailsAndHistory = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

	private HistoryLog history = new HistoryLog();
	private PropertyInformationPanel propertyInformationPanel = new PropertyInformationPanel();
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private ButtonPanel buttonPanel = new ButtonPanel();
	private Board board;
//	ArrayList<NamedLocation> locations;
	private int currentPlayer = 0;

	private JPanel characterImagePanel = new JPanel();
	private JLabel characterImage = new JLabel();
	private int noOfPlayersInstantiated = 0;

	GUI(ArrayList<Player> players, int squares, ArrayList<NamedLocation> locs) throws IOException {	
		

		//Set the frame icon to an image loaded from a file.
		BufferedImage myPhoto = ImageIO.read(new File("savedImages/Monopoly (1).png"));
		Image myGameIcon = myPhoto.getScaledInstance(64, 64, Image.SCALE_SMOOTH);
    	frame.setIconImage(myGameIcon);
		
		board = new Board(squares, locs);
		
		SelectionPanel selectionPanel = new SelectionPanel(players);
		
		while(noOfPlayersInstantiated < players.size()){
			try {
				Thread.sleep(10);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			noOfPlayersInstantiated = selectionPanel.getCurrentPlayerNumber();
		}

		detailsAndHistoryLog.setDividerLocation(.2);
		detailsAndHistoryLog.setTopComponent(propertyInformationPanel);
		detailsAndHistoryLog.setBottomComponent(history);

		// Retrieve path for image and scale it.
		BufferedImage myPicture = ImageIO.read(new File(players.get(currentPlayer).getPathForImageIcon()));

		// Add the scaled image to Jlabel and add this to the necessary panel
		characterImage.setIcon(new ImageIcon(myPicture));
		characterImagePanel.add(characterImage);
		characterImage.setSize(new Dimension(5, 5));

		// Append the current player to the text area to inform users who's turn
		// it is.
		history.getTextArea().append("Current Player is now: " + players.get(currentPlayer).getName() + "\n");

		imageDetailsAndHistory.setDividerLocation(.5);
		imageDetailsAndHistory.setTopComponent(characterImagePanel);
		imageDetailsAndHistory.setBottomComponent(detailsAndHistoryLog);

		buttonsDetailsAndPropInfo.setDividerLocation(.2);
		buttonsDetailsAndPropInfo.setTopComponent(imageDetailsAndHistory);
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
	
		//add player icons to board
		for(Player p:players) {
			BufferedImage myImage = ImageIO.read(new File(p.getPathForImageIcon()));
			p.setLocation(this.getStartPosition());
			board.paintCharacterIcons(p , myImage);
		}


		buttonPanel.getRollButton().addActionListener(e -> history.getTextArea().setText("Roll button clicked"));

		buttonPanel.getSellButton().addActionListener(e -> history.getTextArea().setText("Sell button clicked."));

		buttonPanel.getBuyButton().addActionListener(e -> history.getTextArea().setText("Buy Button Clicked"));

		buttonPanel.getAuctionButton().addActionListener(e -> history.getTextArea().setText("Auction Button Clicked"));

		buttonPanel.getCollectRentButton()
				.addActionListener(e -> history.getTextArea().setText("Collect rent button clicked."));

		buttonPanel.getMortgageButton().addActionListener(e -> history.getTextArea().setText("Mortgage button clicked.")

		);

		buttonPanel.getRedeemMortgageButton()
				.addActionListener(e -> history.getTextArea().setText("Redeem mortgage button clicked."));

		buttonPanel.getTradeButton().addActionListener(e -> history.getTextArea().setText("Trade button clicked."));

		buttonPanel.getFinishTurnButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						if (currentPlayer == players.size()-1) {
							currentPlayer = 0;
						} else {
							currentPlayer++;
						}
						try {
							BufferedImage myPicture = ImageIO
									.read(new File(players.get(currentPlayer).getPathForImageIcon()));
							characterImage.setIcon(new ImageIcon(myPicture));
							history.getTextArea()
									.append("Current Player is now: " + players.get(currentPlayer).getName() + "\n");
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
		// test();

	}

	public void makeGuiVisible() {
		this.frame.setVisible(true);
	}
	
	NamedLocation getStartPosition() {
		return board.getStartLocation();
	}
}
=======
package panopoly;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

import locations.NamedLocation;

public class GUI {

	private JFrame frame = new JFrame();
	private JSplitPane boardAndGameInformationPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	private JSplitPane buttonsDetailsAndPropInfo = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
	private JSplitPane detailsAndHistoryLog = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	private JSplitPane imageDetailsAndHistory = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

	private HistoryLog history = new HistoryLog();
	private PropertyInformationPanel propertyInformationPanel = new PropertyInformationPanel();
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private ButtonPanel buttonPanel = new ButtonPanel();
	private Board board;
//	ArrayList<NamedLocation> locations;
	private int currentPlayer = 0;

	private JPanel characterImagePanel = new JPanel();
	private JLabel characterImage = new JLabel();
	private int noOfPlayersInstantiated = 0;

	GUI(ArrayList<Player> players, int squares, ArrayList<NamedLocation> locs) throws IOException {	
		
		board = new Board(squares, locs);
		
		SelectionPanel selectionPanel = new SelectionPanel(players);
		
		while(noOfPlayersInstantiated < players.size()){
			try {
				Thread.sleep(10);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			noOfPlayersInstantiated = selectionPanel.getCurrentPlayerNumber();
		}

		detailsAndHistoryLog.setDividerLocation(.2);
		detailsAndHistoryLog.setTopComponent(propertyInformationPanel);
		detailsAndHistoryLog.setBottomComponent(history);

		// Retrieve path for image and scale it.
		BufferedImage myPicture = ImageIO.read(new File(players.get(currentPlayer).getPathForImageIcon()));

		// Add the scaled image to Jlabel and add this to the necessary panel
		characterImage.setIcon(new ImageIcon(myPicture));
		characterImagePanel.add(characterImage);
		characterImage.setSize(new Dimension(5, 5));

		// Append the current player to the text area to inform users who's turn
		// it is.
		history.getTextArea().append("Current Player is now: " + players.get(currentPlayer).getName() + "\n");

		imageDetailsAndHistory.setDividerLocation(.5);
		imageDetailsAndHistory.setTopComponent(characterImagePanel);
		imageDetailsAndHistory.setBottomComponent(detailsAndHistoryLog);

		buttonsDetailsAndPropInfo.setDividerLocation(.2);
		buttonsDetailsAndPropInfo.setTopComponent(imageDetailsAndHistory);
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

		buttonPanel.getRollButton().addActionListener(e -> history.getTextArea().setText("Roll button clicked"));

		buttonPanel.getSellButton().addActionListener(e -> history.getTextArea().setText("Sell button clicked."));

		buttonPanel.getBuyButton().addActionListener(e -> history.getTextArea().setText("Buy Button Clicked"));

		buttonPanel.getAuctionButton().addActionListener(e -> history.getTextArea().setText("Auction Button Clicked"));

		buttonPanel.getCollectRentButton()
				.addActionListener(e -> history.getTextArea().setText("Collect rent button clicked."));

		buttonPanel.getMortgageButton().addActionListener(e -> history.getTextArea().setText("Mortgage button clicked.")

		);

		buttonPanel.getRedeemMortgageButton()
				.addActionListener(e -> history.getTextArea().setText("Redeem mortgage button clicked."));

		buttonPanel.getTradeButton().addActionListener(e -> history.getTextArea().setText("Trade button clicked."));

		buttonPanel.getFinishTurnButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						if (currentPlayer == players.size()-1) {
							currentPlayer = 0;
						} else {
							currentPlayer++;
						}
						try {
							BufferedImage myPicture = ImageIO
									.read(new File(players.get(currentPlayer).getPathForImageIcon()));
							characterImage.setIcon(new ImageIcon(myPicture));
							history.getTextArea()
									.append("Current Player is now: " + players.get(currentPlayer).getName() + "\n");
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

		// test();

	}

	public void makeGuiVisible() {
		this.frame.setVisible(true);
	}

}
>>>>>>> master:src/panopoly/GUI.java
