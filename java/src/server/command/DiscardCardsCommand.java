package server.command;

import model.CatanModel;
import server.AuthToken;
import server.exception.ServerException;
import server.facade.IServerFacade;
import shared.communication.JSON.DiscardCardsJSON;
import shared.communication.JSON.IJavaJSON;
import shared.exceptions.resources.NotEnoughResourcesException;

public class DiscardCardsCommand implements ICommand {

	private AuthToken authToken = null;
	private DiscardCardsJSON body = null;
	private final IServerFacade facade;
	
	public DiscardCardsCommand(AuthToken authToken, IJavaJSON jsonBody, IServerFacade facade)
	{
		this.authToken = authToken;
		this.body = (DiscardCardsJSON)jsonBody;
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
			
			cm.resourceManager.discardCards(this.body.getDiscardedCards(), this.body.getPlayerIndex());
			
			facade.updateGame(authToken, cm);
			
		} catch (ServerException | NotEnoughResourcesException e)
		{
			e.printStackTrace();
		}
		return cm;
	}

}
