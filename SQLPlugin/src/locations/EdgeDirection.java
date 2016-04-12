package locations;

public enum EdgeDirection
{

	NorthWest, North, NorthEast, SouthEast, South, SouthWest;

	private EdgeDirection opposite;

	static
	{
		NorthWest.opposite = SouthEast;
		North.opposite = South;
		NorthEast.opposite = SouthWest;
		SouthEast.opposite = NorthWest;
		South.opposite = North;
		SouthWest.opposite = NorthEast;
	}

	public EdgeDirection getOppositeDirection()
	{
		return opposite;
	}
	
	public String toString()
	{
		switch(this)
		{
			case NorthWest:
				return "NW";
			case North:
				return "N";
			case NorthEast:
				return "NE";
			case SouthEast:
				return "SE";
			case South:
				return "S";
			case SouthWest:
				return "SW";
			default:
				return null;
		}
	}
	
	public static EdgeDirection toEnum(String dir)
	{
		switch(dir)
		{
			case "NW":
				return NorthWest;
			case "N":
				return North;
			case "NE":
				return NorthEast;
			case "SE":
				return SouthEast;
			case "S":
				return South;
			case "SW":
				return SouthWest;
			default:
				return null;
		}
	}
}
