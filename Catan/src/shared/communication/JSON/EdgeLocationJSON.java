package shared.communication.JSON;

import shared.locations.EdgeDirection;
import shared.locations.HexLocation;

public class EdgeLocationJSON 
{
	private int x;
	private int y;
	private String direction;
	
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

}
