package server.command;

import model.CatanModel;
import server.AuthToken;
import server.exception.ServerException;
import server.facade.IServerFacade;
import shared.communication.JSON.IJavaJSON;
import shared.communication.JSON.SoldierJSON;
import shared.definitions.DevCardType;
import shared.exceptions.development.NotEnoughDevCardsException;

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
			
			cm.cardManager.playDevCard(DevCardType.SOLDIER, this.body.getPlayerIndex());
			
			facade.updateGame(authToken, cm);
			
		} catch (ServerException | NotEnoughDevCardsException e)
		{
			e.printStackTrace();
		}
		return cm;
	}

}
