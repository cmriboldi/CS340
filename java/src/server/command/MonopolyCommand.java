package server.command;

import model.CatanModel;
import server.AuthToken;
import server.exception.ServerException;
import server.facade.IServerFacade;
import shared.communication.JSON.IJavaJSON;
import shared.communication.JSON.MonopolyJSON;
import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import shared.exceptions.development.NotEnoughDevCardsException;

public class MonopolyCommand extends ICommand {


	private MonopolyJSON body = null;
	private final IServerFacade facade;
	
	public MonopolyCommand(AuthToken authToken, IJavaJSON jsonBody, IServerFacade facade)
	{
		super(authToken);
		this.body = (MonopolyJSON)jsonBody;
		this.facade = facade;
	}

	/**
	 * 
	 * Updates the CatanModel to reflect the playing of a Monopoly card
	 * 
     * @return CatanModel, return the updated Catan model
     */
	@Override
	public Object execute() {
		CatanModel cm = null;
		try
		{
			cm = facade.getGameModel(authToken);
			
			cm.cardManager.playDevCard(DevCardType.MONOPOLY, this.body.getPlayerIndex());
			
			ResourceType resource = ResourceType.toEnum(this.body.getResource());
			if(resource != null) {
				cm.resourceManager.useMonopolyCard(this.body.getPlayerIndex(), resource);
				cm.cardManager.setHasPlayedDevCard(this.body.getPlayerIndex(), true);
			}
			
			cm.chatManager.logAction(cm.playerManager.getPlayerName(this.body.getPlayerIndex()) + " played a monopoly card.", cm.playerManager.getPlayerName(this.body.getPlayerIndex()));
			

			

			
		} catch (ServerException | NotEnoughDevCardsException e)
		{
			e.printStackTrace();
		}
		return cm;
	}
}
