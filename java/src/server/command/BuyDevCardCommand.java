package server.command;

import model.CatanModel;
import server.AuthToken;
import server.exception.ServerException;
import server.facade.FacadeHolder;
import server.facade.IServerFacade;
import shared.communication.JSON.BuyDevCardJSON;
import shared.communication.JSON.IJavaJSON;

public class BuyDevCardCommand implements ICommand {

	private AuthToken authToken = null;
	private BuyDevCardJSON body = null;
	
	public BuyDevCardCommand(AuthToken authToken, IJavaJSON jsonBody)
	{
		this.authToken = authToken;
		this.body = (BuyDevCardJSON)jsonBody;
	}

	/**
	 * 
	 * Updates the CatanModel to reflect buying a dev card
	 * 
     * @return CatanModel, return the updated Catan model
     */
	@Override
	public Object execute() {
		IServerFacade facade;
		CatanModel cm = null;
		try
		{
			facade = FacadeHolder.getFacade();
			cm = facade.getGameModel(authToken);
		} catch (ServerException e)
		{
			e.printStackTrace();
		}
		
		return null;
	}

}
