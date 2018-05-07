package locations;
import interfaces.Locatable;

public class NamedLocation implements Locatable {
	
	private String name;
	private Locatable next;
	private Locatable prev;
	
	public NamedLocation(String name) {
		this.name = name;
	}
		
	@Override
	public String getIdentifier() {
		return name;
	}

	@Override
	public Locatable getNextLoc() {
		return next;
	}

	@Override
	public Locatable getPrevLoc() {
		return prev;
	}

	@Override
	public void setNextLoc(Locatable loc) {
		next = loc;
	}

	@Override
	public void setPrevLoc(Locatable loc) {
		prev = loc;
	}
	
	public String toString() {
		return "";
	}
	
}
