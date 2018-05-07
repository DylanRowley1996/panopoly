package interfaces;

public interface Improvable extends Ownable		
{

	public int getNumHouses();
	public void removeHouse(int number);
	public int getNumHotels();
	public void buildHouses(int h);
	public void buildHotel();
	
}
