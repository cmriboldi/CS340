package command;


import shared.communication.JSON.AcceptTradeJSON;
import shared.communication.JSON.IJavaJSON;
import definitions.TurnType;
import shared.exceptions.player.InvalidPlayerIndexException;
import shared.exceptions.resources.NotEnoughPlayerResourcesException;
import server.exception.ServerException;
import shared.exceptions.resources.TradeOfferNullException;
import model.CatanModel;
import server.AuthToken;
import server.facade.IServerFacade;

public class AcceptTradeCommand extends ICommand
{
	private final IServerFacade facade;
	
	public AcceptTradeCommand(AuthToken authToken, IJavaJSON jsonBody, IServerFacade facade_p)
	{
		super(authToken, jsonBody);
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
			
			if(((AcceptTradeJSON)body).isWillAccept()) {
				cm.resourceManager.acceptPlayerTrade(((AcceptTradeJSON)body).getPlayerIndex());
				cm.chatManager.logAction(cm.playerManager.getPlayerName(((AcceptTradeJSON)body).getPlayerIndex()) + " accepted a trade.", cm.playerManager.getPlayerName(((AcceptTradeJSON)body).getPlayerIndex()));
			} else {
				cm.resourceManager.declineTrade(((AcceptTradeJSON)body).getPlayerIndex());
				cm.chatManager.logAction(cm.playerManager.getPlayerName(((AcceptTradeJSON)body).getPlayerIndex()) + " declined trade.", cm.playerManager.getPlayerName(((AcceptTradeJSON)body).getPlayerIndex()));
			}
			cm.playerManager.setTurnStatus(TurnType.PLAYING);
			
			
			

			

			
		} catch (ServerException | NotEnoughPlayerResourcesException | InvalidPlayerIndexException | TradeOfferNullException e)
		{
			e.printStackTrace();
		}
		return cm;
	}

}
