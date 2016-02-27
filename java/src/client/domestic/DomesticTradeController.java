package client.domestic;

import shared.definitions.*;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import client.base.*;
import client.data.PlayerInfo;
import client.misc.*;
import clientfacade.Facade;


/**
 * Domestic trade controller implementation
 */
public class DomesticTradeController extends Controller implements IDomesticTradeController {

	private IDomesticTradeOverlay tradeOverlay;
	private IWaitView waitOverlay;
	private IAcceptTradeOverlay acceptOverlay;
	private PlayerInfo[] opposingPlayers = null;
	private Map<ResourceType, Integer> sendAmounts;
	private Map<ResourceType, Integer> receiveAmounts;
	private Map<ResourceType, Boolean> sendingResource;
	private int playerToTradeWith;

	/**
	 * DomesticTradeController constructor
	 * 
	 * @param tradeView Domestic trade view (i.e., view that contains the "Domestic Trade" button)
	 * @param tradeOverlay Domestic trade overlay (i.e., view that lets the user propose a domestic trade)
	 * @param waitOverlay Wait overlay used to notify the user they are waiting for another player to accept a trade
	 * @param acceptOverlay Accept trade overlay which lets the user accept or reject a proposed trade
	 */
	public DomesticTradeController(IDomesticTradeView tradeView, IDomesticTradeOverlay tradeOverlay,
									IWaitView waitOverlay, IAcceptTradeOverlay acceptOverlay) {

		super(tradeView);
	
		sendAmounts = new TreeMap<ResourceType, Integer>();
		receiveAmounts = new TreeMap<ResourceType, Integer>();
		sendingResource = new TreeMap<ResourceType, Boolean>();
		playerToTradeWith = -1;
		
		setTradeOverlay(tradeOverlay);
		setWaitOverlay(waitOverlay);
		setAcceptOverlay(acceptOverlay);
	}
	
	private int sum(Collection<Integer> collection) {
		int sum = 0;
		for(Integer i : collection) {
			sum += i;
		}
		return sum;
	}
	
	private void resetView() {
		this.sendAmounts.clear();
		this.receiveAmounts.clear();
		this.sendingResource.clear();
		calculateTradeMessage();
		getTradeOverlay().reset();
	}
	
	private void calculateTradeMessage() {
		String message = "";
		if(sum(this.sendAmounts.values()) > 0 && sum(this.receiveAmounts.values()) > 0) {
			message = "choose with whom you want to trade";
			getTradeOverlay().setStateMessage(message);
			if(playerToTradeWith != -1) {
				message = "Trade!";
				getTradeOverlay().setStateMessage(message);
				getTradeOverlay().setTradeEnabled(true);
			} else {
				getTradeOverlay().setTradeEnabled(false);
			}
		} else {
			message = "select the resources you want to trade";
			getTradeOverlay().setStateMessage(message);
		}
		
	}
	
	public IDomesticTradeView getTradeView() {
		
		return (IDomesticTradeView)super.getView();
	}

	public IDomesticTradeOverlay getTradeOverlay() {
		return tradeOverlay;
	}

	public void setTradeOverlay(IDomesticTradeOverlay tradeOverlay) {
		this.tradeOverlay = tradeOverlay;
	}

	public IWaitView getWaitOverlay() {
		return waitOverlay;
	}

	public void setWaitOverlay(IWaitView waitView) {
		this.waitOverlay = waitView;
	}

	public IAcceptTradeOverlay getAcceptOverlay() {
		return acceptOverlay;
	}

	public void setAcceptOverlay(IAcceptTradeOverlay acceptOverlay) {
		this.acceptOverlay = acceptOverlay;
	}

	@Override
	public void startTrade() {
		calculateTradeMessage();
		
		if(opposingPlayers == null) {
			opposingPlayers = Facade.getCatanModel().playerManager.getOpponentsInfo();
			getTradeOverlay().setPlayers(opposingPlayers);
		}

		getTradeOverlay().unsetResource(ResourceType.BRICK, false);
		getTradeOverlay().unsetResource(ResourceType.ORE, false);
		getTradeOverlay().unsetResource(ResourceType.SHEEP, false);
		getTradeOverlay().unsetResource(ResourceType.WHEAT, false);
		getTradeOverlay().unsetResource(ResourceType.WOOD, false);
		
		getTradeOverlay().showModal();
	}

	@Override
	public void decreaseResourceAmount(ResourceType resource) {
		if(this.sendingResource.get(resource)) {
			this.sendAmounts.put(resource, this.sendAmounts.get(resource) - 1);
			getTradeOverlay().setResourceAmountChangeEnabled(resource, true, this.sendAmounts.get(resource) > 0);
		} else {
			this.receiveAmounts.put(resource, this.receiveAmounts.get(resource) - 1);
			getTradeOverlay().setResourceAmountChangeEnabled(resource, true, this.receiveAmounts.get(resource) > 0);
		}
		
		calculateTradeMessage();
	}

	@Override
	public void increaseResourceAmount(ResourceType resource) {	
		if(this.sendingResource.get(resource)) {
			this.sendAmounts.put(resource, this.sendAmounts.get(resource) + 1);
			
			int localPlayerIndex = Facade.getLocalPlayerIndex();
			int resourceAmount = Facade.getCatanModel().resourceManager.getResourceCount(localPlayerIndex, resource);
			
			getTradeOverlay().setResourceAmountChangeEnabled(resource, (this.sendAmounts.get(resource)) < resourceAmount, true);
		} else {
			this.receiveAmounts.put(resource, this.receiveAmounts.get(resource) + 1);
			
			getTradeOverlay().setResourceAmountChangeEnabled(resource, true, true);
		}
		
		calculateTradeMessage();
	}

	@Override
	public void sendTradeOffer() {
		
		getTradeOverlay().closeModal();
		getWaitOverlay().showModal();
	}

	@Override
	public void setPlayerToTradeWith(int playerIndex) {
		playerToTradeWith = playerIndex;
		calculateTradeMessage();
	}

	@Override
	public void setResourceToReceive(ResourceType resource) {
		this.sendAmounts.put(resource, 0);
		getTradeOverlay().setResourceAmount(resource, "0");
		
		this.receiveAmounts.put(resource, 0);
		this.sendingResource.put(resource, false);
		
		calculateTradeMessage();
		
		getTradeOverlay().setResourceAmountChangeEnabled(resource, true, false);
	}

	@Override
	public void setResourceToSend(ResourceType resource) {
		this.receiveAmounts.put(resource, 0);
		getTradeOverlay().setResourceAmount(resource, "0");
		
		int localPlayerIndex = Facade.getLocalPlayerIndex();
		boolean hasResource = Facade.getCatanModel().resourceManager.getResourceCount(localPlayerIndex, resource) > 0;
		
		this.sendAmounts.put(resource, 0);
		this.sendingResource.put(resource, true);
		
		calculateTradeMessage();
		
		getTradeOverlay().setResourceAmountChangeEnabled(resource, hasResource, false);
		
	}

	@Override
	public void unsetResource(ResourceType resource) {
		getTradeOverlay().setResourceAmount(resource, "0");
		this.sendAmounts.put(resource, 0);
		this.receiveAmounts.put(resource, 0);
		this.sendingResource.put(resource, false);
		calculateTradeMessage();
	}

	@Override
	public void cancelTrade() {
		this.resetView();
		getTradeOverlay().closeModal();
	}

	@Override
	public void acceptTrade(boolean willAccept) {

		getAcceptOverlay().closeModal();
	}

}

