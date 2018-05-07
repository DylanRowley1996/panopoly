package panopoly;

import javax.swing.*;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
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

	public StartingScreen()
	{
		frame = new JFrame();
		frame.setTitle("Panopoly");
//		frame.setSize(910, 910);
		frame.setSize(screenSize.width, screenSize.height-10);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		frame.setLayout(new BorderLayout());
		frame.setContentPane(new JLabel(new ImageIcon("gameImages/Communist.jpg")));

		frame.setLayout(null);
		background = new JLabel();
		background.setBounds(0, 0, 900, 900);
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
//				try
//				{
					buttons.removeAll();
					buttons.add(getPlayerCount());
					frame.add(buttons);
					frame.setVisible(true);
						
//					gameSetup = new SetupGame(noOfPlayers);
//					frame.dispose();
				
//				} catch (EncryptedDocumentException | InvalidFormatException | IOException | URISyntaxException e1)
//				{				
//					e1.printStackTrace();
//				}
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
		
		//return gameSetup;
		
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
		
		JRadioButton one = new JRadioButton("1");
	    JRadioButton two = new JRadioButton("2");
	    JRadioButton three = new JRadioButton("3");
	    JRadioButton four = new JRadioButton("4");
	    JRadioButton five = new JRadioButton("5");
	    JRadioButton six = new JRadioButton("6");
	    JButton confirmButton = new JButton("Confirm");
	    
	    
	    startingButtons.add(one); // Grouping only allows one button to be selected at a time
	    startingButtons.add(two);
	    startingButtons.add(three);
	    startingButtons.add(four);
	    startingButtons.add(five);
	    startingButtons.add(six);
	    
	    JRadioButton numbers[] = {one, two, three, four, five, six};
	    
	    if(numbers[2].isSelected())
	    {
	    	System.out.println("TESTING");
	    }
	    
	    confirmButton.addActionListener(new ActionListener() 
	    {
	    	int i = 0;
	    	
            @Override
            public void actionPerformed(ActionEvent e)
            {
            		while(i < 6 && !numSelected)
            	    {
            	    	if(numbers[i].isSelected())
            	    	{
            	    		noOfPlayers = i;
            	    		
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
	    playerCount.add(one);
	    playerCount.add(two);
	    playerCount.add(three);
	    playerCount.add(four);
	    playerCount.add(five);
	    playerCount.add(six);
	    
	    playerCount.add(confirmButton);
	    
	    return playerCount;
	}
	
	public boolean getNumSelected(){
		return numSelected;
	}
	
	public int getNoOfPlayers(){
		return noOfPlayers;
	}
	

//	public static void main(String[] args) throws NullPointerException
//	{
//		new StartingScreen();
//	}
}