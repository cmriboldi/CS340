package server.command;

import model.CatanModel;
import server.AuthToken;
import server.exception.ServerException;
import server.facade.IServerFacade;
import shared.communication.JSON.BuildRoadJSON;
import shared.communication.JSON.IJavaJSON;
import shared.definitions.DevCardType;
import shared.definitions.TurnType;
import shared.exceptions.development.NotEnoughDevCardsException;
import shared.exceptions.player.TurnIndexException;

/**
 * Created by clayt on 3/9/2016.
 */
public class BuildRoadCommand implements ICommand {

	private AuthToken authToken = null;
	private BuildRoadJSON body = null;
	private final IServerFacade facade;
	
	public BuildRoadCommand(AuthToken authToken, IJavaJSON jsonBody, IServerFacade facade)
	{
		this.authToken = authToken;
		this.body = (BuildRoadJSON)jsonBody;
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
			cm.mapManager.placeRoad(body.getRoadLocation().getEdgeLocation(), body.getPlayerIndex());
			
			if(cm.playerManager.getTurnStatus() == TurnType.FIRST_ROUND) {
				if(body.getPlayerIndex() == 3) {
					cm.playerManager.setTurnStatus(TurnType.SECOND_ROUND);
				}
				cm.playerManager.advanceTurn();
			} else if (cm.playerManager.getTurnStatus() == TurnType.SECOND_ROUND) {
				if(body.getPlayerIndex() == 3) {
					cm.playerManager.setTurnStatus(TurnType.ROLLING);
				}
				cm.playerManager.advanceTurn();
			}
			
			facade.updateGame(authToken, cm);
			
		} catch (ServerException | TurnIndexException e)
		{
			e.printStackTrace();
		}
    	
		return cm;
    }
}