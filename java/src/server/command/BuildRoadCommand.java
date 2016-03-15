package server.command;

import server.AuthToken;
import shared.communication.JSON.BuildRoadJSON;
import shared.communication.JSON.IJavaJSON;

/**
 * Created by clayt on 3/9/2016.
 */
public class BuildRoadCommand implements ICommand {

	private AuthToken authToken = null;
	private BuildRoadJSON body = null;
	
	public BuildRoadCommand(AuthToken authToken, IJavaJSON jsonBody)
	{
		this.authToken = authToken;
		this.body = (BuildRoadJSON)jsonBody;
	}

	/**
	 * 
	 * Updates the CatanModel to reflect the building of a road
	 * 
     * @return CatanModel, return the updated Catan model
     */
    @Override
    public Object execute() {
        return null;
    }
}
