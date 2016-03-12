package shared.communication.JSON;

import shared.locations.VertexLocation;

public class BuildCityJSON implements IJavaJSON
{
	@SuppressWarnings("unused")
	private String type;
	private int playerIndex;
	private VertexLocationJSON vertexLocation;

	public BuildCityJSON(int playerIndex, VertexLocation vertexLocation)
	{
		this.type = "buildCity";
		this.playerIndex = playerIndex;
		this.vertexLocation = new VertexLocationJSON(vertexLocation);
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
}
