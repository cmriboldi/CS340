package client.discard;

import shared.definitions.*;

import java.util.Observable;
import java.util.Observer;

import client.base.*;
import client.misc.*;
import clientfacade.Facade;
import serverProxy.ServerException;


/**
 * Discard controller implementation
 */
public class DiscardController extends Controller implements IDiscardController, Observer {

	private IWaitView waitView;
	
	/**
	 * DiscardController constructor
	 * 
	 * @param view View displayed to let the user select cards to discard
	 * @param waitView View displayed to notify the user that they are waiting for other players to discard
	 */
	public DiscardController(IDiscardView view, IWaitView waitView) {
		
		super(view);		
		this.waitView = waitView;
		Facade.addObserverStatic(this);
	}

	public IDiscardView getDiscardView() {
		return (IDiscardView)super.getView();
	}
	
	public IWaitView getWaitView() {
		return waitView;
	}
	
	private int total;
	private int amount;
	private int woodTotal;
	private int brickTotal;
	private int sheepTotal;
	private int wheatTotal;
	private int oreTotal;
	
	private int wood;
	private int brick;
	private int sheep;
	private int wheat;
	private int ore;
	
	private void setArrows()
	{
		if(wood < woodTotal && (wood+brick+sheep+wheat+ore < amount))
			if(wood > 0)
				getDiscardView().setResourceAmountChangeEnabled(ResourceType.WOOD, true, true);
			else
				getDiscardView().setResourceAmountChangeEnabled(ResourceType.WOOD, true, false);
		else
			if(wood > 0)
				getDiscardView().setResourceAmountChangeEnabled(ResourceType.WOOD, false, true);
			else
				getDiscardView().setResourceAmountChangeEnabled(ResourceType.WOOD, false, false);
		
		if(brick < brickTotal && (wood+brick+sheep+wheat+ore < amount))
			if(brick > 0)
				getDiscardView().setResourceAmountChangeEnabled(ResourceType.BRICK, true, true);
			else
				getDiscardView().setResourceAmountChangeEnabled(ResourceType.BRICK, true, false);
		else
			if(brick > 0)
				getDiscardView().setResourceAmountChangeEnabled(ResourceType.BRICK, false, true);
			else
				getDiscardView().setResourceAmountChangeEnabled(ResourceType.BRICK, false, false);
		
		if(sheep < sheepTotal && (wood+brick+sheep+wheat+ore < amount))
			if(sheep > 0)
				getDiscardView().setResourceAmountChangeEnabled(ResourceType.SHEEP, true, true);
			else
				getDiscardView().setResourceAmountChangeEnabled(ResourceType.SHEEP, true, false);
		else
			if(sheep > 0)
				getDiscardView().setResourceAmountChangeEnabled(ResourceType.SHEEP, false, true);
			else
				getDiscardView().setResourceAmountChangeEnabled(ResourceType.SHEEP, false, false);
		
		if(wheat < wheatTotal && (wood+brick+sheep+wheat+ore < amount))
			if(wheat > 0)
				getDiscardView().setResourceAmountChangeEnabled(ResourceType.WHEAT, true, true);
			else
				getDiscardView().setResourceAmountChangeEnabled(ResourceType.WHEAT, true, false);
		else
			if(wheat > 0)
				getDiscardView().setResourceAmountChangeEnabled(ResourceType.WHEAT, false, true);
			else
				getDiscardView().setResourceAmountChangeEnabled(ResourceType.WHEAT, false, false);
		
		if(ore < oreTotal && (wood+brick+sheep+wheat+ore < amount))
			if(ore > 0)
				getDiscardView().setResourceAmountChangeEnabled(ResourceType.ORE, true, true);
			else
				getDiscardView().setResourceAmountChangeEnabled(ResourceType.ORE, true, false);
		else
			if(ore > 0)
				getDiscardView().setResourceAmountChangeEnabled(ResourceType.ORE, false, true);
			else
				getDiscardView().setResourceAmountChangeEnabled(ResourceType.ORE, false, false);
		
		getDiscardView().setResourceDiscardAmount(ResourceType.WOOD, wood);
		getDiscardView().setResourceDiscardAmount(ResourceType.BRICK, brick);
		getDiscardView().setResourceDiscardAmount(ResourceType.SHEEP, sheep);
		getDiscardView().setResourceDiscardAmount(ResourceType.WHEAT, wheat);
		getDiscardView().setResourceDiscardAmount(ResourceType.ORE, ore);
		getDiscardView().setStateMessage(Integer.toString(wood+brick+sheep+wheat+ore)+"/"+Integer.toString(amount));
		
		if(wood+brick+sheep+wheat+ore >= amount)
			getDiscardView().setDiscardButtonEnabled(true);
		else
			getDiscardView().setDiscardButtonEnabled(false);
	}

	@Override
	public void increaseAmount(ResourceType resource) {
		switch(resource)
		{
			case WOOD:
				wood++;
				this.setArrows();
				break;
			case BRICK:
				brick++;
				this.setArrows();
				break;
			case SHEEP:
				sheep++;
				this.setArrows();
				break;
			case WHEAT:
				wheat++;
				this.setArrows();
				break;
			case ORE:
				ore++;
				this.setArrows();
				break;
		}
	}

