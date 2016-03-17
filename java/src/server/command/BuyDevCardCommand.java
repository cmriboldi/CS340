package server.command;

import model.CatanModel;
import server.AuthToken;
import server.exception.ServerException;
import server.facade.IServerFacade;
import shared.communication.JSON.BuyDevCardJSON;
import shared.communication.JSON.IJavaJSON;
import shared.definitions.DevCardType;
import shared.exceptions.development.NotEnoughDevCardsException;
import shared.exceptions.resources.NotEnoughPlayerResourcesException;
import shared.exceptions.resources.NotEnoughResourcesException;

public class BuyDevCardCommand implements ICommand {

	private AuthToken authToken = null;
	private BuyDevCardJSON body = null;
	private DevCardType boughtDevCard = null;

	private final IServerFacade facade;

	public BuyDevCardCommand(AuthToken authToken, IJavaJSON jsonBody, IServerFacade facade_p)
	{
		this.facade = facade_p;
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
		CatanModel cm = null;
		try
		{
			cm = facade.getGameModel(authToken);
			if(boughtDevCard == null) {
				boughtDevCard = cm.cardManager.drawCard(body.getPlayerIndex());
			} else {
				cm.cardManager.addDevCard(boughtDevCard, body.getPlayerIndex());
			}
			cm.resourceManager.buyDevCard(body.getPlayerIndex());
			
		} catch (ServerException | NotEnoughDevCardsException | NotEnoughPlayerResourcesException | NotEnoughResourcesException e)
		{
			e.printStackTrace();
		}
		
		if(cm != null) {
			return "This is working";
		} else {
			return "This call didn't work.";
		}
		
	}

}
