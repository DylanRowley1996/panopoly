package panopoly;
import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import interfaces.Groupable;
import interfaces.Locatable;
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
	private int currentPlayer = 0;

	private JPanel characterImagePanel = new JPanel();
	private JLabel characterImage = new JLabel();
	private int noOfPlayersInstantiated = 0;


	GUI(ArrayList<Player> players, int squares, ArrayList<NamedLocation> locations) throws IOException {	

		//Set the frame icon to an image loaded from a file.
		BufferedImage myPhoto = ImageIO.read(new File("gameImages/rickMortyCommie.png"));
		Image myGameIcon = myPhoto.getScaledInstance(64, 64, Image.SCALE_SMOOTH);
		frame.setIconImage(myGameIcon);

		board = new Board(squares, locations);
		PartyLeader partyLeader = new PartyLeader(history, board);


		SelectionPanel selectionPanel = new SelectionPanel(players);

		while(noOfPlayersInstantiated < players.size()){
			try {
				Thread.sleep(10);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			noOfPlayersInstantiated = selectionPanel.getCurrentPlayerNumber();
		}

		detailsAndHistoryLog.setDividerLocation(.5);
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
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setSize(screenSize.width, screenSize.height);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);
		
		//add player icons to board
		for(Player p:players) {
			p.setLocation(this.getStartPosition());
			p.setIcon();
			board.paintCharacterIcons(p , p.getIcon());
			board.revalidate();
		}

		propertyInformationPanel.getPropNamePane().setText(locations.get(0).getIdentifier());
		propertyInformationPanel.getPropNamePane().setBackground(Color.WHITE);
		buttonPanel.getRollButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {						
				try {
					if(players.get(currentPlayer).isInJail()) {
						history.getTextArea().append("-> You cannot roll while in Jail.\n\n");
					}
					else if(!players.get(currentPlayer).hasRolled()) {
						partyLeader.roll(players.get(currentPlayer),currentPlayer,characterImage);
						updatePropCard(players.get(currentPlayer));
					}else {
						history.getTextArea().append("-> You have already rolled.\n\n");				
					}
				} catch (InvalidFormatException | IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		buttonPanel.getSellButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				partyLeader.sell(players.get(currentPlayer), history);
			}
		});

		buttonPanel.getBuyButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				partyLeader.buy(players.get(currentPlayer));
				updatePropCard(players.get(currentPlayer));
			}
		});

		buttonPanel.getAuctionButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				partyLeader.auction(players.get(currentPlayer));
				updatePropCard(players.get(currentPlayer));
			}

		});

		buttonPanel.getBuildButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				partyLeader.build(players.get(currentPlayer));
				updatePropCard(players.get(currentPlayer));
			}

		});

		buttonPanel.getQuitGameButton().addActionListener(e->history.getTextArea().append("-> Quit Game clicked\n"));
		buttonPanel.getBankruptyButton().addActionListener(new ActionListener() {        
			@Override
			public void actionPerformed(ActionEvent e) {
				partyLeader.declareBankruptcy(players.get(currentPlayer),currentPlayer,characterImage);
			}
		});
		buttonPanel.getMortgageButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				partyLeader.mortgage(players.get(currentPlayer));
			}
		});

		buttonPanel.getRedeemMortgageButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				partyLeader.redeem(players.get(currentPlayer));
			}
		});

		buttonPanel.getTradeButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				partyLeader.trade(players.get(currentPlayer));
			}
		});

		buttonPanel.getFinishTurnButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int oldPlayer = currentPlayer;
				currentPlayer = partyLeader.finishTurn(players.get(currentPlayer),currentPlayer,characterImage);
				updatePropCard(players.get(currentPlayer));
				if(oldPlayer!=currentPlayer && players.get(currentPlayer).isInJail()) {
					try {
						players.get(currentPlayer).getJail().jailControl();
					} catch (InvalidFormatException | IOException | InterruptedException e1) {
						e1.printStackTrace();
					}
				}
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

	}

		
	
	public void updatePropCard(Player player) {
		Locatable currLoc = player.getLocation();
		propertyInformationPanel.getPropNamePane().setText(currLoc.getIdentifier());
		if(currLoc instanceof Groupable) {
			propertyInformationPanel.getPropNamePane().setBackground(((Groupable) currLoc).getGroup().getColor());
		}
		else	propertyInformationPanel.getPropNamePane().setBackground(Color.WHITE);
		propertyInformationPanel.getPropInfo().setText(currLoc.toString());
	}

	public void makeGuiVisible() {
		this.frame.setVisible(true);
	}

	NamedLocation getStartPosition() {
		return board.getStartLocation();
	}

	public void refresh() {
		this.frame.repaint();
	}


}
