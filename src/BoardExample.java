import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.JPanel;

public class BoardExample extends JPanel
{
	private static JPanel panel = new JPanel();
	static BufferedImage img;
	static int panelWidth;
	static int panelHeight;

	public BoardExample()
	{
		try
		{
			img = ImageIO.read(this.getClass().getResource("monopoly-board.jpg")); // loads in the image of the board
			panelWidth = img.getWidth();
			panelHeight = img.getHeight(); 
			setPreferredSize(new Dimension(panelWidth, panelHeight));


		}
		catch (IOException e) {
		}
	}

	public void paint(Graphics g)
	{
		g.drawImage(img, 0, 0, panelWidth, panelHeight, this); // paints the board image
	}

	public static void loadBoard()
	{
		panel.add(new BoardExample());
	}

	public JPanel getBoard(){
		return panel;
	}

	public static void refresh() {
		panel.repaint();
	}

}