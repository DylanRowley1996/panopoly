package panopoly;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

public class ConfirmationScreen {
	private JFrame confScr = new JFrame();
	private Dimension dimensions = Toolkit.getDefaultToolkit().getScreenSize();
	//Button panel is common to all panels.

	private boolean decision;
	public boolean finish;


	private JLabel question = new JLabel();
	private JLabel blank = new JLabel("");

	private JButton confirmationButton = new JButton("Yes");
	private JButton cancelButton = new JButton("No");
	
	private JPanel buttonPanel = new JPanel(new GridLayout(2,2));
	GridBagConstraints c = new GridBagConstraints();

	public void ConfirmationBankrupt(PartyLeader party,Player currentPlayer,int index,JLabel characterImage, JFrame frame) {
		decision =false;
		finish =false;
		confScr.setTitle("Bankruptcy");
        confScr.setLocationRelativeTo(null);
        confScr.setLocation((int)(dimensions.getWidth()/6),(int)(dimensions.getHeight()/2.25));
        
        question.setText("Are you sure you want to declare Bankruptcy?");
		buttonPanel.add(question);
		buttonPanel.add(blank);
		
        buttonPanel.add(confirmationButton);

        buttonPanel.add(cancelButton);
        confScr.add(buttonPanel);
  
        confScr.setVisible(true);
        confScr.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        confScr.setResizable(true);
        confScr.pack();
        
        confirmationButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {						
				try {
					party.declareBankruptcy(currentPlayer,index,characterImage, frame);
					confScr.dispose();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
        
        
        cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	
				confScr.dispose();
			}
        });
	}
	
	public void ConfirmationQuit(PartyLeader party,Player currentPlayer,int index,JLabel characterImage, JFrame frame) {
		decision =false;
		finish =false;

		confScr.setTitle("Quit Game");

        confScr.setLocationRelativeTo(null);
        confScr.setLocation((int)(dimensions.getWidth()/6),(int)(dimensions.getHeight()/2.25));
        question.setText("Are you sure you want to Quit Game?");


		buttonPanel.add(question);
		buttonPanel.add(blank);
		
        buttonPanel.add(confirmationButton);

        buttonPanel.add(cancelButton);
        confScr.add(buttonPanel);
  
        confScr.setVisible(true);
        confScr.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        confScr.setResizable(true);
        confScr.pack();
        
        confirmationButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {						
				party.QuitEarly();
				confScr.dispose();
			}
		});
        
        
        cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	
				confScr.dispose();
			}
        });
	}
	public boolean getDecision() {
		return decision;
	}
}
