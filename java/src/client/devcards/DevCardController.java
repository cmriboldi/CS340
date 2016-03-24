package client.devcards;

import shared.definitions.DevCardType;
import shared.definitions.ResourceType;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import client.base.*;
import clientfacade.Facade;
import serverProxy.ServerException;


/**
 * "Dev card" controller implementation
 */
public class DevCardController extends Controller implements IDevCardController, Observer {

	private IBuyDevCardView buyCardView;
	private IAction soldierAction;
	private IAction roadAction;
	
	/**
	 * DevCardController constructor
	 * 
	 * @param view "Play dev card" view
	 * @param buyCardView "Buy dev card" view
	 * @param soldierAction Action to be executed when the user plays a soldier card.  It calls "mapController.playSoldierCard()".
	 * @param roadAction Action to be executed when the user plays a road building card.  It calls "mapController.playRoadBuildingCard()".
	 */
	public DevCardController(IPlayDevCardView view, IBuyDevCardView buyCardView, 
								IAction soldierAction, IAction roadAction) {

		super(view);
		
		this.buyCardView = buyCardView;
		this.soldierAction = soldierAction;
		this.roadAction = roadAction;
		
		Facade.addObserverStatic(this);
	}

	public IPlayDevCardView getPlayCardView() {
		return (IPlayDevCardView)super.getView();
	}

	public IBuyDevCardView getBuyCardView() {
		return buyCardView;
	}

	@Override
	public void startBuyCard() {
		if(Facade.getCatanModel().options.canBuyDevCard(Facade.getLocalPlayerInfo().getPlayerIndex()))
		{
			getBuyCardView().showModal();
		}
		else
		{
			
		}
	}

	@Override
	public void cancelBuyCard() {		
		getBuyCardView().closeModal();
	}

	@Override
	public void buyCard() {
		getBuyCardView().closeModal();
		if(Facade.getCatanModel().options.canBuyDevCard(Facade.getLocalPlayerInfo().getPlayerIndex()))
		{
			try {
				Facade.buyDevCard();
			} catch (ServerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			
		}
	}

	@Override
	public void startPlayCard() {
		if(Facade.isMyturn())
			getPlayCardView().showModal();
	}

	@Override
	public void cancelPlayCard() {

		getPlayCardView().closeModal();
	}

	@Override
	public void playMonopolyCard(ResourceType resource) {
		if(Facade.getCatanModel().options.canPlayDevCard(Facade.getLocalPlayerInfo().getPlayerIndex()))
		{
			try {
				Facade.playMonopolyCard(resource);
			} catch (ServerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			
		}
	}

	@Override
	public void playMonumentCard() {
			try {
				Facade.playMonumentCard();
			} catch (ServerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	@Override
	public void playRoadBuildCard() {
		if(Facade.getCatanModel().options.canPlayDevCard(Facade.getLocalPlayerInfo().getPlayerIndex()))
		{
			roadAction.execute();
		}
		else
		{
			
		}
	}

	@Override
	public void playSoldierCard() {
		if(Facade.getCatanModel().options.canPlayDevCard(Facade.getLocalPlayerInfo().getPlayerIndex()))
		{		
			soldierAction.execute();
		}
		else
		{
			
		}
		
	}

	@Override
	public void playYearOfPlentyCard(ResourceType resource1, ResourceType resource2) {
		if(Facade.getCatanModel().options.canPlayDevCard(Facade.getLocalPlayerInfo().getPlayerIndex()))
		{
			try {
				Facade.playYearOfPlentyCard(resource1, resource2);
			} catch (ServerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			
		}
	}

	@Override
	public void update(Observable o, Object arg)
	{
		if(!Facade.hasGameStarted())
			return;

		if(Facade.getCatanModel() != null)
		{
			List<DevCardType> cards = Facade.getPlayerDevTypes();
			List<Integer> cardAmounts = Facade.getPlayerDevAmounts();
			List<Boolean> cardPlayables = Facade.getPlayerDevPlayables();
			
			
			for(int i = 0; (i < cards.size()) && (i < cardAmounts.size()) && (i < cardPlayables.size()); i++)
			{
				getPlayCardView().setCardAmount(cards.get(i), cardAmounts.get(i));
				if(Facade.getCatanModel().options.canPlayDevCard(Facade.getLocalPlayerInfo().getPlayerIndex()))
				{
					getPlayCardView().setCardEnabled(cards.get(i), cardPlayables.get(i));
				}
				else
				{
					getPlayCardView().setCardEnabled(cards.get(i), false);
				}
				if(cards.get(i) == DevCardType.MONUMENT)
				{
					if(Facade.canPlayMonumentCard()) {
						getPlayCardView().setCardEnabled(cards.get(i), true);
					} else {
						getPlayCardView().setCardEnabled(cards.get(i), false);
					}
						
				}
				
			}
		}
		
	}

}

