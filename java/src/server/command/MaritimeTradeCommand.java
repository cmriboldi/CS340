package server.command;

import server.AuthToken;
import server.facade.IServerFacade;
import shared.communication.JSON.IJavaJSON;
import shared.communication.JSON.MaritimeTradeJSON;

public class MaritimeTradeCommand implements ICommand {

	private AuthToken authToken = null;
	private MaritimeTradeJSON body = null;
	private final IServerFacade facade;
	
	public MaritimeTradeCommand(AuthToken authToken, IJavaJSON jsonBody, IServerFacade facade)
	{
		this.authToken = authToken;
		this.body = (MaritimeTradeJSON)jsonBody;
		this.facade = facade;
	}

	/**
	 * 
	 * Updates the CatanModel to reflect desired exchange of resources by a player
	 * 
     * @return CatanModel, return the updated Catan model
     */
	@Override
	public Object execute() {
		// TODO Auto-generated method stub
		return null;
	}

}
