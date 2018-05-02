package panopoly;

import java.util.Random;

public class EquallyLikelyDice extends Dice{
	private	final	static Random	RND =	new	Random();
	@Override
	public int rollDice(int numDice,	int numSides)	{
		return RND.nextInt((numSides-1)*numDice	+	1) + numDice;
	}
}
