package command;


import shared.communication.JSON.BuildRoadJSON;
import shared.communication.JSON.IJavaJSON;
import definitions.PieceType;
import definitions.TurnType;
import model.CatanModel;
import model.resources.ResourceList;
import server.AuthToken;
import server.exception.ServerException;
import server.facade.IServerFacade;
import shared.exceptions.player.TurnIndexException;
import shared.exceptions.resources.InvalidPieceTypeException;
import shared.exceptions.resources.NotEnoughPlayerResourcesException;
import shared.exceptions.resources.NotEnoughResourcesException;

/**
 * Created by clayt on 3/9/2016.
 */
public class BuildRoadCommand extends ICommand {


	private final IServerFacade facade;
	
	public BuildRoadCommand(AuthToken authToken, IJavaJSON jsonBody, IServerFacade facade)
	{
		super(authToken, jsonBody);
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
			cm.mapManager.placeRoad(((BuildRoadJSON)body).getRoadLocation().getEdgeLocation(), ((BuildRoadJSON)body).getPlayerIndex());
			
			if(cm.playerManager.getTurnStatus() == TurnType.FIRST_ROUND) {
				if(((BuildRoadJSON)body).getPlayerIndex() == 3) {
					cm.playerManager.setTurnStatus(TurnType.SECOND_ROUND);
				} else {
					cm.playerManager.advanceTurn();
				}
			} else if (cm.playerManager.getTurnStatus() == TurnType.SECOND_ROUND) {
				if(((BuildRoadJSON)body).getPlayerIndex() == 0) {
					cm.playerManager.setTurnStatus(TurnType.ROLLING);
					ResourceList[] resLists = cm.mapManager.distributeSetupResources();
					cm.resourceManager.payOutResources(resLists);
				} else {
					cm.playerManager.reverseAdvanceTurn();
				}
			} else {
				cm.resourceManager.buyPiece(((BuildRoadJSON)body).getPlayerIndex(), PieceType.ROAD);
			}
			
			cm.playerManager.decrementPieceCount(((BuildRoadJSON)body).getPlayerIndex(), PieceType.ROAD);
			
			if(cm.playerManager.getIndexOfLongestRoad() != ((BuildRoadJSON)body).getPlayerIndex() && cm.mapManager.findLargestRoad() == ((BuildRoadJSON)body).getPlayerIndex()) {
				cm.playerManager.setNewLongestRoad(((BuildRoadJSON)body).getPlayerIndex());
			}
			
			cm.chatManager.logAction(cm.playerManager.getPlayerName(((BuildRoadJSON)body).getPlayerIndex()) + " built a road.", cm.playerManager.getPlayerName(((BuildRoadJSON)body).getPlayerIndex()));
			

			

			
		} catch (ServerException | TurnIndexException | NotEnoughPlayerResourcesException | InvalidPieceTypeException | NotEnoughResourcesException e)
		{
			e.printStackTrace();
		}
    	
		return cm;
    }
}