import java.awt.*;
import javax.swing.*;

public class Main {
	
	public static void main(String args[]) {
		HistoryLog history = new HistoryLog();
		DetailsPanel details = new DetailsPanel();
		JFrame frame = new JFrame();
		
		frame.setSize(500, 500);
		frame.add(history, BorderLayout.LINE_START);
		frame.add(details, BorderLayout.LINE_END);
		frame.setLayout(new GridLayout());  //set layout
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		for(int i=0; i<100; i++) {
			history.displayHistory("test");
		}
		history.displayHistory("test2");
		
		details.displayDetails(true, "Location", "Owner", 150, 50, 5, false);
		details.displayDetails(true, "Income Tax", null, 250, 50, 0, false);
	}

}
