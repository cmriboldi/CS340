package server.command;

import clientfacade.Facade;
import server.AuthToken;
import shared.communication.JSON.FinishTurnJSON;
import shared.communication.JSON.IJavaJSON;

public class FinishTurnCommand implements ICommand {

	private AuthToken authToken = null;
	private FinishTurnJSON body = null;
	
	public FinishTurnCommand(AuthToken authToken, IJavaJSON jsonBody)
	{
		this.authToken = authToken;
		this.body = (FinishTurnJSON)jsonBody;
	}

	/**
	 *
	 * Updates the CatanModel to reflect a player finishing their turn
	 *
	 * @return CatanModel, return the updated Catan model
	 */
	@Override
	public Object execute() {
		
		Facade.getCatanModel().getPlayerManager().getTurnTracker().advancePhase();
		
		return null;
	}

}
