package interfaces;

public interface Locatable extends Identifiable {
	
	public Locatable getLeft();
	public Locatable getRight();
	public void setLeft(Locatable l);
	public void setRight(Locatable r);
}
