package server.command;

import server.AuthToken;
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
		// TODO Auto-generated method stub
		return null;
	}
}