	@Override
	public void decreaseAmount(ResourceType resource) {
		switch(resource)
		{
			case WOOD:
				wood--;
				this.setArrows();
				break;
			case BRICK:
				brick--;
				this.setArrows();
				break;
			case SHEEP:
				sheep--;
				this.setArrows();
				break;
			case WHEAT:
				wheat--;
				this.setArrows();
				break;
			case ORE:
				ore--;
				this.setArrows();
				break;
		}
		
	}

	@Override
	public void discard() {
		getDiscardView().closeModal();
		try {
			Facade.discard(wood,brick,sheep,wheat,ore);
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		total = 0;
		amount = 0;
		woodTotal = 0;
		brickTotal = 0;
		sheepTotal = 0;
		wheatTotal = 0;
		oreTotal = 0;
		wood = 0;
		brick = 0;
		sheep = 0;
		wheat = 0;
		ore = 0;
	}

	@Override
	public void update(Observable o, Object arg) {
		if(Facade.getTurnStatus().equals("Discarding"))
		{		
			woodTotal = Facade.getResourceAmount(ResourceType.WOOD);
			brickTotal = Facade.getResourceAmount(ResourceType.BRICK);
			sheepTotal = Facade.getResourceAmount(ResourceType.SHEEP);
			wheatTotal = Facade.getResourceAmount(ResourceType.WHEAT);
			oreTotal = Facade.getResourceAmount(ResourceType.ORE);
			total = woodTotal+brickTotal+sheepTotal+wheatTotal+oreTotal;
			amount = total/2;
			
			
			if(total > 7)
			{
				getDiscardView().setStateMessage(Integer.toString(wood+brick+sheep+wheat+ore)+"/"+Integer.toString(amount));
				getDiscardView().setDiscardButtonEnabled(false);
				
				getDiscardView().setResourceMaxAmount(ResourceType.WOOD, woodTotal);
				getDiscardView().setResourceMaxAmount(ResourceType.BRICK, brickTotal);
				getDiscardView().setResourceMaxAmount(ResourceType.SHEEP, sheepTotal);
				getDiscardView().setResourceMaxAmount(ResourceType.WHEAT, wheatTotal);
				getDiscardView().setResourceMaxAmount(ResourceType.ORE, oreTotal);
				
				getDiscardView().setResourceDiscardAmount(ResourceType.WOOD, wood);
				getDiscardView().setResourceDiscardAmount(ResourceType.BRICK, brick);
				getDiscardView().setResourceDiscardAmount(ResourceType.SHEEP, sheep);
				getDiscardView().setResourceDiscardAmount(ResourceType.WHEAT, wheat);
				getDiscardView().setResourceDiscardAmount(ResourceType.ORE, ore);
				
				if(wood < woodTotal)
					if(wood > 0)
						getDiscardView().setResourceAmountChangeEnabled(ResourceType.WOOD, true, true);
					else
						getDiscardView().setResourceAmountChangeEnabled(ResourceType.WOOD, true, false);
				else
					if(wood > 0)
						getDiscardView().setResourceAmountChangeEnabled(ResourceType.WOOD, false, true);
					else
						getDiscardView().setResourceAmountChangeEnabled(ResourceType.WOOD, false, false);
				
				if(brick < brickTotal)
					if(brick > 0)
						getDiscardView().setResourceAmountChangeEnabled(ResourceType.BRICK, true, true);
					else
						getDiscardView().setResourceAmountChangeEnabled(ResourceType.BRICK, true, false);
				else
					if(brick > 0)
						getDiscardView().setResourceAmountChangeEnabled(ResourceType.BRICK, false, true);
					else
						getDiscardView().setResourceAmountChangeEnabled(ResourceType.BRICK, false, false);
				
				if(sheep < sheepTotal)
					if(sheep > 0)
						getDiscardView().setResourceAmountChangeEnabled(ResourceType.SHEEP, true, true);
					else
						getDiscardView().setResourceAmountChangeEnabled(ResourceType.SHEEP, true, false);
				else
					if(sheep > 0)
						getDiscardView().setResourceAmountChangeEnabled(ResourceType.SHEEP, false, true);
					else
						getDiscardView().setResourceAmountChangeEnabled(ResourceType.SHEEP, false, false);
				
				if(wheat < wheatTotal)
					if(wheat > 0)
						getDiscardView().setResourceAmountChangeEnabled(ResourceType.WHEAT, true, true);
					else
						getDiscardView().setResourceAmountChangeEnabled(ResourceType.WHEAT, true, false);
				else
					if(wheat > 0)
						getDiscardView().setResourceAmountChangeEnabled(ResourceType.WHEAT, false, true);
					else
						getDiscardView().setResourceAmountChangeEnabled(ResourceType.WHEAT, false, false);
				
				if(ore < oreTotal)
					if(ore > 0)
						getDiscardView().setResourceAmountChangeEnabled(ResourceType.ORE, true, true);
					else
						getDiscardView().setResourceAmountChangeEnabled(ResourceType.ORE, true, false);
				else
					if(ore > 0)
						getDiscardView().setResourceAmountChangeEnabled(ResourceType.ORE, false, true);
					else
						getDiscardView().setResourceAmountChangeEnabled(ResourceType.ORE, false, false);
				
				if(!getDiscardView().isModalShowing())
					getDiscardView().showModal();
			}
			else
			{
				if(!getWaitView().isModalShowing())
					getWaitView().showModal();
			}
		}
		else
		{
			if(getWaitView().isModalShowing())
				getWaitView().closeModal();
		}
	}

}

