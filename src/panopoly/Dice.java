package panopoly;

import java.util.Random;

import interfaces.Rollable;

public class Dice implements Rollable{	
	private int[] faces;
	
	public boolean isDouble () {
		return faces[0] == faces[1];
	}

	@Override
	public int rollDice(int numDice, int numSides) {
		int sum	=	0;
		for (int i	=	0;	i	<	numDice;	i++) {
			Random rand = new Random();
			int x = rand.nextInt(numSides)+1;
			sum	+=	x;
			//faces[i] = x;
		}
		return sum;
	}
}
