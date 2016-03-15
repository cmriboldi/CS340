package shared.definitions;

/**
 * The possible commands that can be run from the server API
 * 
 * @author Christian Riboldi
 * @author Clayton Condie
 * @author Jacob Brewer
 * @author Joshua Powers
 * @author Joshua Van Steeter
 * @version 1.0 Build Winter 2016.
 */
public enum CommandType
{
	ACCEPT_TRADE, BUILD_CITY, BUILD_ROAD, BUILD_SETTLEMENT, BUY_DEVCARD, DISCARD_CARDS, FINISH_TURN, MARITIME_TRADE, MONOPOLY, MONUMENT, OFFER_TRADE, ROAD_BUILDING, ROB_PLAYER, ROLL_NUMBER, SEND_CHAT, SOLDIER, YEAR_OF_PLENTY;

	public static CommandType toCommand(String command)
	{
		switch(command)
		{
			case "sendChat":
				return SEND_CHAT;

			case "rollNumber":
				return ROLL_NUMBER;

			case "robPlayer":
				return ROB_PLAYER;

			case "finishTurn":
				return FINISH_TURN;

			case "buyDevCard":
				return BUY_DEVCARD;

			case "Year_of_Plenty":
				return YEAR_OF_PLENTY;

			case "Road_Building":
				return ROAD_BUILDING;

			case "Soldier":
				return SOLDIER;

			case "Monopoly":
				return MONOPOLY;

			case "Monument":
				return MONUMENT;

			case "buildRoad":
				return BUILD_ROAD;

			case "buildSettlement":
				return BUILD_SETTLEMENT;

			case "buildCity":
				return BUILD_CITY;

			case "offerTrade":
				return OFFER_TRADE;

			case "acceptTrade":
				return ACCEPT_TRADE;

			case "maritimeTrade":
				return MARITIME_TRADE;

			case "discardCards":
				return DISCARD_CARDS;

			default:
				return null;
		}
	}
}
