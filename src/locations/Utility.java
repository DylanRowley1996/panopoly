package locations;

public class Utility extends PrivateProperty {

	private int[] rentTable;
	public Utility(String name, int loc, int price, int mortgage, int rent, int[] rentTable, String group) {
		super(name, loc, price, mortgage, rent, group);
		this.rentTable = rentTable;
	}
	
	public int getRentMultiplier () {
		//return rentTable[super.getOwner().getNumUtilitiesOwned()-1];
		return rentAmount;
	}

	public int getRent (int diceTotal) {
		return diceTotal * getRentMultiplier();
	}

}
