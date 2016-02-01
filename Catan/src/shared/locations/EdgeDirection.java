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
				return "NorthWest";
			case North:
				return "North";
			case NorthEast:
				return "NorthEast";
			case SouthEast:
				return "SouthEast";
			case South:
				return "South";
			case SouthWest:
				return "SouthWest";
			default:
				return null;
		}
	}
}
