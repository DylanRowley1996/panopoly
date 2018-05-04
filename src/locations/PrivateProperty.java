package locations;
import interfaces.*;

public class PrivateProperty extends NamedLocation implements Ownable, Rentable, Mortgageable, Groupable  {
	
	private Playable owner;
	private int price;
	private int mortgageAmount;
	protected int[] rentArray;
	private boolean isMortgaged;
	private PropertyGroup group;
	private int numOfHouses = 0;
	
	public PrivateProperty(String name, PropertyGroup group) {
		super(name);
		owner = null;
		this.group = group;
		group.addMember(this);
		price = group.calculatePrice();
		mortgageAmount = price/2;
		rentArray = group.calculateRentArray();
		isMortgaged = false;
	}

	@Override
	public Playable getOwner() {
		return owner;
	}

	@Override
	public int getPrice() {
		return price;
	}

	@Override
	public void setOwner(Playable owner) {
		this.owner = owner;
	}
	
	@Override
	public int getMortgageAmount() {
		return mortgageAmount;
	}

	@Override
	public int getRentalAmount() {
		return rentArray[numOfHouses];
	}

	@Override
	public boolean isMortgaged() {
		return isMortgaged;
	}

	@Override
	public PropertyGroup getGroup() {
		return group;
	}

	@Override
	public int getRedeemAmount() {
		return (int) (mortgageAmount + (mortgageAmount*.1));
	}

	@Override
	public void mortgage() {
		isMortgaged = true;
		
	}

	@Override
	public void unmortgage() {
		isMortgaged = false;
	}
	
	@Override
	public int[] getAllRents(){
		return rentArray;
	}
	
	public int getNumHouses(){
		return numOfHouses;
	}
	
	
}
