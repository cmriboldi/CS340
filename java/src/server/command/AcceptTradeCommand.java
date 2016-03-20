package server.command;

import model.CatanModel;
import model.resources.TradeOffer;
import server.AuthToken;
import server.exception.ServerException;
import server.facade.IServerFacade;
import shared.communication.JSON.AcceptTradeJSON;
import shared.communication.JSON.IJavaJSON;
import shared.exceptions.player.InvalidPlayerIndexException;
import shared.exceptions.resources.NotEnoughPlayerResourcesException;
import shared.exceptions.resources.TradeOfferNullException;

public class AcceptTradeCommand implements ICommand {
	
	private AuthToken authToken = null;
	private AcceptTradeJSON body = null;
	private final IServerFacade facade;

	
	public AcceptTradeCommand(AuthToken authToken, IJavaJSON jsonBody, IServerFacade facade_p)
	{
		this.authToken = authToken;
		this.body = (AcceptTradeJSON)jsonBody;
		this.facade = facade_p;
	}

	/**
	 * 
	 * Updates the CatanModel to reflect the acceptance by a player of a trade
	 * 
     * @return CatanModel, return the updated Catan model
     */
	@Override
	public Object execute() {
		CatanModel cm = null;
		try
		{
			cm = facade.getGameModel(authToken);
			
			if(this.body.isWillAccept()) {
				cm.resourceManager.acceptPlayerTrade(this.body.getPlayerIndex());
			} else {
				cm.resourceManager.declineTrade(this.body.getPlayerIndex());
			}
			cm.playerManager.setTurnStatus("playing");
			
			facade.updateGame(authToken, cm);
			
		} catch (ServerException | NotEnoughPlayerResourcesException | InvalidPlayerIndexException | TradeOfferNullException e)
		{
			e.printStackTrace();
		}
		return cm;
	}

}
