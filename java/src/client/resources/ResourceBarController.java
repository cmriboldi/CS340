package client.resources;

import java.util.*;

import client.base.*;
import clientfacade.Facade;
import model.resources.ResourceList;
import shared.definitions.PieceType;
import shared.definitions.ResourceType;


/**
 * Implementation for the resource bar controller
 */
public class ResourceBarController extends Controller implements IResourceBarController, Observer {

	private Map<ResourceBarElement, IAction> elementActions;
	
	public ResourceBarController(IResourceBarView view) {

		super(view);
		elementActions = new HashMap<ResourceBarElement, IAction>();
		Facade.addObserverStatic(this);
	}

	@Override
	public IResourceBarView getView() {
		return (IResourceBarView)super.getView();
	}

	/**
	 * Sets the action to be executed when the specified resource bar element is clicked by the user
	 * 
	 * @param element The resource bar element with which the action is associated
	 * @param action The action to be executed
	 */
	public void setElementAction(ResourceBarElement element, IAction action) {

		elementActions.put(element, action);
	}

	@Override
	public void buildRoad() {
		
		executeElementAction(ResourceBarElement.ROAD);
	}

	@Override
	public void buildSettlement() {
		executeElementAction(ResourceBarElement.SETTLEMENT);
	}

	@Override
	public void buildCity() {
		executeElementAction(ResourceBarElement.CITY);
	}

	@Override
	public void buyCard() {
		executeElementAction(ResourceBarElement.BUY_CARD);
	}

	@Override
	public void playCard() {
		executeElementAction(ResourceBarElement.PLAY_CARD);
	}
	
	private void executeElementAction(ResourceBarElement element) {
		
		if (elementActions.containsKey(element)) {
			
			IAction action = elementActions.get(element);
			action.execute();
		}
	}

	@Override
	public void update(Observable o, Object arg)
	{
		if(!Facade.hasGameStarted()) {
			return;
		}
	
		boolean gameOver = Facade.gameOver(); 
		
		

		int playerIndex = Facade.getLocalPlayerIndex();
		ResourceList rs = Facade.getCatanModel().resourceManager.getResourcesForPlayer(playerIndex);
		
		getView().setElementAmount(ResourceBarElement.BRICK, rs.getResourceTypeCount(ResourceType.BRICK));
		getView().setElementAmount(ResourceBarElement.ORE, rs.getResourceTypeCount(ResourceType.ORE));
		getView().setElementAmount(ResourceBarElement.SHEEP, rs.getResourceTypeCount(ResourceType.SHEEP));
		getView().setElementAmount(ResourceBarElement.WHEAT, rs.getResourceTypeCount(ResourceType.WHEAT));
		getView().setElementAmount(ResourceBarElement.WOOD, rs.getResourceTypeCount(ResourceType.WOOD));

		int localIndex = Facade.getLocalPlayerIndex();

		if(localIndex == -1)
			return;

		if(Facade.getCatanModel().getOptions().canAffordRoad(localIndex) && Facade.hasPiece(PieceType.ROAD) && !gameOver) {
			getView().setElementEnabled(ResourceBarElement.ROAD, true);
		}			
		else {
			getView().setElementEnabled(ResourceBarElement.ROAD, false);
		}
			


		if(Facade.getCatanModel().getOptions().canAffordCity(localIndex) && Facade.hasPiece(PieceType.CITY) && !gameOver) {
			getView().setElementEnabled(ResourceBarElement.CITY, true);
		}	
		else {
			getView().setElementEnabled(ResourceBarElement.CITY, false);
		}


		if(Facade.getCatanModel().getOptions().canAffordTown(localIndex) && Facade.hasPiece(PieceType.SETTLEMENT) && !gameOver) {
			getView().setElementEnabled(ResourceBarElement.SETTLEMENT, true);
		}			
		else {
			getView().setElementEnabled(ResourceBarElement.SETTLEMENT, false);
		}
			


		getView().setElementAmount(ResourceBarElement.ROAD, Facade.getCatanModel().getPlayerManager().getPlayerByIndex(localIndex).getRoadsRemaining());
		getView().setElementAmount(ResourceBarElement.SETTLEMENT, Facade.getCatanModel().getPlayerManager().getPlayerByIndex(localIndex).getSettlementsRemaining());
		getView().setElementAmount(ResourceBarElement.CITY, Facade.getCatanModel().getPlayerManager().getPlayerByIndex(localIndex).getCitiesRemaining());

		if(Facade.getCatanModel().options.canAffordDevCard(Facade.getLocalPlayerIndex()) && Facade.isMyturn() && Facade.hasDevCards() && !gameOver) {
			getView().setElementEnabled(ResourceBarElement.BUY_CARD, true);
		}	
		else {
			getView().setElementEnabled(ResourceBarElement.BUY_CARD, false);
		}
		
		if(!Facade.hasPlayedDevCard() && Facade.isMyturn() && !gameOver) {
			getView().setElementEnabled(ResourceBarElement.PLAY_CARD, true);
		}
		else {
			getView().setElementEnabled(ResourceBarElement.PLAY_CARD, false);
		}
			
		int soldier = Facade.getSoldierCount();
		getView().setElementAmount(ResourceBarElement.SOLDIERS, soldier);

		
	}

}

