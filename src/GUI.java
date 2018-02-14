import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUI {
	HistoryLog history = new HistoryLog();
	DetailsPanel details = new DetailsPanel();
	BoardExample board = new BoardExample();
	JPanel boardPanel = board.getBoard();
	JFrame frame = new JFrame();
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	GUI() {		
		BoardExample.loadBoard();
		frame.setSize(screenSize.width, screenSize.height);
		frame.setLayout(new GridBagLayout());
	    GridBagConstraints c = new GridBagConstraints();
	    
	    c.fill = GridBagConstraints.HORIZONTAL;
	    c.weightx = 0.5;
	    c.gridwidth = 2;
	    c.gridheight = 3;
	    c.gridx = 0;
	    c.gridy = 0;
	    frame.add(boardPanel, c);
	    
	    c.fill = GridBagConstraints.HORIZONTAL;
	    c.weightx = 1;
	    c.gridwidth = 1;
	    c.gridheight = 1;
	    c.gridx = 2;
	    c.gridy = 0;
	    frame.add(details, c);
	    c.fill = GridBagConstraints.HORIZONTAL;
	    c.weightx = 0.5;
	    c.gridwidth = 1;
	    c.gridheight = 1;
	    c.gridx = 2;
	    c.gridy = 1;
	    frame.add(history, c);

	    
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
