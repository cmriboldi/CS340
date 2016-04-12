package communication;

public class RollNumberJSON extends IJavaJSON
{
	private int playerIndex;
	private int number;
	
	public RollNumberJSON(int playerIndex, int number)
	{
		super("rollNumber");
		this.playerIndex = playerIndex;
		this.number = number;
	}

	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

}
