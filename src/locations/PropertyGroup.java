package locations;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import interfaces.Groupable;
import interfaces.Ownable;
import panopoly.Player;

public class PropertyGroup {

	private ArrayList<Groupable> locs = new ArrayList<Groupable>();
	private String name;
	private int min;
	private int max;
	Color color;
	
	public PropertyGroup(String name, int[] priceRange, Color color) {
		this.name = name;
		min = priceRange[0];
		max = priceRange[1];
		this.color = color;
		return;
	}
	
	public void addMember(Groupable site) {
		locs.add(site);
		return;
	}
	
	public ArrayList<Groupable> getMembers() {
		return locs;
	}
	
	public String getName() {
		return name;
	}
	
	public int size() {
		return locs.size();
	}
	
	public int calculatePrice() {
		Random r = new Random();
		int randInRange = r.nextInt((max - min) + 1) + min;  // gets a random number in the range [min, max]
		int roundToFive = 5*(Math.round(randInRange/5));
		
		return roundToFive;
	}
	
	public int[] calculateRentArray() {
		int[] rentArray = new int[5];
		rentArray[0] = min/10;
		rentArray[1] = rentArray[0] * 5;
		rentArray[2] = rentArray[1] * 3;
		rentArray[3] = rentArray[2] * 3;
		rentArray[4] = (int) (rentArray[3] * 1.8);
		return rentArray;
	}
	
	public Color getColor() {
		return color;
	}
	
	public int getNumLocsOwnedByPlayer(Player player) { // TODO change to Playable 
		int locsOwned = 0;
		for(Groupable loc : locs) {
			if(loc instanceof Ownable) {
				if(((Ownable) loc).getOwner() == player) {
					locsOwned++;
				}
			}
		}
		
		return locsOwned;
	}
}
