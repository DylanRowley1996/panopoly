package locations;
import interfaces.*;

public class PrivateProperty extends NamedLocation implements Ownable, Rentable, Mortgageable {
	
	private Playable owner;
	private int price;
	private int mortgageAmount;
	protected int rentAmount;
	private boolean isMortgaged;
	private String group;
	
	public PrivateProperty(String name, int loc, int price, int mortgage, int rent, String group) {
		super(name, loc);
		owner = null;
		this.price = price;
		mortgageAmount = mortgage;
		rentAmount = rent;
		this.group = group;
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
		return rentAmount;
	}

	@Override
	public boolean isMortgaged() {
		return isMortgaged;
	}
	
	public String getGroup() {
		return group;
	}

}
