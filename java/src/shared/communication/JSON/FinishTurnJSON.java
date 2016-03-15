package shared.communication.JSON;

public class FinishTurnJSON extends IJavaJSON
{
	private int playerIndex;
	
	public FinishTurnJSON(int playerIndex)
	{
		super("finishTurn");
		this.playerIndex = playerIndex;
	}

	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}

}
