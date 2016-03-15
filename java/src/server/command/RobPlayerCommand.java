package server.command;

import server.AuthToken;
import shared.communication.JSON.IJavaJSON;

public class RobPlayerCommand implements ICommand
{
	public RobPlayerCommand(AuthToken authToken, IJavaJSON jsonBody)
	{
		// TODO Auto-generated constructor stub
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
