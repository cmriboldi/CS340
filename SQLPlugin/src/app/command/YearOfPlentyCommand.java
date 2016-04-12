package app.command;

import app.communication.IJavaJSON;
import app.communication.YearOfPlentyJSON;
import app.definitions.DevCardType;
import app.definitions.ResourceType;
import app.exception.InvalidNumberOfResourcesRequested;
import app.exception.NotEnoughBankResourcesException;
import app.exception.NotEnoughDevCardsException;
import app.exception.ServerException;
import app.model.CatanModel;
import app.model.resources.ResourceList;
import app.server.AuthToken;
import app.server.IServerFacade;

public class YearOfPlentyCommand extends ICommand {


	private final IServerFacade facade;
	
	public YearOfPlentyCommand(AuthToken authToken, IJavaJSON jsonBody, IServerFacade facade)
	{
		super(authToken, jsonBody);
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
			rs.addResource(ResourceType.toEnum(((YearOfPlentyJSON)body).getResource1()), 1);
			rs.addResource(ResourceType.toEnum(((YearOfPlentyJSON)body).getResource2()), 1);
			
			cm.resourceManager.useYearOfPlentyCard(((YearOfPlentyJSON)body).getPlayerIndex(), rs);
			cm.cardManager.setHasPlayedDevCard(((YearOfPlentyJSON)body).getPlayerIndex(), true);
			
			cm.cardManager.playDevCard(DevCardType.YEAR_OF_PLENTY, ((YearOfPlentyJSON)body).getPlayerIndex());
			
			cm.chatManager.logAction(cm.playerManager.getPlayerName(((YearOfPlentyJSON)body).getPlayerIndex()) + " played a year of plenty card.", cm.playerManager.getPlayerName(((YearOfPlentyJSON)body).getPlayerIndex()));
			

			

			
		} catch (ServerException | NotEnoughBankResourcesException | InvalidNumberOfResourcesRequested | NotEnoughDevCardsException e)
		{
			e.printStackTrace();
		}
		return cm;
	}

}
