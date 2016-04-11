package server.command;

import java.util.ArrayList;

import model.CatanModel;
import model.resources.ResourceList;
import server.AuthToken;
import server.exception.ServerException;
import server.facade.IServerFacade;
import shared.communication.JSON.IJavaJSON;
import shared.communication.JSON.RollNumberJSON;
import shared.definitions.TurnType;
import shared.exceptions.resources.NotEnoughBankResourcesException;

public class RollNumberCommand extends ICommand {


	private final IServerFacade facade;
	
	public RollNumberCommand(AuthToken authToken, IJavaJSON jsonBody, IServerFacade facade)
	{
		super(authToken, jsonBody);
		this.facade = facade;
	}

	/**
	 * 
	 * Updates the CatanModel to reflect rolling a number
	 * 
     * @return CatanModel, return the updated Catan model
     */
	@Override
	public Object execute() {
		CatanModel cm = null;
		try
		{
			cm = facade.getGameModel(authToken);

			if(((RollNumberJSON)body).getNumber() == 7)
			{
				if(cm.resourceManager.getGreatestCardCount() > 7)
				{
					cm.playerManager.setTurnStatus(TurnType.DISCARDING);
					cm.resourceManager.setPlayerDiscard();
				}
				else
				{
					cm.playerManager.setTurnStatus(TurnType.ROBBING);
				}
			}
			else
			{
				ResourceList[] resLists = cm.mapManager.distributeResources(((RollNumberJSON)body).getNumber());
//				for(int i = 0; i < resLists.length; i++) {
//					System.out.println("Player " + i + "'s resources are " + resLists[i].toString());
//				}
				cm.resourceManager.payOutResources(resLists);
				cm.playerManager.setTurnStatus(TurnType.PLAYING);
			}

			cm.chatManager.logAction(cm.playerManager.getPlayerName(((RollNumberJSON)body).getPlayerIndex()) + " rolled a " + ((RollNumberJSON)body).getNumber() + ".", cm.playerManager.getPlayerName(((RollNumberJSON)body).getPlayerIndex()));




		} catch (ServerException e)
		{
			e.printStackTrace();
		}
		return cm;
	}

}
