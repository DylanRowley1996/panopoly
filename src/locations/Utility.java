package locations;

public class Utility extends PrivateProperty {

	public Utility(String name, PropertyGroup group) {
		super(name, group);
	}
	
	private int getRentMultiplier() {
		//return rentTable[super.getOwner().getNumUtilitiesOwned()-1];
		return rentArray[0];
	}

	public int getRentalAmount(int diceTotal) {
		return diceTotal * getRentMultiplier();
	}

}
