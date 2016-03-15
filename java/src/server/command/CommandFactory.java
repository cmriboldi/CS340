package server.command;

import server.AuthToken;
import shared.communication.JSON.IJavaJSON;
import shared.definitions.CommandType;

public class CommandFactory
{
	public CommandFactory() {
		
	}
	
	public ICommand buildCommand(AuthToken authToken, CommandType commandType, IJavaJSON jsonBody) {
		ICommand command = null;
		switch(commandType) {
			case ACCEPT_TRADE:
				command = new AcceptTradeCommand(authToken, jsonBody);
			case BUILD_CITY:
				command = new BuildCityCommand(authToken, jsonBody);
			case BUILD_ROAD:
				command = new BuildRoadCommand(authToken, jsonBody);
			case BUILD_SETTLEMENT:
				command = new BuildSettlementCommand(authToken, jsonBody);
			case BUY_DEVCARD:
				command = new BuyDevCardCommand(authToken, jsonBody);
			case DISCARD_CARDS:
				command = new DiscardCardsCommand(authToken, jsonBody);
			case FINISH_TURN:
				command = new FinishTurnCommand(authToken, jsonBody);
			case MARITIME_TRADE:
				command = new MaritimeTradeCommand(authToken, jsonBody);
			case MONOPOLY:
				command = new MonopolyCommand(authToken, jsonBody);
			case MONUMENT:
				command = new MonumentCommand(authToken, jsonBody);
			case OFFER_TRADE:
				command = new OfferTradeCommand(authToken, jsonBody);
			case ROAD_BUILDING:
				command = new RoadBuildingCommand(authToken, jsonBody);
			case ROB_PLAYER:
				command = new RobPlayerCommand(authToken, jsonBody);
			case ROLL_NUMBER:
				command = new RollNumberCommand(authToken, jsonBody);
			case SEND_CHAT:
				command = new SendChatCommand(authToken, jsonBody);
			case SOLDIER:
				command = new SoldierCommand(authToken, jsonBody);
			case YEAR_OF_PLENTY:
				command = new YearOfPlentyCommand(authToken, jsonBody);
		}
		return command;
	}
}
