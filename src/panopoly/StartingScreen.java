package panopoly;

import javax.imageio.ImageIO;
import javax.swing.*;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class StartingScreen
{	
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	JFrame frame;
	SetupGame gameSetup;
	private int noOfPlayers = 0;
	private boolean numSelected = false;


	JLabel background;
	JPanel buttons;

	JButton startGameButton;
	JButton quitGameButton;

	public StartingScreen() throws IOException
	{
		frame = new JFrame();
		frame.setTitle("Panopoly");
		BufferedImage myPhoto = ImageIO.read(new File("gameImages/rickMortyCommie.png"));
		Image myGameIcon = myPhoto.getScaledInstance(64, 64, Image.SCALE_SMOOTH);
		frame.setIconImage(myGameIcon);

//		frame.setSize(screenSize.width - 10, screenSize.height - 10);
		frame.setBounds(screenSize.width/2, 0, screenSize.width, screenSize.height);

		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		frame.setLayout(new BorderLayout());
		frame.setContentPane(new JLabel(new ImageIcon("gameImages/Communist.jpg")));

		frame.setLayout(null);
		background = new JLabel();
		background.setBounds(0, 0, screenSize.width, screenSize.height);
		frame.add(background);

		buttons = new JPanel();
		buttons.setBounds(200, 650, 500, 95);
		buttons.setBackground(Color.RED);
		startGameButton = new JButton("START GAME");
		startGameButton.setPreferredSize(new Dimension(235, 85));
		quitGameButton = new JButton("QUIT");
		quitGameButton.setPreferredSize(new Dimension(235, 85));

		buttons.add(startGameButton);
		buttons.add(quitGameButton);

		frame.add(buttons);
		
		frame.setSize(900, 900);
		
		startGame();
	}

	public JButton getStartGameButton()
	{
		return startGameButton;
	}
	
	public void startGame()
	{
		getStartGameButton().addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{				

					buttons.removeAll();
					buttons.add(getPlayerCount());
					frame.add(buttons);
					frame.setVisible(true);
			}
		});
		
		getQuitGameButton().addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{				
				frame.dispose();
			}
		});
		
	}

	public JButton getQuitGameButton()
	{
		return quitGameButton;
	}
	
	public JPanel getPlayerCount()
	{
		JPanel playerCount = new JPanel();
		playerCount.setBackground(Color.RED);
		playerCount.setSize(500, 200);
		
		playerCount.setLayout(new FlowLayout());
		ButtonGroup startingButtons = new ButtonGroup();
		
	    JRadioButton two = new JRadioButton("2");
	    two.setBackground(Color.RED);
	    JRadioButton three = new JRadioButton("3");
	    three.setBackground(Color.RED);
	    JRadioButton four = new JRadioButton("4");
	    four.setBackground(Color.RED);
	    JRadioButton five = new JRadioButton("5");
	    five.setBackground(Color.RED);
	    JRadioButton six = new JRadioButton("6");
	    six.setBackground(Color.RED);
	    JButton confirmButton = new JButton("Confirm");
	    confirmButton.setBackground(Color.RED);
	    
	    
	    // Grouping only allows one button to be selected at a time
	    startingButtons.add(two);
	    startingButtons.add(three);
	    startingButtons.add(four);
	    startingButtons.add(five);
	    startingButtons.add(six);
	    
	    JRadioButton numbers[] = {two, three, four, five, six};
	    
	    confirmButton.addActionListener(new ActionListener() 
	    {
	    	int i = 0;
	    	
            @Override
            public void actionPerformed(ActionEvent e)
            {

            		while(i < 5 && !numSelected)
            	    {
            	    	if(numbers[i].isSelected())
            	    	{
            	    		
            	    		noOfPlayers = i + 2; // Numbers start at 2

            	    		numSelected = true;
            	    		
            	    	}
            	    	else
            	    	{
            	    		i++;
            	    	}
            	    }
            }
	    });
	    
	  
	    JLabel question = new JLabel("How many players are there:\n");
	    
	    playerCount.add(question, BorderLayout.LINE_START);
	    playerCount.add(two);
	    playerCount.add(three);
	    playerCount.add(four);
	    playerCount.add(five);
	    playerCount.add(six);
	    
	    playerCount.add(confirmButton);
	    
	    return playerCount;
	  
	}
	
	public boolean getNumSelected()
	{
		return numSelected;
	}
	
	public int getNoOfPlayers()
	{
		return noOfPlayers;
	}

}