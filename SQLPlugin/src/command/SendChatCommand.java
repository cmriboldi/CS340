package command;

import communication.IJavaJSON;
import communication.SendChatJSON;
import exception.ServerException;
import model.CatanModel;
import server.AuthToken;
import server.facade.IServerFacade;

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
