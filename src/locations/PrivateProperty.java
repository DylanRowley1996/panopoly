package locations;
import interfaces.*;

public class PrivateProperty extends NamedLocation implements Ownable, Rentable, Mortgageable {
	
	private Playable owner;
	private int price;
	private int mortgageAmount;
	protected int rentAmount;
	private boolean isMortgaged;
	
	public PrivateProperty(String n, int l, int p, int m, int r) {
		super(n, l);
		owner = null;
		price = p;
		mortgageAmount = m;
		rentAmount = r;
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

}
