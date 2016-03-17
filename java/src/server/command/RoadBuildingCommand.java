package server.command;

import server.AuthToken;
import server.facade.IServerFacade;
import shared.communication.JSON.IJavaJSON;
import shared.communication.JSON.RoadBuildingJSON;

public class RoadBuildingCommand implements ICommand
{

	private AuthToken authToken = null;
	private RoadBuildingJSON body = null;
	private final IServerFacade facade;
	
	public RoadBuildingCommand(AuthToken authToken, IJavaJSON jsonBody, IServerFacade facade)
	{
		this.authToken = authToken;
		this.body = (RoadBuildingJSON)jsonBody;
		this.facade = facade;
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
