package command;

import communication.IJavaJSON;
import communication.MonumentJSON;
import definitions.DevCardType;
import exception.NotEnoughDevCardsException;
import exception.ServerException;
import model.CatanModel;
import server.AuthToken;
import server.facade.IServerFacade;

public class MonumentCommand extends ICommand {


	private final IServerFacade facade;
	
	public MonumentCommand(AuthToken authToken, IJavaJSON jsonBody, IServerFacade facade)
	{
		super(authToken, jsonBody);
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
			
			cm.cardManager.playDevCard(DevCardType.MONUMENT, ((MonumentJSON)body).getPlayerIndex());
			cm.playerManager.incrementPlayerPoints(((MonumentJSON)body).getPlayerIndex());
			
			cm.chatManager.logAction(cm.playerManager.getPlayerName(((MonumentJSON)body).getPlayerIndex()) + " played a monument card.", cm.playerManager.getPlayerName(((MonumentJSON)body).getPlayerIndex()));
			

			

			
		} catch (ServerException | NotEnoughDevCardsException e)
		{
			e.printStackTrace();
		}
		return cm;
	}

}
