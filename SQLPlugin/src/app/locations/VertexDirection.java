package app.locations;

public enum VertexDirection
{
	West, NorthWest, NorthEast, East, SouthEast, SouthWest;

	private VertexDirection opposite;

	static
	{
		West.opposite = East;
		NorthWest.opposite = SouthEast;
		NorthEast.opposite = SouthWest;
		East.opposite = West;
		SouthEast.opposite = NorthWest;
		SouthWest.opposite = NorthEast;
	}

	public VertexDirection getOppositeDirection()
	{
		return opposite;
	}
	
	public String toString()
	{
		switch(this)
		{
			case West:
				return "W";
			case NorthWest:
				return "NW";
			case NorthEast:
				return "NE";
			case East:
				return "E";
			case SouthEast:
				return "SE";
			case SouthWest:
				return "SW";
			default:
				return null;
		}
	}

	public static VertexDirection toEnum(String input)
	{
		switch(input)
		{
			case "W":
				return West;
			case "NW":
				return NorthWest;
			case "NE":
				return NorthEast;
			case "E":
				return East;
			case "SE":
				return SouthEast;
			case "SW":
				return SouthWest;
			default:
				return null;
		}
	}
}
