package server.command;

import server.AuthToken;
import server.facade.IServerFacade;
import shared.communication.JSON.FinishTurnJSON;
import shared.communication.JSON.IJavaJSON;

public class FinishTurnCommand implements ICommand {

	private AuthToken authToken = null;
	private FinishTurnJSON body = null;
	private final IServerFacade facade;
	
	public FinishTurnCommand(AuthToken authToken, IJavaJSON jsonBody, IServerFacade facade)
	{
		this.authToken = authToken;
		this.body = (FinishTurnJSON)jsonBody;
		this.facade = facade;
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
