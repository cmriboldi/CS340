package app.communication;


import app.locations.VertexLocation;

public class BuildCityJSON extends IJavaJSON
{
	private int playerIndex;
	private VertexLocationJSON vertexLocation;

	public BuildCityJSON(int playerIndex, VertexLocation vertexLocation)
	{
		super("buildCity");
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
