package shared.communication.JSON;

public class MonumentJSON 
{
	@SuppressWarnings("unused")
	private String type;
	private int playerIndex;
	
	public MonumentJSON(int playerIndex)
	{
		this.type = "Monument";
		this.playerIndex = playerIndex;
	}

	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}

}
