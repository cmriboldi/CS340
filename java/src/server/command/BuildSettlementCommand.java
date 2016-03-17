package server.command;

import server.AuthToken;
import server.facade.IServerFacade;
import shared.communication.JSON.BuildSettlementJSON;
import shared.communication.JSON.IJavaJSON;

public class BuildSettlementCommand implements ICommand {

	private AuthToken authToken = null;
	private BuildSettlementJSON body = null;
	private final IServerFacade facade;
	
	public BuildSettlementCommand(AuthToken authToken, IJavaJSON jsonBody, IServerFacade facade)
	{
		this.authToken = authToken;
		this.body = (BuildSettlementJSON)jsonBody;
		this.facade = facade;
	}

	/**
	 * 
	 * Updates the CatanModel to reflect the building of a settlement
	 * 
     * @return CatanModel, return the updated Catan model
     */
	@Override
	public Object execute() {
		// TODO Auto-generated method stub
		return null;
	}

}
