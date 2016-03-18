package server.command;

import model.CatanModel;
import server.AuthToken;
import server.exception.ServerException;
import server.facade.IServerFacade;
import shared.communication.JSON.IJavaJSON;
import shared.communication.JSON.RoadBuildingJSON;
import shared.definitions.DevCardType;
import shared.exceptions.development.NotEnoughDevCardsException;

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
    	CatanModel cm = null;
		try
		{
			cm = facade.getGameModel(authToken);
			
			cm.cardManager.playDevCard(DevCardType.ROAD_BUILD, this.body.getPlayerIndex());
			cm.mapManager.placeRoad(this.body.getSpot1().getEdgeLocation(), this.body.getPlayerIndex());
			cm.mapManager.placeRoad(this.body.getSpot2().getEdgeLocation(), this.body.getPlayerIndex());
			
			facade.updateGame(authToken, cm);
			
		} catch (ServerException | NotEnoughDevCardsException e)
		{
			e.printStackTrace();
		}
		return cm;
    }
}
