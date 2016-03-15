package server.command;

import server.AuthToken;
import shared.communication.JSON.IJavaJSON;
import shared.communication.JSON.YearOfPlentyJSON;

public class YearOfPlentyCommand implements ICommand {

	private AuthToken authToken = null;
	private YearOfPlentyJSON body = null;
	
	public YearOfPlentyCommand(AuthToken authToken, IJavaJSON jsonBody)
	{
		this.authToken = authToken;
		this.body = (YearOfPlentyJSON)jsonBody;
	}

	/**
	 * 
	 * Updates the CatanModel to reflect using a 'year of plenty' card
	 * 
     * @return CatanModel, return the updated Catan model
     */
	@Override
	public Object execute() {
		// TODO Auto-generated method stub
		return null;
	}

}
