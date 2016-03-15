package server.command;

import server.AuthToken;
import shared.communication.JSON.IJavaJSON;

public class FinishTurnCommand implements ICommand {

	public FinishTurnCommand(AuthToken authToken, IJavaJSON jsonBody)
	{
		// TODO Auto-generated constructor stub
	}

	/**
	 *
	 * Updates the CatanModel to reflect a player finishing their turn
	 *
	 * @return CatanModel, return the updated Catan model
	 */
	@Override
	public Object execute() {
		// TODO Auto-generated method stub
		return null;
	}

}
