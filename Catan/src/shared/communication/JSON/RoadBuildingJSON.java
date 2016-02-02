package shared.communication.JSON;

import shared.locations.EdgeLocation;

public class RoadBuildingJSON 
{
	@SuppressWarnings("unused")
	private String type;
	private int playerIndex;
	private EdgeLocationJSON spot1;
	private EdgeLocationJSON spot2;

	public RoadBuildingJSON(int playerIndex, EdgeLocation spot1, EdgeLocation spot2)
	{
		this.type = "Road_Building";
		this.playerIndex = playerIndex;
		this.spot1 = new EdgeLocationJSON(spot1.getHexLoc(), spot1.getDir());
		this.spot2 = new EdgeLocationJSON(spot2.getHexLoc(), spot2.getDir());
	}

	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}

	public EdgeLocationJSON getSpot1() {
		return spot1;
	}

	public void setSpot1(EdgeLocationJSON spot1) {
		this.spot1 = spot1;
	}

	public EdgeLocationJSON getSpot2() {
		return spot2;
	}

	public void setSpot2(EdgeLocationJSON spot2) {
		this.spot2 = spot2;
	}
}
