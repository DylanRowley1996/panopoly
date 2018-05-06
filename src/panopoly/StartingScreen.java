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
	JFrame frame;
	SetupGame gameSetup;
	int noOfPlayers = 6;

	JLabel background;
	JPanel buttons;

	JButton startGameButton;
	JButton quitGameButton;

	public StartingScreen()
	{
		frame = new JFrame();
		frame.setTitle("Panopoly");
		frame.setSize(910, 910);
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
	
	public SetupGame startGame()
	{
		getStartGameButton().addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{				
				try
				{
//					getPlayerCount();
					gameSetup = new SetupGame(noOfPlayers);
					frame.dispose();
				
				} catch (EncryptedDocumentException | InvalidFormatException | IOException | URISyntaxException e1)
				{				
					e1.printStackTrace();
				}
			}
		});
		
//		frame.dispose();
		
		getQuitGameButton().addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{				
				frame.dispose();
			}
		});
//		System.exit(0);
		return gameSetup;
		
//		return gameSetup;
	}

	public JButton getQuitGameButton()
	{
		return quitGameButton;
	}
	
	public SetupGame quit()
	{
		getQuitGameButton().addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{				
				frame.dispose();
			}
		});
//		System.exit(0);
		return gameSetup;
	}
	
	public int getPlayerCount()
	{
		JPanel playerCount = new JPanel(new GridLayout(0, 1));
		
		ButtonGroup startingButtons = new ButtonGroup();
		
		JRadioButton one = new JRadioButton("1");
	    JRadioButton two = new JRadioButton("2");
	    JRadioButton three = new JRadioButton("3");
	    JRadioButton four = new JRadioButton("4");
	    JRadioButton five = new JRadioButton("5");
	    JRadioButton six = new JRadioButton("6");
	    
	    startingButtons.add(one);
	    startingButtons.add(two);
	    startingButtons.add(three);
	    startingButtons.add(four);
	    startingButtons.add(five);
	    startingButtons.add(six);
	    
	    JLabel question = new JLabel("How many players are there:");
	    
	    playerCount.add(question);
	    playerCount.add(one);
	    playerCount.add(two);
	    playerCount.add(three);
	    playerCount.add(four);
	    playerCount.add(five);
	    playerCount.add(six);
	    
	    return 6;
	}

	public static void main(String[] args) throws NullPointerException
	{
		new StartingScreen();
	}
}