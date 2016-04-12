package app.command;

import app.communication.FinishTurnJSON;
import app.communication.IJavaJSON;
import app.definitions.TurnType;
import app.exception.ServerException;
import app.exception.TurnIndexException;
import app.model.CatanModel;
import app.server.AuthToken;
import app.server.IServerFacade;

public class FinishTurnCommand extends ICommand {


	private final IServerFacade facade;
	
	public FinishTurnCommand(AuthToken authToken, IJavaJSON jsonBody, IServerFacade facade)
	{
		super(authToken, jsonBody);
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
			cm.cardManager.finishTurnRotateCards(((FinishTurnJSON)body).getPlayerIndex());
			cm.playerManager.advanceTurn();
			cm.playerManager.setTurnStatus(TurnType.ROLLING);
			
			cm.chatManager.logAction(cm.playerManager.getPlayerName(((FinishTurnJSON)body).getPlayerIndex()) + " finished his turn.", cm.playerManager.getPlayerName(((FinishTurnJSON)body).getPlayerIndex()));
			

			

			
		} catch (ServerException | TurnIndexException e)
		{
			e.printStackTrace();
		}
    	
		return cm;
	}

}
