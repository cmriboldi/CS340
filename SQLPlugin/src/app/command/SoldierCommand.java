package app.command;

import app.communication.IJavaJSON;
import app.communication.SoldierJSON;
import app.definitions.DevCardType;
import app.exception.NotEnoughDevCardsException;
import app.exception.NotEnoughResourcesException;
import app.exception.ServerException;
import app.model.CatanModel;
import app.server.AuthToken;
import app.server.IServerFacade;

public class SoldierCommand extends ICommand {


	private final IServerFacade facade;
	
	public SoldierCommand(AuthToken authToken, IJavaJSON jsonBody, IServerFacade facade)
	{
		super(authToken, jsonBody);
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
			
			if(((SoldierJSON)body).getVictimIndex() > -1 && ((SoldierJSON)body).getVictimIndex() < 4) {
				cm.resourceManager.robPlayer(((SoldierJSON)body).getVictimIndex(), ((SoldierJSON)body).getPlayerIndex());
				cm.chatManager.logAction(cm.playerManager.getPlayerName(((SoldierJSON)body).getPlayerIndex()) + " robbed a player.", cm.playerManager.getPlayerName(((SoldierJSON)body).getPlayerIndex()));
			}
			
			cm.getMapManager().placeRobber(((SoldierJSON)body).getHexLocation());
			cm.cardManager.playDevCard(DevCardType.SOLDIER, ((SoldierJSON)body).getPlayerIndex());
			cm.cardManager.setHasPlayedDevCard(((SoldierJSON)body).getPlayerIndex(), true);
			
			if(cm.playerManager.getIndexOfLargestArmy() != ((SoldierJSON)body).getPlayerIndex() && cm.cardManager.getIndexOfLargestArmy() == ((SoldierJSON)body).getPlayerIndex()) {
				cm.playerManager.setNewLargestArmy(((SoldierJSON)body).getPlayerIndex());
			}
			
			cm.chatManager.logAction(cm.playerManager.getPlayerName(((SoldierJSON)body).getPlayerIndex()) + " played a soldier card.", cm.playerManager.getPlayerName(((SoldierJSON)body).getPlayerIndex()));
			

			

			
		} catch (ServerException | NotEnoughDevCardsException | NotEnoughResourcesException e)
		{
			e.printStackTrace();
		}
		return cm;
	}

}
