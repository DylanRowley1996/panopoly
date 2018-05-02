package locations;

public class Station extends PrivateProperty {

	public Station(String name, PropertyGroup group) {
		super(name, group);
	}
	
	public int getRent() {
		//return rentTable[super.getOwner().getNumStationsOwned()-1];
		return rentArray[0];
	}
	
	public void travelToStation() {
		// TODO if player lands on Station and owns another Station they have the option to travel directly to that Station
	}

}
