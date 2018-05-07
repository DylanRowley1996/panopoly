package locations;

import java.util.ArrayList;

import panopoly.HistoryLog;
import panopoly.Player;

public class CommunismTax extends NamedLocation {

	public CommunismTax(String name, int loc) {
		super(name);
	}
	
	public static void spreadWealth(ArrayList<Player> players, HistoryLog history) {
		int totalWealth = 0;
		for(Player player: players) {
			totalWealth+=player.getNetWorth();
			player.deductFromBalance(player.getNetWorth());
		}
		for(Player player: players) {
			player.addToBalance(totalWealth/players.size());
		}
		history.getTextArea().append("-> Communism is alive and well! All players net worth shared" 
		+" evenly, everyone has $"+totalWealth/players.size()+"\n\n");
	}

}
