package server.command;

import server.AuthToken;
import shared.communication.JSON.AcceptTradeJSON;
import shared.communication.JSON.IJavaJSON;

public class AcceptTradeCommand implements ICommand {
	
	private AuthToken authToken = null;
	private AcceptTradeJSON body = null;

	
	public AcceptTradeCommand(AuthToken authToken, IJavaJSON jsonBody)
	{
		this.authToken = authToken;
		this.body = (AcceptTradeJSON)jsonBody;
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
