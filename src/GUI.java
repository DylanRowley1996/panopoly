import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class GUI {
	
	private JFrame frame = new JFrame();
	private JSplitPane boardAndGameInformationPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	private JSplitPane buttonsAndGameInformation = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
	private JSplitPane detailsAndHistoryLog = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
	private HistoryLog history = new HistoryLog();
	private DetailsPanel details = new DetailsPanel();
	private BoardExample board = new BoardExample();
	private JPanel boardPanel = board.getBoard();
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private ButtonPanel buttonPanel;

	GUI(Player[] players) {		
		
		buttonPanel = new ButtonPanel(players);
	
		BoardExample.loadBoard();
		detailsAndHistoryLog.setDividerLocation(.25);
		detailsAndHistoryLog.setTopComponent(details);
		detailsAndHistoryLog.setBottomComponent(history);
		
		buttonsAndGameInformation.setDividerLocation(.5);
		buttonsAndGameInformation.setTopComponent(detailsAndHistoryLog);
		buttonsAndGameInformation.setBottomComponent(buttonPanel);
		
		boardAndGameInformationPane.setDividerLocation(.3);
		boardAndGameInformationPane.setLeftComponent(boardPanel);
		boardAndGameInformationPane.setRightComponent(buttonsAndGameInformation);
		
		frame.add(boardAndGameInformationPane);
		frame.setTitle("Interdimensional Panopoly");
		frame.setSize(screenSize.width, screenSize.height);
	    frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		test();
	}
	
	private void test() {
		
		for(int i=0; i<100; i++) {
			history.displayHistory("test");
		}
		
		history.displayHistory("test2");
		
		details.displayDetails(true, "Location", "Owner", 150, 50, 5, false);
		details.displayDetails(true, "Income Tax", null, 250, 50, 0, false);
	}
}