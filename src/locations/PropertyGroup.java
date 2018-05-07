package locations;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import interfaces.Groupable;
import interfaces.Ownable;
import interfaces.Playable;

public class PropertyGroup {

	private ArrayList<PrivateProperty> locs = new ArrayList<PrivateProperty>();
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
	
	public void addMember(PrivateProperty site) {
		locs.add(site);
		return;
	}
	
	public ArrayList<PrivateProperty> getMembers() {
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
		int[] rentArray = new int[6];
		rentArray[0] = min/10;
		rentArray[1] = rentArray[0] * 5;
		rentArray[2] = rentArray[1] * 3;
		rentArray[3] = (int) (rentArray[2] * 1.8);
		rentArray[4] = (int) (rentArray[3] * 1.5);
		rentArray[5] = (int) (rentArray[4] * 1.6);
		return rentArray;
	}
	
	public Color getColor() {
		return color;
	}
	
	public int getNumLocsOwned(Playable player) {
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
	
	public boolean hasMonopoly(Playable player) {
		boolean monopoly = true;
		for(PrivateProperty prop : locs) {
			if(prop.getOwner()!=player || prop.isMortgaged()){
				monopoly=false;
			}
		}
		return monopoly;
	}
}
