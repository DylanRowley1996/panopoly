package locations;

public class Utility extends PrivateProperty {

	private int[] rentTable;
	public Utility(String n, int l, int p, int m, int r, int[] rT) {
		super(n, l, p, m, r);
		rentTable = rT;
	}
	
	public int getRentMultiplier () {
		//return rentTable[super.getOwner().getNumUtilitiesOwned()-1];
		return rentAmount;
	}

	public int getRent (int diceTotal) {
		return diceTotal * getRentMultiplier();
	}

}
