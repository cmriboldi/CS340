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

public class MonopolyCommand implements ICommand {

	private AuthToken authToken = null;
	private MonopolyJSON body = null;
	private final IServerFacade facade;
	
	public MonopolyCommand(AuthToken authToken, IJavaJSON jsonBody, IServerFacade facade)
	{
		this.authToken = authToken;
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
			cm.resourceManager.useMonopolyCard(this.body.getPlayerIndex(), ResourceType.toEnum(this.body.getResource()));
			
			cm.chatManager.logAction(cm.playerManager.getPlayerName(this.body.getPlayerIndex()) + " played a monopoly card.", cm.playerManager.getPlayerName(this.body.getPlayerIndex()));
			
			facade.updateGame(authToken, cm);
			
			facade.recordCommand(authToken, this);
			
		} catch (ServerException | NotEnoughDevCardsException e)
		{
			e.printStackTrace();
		}
		return cm;
	}
}
