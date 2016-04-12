package app.command;


import app.communication.DiscardCardsJSON;
import app.communication.IJavaJSON;
import app.definitions.TurnType;
import app.exception.NotEnoughResourcesException;
import app.exception.ServerException;
import app.model.CatanModel;
import app.server.AuthToken;
import app.server.IServerFacade;

public class DiscardCardsCommand extends ICommand {


	private final IServerFacade facade;
	
	public DiscardCardsCommand(AuthToken authToken, IJavaJSON jsonBody, IServerFacade facade)
	{
		super(authToken, jsonBody);
		this.facade = facade;
	}

	/**
	 * 
	 * Updates the CatanModel to reflect the new resource card amounts after discarding
	 * 
     * @return CatanModel, return the updated Catan model
     */
	@Override
	public Object execute() {
		CatanModel cm = null;
		try
		{
			cm = facade.getGameModel(authToken);
			
			cm.resourceManager.discardCards(((DiscardCardsJSON)body).getDiscardedCards(), ((DiscardCardsJSON)body).getPlayerIndex());
			if(cm.resourceManager.havePlayersDiscarded()) {
				cm.playerManager.setTurnStatus(TurnType.ROBBING);
			}
			
			cm.chatManager.logAction(cm.playerManager.getPlayerName(((DiscardCardsJSON)body).getPlayerIndex()) + " discarded cards.", cm.playerManager.getPlayerName(((DiscardCardsJSON)body).getPlayerIndex()));
			

			

			
		} catch (ServerException | NotEnoughResourcesException e)
		{
			e.printStackTrace();
		}
		return cm;
	}

}
