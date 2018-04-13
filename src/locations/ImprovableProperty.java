package locations;
import interfaces.Improvable;

public class ImprovableProperty extends PrivateProperty implements Improvable {

	private int numHouses;
	private int numHotels;
	
	public ImprovableProperty(String name, int loc, int price, int mortgage, int rent, String group) {
		super(name, loc, price, mortgage, rent, group);
		numHouses = 0;
		numHotels = 0;
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

}
