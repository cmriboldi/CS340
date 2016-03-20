package server.command;

import model.CatanModel;
import model.resources.ResourceList;
import server.AuthToken;
import server.exception.ServerException;
import server.facade.IServerFacade;
import shared.communication.JSON.IJavaJSON;
import shared.communication.JSON.MaritimeTradeJSON;
import shared.definitions.ResourceType;
import shared.exceptions.resources.NotEnoughBankResourcesException;
import shared.exceptions.resources.NotEnoughPlayerResourcesException;

public class MaritimeTradeCommand implements ICommand {

	private AuthToken authToken = null;
	private MaritimeTradeJSON body = null;
	private final IServerFacade facade;
	
	public MaritimeTradeCommand(AuthToken authToken, IJavaJSON jsonBody, IServerFacade facade)
	{
		this.authToken = authToken;
		this.body = (MaritimeTradeJSON)jsonBody;
		this.facade = facade;
	}

	/**
	 * 
	 * Updates the CatanModel to reflect desired exchange of resources by a player
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
			rs.addResource(ResourceType.toEnum(this.body.getOutputResource()), 1);
			rs.removeResource(ResourceType.toEnum(this.body.getInputResource()), this.body.getRatio());
			
			cm.resourceManager.tradeWithBank(rs, this.body.getPlayerIndex());
			
			facade.updateGame(authToken, cm);
			
		} catch (ServerException | NotEnoughBankResourcesException | NotEnoughPlayerResourcesException e)
		{
			e.printStackTrace();
		}
		return cm;
	}

}
