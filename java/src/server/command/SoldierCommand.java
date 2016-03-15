package server.command;

import server.AuthToken;
import shared.communication.JSON.IJavaJSON;
import shared.communication.JSON.SoldierJSON;

public class SoldierCommand implements ICommand {

	private AuthToken authToken = null;
	private SoldierJSON body = null;
	
	public SoldierCommand(AuthToken authToken, IJavaJSON jsonBody)
	{
		this.authToken = authToken;
		this.body = (SoldierJSON)jsonBody;
	}

	/**
	 * 
	 * Updates the CatanModel to reflect the playing of a soldier card
	 * 
     * @return CatanModel, return the updated Catan model
     */
	@Override
	public Object execute() {
		// TODO Auto-generated method stub
		return null;
	}

}
