package server.command;

import model.CatanModel;
import server.AuthToken;
import server.exception.ServerException;
import server.facade.IServerFacade;
import shared.communication.JSON.BuildCityJSON;
import shared.communication.JSON.IJavaJSON;

public class BuildCityCommand implements ICommand {

	private AuthToken authToken = null;
	private BuildCityJSON body = null;
	private final IServerFacade facade;
	
	public BuildCityCommand(AuthToken authToken, IJavaJSON jsonBody, IServerFacade facade)
	{
		this.authToken = authToken;
		this.body = (BuildCityJSON)jsonBody;
		this.facade = facade;
	}

	/**
	 * 
	 * Updates the CatanModel to reflect the building of a city
	 * 
     * @return CatanModel, return the updated Catan model
     */
	@Override
	public Object execute() {
		
		CatanModel cm = null; 
		try
		{
			cm = facade.getGameModel(authToken);
			cm.getMapManager().upgradeSettlement(body.getVertexLocation(), body.getPlayerIndex());
			facade.updateGame(authToken, cm);
			
		} catch (ServerException e)
		{
			e.printStackTrace();
		}
    	
		return cm;

	}
}
