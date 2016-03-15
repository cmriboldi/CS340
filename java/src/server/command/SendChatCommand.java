package server.command;

import server.AuthToken;
import shared.communication.JSON.IJavaJSON;
import shared.communication.JSON.SendChatJSON;

public class SendChatCommand implements ICommand {

	private AuthToken authToken = null;
	private SendChatJSON body = null;
	
	public SendChatCommand(AuthToken authToken, IJavaJSON jsonBody)
	{
		this.authToken = authToken;
		this.body = (SendChatJSON)jsonBody;
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
		return "This is a test of awesomeness";
	}

}
