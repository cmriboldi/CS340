package server.command;

import server.AuthToken;
import server.facade.IServerFacade;
import shared.communication.JSON.IJavaJSON;
import shared.communication.JSON.SendChatJSON;

public class SendChatCommand implements ICommand {

	private AuthToken authToken = null;
	private SendChatJSON body = null;
	private final IServerFacade facade;
	
	public SendChatCommand(AuthToken authToken, IJavaJSON jsonBody, IServerFacade facade)
	{
		this.authToken = authToken;
		this.body = (SendChatJSON)jsonBody;
		this.facade = facade;
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
