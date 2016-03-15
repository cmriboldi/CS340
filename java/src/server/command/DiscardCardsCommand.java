package server.command;

import server.AuthToken;
import shared.communication.JSON.DiscardCardsJSON;
import shared.communication.JSON.IJavaJSON;

public class DiscardCardsCommand implements ICommand {

	private AuthToken authToken = null;
	private DiscardCardsJSON body = null;
	
	public DiscardCardsCommand(AuthToken authToken, IJavaJSON jsonBody)
	{
		this.authToken = authToken;
		this.body = (DiscardCardsJSON)jsonBody;
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
