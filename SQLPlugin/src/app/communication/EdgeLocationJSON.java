package app.communication;


import app.locations.EdgeDirection;
import app.locations.EdgeLocation;
import app.locations.HexLocation;

public class EdgeLocationJSON
{
	private int x;
	private int y;
	private String direction;
	
	public EdgeLocationJSON(EdgeLocation edgeLocation)
	{
		this.x = edgeLocation.getHexLoc().getX();
		this.y = edgeLocation.getHexLoc().getY();
		this.direction = edgeLocation.getDir().toString();
	}
	
	public EdgeLocationJSON(HexLocation hex, EdgeDirection direction)
	{
		this.x = hex.getX();
		this.y = hex.getY();
		this.direction = direction.toString();
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
	
	public EdgeLocation getEdgeLocation() {
		HexLocation hex = new HexLocation(this.x, this.y);
		return new EdgeLocation(hex, EdgeDirection.toEnum(this.direction));
	}

}
