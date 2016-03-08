package client.domestic;

import shared.definitions.*;

import java.util.Collection;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.TreeMap;

import client.base.*;
import client.data.PlayerInfo;
import client.misc.*;
import clientfacade.Facade;
import model.resources.ResourceList;
import model.resources.TradeOffer;
import serverProxy.ServerException;


/**
 * Domestic trade controller implementation
 */
public class DomesticTradeController extends Controller implements IDomesticTradeController, Observer {

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
		
		Facade.addObserverStatic(this);
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
		
		ResourceList rs = new ResourceList();
		for (Map.Entry<ResourceType, Integer> entry : this.sendAmounts.entrySet()) {
			ResourceType resource = entry.getKey();
		    Integer amount = entry.getValue();
		    rs.addResource(resource, amount);
		}
		
		for (Map.Entry<ResourceType, Integer> entry : this.receiveAmounts.entrySet()) {
			ResourceType resource = entry.getKey();
		    Integer amount = entry.getValue();
		    rs.removeResource(resource, amount);
		}
		
		try
		{
			Facade.sendTradeOffer(new TradeOffer(rs, Facade.getLocalPlayerIndex(), playerToTradeWith));
		} catch (ServerException e)
		{
			e.printStackTrace();
		}
		
		getTradeOverlay().closeModal();
		this.resetView();
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
		
		try
		{
			Facade.acceptTrade(willAccept);
		} catch (ServerException e)
		{
			e.printStackTrace();
		}

		getAcceptOverlay().reset();
		getAcceptOverlay().closeModal();
	}

	@Override
	public void update(Observable o, Object arg)
	{
		if(!Facade.hasGameStarted()) {
			return;
		}
		
		boolean gameOver = Facade.gameOver(); 
		
		getTradeView().enableDomesticTrade(Facade.isMyturn() && !gameOver);
		
		TradeOffer tradeOffer = Facade.getCatanModel().resourceManager.getTradeOffer();
		if (tradeOffer == null && getWaitOverlay().isModalShowing()) {
			getWaitOverlay().closeModal();
		}
		
		if(tradeOffer != null && tradeOffer.getReceiver() == Facade.getLocalPlayerIndex()) {
			
			getAcceptOverlay().setPlayerName(Facade.getCatanModel().playerManager.getPlayerName(tradeOffer.getSender()));
			
			ResourceList resOffer = tradeOffer.getResourcesOffer();
			for(ResourceType resource : ResourceType.values()) {
				int amount = resOffer.getResourceTypeCount(resource);
				if(amount > 0) {
					getAcceptOverlay().addGetResource(resource, amount);
				} else if (amount < 0) {
					getAcceptOverlay().addGiveResource(resource, amount*-1);
				}
			}
			
			if(Facade.getCatanModel().resourceManager.canAcceptTrade(Facade.getLocalPlayerIndex())) {
				getAcceptOverlay().setAcceptEnabled(true);
			} else {
				getAcceptOverlay().setAcceptEnabled(false);
			}
			getAcceptOverlay().showModal();
		}
	}

}

