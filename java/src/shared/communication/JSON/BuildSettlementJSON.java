package shared.communication.JSON;

import shared.locations.VertexLocation;

public class BuildSettlementJSON implements IJavaJSON
{
	@SuppressWarnings("unused")
	private String type;
	private int playerIndex;
	private VertexLocationJSON vertexLocation;
	private boolean free;

	public BuildSettlementJSON(int playerIndex, VertexLocation vertexLocation, boolean free)
	{
		this.type = "buildSettlement";
		this.playerIndex = playerIndex;
		this.vertexLocation = new VertexLocationJSON(vertexLocation);
		this.free = free;
	}

	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}

	public VertexLocationJSON getVertexLocation() {
		return vertexLocation;
	}

	public void setVertexLocation(VertexLocation vertexLocation) {
		this.vertexLocation = new VertexLocationJSON(vertexLocation);
	}

	public boolean isFree() {
		return free;
	}

	public void setFree(boolean free) {
		this.free = free;
	}
	
}
