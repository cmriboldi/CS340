package server.command;

import server.AuthToken;
import shared.communication.JSON.IJavaJSON;

public class RoadBuildingCommand implements ICommand
{
    public RoadBuildingCommand(AuthToken authToken, IJavaJSON jsonBody)
	{
		// TODO Auto-generated constructor stub
	}

	/**
     *
     * Updates the CatanModel to reflect a player using a Road Building Dev Card
     *
     * @return CatanModel, return the updated Catan model
     */
    @Override
    public Object execute() {
        return null;
    }
}
