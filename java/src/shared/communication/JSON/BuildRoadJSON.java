package shared.communication.JSON;

import shared.locations.EdgeLocation;

public class BuildRoadJSON extends IJavaJSON
{

	private int playerIndex;
	private EdgeLocationJSON roadLocation;
	private boolean free;

	public BuildRoadJSON(int playerIndex, EdgeLocation roadLocation, boolean free)
	{
		super("buildRoad");
		this.playerIndex = playerIndex;
		this.roadLocation = new EdgeLocationJSON(roadLocation);
		this.free = free;
	}

	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}

	public EdgeLocationJSON getRoadLocation() {
		return roadLocation;
	}

	public void setRoadLocation(EdgeLocation roadLocation) 
	{
		this.roadLocation = new EdgeLocationJSON(roadLocation);
	}

	public boolean isFree() {
		return free;
	}

	public void setFree(boolean free) {
		this.free = free;
	}
}
