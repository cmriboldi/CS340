package shared.communication.JSON;

public class FinishTurnJSON 
{
	@SuppressWarnings("unused")
	private String type;
	private int playerIndex;
	
	public FinishTurnJSON(int playerIndex)
	{
		this.type = "finishTurn";
		this.playerIndex = playerIndex;
	}

	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}

}
