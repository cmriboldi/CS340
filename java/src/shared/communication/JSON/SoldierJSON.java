package shared.communication.JSON;

import shared.locations.HexLocation;

public class SoldierJSON extends IJavaJSON
{
	private int playerIndex;
	private int victimIndex;
	@SuppressWarnings("unused")
	private HexLocationJSON location;
	
	public SoldierJSON(int playerIndex, int victimIndex, HexLocation location)
	{
		super("Soldier");
		this.playerIndex = playerIndex;
		this.victimIndex = victimIndex;
		this.location = new HexLocationJSON(location.getX(), location.getY());
	}

	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}

	public int getVictimIndex() {
		return victimIndex;
	}

	public void setVictimIndex(int victimIndex) {
		this.victimIndex = victimIndex;
	}
	
	public HexLocation getHexLocation() {
		return new HexLocation(this.location.getX(), this.location.getY());
	}

}
