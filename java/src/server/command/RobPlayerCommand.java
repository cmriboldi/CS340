package server.command;

import model.CatanModel;
import server.AuthToken;
import server.exception.ServerException;
import server.facade.IServerFacade;
import shared.communication.JSON.IJavaJSON;
import shared.communication.JSON.RobPlayerJSON;
import shared.definitions.TurnType;
import shared.exceptions.resources.NotEnoughResourcesException;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

public class RobPlayerCommand implements ICommand
{
	private AuthToken authToken = null;
	private RobPlayerJSON body = null;
	private final IServerFacade facade;
	
	public RobPlayerCommand(AuthToken authToken, IJavaJSON jsonBody, IServerFacade facade)
	{
		this.authToken = authToken;
		this.body = (RobPlayerJSON)jsonBody;
		this.facade = facade;
	}

	/**
	 * 
	 * Updates the CatanModel to reflect robbing a player
	 * 
     * @return CatanModel, return the updated Catan model
     */
    @Override
    public Object execute() {
    	CatanModel cm = null;
		try
		{
			cm = facade.getGameModel(authToken);
			
			if(this.body.getVictimIndex() > -1 && this.body.getVictimIndex() < 4) {
				cm.resourceManager.robPlayer(this.body.getVictimIndex(), this.body.getPlayerIndex());
			}
			cm.playerManager.setTurnStatus(TurnType.PLAYING);

			//Translate from JSONbody into a Java settlement location
			int hexLocX = body.getLocation().getX();
			System.out.println("hexLocX is: " + hexLocX);
			int hexLocY = body.getLocation().getY();
			System.out.println("hexLocY is: " + hexLocY);
			HexLocation hexLoc = new HexLocation(hexLocX, hexLocY);

			cm.getMapManager().placeRobber(hexLoc);
			
			cm.chatManager.logAction(cm.playerManager.getPlayerName(this.body.getPlayerIndex()) + " robbed a player.", cm.playerManager.getPlayerName(this.body.getPlayerIndex()));
			
			facade.updateGame(authToken, cm);
			
			facade.recordCommand(authToken, this);
			
		} catch (ServerException | NotEnoughResourcesException e)
		{
			e.printStackTrace();
		}
		return cm;
    }
}
