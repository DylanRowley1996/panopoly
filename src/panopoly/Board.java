package panopoly;
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
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import interfaces.Groupable;
import locations.*;

public class Board extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7490346528321340119L;
	private ArrayList<JPanel> squares = new ArrayList<JPanel>();
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private final int boardWidth = (int) (screenSize.getWidth()*0.66);
	private final int boardHeight = (int) (screenSize.getHeight()*0.9);
	private final int boardRows;
	private static  Map<Player,JLabel> labelMapping = new HashMap<Player,JLabel>();
	private static ArrayList<NamedLocation> locations;
	private static HashMap<NamedLocation, JPanel> locationMap = new HashMap<NamedLocation, JPanel>();
	
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
				int locationIndex = i;
				Image myResizedPicture;
				if((locationIndex>0 && locationIndex<10) ||(locationIndex>20 && locationIndex<30)) {
					myResizedPicture = x.getScaledInstance(panelSize.width/2, panelSize.height/4, Image.SCALE_SMOOTH);
				}else if((locationIndex>10 && locationIndex<20) ||(locationIndex>30 && locationIndex<40)) {
					myResizedPicture = x.getScaledInstance(panelSize.width/4, panelSize.height/2, Image.SCALE_SMOOTH);
				}else {
					myResizedPicture = x.getScaledInstance(panelSize.width/4, panelSize.height/4, Image.SCALE_SMOOTH);
					}
				//create a label of the players image icon 
				GridLayout myGrid = new GridLayout(3,3,0,0);
				locationMap.get(locations.get(i)).setLayout(myGrid);
				JLabel currentLabel = new JLabel(new ImageIcon(myResizedPicture));
				//add that label to the Jpanel the player is at
				locationMap.get(locations.get(i)).add(currentLabel);
				labelMapping.put(p, currentLabel);
			}
		}
	}
	void updateIcons(Player p, NamedLocation oldLoc) {
		for(int i=0;i<locations.size();i++) {
			if(locations.get(i) == p.getLocation()) {
				Dimension panelSize = locationMap.get(locations.get(i)).getSize();
				int locationIndex = i;
				Image myResizedPicture ;//= p.getIcon().getScaledInstance(panelSize.width/4, panelSize.height/4, Image.SCALE_SMOOTH);
				if((locationIndex>0 && locationIndex<locations.size()/4) ||(locationIndex>(locations.size()-1)-locations.size()/4 && locationIndex<locations.size()-1)) {
					myResizedPicture = p.getIcon().getScaledInstance(panelSize.height/4, panelSize.height/4, Image.SCALE_SMOOTH);
				}else if((locationIndex>locations.size()/4 && locationIndex<(locations.size()-1)-locations.size()/4)) {
					myResizedPicture = p.getIcon().getScaledInstance(panelSize.height/2, panelSize.height/3, Image.SCALE_SMOOTH);
				}else {
					myResizedPicture = p.getIcon().getScaledInstance(panelSize.width/4, panelSize.height/4, Image.SCALE_SMOOTH);
					}
				GridLayout myGrid = new GridLayout(3,3,0,0);
				JLabel currentLabel = new JLabel(new ImageIcon(myResizedPicture));
				JLabel oldLabel = labelMapping.get(p);
				locationMap.get(locations.get(i)).setLayout(myGrid);
				locationMap.get(oldLoc).remove(oldLabel);//Remove old icon from previous square
				labelMapping.remove(p, oldLabel);
				locationMap.get(locations.get(i)).add(currentLabel);//Add it to the new square
				labelMapping.put(p, currentLabel);
			}
			this.revalidate();
			
		}
	}
	NamedLocation getStartLocation() {
		return locations.get(0);
	}
	
	public void refresh(){
		this.repaint();
		Iterator<Entry<NamedLocation, JPanel>> it = locationMap.entrySet().iterator();
		
		while(it.hasNext()){
			Entry<NamedLocation, JPanel> square = it.next();
			square.getValue().repaint();
		}
	}
	public void removeCharacter(Player p) {
		for(int i=0;i<locations.size();i++) {
			if(locations.get(i) == p.getLocation()) {
				JLabel oldLabel = labelMapping.get(p);
				locationMap.get(locations.get(i)).remove(oldLabel);//Remove old icon from previous square
				labelMapping.remove(p);				
			}	
		}
		this.revalidate();

	}
}
