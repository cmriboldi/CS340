package server.command;

import com.google.inject.Inject;
import server.AuthToken;
import server.facade.IServerFacade;
import shared.communication.JSON.IJavaJSON;
import shared.definitions.CommandType;

public class CommandFactory
{
	private final IServerFacade facadeInstance;

	@Inject
	public CommandFactory(IServerFacade facade_p) {
		facadeInstance = facade_p;
	}
	
	public ICommand buildCommand(AuthToken authToken, IJavaJSON jsonBody, IServerFacade facade) {
		ICommand command = null;
		switch(jsonBody.getCommand()) {
			case ACCEPT_TRADE:
				command = new AcceptTradeCommand(authToken, jsonBody, facade);
				break;
			case BUILD_CITY:
				command = new BuildCityCommand(authToken, jsonBody, facade);
				break;
			case BUILD_ROAD:
				command = new BuildRoadCommand(authToken, jsonBody, facade);
				break;
			case BUILD_SETTLEMENT:
				command = new BuildSettlementCommand(authToken, jsonBody, facade);
				break;
			case BUY_DEVCARD:
				command = new BuyDevCardCommand(authToken, jsonBody, facade);
				break;
			case DISCARD_CARDS:
				command = new DiscardCardsCommand(authToken, jsonBody, facade);
				break;
			case FINISH_TURN:
				command = new FinishTurnCommand(authToken, jsonBody, facade);
				break;
			case MARITIME_TRADE:
				command = new MaritimeTradeCommand(authToken, jsonBody, facade);
				break;
			case MONOPOLY:
				command = new MonopolyCommand(authToken, jsonBody, facade);
				break;
			case MONUMENT:
				command = new MonumentCommand(authToken, jsonBody, facade);
				break;
			case OFFER_TRADE:
				command = new OfferTradeCommand(authToken, jsonBody, facade);
				break;
			case ROAD_BUILDING:
				command = new RoadBuildingCommand(authToken, jsonBody, facade);
				break;
			case ROB_PLAYER:
				command = new RobPlayerCommand(authToken, jsonBody, facade);
				break;
			case ROLL_NUMBER:
				command = new RollNumberCommand(authToken, jsonBody, facade);
				break;
			case SEND_CHAT:
				command = new SendChatCommand(authToken, jsonBody, facade);
				break;
			case SOLDIER:
				command = new SoldierCommand(authToken, jsonBody, facade);
				break;
			case YEAR_OF_PLENTY:
				command = new YearOfPlentyCommand(authToken, jsonBody, facade);
				break;
		}
		return command;
	}
}
