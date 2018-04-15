import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
	
	public Board(int rows) {
		
		boardRows = rows;
		
		FlowLayout layout = new FlowLayout();
		layout.setVgap(0);
		layout.setHgap(0);
		setLayout(layout);
		setPreferredSize(new Dimension(boardWidth, boardHeight));
		int squareWidth;
		int squareHeight;
		
		for(int i=0; i<boardRows; i++) {
			for(int j=0; j<boardRows; j++) {
				JPanel test = new JPanel();
				squareWidth = boardWidth/boardRows;
				squareHeight = boardHeight/boardRows;
				
				if(i==0 || i==(boardRows-2) || j==0 || j==(boardRows-2)) {
					int labelWidth = squareWidth;
					int labelHeight = squareHeight;
					String s = "The Adventures of Sean";
					String text = "<html><div style='text-align: center;'>" + s + "</div></html>"; 
				    JLabel jlabel = new JLabel(text);
				    String nameLayout = BorderLayout.CENTER;
					
					if(i==0&&j==0 || i==0&&j==(boardRows-2)  || i==(boardRows-2)&&j==0 || i==(boardRows-2)&&j==(boardRows-2)) {
						squareWidth = (boardWidth/boardRows)*2;
						squareHeight = (boardHeight/boardRows)*2;
						labelWidth = squareWidth;
						labelHeight = squareHeight;
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
					
					test.setBackground(Color.WHITE);
					test.setBorder(BorderFactory.createLineBorder(Color.BLACK));
  
					jlabel.setPreferredSize(new Dimension(labelWidth, labelHeight));
					jlabel.setFont(new Font("Verdana", 1, getFontSizeFit(jlabel, s, labelWidth)+1));

				    test.setLayout(new BorderLayout());
				    test.add(jlabel, nameLayout);

				}
				
				test.setPreferredSize(new Dimension(squareWidth, squareHeight));
				
				squares.add(test);
				
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
