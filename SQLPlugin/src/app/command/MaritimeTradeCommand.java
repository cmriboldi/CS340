package app.command;


import app.communication.IJavaJSON;
import app.communication.MaritimeTradeJSON;
import app.definitions.ResourceType;
import app.exception.NotEnoughBankResourcesException;
import app.exception.NotEnoughPlayerResourcesException;
import app.exception.ServerException;
import app.model.CatanModel;
import app.model.resources.ResourceList;
import app.server.AuthToken;
import app.server.IServerFacade;

public class MaritimeTradeCommand extends ICommand {


	private final IServerFacade facade;
	
	public MaritimeTradeCommand(AuthToken authToken, IJavaJSON jsonBody, IServerFacade facade)
	{
		super(authToken, jsonBody);
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
			rs.addResource(ResourceType.toEnum(((MaritimeTradeJSON)body).getOutputResource()), 1);
			rs.removeResource(ResourceType.toEnum(((MaritimeTradeJSON)body).getInputResource()), ((MaritimeTradeJSON)body).getRatio());
			
			cm.resourceManager.tradeWithBank(rs, ((MaritimeTradeJSON)body).getPlayerIndex());
			

			

			
		} catch (ServerException | NotEnoughBankResourcesException | NotEnoughPlayerResourcesException e)
		{
			e.printStackTrace();
		}
		return cm;
	}

}
