package locations;

public class Station extends PrivateProperty {
	
	private int[] rentTable;

	public Station(String n, int l, int p, int m, int r, int[] rT) {
		super(n, l, p, m, r);
		rentTable = rT;
	}
	
	public int getRent() {
		//return rentTable[super.getOwner().getNumStationsOwned()-1];
		return rentAmount;
	}
	
	public void travelToStation() {
		// TODO if player lands on Station and owns another Station they have the option to travel directly to that Station
	}

}
