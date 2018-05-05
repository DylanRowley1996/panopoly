package panopoly;

import java.io.IOException;
import java.util.Random;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

public class Jail {
	
	private Player player;
	private HistoryLog history;
	private int turnsInJail;
	private int qsToAnswer;
	private Random rand = new Random();
	
	public Jail(Player player, HistoryLog history) {
		this.player = player;
		player.setInJail(true);
		this.history = history;
		turnsInJail = 0;
		qsToAnswer = rand.nextInt(5)+3;
	}
	
	public void jailControl() throws InvalidFormatException, IOException, InterruptedException {
		MCQ mcq = new MCQ();
		history.getTextArea().append("-> You are in Jail\nAnswer "+qsToAnswer+" correct questions to escape jail\n\n");
		mcq.createMCQPanel(player, history, this);
	}
	
	public void correctAnswer() {
		qsToAnswer--;
	}
	
	public void turnInJail() {
		turnsInJail--;
	}
	 
	public int getQsToAnswer() {
		return qsToAnswer;
	}
	
	public void checkExit() {
		if(turnsInJail==3 && player.isInJail()) {
			history.getTextArea().append("-> Your prison sentence is up! Pay $50 for your freedom.\n\n");
			player.deductFromBalance(50);
			player.setInJail(false);
			player.setRolled(false);
		}
	}
}
