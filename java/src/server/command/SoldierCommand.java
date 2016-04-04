package server.command;

import model.CatanModel;
import server.AuthToken;
import server.exception.ServerException;
import server.facade.IServerFacade;
import shared.communication.JSON.IJavaJSON;
import shared.communication.JSON.SoldierJSON;
import shared.definitions.DevCardType;
import shared.exceptions.development.NotEnoughDevCardsException;
import shared.exceptions.resources.NotEnoughResourcesException;
import shared.locations.HexLocation;

public class SoldierCommand implements ICommand {

	private AuthToken authToken = null;
	private SoldierJSON body = null;
	private final IServerFacade facade;
	
	public SoldierCommand(AuthToken authToken, IJavaJSON jsonBody, IServerFacade facade)
	{
		this.authToken = authToken;
		this.body = (SoldierJSON)jsonBody;
		this.facade = facade;
	}

	/**
	 * 
	 * Updates the CatanModel to reflect the playing of a soldier card
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
				cm.chatManager.logAction(cm.playerManager.getPlayerName(this.body.getPlayerIndex()) + " robbed a player.", cm.playerManager.getPlayerName(this.body.getPlayerIndex()));
			}
			
			cm.getMapManager().placeRobber(this.body.getHexLocation());
			cm.cardManager.playDevCard(DevCardType.SOLDIER, this.body.getPlayerIndex());
			cm.cardManager.setHasPlayedDevCard(this.body.getPlayerIndex(), true);
			
			if(cm.playerManager.getIndexOfLargestArmy() != this.body.getPlayerIndex() && cm.cardManager.getIndexOfLargestArmy() == this.body.getPlayerIndex()) {
				cm.playerManager.setNewLargestArmy(this.body.getPlayerIndex());
			}
			
			cm.chatManager.logAction(cm.playerManager.getPlayerName(this.body.getPlayerIndex()) + " played a soldier card.", cm.playerManager.getPlayerName(this.body.getPlayerIndex()));
			

			

			
		} catch (ServerException | NotEnoughDevCardsException | NotEnoughResourcesException e)
		{
			e.printStackTrace();
		}
		return cm;
	}

}
