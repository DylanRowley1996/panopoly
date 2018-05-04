package panopoly;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

public class SelectionPanel extends JPanel{
	
	private int currentPlayerNumber = 0;
	
	public SelectionPanel(ArrayList<Player> players) throws IOException{
    	
    	JPanel characterPanel = new JPanel(new GridBagLayout());
    	JButton[] imageButtons = new JButton[players.size()];
    	JFrame selectionPanel = new JFrame();
    	JLabel informationArea = new JLabel("The label",SwingConstants.CENTER);
    	
    	selectionPanel.setLayout(new GridBagLayout());
    	GridBagConstraints c = new GridBagConstraints();

    	//https://stackoverflow.com/questions/3360255/how-to-get-a-single-file-from-a-folder-in-java
        File dir = new File("savedImages");
        File[] children = dir.listFiles();
        
        //Ensures all images are resized evenly.
        c.weightx = .5;
        c.weighty = .5;
        
        /*
         * Images are obtained from /savedImages.
         * These are then resized and added to the buttons.
         * Each button added to the JPanel.
         */
    	for(int i=0;i<players.size();i++){
    		c.fill = GridBagConstraints.HORIZONTAL;
    		c.gridx = i;
    		c.gridy = 0;
    		imageButtons[i] = new JButton();
    		BufferedImage myPicture = ImageIO.read(new File(children[i].toString()));
    		Image myResizedPicture = myPicture.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
    		//ImageIO.write((BufferedImage)myResizedPicture, "jpg", new File(children[i].toString()));
        	imageButtons[i].setIcon(new ImageIcon(myResizedPicture));
        	characterPanel.add(imageButtons[i],c);
    	}
    	
    	//Constraints for JLabel that presents character selection info.
        c.fill = GridBagConstraints.BOTH;
    	c.gridx = 0;
    	c.gridy = 1;
    	
    	//Ensures the JLabel spans all columns when beneath the images.
    	c.gridwidth = players.size();
    
    	informationArea.setText("Click an image to select a character for player: "+(currentPlayerNumber+1));
    	
    	//Add action listeners to all images.
    	for(int i=0;i<players.size();i++){
    		
    		//https://stackoverflow.com/questions/33799800/java-local-variable-mi-defined-in-an-enclosing-scope-must-be-final-or-effective
    	    final Integer innerI = new Integer(i);
    	    
    		imageButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	
                	System.out.println("Child "+children[innerI].toString());
                	players.get(currentPlayerNumber).setPathForImageIcon(children[innerI].toString());
                	players.get(currentPlayerNumber).setName(children[innerI].toString());
                	currentPlayerNumber++;
                
                	characterPanel.remove(imageButtons[innerI]);
                                	
                	//Repaint JFrame so removed button is present to user.
                	selectionPanel.repaint();
                	
                	informationArea.setText("Click an image to select a character for player: "+(currentPlayerNumber+1));

                	//When all characters are chosen, close JFrame and create players
                	if(currentPlayerNumber == players.size()){
                		selectionPanel.dispose();
                		//createPlayers();
                		/*try {
							gui = new GUI(createPlayers());
						} catch (EncryptedDocumentException | InvalidFormatException | IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}*/
                	}
                }
    		});
       }
    	
    	characterPanel.add(informationArea,c);//Add information about selecting characters under buttons with images
    	selectionPanel.add(characterPanel);//Add this to JFrame.
    	selectionPanel.setPreferredSize(new Dimension(1125,150));
    	selectionPanel.pack();
    	selectionPanel.setLocationRelativeTo(null);//Centers JFrame on users screen.
    	selectionPanel.setVisible(true);
    	selectionPanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 }
	
	public int getCurrentPlayerNumber(){
		return this.currentPlayerNumber;
	}

}
