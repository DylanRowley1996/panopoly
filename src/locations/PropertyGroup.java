package locations;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import locations.*;

public class PropertyGroup {

	private ArrayList<NamedLocation> locs = new ArrayList<NamedLocation>();
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
	
	public void addMember(NamedLocation site) {
		locs.add(site);
		return;
	}
	
	public ArrayList<NamedLocation> getMembers() {
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
		
		
		return null;
	}
	
	public int calculateMortgage() {
		return 0;
	}
	
	public Color getColor() {
		return color;
	}
}
