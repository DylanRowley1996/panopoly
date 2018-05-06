package locations;

public class Station extends PrivateProperty {

	public Station(String name, PropertyGroup group) {
		super(name, group);
	}
	
	public int getRent() {
		//return rentTable[super.getOwner().getNumStationsOwned()-1];
		return rentArray[super.getGroup().getNumLocsOwned(super.getOwner())-1];
	}
	
	public void travelToStation() {
		// TODO if player lands on Station and owns another Station they have the option to travel directly to that Station
	}
	
	public String toString() {
		String str = "";
		str += "Rents:\n";
		str += "Rent with 1 station: $" + rentArray[0] + "\n";
		for(int i=1; i<4; i++){
			str += "Rent with "+(i+1)+" stations: $" +rentArray[i]+"\n";
		}
		return super.toString()+str;
	}

}
