package server.command;

import server.AuthToken;
import shared.communication.JSON.IJavaJSON;
import shared.communication.JSON.OfferTradeJSON;

public class OfferTradeCommand implements ICommand{

	private AuthToken authToken = null;
	private OfferTradeJSON body = null;
	
	public OfferTradeCommand(AuthToken authToken, IJavaJSON jsonBody)
	{
		this.authToken = authToken;
		this.body = (OfferTradeJSON)jsonBody;
	}

	/**
	 * 
	 * Updates the CatanModel to reflect a players offered trade
	 * 
     * @return CatanModel, return the updated Catan model
     */
	@Override
	public Object execute() {
		// TODO Auto-generated method stub
		return null;
	}

}
