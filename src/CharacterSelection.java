import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JRadioButton;

public class CharacterSelection extends JFrame{
	
	private ButtonGroup buttonGroup = new ButtonGroup();
	private JRadioButton[] playerButtons;
	
	public CharacterSelection(Player[] players){
		
	     this.setLayout( new GridLayout(3,1));
		 this.setSize(100,200);

		playerButtons = new JRadioButton[players.length];
		
		for(int i=0;i<players.length;i++){
			playerButtons[i] = new JRadioButton(players[i].getName());
			buttonGroup.add(playerButtons[i]);
			this.add(playerButtons[i]);
		}
		

	     playerButtons[0].setSelected(true);
	     this.setVisible(true);
	}
	
}
