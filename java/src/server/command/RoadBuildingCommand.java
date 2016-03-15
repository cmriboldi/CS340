package server.command;

import server.AuthToken;
import shared.communication.JSON.IJavaJSON;
import shared.communication.JSON.RoadBuildingJSON;

public class RoadBuildingCommand implements ICommand
{

	private AuthToken authToken = null;
	private RoadBuildingJSON body = null;
	
	public RoadBuildingCommand(AuthToken authToken, IJavaJSON jsonBody)
	{
		this.authToken = authToken;
		this.body = (RoadBuildingJSON)jsonBody;
	}

	/**
     *
     * Updates the CatanModel to reflect a player using a Road Building Dev Card
     * @return CatanModel, return the updated Catan model
     */
    @Override
    public Object execute() {
        return null;
    }
}
