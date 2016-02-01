package shared.communication.JSON;

public class BuyDevCardJSON 
{
	@SuppressWarnings("unused")
	private String type;
	private int playerIndex;
	
	public BuyDevCardJSON(int playerIndex)
	{
		this.type = "buyDevCard";
		this.playerIndex = playerIndex;
	}

	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}

}
