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

public class BuyDevCardCommand extends ICommand {


	private BuyDevCardJSON body = null;
	private DevCardType boughtDevCard = null;

	private final IServerFacade facade;

	public BuyDevCardCommand(AuthToken authToken, IJavaJSON jsonBody, IServerFacade facade_p)
	{
		super(authToken);
		this.facade = facade_p;
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
			
			//The reason we are checking if this devCard is null is because we are planning on saving the commandObject in a list to be executed later.
			if(boughtDevCard == null) {
				boughtDevCard = cm.cardManager.drawCard(body.getPlayerIndex());
			} else {
				cm.cardManager.addDevCard(boughtDevCard, body.getPlayerIndex());
			}
			cm.resourceManager.buyDevCard(body.getPlayerIndex());
			
			cm.chatManager.logAction(cm.playerManager.getPlayerName(this.body.getPlayerIndex()) + " bought a development card.", cm.playerManager.getPlayerName(this.body.getPlayerIndex()));
			

			

			
		} catch (ServerException | NotEnoughDevCardsException | NotEnoughPlayerResourcesException | NotEnoughResourcesException e)
		{
			e.printStackTrace();
		}
		return cm;
	}

}
