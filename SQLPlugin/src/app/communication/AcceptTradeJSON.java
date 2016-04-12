package app.communication;

public class AcceptTradeJSON extends IJavaJSON
{
	private int playerIndex;
	private boolean willAccept;
	
	public AcceptTradeJSON(int playerIndex, boolean willAccept)
	{
		super("acceptTrade");
		this.playerIndex = playerIndex;
		this.willAccept = willAccept;
	}

	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}

	public boolean isWillAccept() {
		return willAccept;
	}

	public void setWillAccept(boolean willAccept) {
		this.willAccept = willAccept;
	}
}
