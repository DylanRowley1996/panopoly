import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import interfaces.Groupable;
import locations.*;

public class Board extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7490346528321340119L;
	ArrayList<JPanel> squares = new ArrayList<JPanel>();
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private final int boardWidth = (int) (screenSize.getWidth()*0.66);
	private final int boardHeight = (int) (screenSize.getHeight()*0.9);
	private final int boardRows;
	ArrayList<NamedLocation> locations;
	HashMap<NamedLocation, JPanel> locationMap = new HashMap<NamedLocation, JPanel>();
	
	public Board(int rows, ArrayList<NamedLocation> locList) {
		
		boardRows = rows;
		locations = locList;
		
		FlowLayout layout = new FlowLayout();
		layout.setVgap(0);
		layout.setHgap(0);
		setLayout(layout);
		setPreferredSize(new Dimension(boardWidth, boardHeight));
		int squareWidth;
		int squareHeight;
		
		Iterator<NamedLocation> locIt = locations.iterator();
		
		for(int i=0; i<boardRows; i++) {
			for(int j=0; j<boardRows; j++) {
				JPanel square = new JPanel();
				squareWidth = boardWidth/boardRows;
				squareHeight = boardHeight/boardRows;
				
				if(i==0 || i==(boardRows-2) || j==0 || j==(boardRows-2)) {
					NamedLocation loc = null;
					Color locColor = Color.WHITE;
					int labelWidth = squareWidth;
					int labelHeight = squareHeight;
					String locName;
					if(locIt.hasNext()) {
						loc = locIt.next();
						locName = loc.getIdentifier();
						if(loc instanceof Groupable) {
							locColor = ((Groupable) loc).getGroup().getColor();
						}
					}
					else	locName = "Unnamed Location";
					String wrappedName = "<html><div style='text-align: center;'>" + locName + "</div></html>"; 
				    JLabel nameLabel = new JLabel(wrappedName);
				    String nameLayout = BorderLayout.CENTER;
					
					if(i==0&&j==0 || i==0&&j==(boardRows-2)  || i==(boardRows-2)&&j==0 || i==(boardRows-2)&&j==(boardRows-2)) {
						squareWidth = (boardWidth/boardRows)*2;
						squareHeight = (boardHeight/boardRows)*2;
						labelWidth = squareWidth;
						labelHeight = squareHeight;
						nameLayout = BorderLayout.CENTER;
					}
					else if(i==0 || i==(boardRows-2)) {
						squareWidth = boardWidth/boardRows;
						squareHeight = (boardHeight/boardRows)*2;
						labelWidth = squareWidth;
						labelHeight = squareHeight/2;
						nameLayout = BorderLayout.NORTH;
					}
					else if(j==0 || j==(boardRows-2)) {
						squareWidth = (boardWidth/boardRows)*2;
						squareHeight = boardHeight/boardRows;
						labelWidth = squareWidth/2;
						labelHeight = squareHeight;
						nameLayout = BorderLayout.WEST;
					}
					
					square.setBackground(locColor);
					square.setBorder(BorderFactory.createLineBorder(Color.BLACK));
  
					nameLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));
					//nameLabel.setFont(new Font("Verdana", 1, 12));
					nameLabel.setFont(new Font(nameLabel.getFont().getFontName(), 1, getFontSizeFit(nameLabel, locName, labelWidth)));
					
				    square.setLayout(new BorderLayout());
				    square.add(nameLabel, nameLayout);

				    locationMap.put(loc, square);
				}
				
				square.setPreferredSize(new Dimension(squareWidth, squareHeight));
				
				squares.add(square);
				
				if(j==0 || j==(boardRows-2))	j++;
			}
			if(i==0 || i==(boardRows-2))	i++;
		}
		
		for(JPanel square: squares) {
			add(square);
		}
		
		
			
		return;
	}
	
	
	int getFontSizeFit(JLabel label, String text, int w) {
	    
		Font originalFont = label.getFont();

	    int stringWidth = label.getFontMetrics(originalFont).stringWidth(text);
	    int componentWidth = w;

	    if (stringWidth > componentWidth) { // Resize only if needed
	        // Find out how much the font can shrink in width.
	        double widthRatio = (double)componentWidth / (double)stringWidth;
	        
	        int newFontSize = (int)Math.floor(originalFont.getSize() * widthRatio); // Keep the minimum size

	        // Set the label's font size to the newly determined size.
	        return newFontSize;
	    } else
	    	return originalFont.getSize();

	}
	
	void paintCharacterIcons(Player p, BufferedImage x) {
		//for all locations
		for(int i=0;i<locations.size();i++) {
			//find location that current player is at
			if(locations.get(i) ==p.getLocation()){
				//get current jPanel dimensions so icons can be fitted correctly
				Dimension panelSize = locationMap.get(locations.get(i)).getSize();
				//resize image to a very small icon
				int locationIndex = 0;
				for(int j=0;j<locations.size();j++) {
					if(p.getLocation().equals(locations.get(j))) {
						locationIndex = j;
					}
				}
				Image myResizedPicture;
				if((locationIndex>0 && locationIndex<10) ||(locationIndex>20 && locationIndex<30)) {
					myResizedPicture = x.getScaledInstance(panelSize.width/2, panelSize.height/4, Image.SCALE_SMOOTH);
					
				}else if((locationIndex>10 && locationIndex<20) ||(locationIndex>30 && locationIndex<40)) {
					myResizedPicture = x.getScaledInstance(panelSize.width/4, panelSize.height/2, Image.SCALE_SMOOTH);

				}else {
					myResizedPicture = x.getScaledInstance(panelSize.width/4, panelSize.height/4, Image.SCALE_SMOOTH);
					}
				//set up the label and allign it , probably can get rid of this
		//		JLabel myLabel = new JLabel(new ImageIcon(myResizedPicture));
//				
//				myLabel.setHorizontalAlignment(SwingConstants.LEFT);
//				myLabel.setVerticalAlignment(SwingConstants.CENTER);
				

				//create a label of the players image icon 
				GridLayout myGrid = new GridLayout(3,3,0,0);
				locationMap.get(locations.get(i)).setLayout(myGrid);
				
				//add that label to the Jpanel the player is at
			
				locationMap.get(locations.get(i)).add(new JLabel(new ImageIcon(myResizedPicture)));
			}
		}
	}

	NamedLocation getStartLocation() {
		Random rand = new Random();

		int  n = rand.nextInt(20) + 1;
		return locations.get(n);
	}
}
