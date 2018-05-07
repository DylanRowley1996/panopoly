package locations;
import interfaces.Improvable;

public class ImprovableProperty extends PrivateProperty implements Improvable {

	private int numHouses;
	private int numHotels;
	private int buildCost;
	
	public ImprovableProperty(String name, PropertyGroup group) {
		super(name, group);
		numHouses = 1;//TODO Set back to 0
		numHotels = 0;
		buildCost = (int) (super.getPrice()*0.6);
	}
	
	@Override
	public int getRentalAmount() {
		return rentArray[numHouses];
	}

	@Override
	public int getNumHouses() {
		return numHouses;
	}

	@Override
	public int getNumHotels() {
		return numHotels;
	}

	@Override
	public void buildHouses(int amount) {
		if(numHouses + amount <= 4) {
			numHouses += amount;
		}
		else if(numHouses+amount==5) {
			numHouses = 5;
			buildHotel();
		}
		else System.out.println("Error: Too many houses!");
	}

	@Override
	public void buildHotel() {
		if(numHouses==4) {
			numHotels++;
		}
	}
	
	public int getBuildCost() {
		return buildCost;
	}

	public String toString() {
		String str = "";
		if(super.owner!=null) {
			str += "Rent: $" + getRentalAmount() + "\n";
			str += "Build cost per house: $" + buildCost + "\n";
			if(numHouses<5) str += "Houses: " + numHouses + "\n";
			else			str += "Hotel";
		}
		else {
			str += "Rents:\n";
			str += "Base rent: $" + rentArray[0] + "\n";
			for(int i=1; i<5; i++){
				str += "Rent with "+i+" houses: $" +rentArray[i]+"\n";
			}
			str += "Rent with a hotel: $" + rentArray[5] + "\n";
		}
		return super.toString()+str;
	}

	@Override
	public void removeHouse(int number) {
		numHouses -= number;
	}
}
