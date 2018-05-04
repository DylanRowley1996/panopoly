package locations;
import interfaces.Improvable;

public class ImprovableProperty extends PrivateProperty implements Improvable {

	private int numHouses;
	private int numHotels;
	private int buildCost;
	
	public ImprovableProperty(String name, PropertyGroup group) {
		super(name, group);
		numHouses = 0;
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
		else System.out.println("Error: Too many houses!");
	}

	@Override
	public void buildHotel() {
		if(numHouses==4) {
			numHouses = 0;
			numHotels++;
		}
	}
	
	public int getHouseCost() {
		return buildCost;
	}

	public String toString() {
		String str = "";
		if(super.owner!=null) {
			str += "Build cost per house: $" + buildCost + "\n";
			if(numHouses<5) str += "Houses: " + numHouses + "\n";
			else			str += "Hotel";
		}
		else {
			str += "Rents:\n";
			str += "Base rent: $" + rentArray[0] + "\n";
			for(int i=1; i<4; i++){
				str += "Rent with "+i+" houses: $" +rentArray[i]+"\n";
			}
			str += "Rent with a hotel: $" + rentArray[4] + "\n";
		}
		return super.toString()+str;
	}
}
