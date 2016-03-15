package server.command;

import server.AuthToken;
import shared.communication.JSON.BuyDevCardJSON;
import shared.communication.JSON.IJavaJSON;

public class BuyDevCardCommand implements ICommand {

	private AuthToken authToken = null;
	private BuyDevCardJSON body = null;
	
	public BuyDevCardCommand(AuthToken authToken, IJavaJSON jsonBody)
	{
		this.authToken = authToken;
		this.body = (BuyDevCardJSON)jsonBody;
	}

	/**
	 *
	 * Updates the CatanModel to reflect a player buying a dev card
	 *
	 * @return CatanModel, return the updated Catan model
	 */
	@Override
	public Object execute() {
		// TODO Auto-generated method stub
		return null;
	}

}
