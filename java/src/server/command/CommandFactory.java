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
	
	public ICommand buildCommand(AuthToken authToken, IJavaJSON jsonBody) {
		ICommand command = null;
		switch(jsonBody.getCommand()) {
			case ACCEPT_TRADE:
				command = new AcceptTradeCommand(authToken, jsonBody, facadeInstance);
				break;
			case BUILD_CITY:
				command = new BuildCityCommand(authToken, jsonBody, facadeInstance);
				break;
			case BUILD_ROAD:
				command = new BuildRoadCommand(authToken, jsonBody, facadeInstance);
				break;
			case BUILD_SETTLEMENT:
				command = new BuildSettlementCommand(authToken, jsonBody, facadeInstance);
				break;
			case BUY_DEVCARD:
				command = new BuyDevCardCommand(authToken, jsonBody, facadeInstance);
				break;
			case DISCARD_CARDS:
				command = new DiscardCardsCommand(authToken, jsonBody, facadeInstance);
				break;
			case FINISH_TURN:
				command = new FinishTurnCommand(authToken, jsonBody, facadeInstance);
				break;
			case MARITIME_TRADE:
				command = new MaritimeTradeCommand(authToken, jsonBody, facadeInstance);
				break;
			case MONOPOLY:
				command = new MonopolyCommand(authToken, jsonBody, facadeInstance);
				break;
			case MONUMENT:
				command = new MonumentCommand(authToken, jsonBody, facadeInstance);
				break;
			case OFFER_TRADE:
				command = new OfferTradeCommand(authToken, jsonBody, facadeInstance);
				break;
			case ROAD_BUILDING:
				command = new RoadBuildingCommand(authToken, jsonBody, facadeInstance);
				break;
			case ROB_PLAYER:
				command = new RobPlayerCommand(authToken, jsonBody, facadeInstance);
				break;
			case ROLL_NUMBER:
				command = new RollNumberCommand(authToken, jsonBody, facadeInstance);
				break;
			case SEND_CHAT:
				command = new SendChatCommand(authToken, jsonBody, facadeInstance);
				break;
			case SOLDIER:
				command = new SoldierCommand(authToken, jsonBody, facadeInstance);
				break;
			case YEAR_OF_PLENTY:
				command = new YearOfPlentyCommand(authToken, jsonBody, facadeInstance);
				break;
		}
		return command;
	}
}
