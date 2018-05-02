package panopoly;

<<<<<<< HEAD
import java.util.Random;

import interfaces.Rollable;

public class Dice implements Rollable{
	private	final	static Random	RND =	new	Random();
	
	public int rollDice(int numDice,	int numSides)	{
		int sum	=	0;
		for (int i	=	0;	i	<	numDice;	i++)
			sum	+=	RND.nextInt(numSides)+1;
		return sum;
	}

=======
public class Dice {
	
	private static int NUM_DICE = 2;
	
	private int[] dice = new int [NUM_DICE];
	
	
	public void roll () {
		for (int i=0; i<NUM_DICE; i++) {
			dice[i] = 1 + (int)(Math.random() * 6);   
		}
		return;
	}

	public int[] getDice () {
		return dice;
	}
	
	public int getTotal () {
		return (dice[0] + dice[1]);
	}
	
	public boolean isDouble () {
		return dice[0] == dice[1];
	}
	
	public String toString () {
		return dice[0] + " " + dice[1];
	}
	
>>>>>>> a80e79af0cf4bcf7f45d9ce592a54664a4ce84e8
}
