package server.command;

import server.AuthToken;
import shared.communication.JSON.IJavaJSON;

public class SendChatCommand implements ICommand {

	public SendChatCommand(AuthToken authToken, IJavaJSON jsonBody)
	{
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * Updates the CatanModel to reflect sending a chat message
	 * 
     * @return CatanModel, return the updated Catan model
     */
	@Override
	public Object execute() {
		// TODO Auto-generated method stub
		return null;
	}

}
