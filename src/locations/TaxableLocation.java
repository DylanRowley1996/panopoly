package locations;
import interfaces.Taxable;

public class TaxableLocation extends NamedLocation implements Taxable {

	private int incomePercentage;
	private int flatAmount;
	
	public TaxableLocation(String n, int l, int iP, int fA) {
		super(n, l);
		incomePercentage = iP;
		flatAmount = fA;
	}

	@Override
	public int getIncomePercentage() {
		return incomePercentage;
	}

	@Override
	public int getFlatAmount() {
		return flatAmount;
	}

}
