import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class DetailsPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private static final int TEXT_AREA_HEIGHT = 10;
	private static final int TEXT_AREA_WIDTH = 20;
	private static final int FONT_SIZE = 14;

	JTextArea textArea = new JTextArea(TEXT_AREA_HEIGHT, TEXT_AREA_WIDTH);
	
	DetailsPanel() {
		textArea.setEditable(false);
		textArea.setFont(new Font("Serif", Font.BOLD, FONT_SIZE));
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		setLayout(new BorderLayout());
		add(textArea);
		return;
	}

	public void displayDetails(boolean ownable, String name, String owner, int cost, int rent, int houses, boolean mortgaged) {	//TODO change argument to type Square, all details will be derived from the Property object
		textArea.setText("Name: " + name);
		if(ownable) { // TODO change to check if argument implements Ownable (or something similar)
			if(owner!=null){
				textArea.append("\nOwner: " + owner);
				textArea.append("\nRent: $" + rent);
				if(houses<5) textArea.append("\nHouses: " + houses);
				else textArea.append("\nHotel");
				if(mortgaged) textArea.append("\nThis propery is mortgaged! You do not need to pay rent");
				else textArea.append("\nYou must pay rent on this property!");
			}
			else {
				textArea.append("\nThis property is unowned! \nChoose to buy or put up for auction");
				textArea.append("\nCost: $" + cost);
				textArea.append("\nRent: $" + rent);
			}
		}
		//TODO show details of un-ownable locations
	}
	
}
