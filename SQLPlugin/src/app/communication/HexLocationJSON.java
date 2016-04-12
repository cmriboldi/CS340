package app.communication;

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

	public int getX() {
		return Integer.parseInt(x);
	}

	public void setX(String x) {
		this.x = x;
	}

	public int getY() {
		return Integer.parseInt(y);
	}

	public void setY(String y) {
		this.y = y;
	}

}
