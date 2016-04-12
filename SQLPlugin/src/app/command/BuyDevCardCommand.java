package app.command;

import app.communication.BuyDevCardJSON;
import app.communication.IJavaJSON;
import app.definitions.DevCardType;
import app.exception.NotEnoughDevCardsException;
import app.exception.NotEnoughPlayerResourcesException;
import app.exception.NotEnoughResourcesException;
import app.exception.ServerException;
import app.model.CatanModel;
import app.server.AuthToken;
import app.server.IServerFacade;

public class BuyDevCardCommand extends ICommand {


	private DevCardType boughtDevCard = null;

	private final IServerFacade facade;

	public BuyDevCardCommand(AuthToken authToken, IJavaJSON jsonBody, IServerFacade facade_p)
	{
		super(authToken, jsonBody);
		this.facade = facade_p;
		body = (BuyDevCardJSON)jsonBody;
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
			
			//The reason we are checking if this devCard is null is because we are planning on saving the commandObject in a list to be executed later.
			if(boughtDevCard == null) {
				boughtDevCard = cm.cardManager.drawCard(((BuyDevCardJSON)body).getPlayerIndex());
			} else {
				cm.cardManager.addDevCard(boughtDevCard, ((BuyDevCardJSON)body).getPlayerIndex());
			}
			cm.resourceManager.buyDevCard(((BuyDevCardJSON)body).getPlayerIndex());
			
			cm.chatManager.logAction(cm.playerManager.getPlayerName(((BuyDevCardJSON)body).getPlayerIndex()) + " bought a development card.", cm.playerManager.getPlayerName(((BuyDevCardJSON)body).getPlayerIndex()));
			

			

			
		} catch (ServerException | NotEnoughDevCardsException | NotEnoughPlayerResourcesException | NotEnoughResourcesException e)
		{
			e.printStackTrace();
		}
		return cm;
	}

}
