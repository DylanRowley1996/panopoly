import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
					int labelWidth = squareWidth;
					int labelHeight = squareHeight;
					String locName;
					if(locIt.hasNext()) {
						locName = locIt.next().getIdentifier();
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
					
					square.setBackground(Color.WHITE);
					square.setBorder(BorderFactory.createLineBorder(Color.BLACK));
  
					nameLabel.setPreferredSize(new Dimension(labelWidth, labelHeight));
					//nameLabel.setFont(new Font("Verdana", 1, 12));
					nameLabel.setFont(new Font(nameLabel.getFont().getFontName(), 1, getFontSizeFit(nameLabel, locName, labelWidth)));
					
				    square.setLayout(new BorderLayout());
				    square.add(nameLabel, nameLayout);

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
	

}
