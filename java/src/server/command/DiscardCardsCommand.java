package server.command;

import server.AuthToken;
import shared.communication.JSON.IJavaJSON;

public class DiscardCardsCommand implements ICommand {

	
	public DiscardCardsCommand(AuthToken authToken, IJavaJSON jsonBody)
	{
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * Updates the CatanModel to reflect the new resource card amounts after discarding
	 * 
     * @return CatanModel, return the updated Catan model
     */
	@Override
	public Object execute() {
		// TODO Auto-generated method stub
		return null;
	}

}
