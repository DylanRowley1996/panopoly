package panopoly;

import java.util.ArrayList;
import java.util.Random;

import interfaces.Rollable;

public class Dice implements Rollable{	
	private ArrayList<Integer> faces = new ArrayList<Integer>();
	private int timesRolled = 0; //three rolls go to jail
	
	
	public boolean isDouble () {
		return faces.get(0) == faces.get(1);
	}

	@Override
	public int rollDice(int numDice, int numSides) {
		int sum	=	0;
		for (int i	=	0;	i	<	numDice;	i++) {
			Random rand = new Random();
			int x = rand.nextInt(numSides)+1;
			faces.add(x);
			sum	+=	x;
		}
		timesRolled++;
		return sum;
	}
	public ArrayList<Integer> getFaces() {
		return faces;
	}
	public int getNumberOfRolls() {
		return timesRolled;
	}
	public void refreshDice() {
		faces.clear();
		timesRolled = 0;
	}
}
