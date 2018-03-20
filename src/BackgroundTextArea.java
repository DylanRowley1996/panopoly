

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JTextArea;

public class BackgroundTextArea extends JTextArea {

	private static final long serialVersionUID = 1637737989492163589L;
	private BufferedImage img;

	public BackgroundTextArea(int a, int b) {
		super(a,b);
		try{
			img = ImageIO.read(this.getClass().getResource("Template3.png"));
		} catch(IOException e) {
			System.out.println(e.toString());
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(img,0,0,null);
		super.paintComponent(g);
	}
}
