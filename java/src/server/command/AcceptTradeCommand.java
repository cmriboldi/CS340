package server.command;

import server.AuthToken;
import shared.communication.JSON.IJavaJSON;

public class AcceptTradeCommand implements ICommand {

	
	public AcceptTradeCommand(AuthToken authToken, IJavaJSON jsonBody)
	{
		// TODO Auto-generated constructor stub
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
