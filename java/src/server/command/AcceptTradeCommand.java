package server.command;

import server.AuthToken;
import server.facade.IServerFacade;
import shared.communication.JSON.AcceptTradeJSON;
import shared.communication.JSON.IJavaJSON;

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
		// TODO Auto-generated method stub
		return null;
	}

}
