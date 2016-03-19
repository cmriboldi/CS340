package server.command;

import model.CatanModel;
import server.AuthToken;
import server.exception.ServerException;
import server.facade.IServerFacade;
import shared.communication.JSON.BuildRoadJSON;
import shared.communication.JSON.IJavaJSON;
import shared.definitions.DevCardType;
import shared.exceptions.development.NotEnoughDevCardsException;

/**
 * Created by clayt on 3/9/2016.
 */
public class BuildRoadCommand implements ICommand {

	private AuthToken authToken = null;
	private BuildRoadJSON body = null;
	private final IServerFacade facade;
	
	public BuildRoadCommand(AuthToken authToken, IJavaJSON jsonBody, IServerFacade facade)
	{
		this.authToken = authToken;
		this.body = (BuildRoadJSON)jsonBody;
		this.facade = facade;
		
	}

	/**
	 * 
	 * Updates the CatanModel to reflect the building of a road
	 * 
     * @return CatanModel, return the updated Catan model
     */
    @Override
    public Object execute() {
    	
    	CatanModel cm = null;
		try
		{
			cm = facade.getGameModel(authToken);
			cm.mapManager.placeRoad(body.getRoadLocation(), body.getPlayerIndex());			
			facade.updateGame(authToken, cm);
			
		} catch (ServerException e)
		{
			e.printStackTrace();
		}
    	
		return cm;
    }
}
