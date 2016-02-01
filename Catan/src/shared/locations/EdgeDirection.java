package shared.locations;

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
}
