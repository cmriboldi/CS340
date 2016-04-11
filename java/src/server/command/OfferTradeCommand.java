package server.command;

import model.CatanModel;
import model.resources.TradeOffer;
import server.AuthToken;
import server.exception.ServerException;
import server.facade.IServerFacade;
import shared.communication.JSON.IJavaJSON;
import shared.communication.JSON.OfferTradeJSON;

public class OfferTradeCommand extends ICommand{


	private final IServerFacade facade;
	
	public OfferTradeCommand(AuthToken authToken, IJavaJSON jsonBody, IServerFacade facade)
	{
		super(authToken, jsonBody);
		this.facade = facade;
	}

	/**
	 * 
	 * Updates the CatanModel to reflect a players offered trade
	 * 
     * @return CatanModel, return the updated Catan model
     */
	@Override
	public Object execute() {
		CatanModel cm = null;
		try
		{
			cm = facade.getGameModel(authToken);
			
			TradeOffer tradeOffer = new TradeOffer(((OfferTradeJSON)body).getOffer(), ((OfferTradeJSON)body).getPlayerIndex(), ((OfferTradeJSON)body).getReceiver());
			
			cm.resourceManager.setTradeOffer(tradeOffer);
			
			cm.chatManager.logAction(cm.playerManager.getPlayerName(((OfferTradeJSON)body).getPlayerIndex()) + " offered a trade.", cm.playerManager.getPlayerName(((OfferTradeJSON)body).getPlayerIndex()));
			

			

			
		} catch (ServerException e)
		{
			e.printStackTrace();
		}
		return cm;
	}

}
