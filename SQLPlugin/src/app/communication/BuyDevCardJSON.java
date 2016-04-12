package app.communication;

public class BuyDevCardJSON extends IJavaJSON
{
	private int playerIndex;
	
	public BuyDevCardJSON(int playerIndex)
	{
		super("buyDevCard");
		this.playerIndex = playerIndex;
	}

	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}

}
