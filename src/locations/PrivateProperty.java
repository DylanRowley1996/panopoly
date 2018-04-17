package locations;
import interfaces.*;

public class PrivateProperty extends NamedLocation implements Ownable, Rentable, Mortgageable, Groupable  {
	
	private Playable owner;
	private int price;
	private int mortgageAmount;
	protected int[] rentArray;
	private boolean isMortgaged;
	private PropertyGroup group;
	
	public PrivateProperty(String name, PropertyGroup group) {
		super(name);
		owner = null;
		this.group = group;
		price = group.calculatePrice();
		mortgageAmount = group.calculateMortgage();
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
		return rentArray[0];
	}

	@Override
	public boolean isMortgaged() {
		return isMortgaged;
	}

	@Override
	public PropertyGroup getGroup() {
		return group;
	}

}
