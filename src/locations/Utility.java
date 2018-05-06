package locations;

import java.util.Random;

public class Utility extends PrivateProperty {
	
	private int baseMultiplier;
	private int monopolyMultiplier;

	public Utility(String name, PropertyGroup group) {
		super(name, group);
		Random rand = new Random();
		baseMultiplier = rand.nextInt(4)+4;
		monopolyMultiplier = rand.nextInt(5)+8;
	}
	
	private int getRentMultiplier() {
		if(super.getGroup().hasMonopoly(super.getOwner())) {
			return monopolyMultiplier;
		}
		else return baseMultiplier;
	}

	public int getRentalAmount(int diceTotal) {
		return diceTotal * getRentMultiplier();
	}

	public String toString() {
		String str = "";
		str += "Rents:\n";
		str += "Base rent: Dice total * " + baseMultiplier + "\n";
		str += "Rent with Monopoly: Dice total * " + monopolyMultiplier + "\n";
		return super.toString()+str;
	}
}
