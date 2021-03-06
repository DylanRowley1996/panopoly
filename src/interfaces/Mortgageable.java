package interfaces;

public interface Mortgageable extends Ownable {

	public int getMortgageAmount();
	public boolean isMortgaged();
	public int getRedeemAmount();
	public void mortgage();
	public void unmortgage();
}
