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
				break;
			case BUILD_CITY:
				command = new BuildCityCommand(authToken, jsonBody);
				break;
			case BUILD_ROAD:
				command = new BuildRoadCommand(authToken, jsonBody);
				break;
			case BUILD_SETTLEMENT:
				command = new BuildSettlementCommand(authToken, jsonBody);
				break;
			case BUY_DEVCARD:
				command = new BuyDevCardCommand(authToken, jsonBody);
				break;
			case DISCARD_CARDS:
				command = new DiscardCardsCommand(authToken, jsonBody);
				break;
			case FINISH_TURN:
				command = new FinishTurnCommand(authToken, jsonBody);
				break;
			case MARITIME_TRADE:
				command = new MaritimeTradeCommand(authToken, jsonBody);
				break;
			case MONOPOLY:
				command = new MonopolyCommand(authToken, jsonBody);
				break;
			case MONUMENT:
				command = new MonumentCommand(authToken, jsonBody);
				break;
			case OFFER_TRADE:
				command = new OfferTradeCommand(authToken, jsonBody);
				break;
			case ROAD_BUILDING:
				command = new RoadBuildingCommand(authToken, jsonBody);
				break;
			case ROB_PLAYER:
				command = new RobPlayerCommand(authToken, jsonBody);
				break;
			case ROLL_NUMBER:
				command = new RollNumberCommand(authToken, jsonBody);
				break;
			case SEND_CHAT:
				command = new SendChatCommand(authToken, jsonBody);
				break;
			case SOLDIER:
				command = new SoldierCommand(authToken, jsonBody);
				break;
			case YEAR_OF_PLENTY:
				command = new YearOfPlentyCommand(authToken, jsonBody);
				break;
		}
		return command;
	}
}
