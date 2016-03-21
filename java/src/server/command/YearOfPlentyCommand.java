package server.command;

import model.CatanModel;
import model.resources.ResourceList;
import server.AuthToken;
import server.exception.ServerException;
import server.facade.IServerFacade;
import shared.communication.JSON.IJavaJSON;
import shared.communication.JSON.YearOfPlentyJSON;
import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import shared.exceptions.development.NotEnoughDevCardsException;
import shared.exceptions.resources.InvalidNumberOfResourcesRequested;
import shared.exceptions.resources.NotEnoughBankResourcesException;

public class YearOfPlentyCommand implements ICommand {

	private AuthToken authToken = null;
	private YearOfPlentyJSON body = null;
	private final IServerFacade facade;
	
	public YearOfPlentyCommand(AuthToken authToken, IJavaJSON jsonBody, IServerFacade facade)
	{
		this.authToken = authToken;
		this.body = (YearOfPlentyJSON)jsonBody;
		this.facade = facade;
	}

	/**
	 * 
	 * Updates the CatanModel to reflect using a 'year of plenty' card
	 * 
     * @return CatanModel, return the updated Catan model
     */
	@Override
	public Object execute() {
		CatanModel cm = null;
		try
		{
			cm = facade.getGameModel(authToken);
			
			ResourceList rs = new ResourceList();
			rs.addResource(ResourceType.toEnum(this.body.getResource1()), 1);
			rs.addResource(ResourceType.toEnum(this.body.getResource2()), 1);
			
			cm.resourceManager.useYearOfPlentyCard(this.body.getPlayerIndex(), rs);
			
			cm.cardManager.playDevCard(DevCardType.YEAR_OF_PLENTY, this.body.getPlayerIndex());
			
			cm.chatManager.logAction(cm.playerManager.getPlayerName(this.body.getPlayerIndex()) + " played a year of plenty card.", cm.playerManager.getPlayerName(this.body.getPlayerIndex()));
			
			facade.updateGame(authToken, cm);
			
			facade.recordCommand(authToken, this);
			
		} catch (ServerException | NotEnoughBankResourcesException | InvalidNumberOfResourcesRequested | NotEnoughDevCardsException e)
		{
			e.printStackTrace();
		}
		return cm;
	}

}
