package shared.communication.JSON;

public class HexLocationJSON 
{
	private String x;
	private String y;
	
	public HexLocationJSON(String x, String y)
	{
		this.x = x;
		this.y = y;
	}
	
	public HexLocationJSON(int x, int y)
	{
		this.x = "" + x;
		this.y = "" + y;
	}

	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}

}
