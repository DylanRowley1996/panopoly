package locations;
import interfaces.Taxable;

public class TaxableLocation extends NamedLocation implements Taxable {

	private double incomePercentage;
	private int flatAmount;
	
	public TaxableLocation(String name, double iP, int fA) {
		super(name);
		incomePercentage = Math.round(incomePercentage*100.0)/100.0;
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
