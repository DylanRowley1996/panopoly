package locations;

public class Utility extends PrivateProperty {

	public Utility(String name, PropertyGroup group) {
		super(name, group);
	}
	
	public int getRentMultiplier () {
		//return rentTable[super.getOwner().getNumUtilitiesOwned()-1];
		return rentArray[0];
	}

	public int getRent (int diceTotal) {
		return diceTotal * getRentMultiplier();
	}

}
