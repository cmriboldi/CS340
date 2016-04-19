package command;


import shared.communication.JSON.IJavaJSON;
import shared.communication.JSON.RobPlayerJSON;
import definitions.TurnType;
import shared.exceptions.resources.NotEnoughResourcesException;
import server.exception.ServerException;
import shared.locations.HexLocation;
import model.CatanModel;
import server.AuthToken;
import server.facade.IServerFacade;

public class RobPlayerCommand extends ICommand
{

	private final IServerFacade facade;
	
	public RobPlayerCommand(AuthToken authToken, IJavaJSON jsonBody, IServerFacade facade)
	{
		super(authToken, jsonBody);
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
			
			if(((RobPlayerJSON)body).getVictimIndex() > -1 && ((RobPlayerJSON)body).getVictimIndex() < 4) {
				cm.resourceManager.robPlayer(((RobPlayerJSON)body).getVictimIndex(), ((RobPlayerJSON)body).getPlayerIndex());
				cm.chatManager.logAction(cm.playerManager.getPlayerName(((RobPlayerJSON)body).getPlayerIndex()) + " robbed a player.", cm.playerManager.getPlayerName(((RobPlayerJSON)body).getPlayerIndex()));
			}
			cm.playerManager.setTurnStatus(TurnType.PLAYING);

			//Translate from JSONbody into a Java settlement location
			int hexLocX = ((RobPlayerJSON)body).getLocation().getX();
			int hexLocY = ((RobPlayerJSON)body).getLocation().getY();
			HexLocation hexLoc = new HexLocation(hexLocX, hexLocY);

			cm.getMapManager().placeRobber(hexLoc);
			

			

			
		} catch (ServerException | NotEnoughResourcesException e)
		{
			e.printStackTrace();
		}
		return cm;
    }
}
