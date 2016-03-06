package client.maritime;

import shared.definitions.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.Vector;

import client.base.*;
import clientfacade.Facade;
import model.resources.ResourceList;
import serverProxy.ServerException;


/**
 * Implementation for the maritime trade controller
 */
public class MaritimeTradeController extends Controller implements IMaritimeTradeController, Observer {

	private IMaritimeTradeOverlay tradeOverlay;
	private ResourceType[] enabledGetResources = new ResourceType[]{ResourceType.BRICK,ResourceType.ORE,ResourceType.SHEEP,ResourceType.WHEAT,ResourceType.WOOD};
	private ResourceType[] enabledGiveResources = new ResourceType[]{};
	private Map<ResourceType, Integer> ratioMap = null;
	private ResourceType giveResourceType;
	private ResourceType getResourceType;
	private int tradeRatio;
	
	public MaritimeTradeController(IMaritimeTradeView tradeView, IMaritimeTradeOverlay tradeOverlay) {
		
		super(tradeView);
		ratioMap = new HashMap<ResourceType, Integer>();
		setTradeOverlay(tradeOverlay);

		Facade.addObserverStatic(this);
	}

	private void calculateGetResources()
	{
		Vector<ResourceType> enabledResources = new Vector<ResourceType>();
		for(ResourceType resource : ResourceType.values()) {
			if(Facade.bankHasResource(resource)) {
				enabledResources.add(resource);
			}
		}
		enabledGetResources = new ResourceType[enabledResources.size()];
		enabledResources.toArray(enabledGetResources);
		
	}
	
	private void calculateGiveResources() {
		Vector<ResourceType> enabledResources = new Vector<ResourceType>();
		int localPlayer = Facade.getLocalPlayerIndex();
		ratioMap = new HashMap<ResourceType, Integer>();
		
		if(Facade.getCatanModel().resourceManager.canAfford(localPlayer, new ResourceList(4,0,0,0,0))) {
			enabledResources.add(ResourceType.BRICK);
			ratioMap.put(ResourceType.BRICK, 4);
		}
		if(Facade.getCatanModel().resourceManager.canAfford(localPlayer, new ResourceList(0,4,0,0,0))) {
			enabledResources.add(ResourceType.ORE);
			ratioMap.put(ResourceType.ORE, 4);
		}
		if(Facade.getCatanModel().resourceManager.canAfford(localPlayer, new ResourceList(0,0,4,0,0))) {
			enabledResources.add(ResourceType.SHEEP);
			ratioMap.put(ResourceType.SHEEP, 4);
		}
		if(Facade.getCatanModel().resourceManager.canAfford(localPlayer, new ResourceList(0,0,0,4,0))) {
			enabledResources.add(ResourceType.WHEAT);
			ratioMap.put(ResourceType.WHEAT, 4);
		}
		if(Facade.getCatanModel().resourceManager.canAfford(localPlayer, new ResourceList(0,0,0,0,4))) {
			enabledResources.add(ResourceType.WOOD);
			ratioMap.put(ResourceType.WOOD, 4);
		}
		
		
		Set<PortType> ports = Facade.getCatanModel().options.canMaritimeTrade(localPlayer);
		for(PortType port : ports) {
			switch(port)
			{
			case BRICK:
				if(Facade.getCatanModel().resourceManager.canAfford(localPlayer, new ResourceList(2,0,0,0,0))) {
					enabledResources.add(ResourceType.BRICK);
					ratioMap.put(ResourceType.BRICK, 2);
				}
				break;
			case ORE:
				if(Facade.getCatanModel().resourceManager.canAfford(localPlayer, new ResourceList(0,2,0,0,0))) {
					enabledResources.add(ResourceType.ORE);
					ratioMap.put(ResourceType.ORE, 2);
				}
				break;
			case SHEEP:
				if(Facade.getCatanModel().resourceManager.canAfford(localPlayer, new ResourceList(0,0,2,0,0))) {
					enabledResources.add(ResourceType.SHEEP);
					ratioMap.put(ResourceType.SHEEP, 2);
				}
				break;
			case WHEAT:
				if(Facade.getCatanModel().resourceManager.canAfford(localPlayer, new ResourceList(0,0,0,2,0))) {
					enabledResources.add(ResourceType.WHEAT);
					ratioMap.put(ResourceType.WHEAT, 2);
				}
				break;
			case WOOD:
				if(Facade.getCatanModel().resourceManager.canAfford(localPlayer, new ResourceList(0,0,0,0,2))) {
					enabledResources.add(ResourceType.WOOD);
					ratioMap.put(ResourceType.WOOD, 2);
				}
				break;
			case THREE:
				if(Facade.getCatanModel().resourceManager.canAfford(localPlayer, new ResourceList(3,0,0,0,0))) {
					enabledResources.add(ResourceType.BRICK);
					ratioMap.put(ResourceType.BRICK, 3);
				}
				if(Facade.getCatanModel().resourceManager.canAfford(localPlayer, new ResourceList(0,3,0,0,0))) {
					enabledResources.add(ResourceType.ORE);
					ratioMap.put(ResourceType.ORE, 3);
				}
				if(Facade.getCatanModel().resourceManager.canAfford(localPlayer, new ResourceList(0,0,3,0,0))) {
					enabledResources.add(ResourceType.SHEEP);
					ratioMap.put(ResourceType.SHEEP, 3);
				}
				if(Facade.getCatanModel().resourceManager.canAfford(localPlayer, new ResourceList(0,0,0,3,0))) {
					enabledResources.add(ResourceType.WHEAT);
					ratioMap.put(ResourceType.WHEAT, 3);
				}
				if(Facade.getCatanModel().resourceManager.canAfford(localPlayer, new ResourceList(0,0,0,0,3))) {
					enabledResources.add(ResourceType.WOOD);
					ratioMap.put(ResourceType.WOOD, 3);
				}
				break;
			}
		}
		enabledGiveResources = new ResourceType[enabledResources.size()];
		enabledResources.toArray(enabledGiveResources);
	}
	
