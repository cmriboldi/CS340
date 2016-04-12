package communication;

public class MonumentJSON extends IJavaJSON
{
	private int playerIndex;
	
	public MonumentJSON(int playerIndex)
	{
		super("Monument");
		this.playerIndex = playerIndex;
	}

	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}

}
