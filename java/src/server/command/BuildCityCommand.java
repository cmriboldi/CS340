package server.command;

import server.AuthToken;
import shared.communication.JSON.BuildCityJSON;
import shared.communication.JSON.IJavaJSON;

public class BuildCityCommand implements ICommand {

	private AuthToken authToken = null;
	private BuildCityJSON body = null;
	
	public BuildCityCommand(AuthToken authToken, IJavaJSON jsonBody)
	{
		this.authToken = authToken;
		this.body = (BuildCityJSON)jsonBody;
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
