package server.command;

import model.CatanModel;
import model.resources.TradeOffer;
import server.AuthToken;
import server.exception.ServerException;
import server.facade.IServerFacade;
import shared.communication.JSON.IJavaJSON;
import shared.communication.JSON.OfferTradeJSON;

public class OfferTradeCommand implements ICommand{

	private AuthToken authToken = null;
	private OfferTradeJSON body = null;
	private final IServerFacade facade;
	
	public OfferTradeCommand(AuthToken authToken, IJavaJSON jsonBody, IServerFacade facade)
	{
		this.authToken = authToken;
		this.body = (OfferTradeJSON)jsonBody;
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
			
			cm.playerManager.setTurnStatus("trading");
			
			TradeOffer tradeOffer = new TradeOffer(this.body.getOffer(), this.body.getPlayerIndex(), this.body.getReceiver());
			
			cm.resourceManager.setTradeOffer(tradeOffer);
			
			facade.updateGame(authToken, cm);
			
		} catch (ServerException e)
		{
			e.printStackTrace();
		}
		return cm;
	}

}
