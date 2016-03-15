package server.command;

import server.AuthToken;
import shared.communication.JSON.IJavaJSON;
import shared.communication.JSON.MonopolyJSON;

public class MonopolyCommand implements ICommand {

	private AuthToken authToken = null;
	private MonopolyJSON body = null;
	
	public MonopolyCommand(AuthToken authToken, IJavaJSON jsonBody)
	{
		this.authToken = authToken;
		this.body = (MonopolyJSON)jsonBody;
	}

	/**
	 * 
	 * Updates the CatanModel to reflect the playing of a Monopoly card
	 * 
     * @return CatanModel, return the updated Catan model
     */
	@Override
	public Object execute() {
		// TODO Auto-generated method stub
		return null;
	}
}
