package server.command;

import model.CatanModel;
import clientfacade.Facade;
import server.AuthToken;
import server.exception.ServerException;
import server.facade.IServerFacade;
import shared.communication.JSON.FinishTurnJSON;
import shared.communication.JSON.IJavaJSON;
import shared.definitions.TurnType;
import shared.exceptions.player.TurnIndexException;

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
			System.out.println("FinishTurnCommand");

			cm = facade.getGameModel(authToken);
			cm.playerManager.advanceTurn();
			cm.playerManager.setTurnStatus(TurnType.ROLLING);
			facade.updateGame(authToken, cm);
			
			facade.recordCommand(authToken, this);
			
		} catch (ServerException | TurnIndexException e)
		{
			e.printStackTrace();
		}
    	
		return cm;
	}

}
