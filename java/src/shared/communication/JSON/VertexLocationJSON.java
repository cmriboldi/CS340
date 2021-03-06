package shared.communication.JSON;

import shared.locations.VertexLocation;

public class VertexLocationJSON
{
	private int x;
	private int y;
	private String direction;
	
	public VertexLocationJSON(VertexLocation location)
	{
		this.x = location.getHexLoc().getX();
		this.y = location.getHexLoc().getY();
		this.direction = location.getDir().toString();
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}
}
