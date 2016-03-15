package server.command;

import server.AuthToken;
import shared.communication.JSON.BuildSettlementJSON;
import shared.communication.JSON.IJavaJSON;

public class BuildSettlementCommand implements ICommand {

	private AuthToken authToken = null;
	private BuildSettlementJSON body = null;
	
	public BuildSettlementCommand(AuthToken authToken, IJavaJSON jsonBody)
	{
		this.authToken = authToken;
		this.body = (BuildSettlementJSON)jsonBody;
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
