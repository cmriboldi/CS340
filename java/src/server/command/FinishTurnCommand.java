package server.command;

import model.CatanModel;
import clientfacade.Facade;
import server.AuthToken;
import server.exception.ServerException;
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
		
		CatanModel cm = null;
		try
		{
			cm = facade.getGameModel(authToken);
			cm.getPlayerManager().getTurnTracker().advancePhase();
			facade.updateGame(authToken, cm);
			
		} catch (ServerException e)
		{
			e.printStackTrace();
		}
    	
		return cm;
	}

}
