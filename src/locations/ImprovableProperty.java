package locations;
import interfaces.Improvable;

public class ImprovableProperty extends PrivateProperty implements Improvable {

	private int numHouses;
	private int numHotels;
	
	public ImprovableProperty(String n, int l, int p, int m, int r) {
		super(n, l, p, m, r);
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
	public void buildHouses(int h) {
		if(numHouses + h <= 4) {
			numHouses += h;
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
