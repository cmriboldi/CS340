package server.command;

import model.CatanModel;
import server.AuthToken;
import server.exception.ServerException;
import server.facade.IServerFacade;
import shared.communication.JSON.IJavaJSON;
import shared.communication.JSON.MonumentJSON;
import shared.definitions.DevCardType;
import shared.exceptions.development.NotEnoughDevCardsException;

public class MonumentCommand extends ICommand {


	private MonumentJSON body = null;
	private final IServerFacade facade;
	
	public MonumentCommand(AuthToken authToken, IJavaJSON jsonBody, IServerFacade facade)
	{
		super(authToken);
		this.body = (MonumentJSON)jsonBody;
		this.facade = facade;
	}

	/**
	 * 
	 * Updates the CatanModel to reflect the playing of a Monument card
	 * 
     * @return CatanModel, return the updated Catan model
     */
	@Override
	public Object execute() {
		
		CatanModel cm = null;
		try
		{
			cm = facade.getGameModel(authToken);
			
			cm.cardManager.playDevCard(DevCardType.MONUMENT, this.body.getPlayerIndex());
			cm.playerManager.incrementPlayerPoints(this.body.getPlayerIndex());
			
			cm.chatManager.logAction(cm.playerManager.getPlayerName(this.body.getPlayerIndex()) + " played a monument card.", cm.playerManager.getPlayerName(this.body.getPlayerIndex()));
			

			

			
		} catch (ServerException | NotEnoughDevCardsException e)
		{
			e.printStackTrace();
		}
		return cm;
	}

}
