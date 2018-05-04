package interfaces;

public interface Locatable extends Identifiable {
	
	public Locatable getNextLoc();
	public Locatable getPrevLoc();
	public void setNextLoc(Locatable loc);
	public void setPrevLoc(Locatable loc);
}
