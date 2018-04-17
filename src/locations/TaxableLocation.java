package locations;
import interfaces.Taxable;

public class TaxableLocation extends NamedLocation implements Taxable {

	private double incomePercentage;
	private int flatAmount;
	
	public TaxableLocation(String name, int loc, double iP, int fA) {
		super(name);
		incomePercentage = iP;
		flatAmount = fA;
	}

	@Override
	public double getIncomePercentage() {
		return incomePercentage;
	}

	@Override
	public int getFlatAmount() {
		return flatAmount;
	}

}
