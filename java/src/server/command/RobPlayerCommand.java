package server.command;

import server.AuthToken;
import shared.communication.JSON.IJavaJSON;
import shared.communication.JSON.RobPlayerJSON;

public class RobPlayerCommand implements ICommand
{
	private AuthToken authToken = null;
	private RobPlayerJSON body = null;
	
	public RobPlayerCommand(AuthToken authToken, IJavaJSON jsonBody)
	{
		this.authToken = authToken;
		this.body = (RobPlayerJSON)jsonBody;
	}

	/**
	 * 
	 * Updates the CatanModel to reflect robbing a player
	 * 
     * @return CatanModel, return the updated Catan model
     */
    @Override
    public Object execute() {
        return null;
    }
}
