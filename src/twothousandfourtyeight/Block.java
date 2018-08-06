package twothousandfourtyeight;

public class Block {
	int value;
	boolean alreadyAdded = false; //ensures there is no double adding when a key is pressed
	public Block(int v)
	{
		value = v;
	}
	public String toString()
	{
		String numberAsString = String.format ("%d", value);
		return numberAsString;
	}
}
