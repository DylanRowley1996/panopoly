package interfaces;

public interface Mortgageable extends Ownable {

	public int getMortgageAmount();
	public boolean isMortgaged();
	public int getRedeemAmount();
	public void mortagage();
	public void unmortgage();
}