	public IMaritimeTradeView getTradeView() {
		
		return (IMaritimeTradeView)super.getView();
	}
	
	public IMaritimeTradeOverlay getTradeOverlay() {
		return tradeOverlay;
	}

	public void setTradeOverlay(IMaritimeTradeOverlay tradeOverlay) {
		this.tradeOverlay = tradeOverlay;
	}

	@Override
	public void startTrade() {
		calculateGiveResources();
		getTradeOverlay().showGiveOptions(enabledGiveResources);
		getTradeOverlay().setTradeEnabled(false);
		getTradeOverlay().hideGetOptions();
		getTradeOverlay().showModal();
	}

	@Override
	public void makeTrade() {
		try
		{
			Facade.portTrade(tradeRatio, giveResourceType, getResourceType);
		} catch (ServerException e)
		{
			e.printStackTrace();
		}
		getTradeOverlay().closeModal();
	}

	@Override
	public void cancelTrade() {
		getResourceType = null;
		giveResourceType = null;
		tradeRatio = -1;
		getTradeOverlay().closeModal();
	}

	@Override
	public void setGetResource(ResourceType resource) {
		getResourceType = resource;
		tradeOverlay.selectGetOption(resource, 1);
		tradeOverlay.setTradeEnabled(true);
	}

	@Override
	public void setGiveResource(ResourceType resource) {
		giveResourceType = resource;
		
		if(ratioMap != null && ratioMap.get(resource) != null) {
			tradeRatio = ratioMap.get(resource);
			getTradeOverlay().selectGiveOption(resource, ratioMap.get(resource));
		}
		calculateGetResources();
		getTradeOverlay().showGetOptions(enabledGetResources);
	}

	@Override
	public void unsetGetValue() {
		getResourceType = null;
		calculateGetResources();
		getTradeOverlay().showGetOptions(enabledGetResources);
		getTradeOverlay().setTradeEnabled(false);
	}

	@Override
	public void unsetGiveValue() {
		giveResourceType = null;
		tradeRatio = -1;
		getTradeOverlay().hideGetOptions();
		getTradeOverlay().showGiveOptions(enabledGiveResources);
	}
	
	@Override
	public void update(Observable o, Object arg)
	{
		if(!Facade.hasGameStarted()) {
			return;
		}

		getTradeView().enableMaritimeTrade(Facade.isMyturn());
		calculateGiveResources();
		getTradeOverlay().showGiveOptions(enabledGiveResources);
		
		calculateGetResources();
		
	}

}

