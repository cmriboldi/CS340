package shared.communication.JSON;

public class RollNumberJSON 
{
	@SuppressWarnings("unused")
	private String type;
	private int playerIndex;
	private int number;
	
	public RollNumberJSON(int playerIndex, int number)
	{
		this.type = "rollNumber";
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
