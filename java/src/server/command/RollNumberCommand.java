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

public class RollNumberCommand implements ICommand {

	private AuthToken authToken = null;
	private RollNumberJSON body = null;
	private final IServerFacade facade;
	
	public RollNumberCommand(AuthToken authToken, IJavaJSON jsonBody, IServerFacade facade)
	{
		this.authToken = authToken;
		this.body = (RollNumberJSON)jsonBody;
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
			
			if(this.body.getNumber() == 7) {
				if(cm.resourceManager.getGreatestCardCount() > 7) {
					cm.playerManager.setTurnStatus(TurnType.DISCARDING);
					cm.resourceManager.setPlayerDiscard();
				} else {
					cm.playerManager.setTurnStatus(TurnType.ROBBING);
				}
			} else {
				ResourceList[] resLists = cm.mapManager.distributeResources(this.body.getNumber());
				cm.resourceManager.payOutResources(resLists);
			}
			
			facade.updateGame(authToken, cm);
			
		} catch (ServerException | NotEnoughBankResourcesException e)
		{
			e.printStackTrace();
		}
		return cm;
	}

}
