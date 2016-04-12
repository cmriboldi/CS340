package command;


import communication.IJavaJSON;
import communication.MaritimeTradeJSON;
import definitions.ResourceType;
import exception.NotEnoughBankResourcesException;
import exception.NotEnoughPlayerResourcesException;
import exception.ServerException;
import model.CatanModel;
import model.resources.ResourceList;
import server.AuthToken;
import server.facade.IServerFacade;

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
