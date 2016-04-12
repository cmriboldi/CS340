package definitions;

public enum ResourceType
{
	WOOD, BRICK, SHEEP, WHEAT, ORE;
	
	public String toString()
	{
		switch(this)
		{
			case WOOD:
				return "wood";
			case BRICK:
				return "brick";
			case SHEEP:
				return "sheep";
			case WHEAT:
				return "wheat";
			case ORE:
				return "ore";
			default:
				return null;
		}
	}
	
	public static ResourceType toEnum(String resource)
	{
		switch(resource)
		{
			case "wood":
				return WOOD;
			case "brick":
				return BRICK;
			case "sheep":
				return SHEEP;
			case "wheat":
				return WHEAT;
			case "ore":
				return ORE;
			default:
				return null;
		}
	}
}
