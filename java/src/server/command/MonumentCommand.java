package server.command;

import server.AuthToken;
import shared.communication.JSON.IJavaJSON;
import shared.communication.JSON.MonumentJSON;

public class MonumentCommand implements ICommand {

	private AuthToken authToken = null;
	private MonumentJSON body = null;
	
	public MonumentCommand(AuthToken authToken, IJavaJSON jsonBody)
	{
		this.authToken = authToken;
		this.body = (MonumentJSON)jsonBody;
	}

	/**
	 * 
	 * Updates the CatanModel to reflect the playing of a Monument card
	 * 
     * @return CatanModel, return the updated Catan model
     */
	@Override
	public Object execute() {
		// TODO Auto-generated method stub
		return null;
	}

}
