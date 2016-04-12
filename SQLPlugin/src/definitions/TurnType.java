package definitions;

public enum TurnType
{
	ROLLING, ROBBING, PLAYING, DISCARDING, FIRST_ROUND, SECOND_ROUND;
	
	public String toString()
	{
		switch(this)
		{
			case ROLLING:
				return "Rolling";
			case ROBBING:
				return "Robbing";
			case PLAYING:
				return "Playing";
			case DISCARDING:
				return "Discarding";
			case FIRST_ROUND:
				return "FirstRound";
			case SECOND_ROUND:
				return "SecondRound";
			default:
				return null;
		}
	}
	
	public static TurnType toEnum(String turnType)
	{
		switch(turnType)
		{
			case "Rolling":
				return ROLLING;
			case "Robbing":
				return ROBBING;
			case "Playing":
				return PLAYING;
			case "Discarding":
				return DISCARDING;
			case "FirstRound":
				return FIRST_ROUND;
			case "SecondRound":
				return SECOND_ROUND;
			default:
				return null;
		}
	}
}