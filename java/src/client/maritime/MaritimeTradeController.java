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


/**
 * Implementation for the maritime trade controller
 */
public class MaritimeTradeController extends Controller implements IMaritimeTradeController, Observer {

	private IMaritimeTradeOverlay tradeOverlay;
	private ResourceType[] enabledGetResources = new ResourceType[]{ResourceType.BRICK,ResourceType.ORE,ResourceType.SHEEP,ResourceType.WHEAT,ResourceType.WOOD};
	private ResourceType[] enabledGiveResources = new ResourceType[]{ResourceType.BRICK,ResourceType.ORE,ResourceType.SHEEP,ResourceType.WHEAT,ResourceType.WOOD};
	private Map<ResourceType, Integer> ratioMap = null;
	
	public MaritimeTradeController(IMaritimeTradeView tradeView, IMaritimeTradeOverlay tradeOverlay) {
		
		super(tradeView);
		ratioMap = new HashMap<ResourceType, Integer>();
		setTradeOverlay(tradeOverlay);
		System.out.println("We have arrived at Ports");

		Facade.addObserverStatic(this);
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
		getTradeOverlay().showGiveOptions(enabledGiveResources);
		getTradeOverlay().setTradeEnabled(false);
		getTradeOverlay().showModal();
	}

	@Override
	public void makeTrade() {

		getTradeOverlay().closeModal();
	}

	@Override
	public void cancelTrade() {

		getTradeOverlay().closeModal();
	}

	@Override
	public void setGetResource(ResourceType resource) {
		tradeOverlay.selectGetOption(resource, 1);
		tradeOverlay.setTradeEnabled(true);
	}

	@Override
	public void setGiveResource(ResourceType resource) {
		System.out.println("called setGiveResource");
		System.out.println("getTradeOverlay is: "+ getTradeOverlay());
		System.out.println("ratioMap.get(resource) is: "+ ratioMap.get(resource));
		System.out.println("enabledGetResources is: "+ enabledGetResources);
//		if(ratioMap != null && ratioMap.get(resource) != null) {
			getTradeOverlay().selectGiveOption(resource, 4);
//		}
		getTradeOverlay().showGetOptions(enabledGetResources);
	}

	@Override
	public void unsetGetValue() {
		getTradeOverlay().showGetOptions(enabledGetResources);
		getTradeOverlay().setTradeEnabled(false);
	}

	@Override
	public void unsetGiveValue() {
		getTradeOverlay().hideGetOptions();
		getTradeOverlay().showGiveOptions(enabledGiveResources);
	}
	
	@Override
	public void update(Observable o, Object arg)
	{
		System.out.println("We have arrived at Ports");
		System.out.println("");
		
		Vector<ResourceType> enabledResources = new Vector<ResourceType>();
		int localPlayer = Facade.getLocalPlayerInfo().getPlayerIndex();
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
				if(Facade.getCatanModel().resourceManager.canAfford(localPlayer, new ResourceList(3,0,0,0,0)) && !ratioMap.containsKey(ResourceType.BRICK) ) {
					enabledResources.add(ResourceType.BRICK);
					ratioMap.put(ResourceType.BRICK, 3);
				}
				if(Facade.getCatanModel().resourceManager.canAfford(localPlayer, new ResourceList(0,3,0,0,0)) && !ratioMap.containsKey(ResourceType.ORE) ) {
					enabledResources.add(ResourceType.ORE);
					ratioMap.put(ResourceType.ORE, 3);
				}
				if(Facade.getCatanModel().resourceManager.canAfford(localPlayer, new ResourceList(0,0,3,0,0)) && !ratioMap.containsKey(ResourceType.SHEEP) ) {
					enabledResources.add(ResourceType.SHEEP);
					ratioMap.put(ResourceType.SHEEP, 3);
				}
				if(Facade.getCatanModel().resourceManager.canAfford(localPlayer, new ResourceList(0,0,0,3,0)) && !ratioMap.containsKey(ResourceType.WHEAT) ) {
					enabledResources.add(ResourceType.WHEAT);
					ratioMap.put(ResourceType.WHEAT, 3);
				}
				if(Facade.getCatanModel().resourceManager.canAfford(localPlayer, new ResourceList(0,0,0,0,3)) && !ratioMap.containsKey(ResourceType.WOOD) ) {
					enabledResources.add(ResourceType.WOOD);
					ratioMap.put(ResourceType.WOOD, 3);
				}
				break;
			}
			
			enabledGiveResources = new ResourceType[enabledResources.size()];
			enabledResources.toArray(enabledGiveResources);
			getTradeOverlay().showGiveOptions(enabledGiveResources);
			
		}
		
	}

}

