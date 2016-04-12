package app.command;

import app.communication.IJavaJSON;
import app.communication.RoadBuildingJSON;
import app.definitions.DevCardType;
import app.definitions.PieceType;
import app.exception.NotEnoughDevCardsException;
import app.exception.ServerException;
import app.model.CatanModel;
import app.server.AuthToken;
import app.server.IServerFacade;

public class RoadBuildingCommand extends ICommand
{


	private final IServerFacade facade;
	
	public RoadBuildingCommand(AuthToken authToken, IJavaJSON jsonBody, IServerFacade facade)
	{
		super(authToken, jsonBody);
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
			
			cm.cardManager.playDevCard(DevCardType.ROAD_BUILD, ((RoadBuildingJSON)body).getPlayerIndex());
			cm.mapManager.placeRoad(((RoadBuildingJSON)body).getSpot1().getEdgeLocation(), ((RoadBuildingJSON)body).getPlayerIndex());
			cm.mapManager.placeRoad(((RoadBuildingJSON)body).getSpot2().getEdgeLocation(), ((RoadBuildingJSON)body).getPlayerIndex());
			cm.playerManager.decrementPieceCount(((RoadBuildingJSON)body).getPlayerIndex(), PieceType.ROAD);
			cm.playerManager.decrementPieceCount(((RoadBuildingJSON)body).getPlayerIndex(), PieceType.ROAD);
			cm.cardManager.setHasPlayedDevCard(((RoadBuildingJSON)body).getPlayerIndex(), true);
			
			cm.chatManager.logAction(cm.playerManager.getPlayerName(((RoadBuildingJSON)body).getPlayerIndex()) + " played a road building card.", cm.playerManager.getPlayerName(((RoadBuildingJSON)body).getPlayerIndex()));
			

			

			
		} catch (ServerException | NotEnoughDevCardsException e)
		{
			e.printStackTrace();
		}
		return cm;
    }
}
