package Interfaces;

public interface Locatable extends Identifiable {
	
	public Locatable goLeft();
	public Locatable goRight();
	public void setLeft(Locatable l);
	public void setRight(Locatable r);
	public int getLocation();
}
