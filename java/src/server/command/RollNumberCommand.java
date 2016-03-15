package server.command;

import server.AuthToken;
import shared.communication.JSON.IJavaJSON;
import shared.communication.JSON.RollNumberJSON;

public class RollNumberCommand implements ICommand {

	private AuthToken authToken = null;
	private RollNumberJSON body = null;
	
	public RollNumberCommand(AuthToken authToken, IJavaJSON jsonBody)
	{
		this.authToken = authToken;
		this.body = (RollNumberJSON)jsonBody;
	}

	/**
	 * 
	 * Updates the CatanModel to reflect rolling a number
	 * 
     * @return CatanModel, return the updated Catan model
     */
	@Override
	public Object execute() {
		// TODO Auto-generated method stub
		return null;
	}

}
