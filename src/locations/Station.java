package locations;

public class Station extends PrivateProperty {
	
	private int[] rentTable;

	public Station(String name, int loc, int price, int mortgage, int rent, int[] rentTable, String group) {
		super(name, loc, price, mortgage, rent, group);
		this.rentTable = rentTable;
	}
	
	public int getRent() {
		//return rentTable[super.getOwner().getNumStationsOwned()-1];
		return rentAmount;
	}
	
	public void travelToStation() {
		// TODO if player lands on Station and owns another Station they have the option to travel directly to that Station
	}

}
