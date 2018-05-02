package panopoly;

import java.util.ArrayList;
import java.util.Iterator;
import locations.*;

public class PartyLeader {
	private static ArrayList<NamedLocation> locations = (ArrayList<NamedLocation>) SetupGame.getLocationList();
	private static ArrayList<Player> players = SetupGame.getPlayers();
	
	public void roll(Player player) {
		// TODO
	}
	
	public void buy(Player player) {
		// TODO
	}
	
	public void sell(Player player) {
		// TODO
	}
	
	public void finishTurn(Player player){
		//TODO
		// check for in jail too long, unpaid rent, etc.
	}
}
