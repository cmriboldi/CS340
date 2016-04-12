package app.command;

import app.communication.IJavaJSON;
import app.communication.SendChatJSON;
import app.exception.ServerException;
import app.model.CatanModel;
import app.server.AuthToken;
import app.server.IServerFacade;

public class SendChatCommand extends ICommand {


	private final IServerFacade facade;
	
	public SendChatCommand(AuthToken authToken, IJavaJSON jsonBody, IServerFacade facade)
	{
		super(authToken, jsonBody);
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
		CatanModel cm = null;
		try
		{
			cm = facade.getGameModel(authToken);
			
			cm.chatManager.sendMessage(((SendChatJSON)body).getContent(), cm.playerManager.getPlayerName(((SendChatJSON)body).getPlayerIndex()));
			

			

			
		} catch (ServerException e)
		{
			e.printStackTrace();
		}
		return cm;
	}

}
