package locations;
import interfaces.Locatable;

public class NamedLocation implements Locatable {
	
	private String name;
	private Locatable left;
	private Locatable right;
	private int location;
	
	public NamedLocation(String n, int l) {
		name = n;
		location = l;
	}
		
	@Override
	public String getIdentifier() {
		return name;
	}

	@Override
	public Locatable getLeft() {
		return left;
	}

	@Override
	public Locatable getRight() {
		return right;
	}

	@Override
	public void setLeft(Locatable l) {
		left = l;
	}

	@Override
	public void setRight(Locatable r) {
		right = r;
	}

	@Override
	public int getLocation() {
		return location;
	}
	
}
