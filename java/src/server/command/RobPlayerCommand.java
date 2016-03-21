package server.command;

import model.CatanModel;
import server.AuthToken;
import server.exception.ServerException;
import server.facade.IServerFacade;
import shared.communication.JSON.IJavaJSON;
import shared.communication.JSON.RobPlayerJSON;
import shared.definitions.TurnType;
import shared.exceptions.resources.NotEnoughResourcesException;

public class RobPlayerCommand implements ICommand
{
	private AuthToken authToken = null;
	private RobPlayerJSON body = null;
	private final IServerFacade facade;
	
	public RobPlayerCommand(AuthToken authToken, IJavaJSON jsonBody, IServerFacade facade)
	{
		this.authToken = authToken;
		this.body = (RobPlayerJSON)jsonBody;
		this.facade = facade;
	}

	/**
	 * 
	 * Updates the CatanModel to reflect robbing a player
	 * 
     * @return CatanModel, return the updated Catan model
     */
    @Override
    public Object execute() {
    	CatanModel cm = null;
		try
		{
			cm = facade.getGameModel(authToken);
			
			cm.resourceManager.robPlayer(this.body.getVictimIndex(), this.body.getPlayerIndex());
			cm.playerManager.setTurnStatus(TurnType.PLAYING);
			
			facade.updateGame(authToken, cm);
			
		} catch (ServerException | NotEnoughResourcesException e)
		{
			e.printStackTrace();
		}
		return cm;
    }
}
