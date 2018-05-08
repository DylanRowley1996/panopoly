package locations;

public class Station extends PrivateProperty {

	public Station(String name, PropertyGroup group) {
		super(name, group);
	}
	
	public int getRentalAmount() {
		return rentArray[super.getGroup().getNumLocsOwned(super.getOwner())-1];
	}
	
	public String toString() {
		String str = "";
		if(super.owner!=null) {
			str += "Rent: $" + getRentalAmount() + "\n";
		}
		else {
			str += "Rents:\n";
			str += "Rent with 1 station: $" + rentArray[0] + "\n";
			for(int i=1; i<4; i++){
				str += "Rent with "+(i+1)+" stations: $" +rentArray[i]+"\n";
			}
		}
		return super.toString()+str;
	}

}
