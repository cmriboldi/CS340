package client.discard;

import shared.definitions.*;

import java.util.Observable;
import java.util.Observer;

import client.base.*;
import client.misc.*;
import clientfacade.Facade;


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
	
	private int total = 0;
	private int amount = 0;
	private int woodTotal = 0;
	private int brickTotal = 0;
	private int sheepTotal = 0;
	private int wheatTotal = 0;
	private int oreTotal = 0;
	
	private int wood = 0;
	private int brick = 0;
	private int sheep = 0;
	private int wheat = 0;
	private int ore = 0;

	@Override
	public void increaseAmount(ResourceType resource) {
		switch(resource)
		{
			case WOOD:
				wood++;
				getDiscardView().setResourceDiscardAmount(ResourceType.WOOD, wood);
				if(wood < woodTotal)
					getDiscardView().setResourceAmountChangeEnabled(ResourceType.WOOD, true, true);
				else if(wood >= woodTotal)
					getDiscardView().setResourceAmountChangeEnabled(ResourceType.WOOD, false, true);
				
				if(wood+brick+sheep+wheat+ore >= amount)
					getDiscardView().setDiscardButtonEnabled(true);
				else
					getDiscardView().setDiscardButtonEnabled(false);
				break;
			case BRICK:
				brick++;
				getDiscardView().setResourceDiscardAmount(ResourceType.BRICK, brick);
				if(brick < brickTotal)
					getDiscardView().setResourceAmountChangeEnabled(ResourceType.BRICK, true, true);
				else if(brick >= brickTotal)
					getDiscardView().setResourceAmountChangeEnabled(ResourceType.BRICK, false, true);
				
				if(wood+brick+sheep+wheat+ore >= amount)
					getDiscardView().setDiscardButtonEnabled(true);
				else
					getDiscardView().setDiscardButtonEnabled(false);
				break;
			case SHEEP:
				sheep++;
				getDiscardView().setResourceDiscardAmount(ResourceType.SHEEP, sheep);
				if(sheep < sheepTotal)
					getDiscardView().setResourceAmountChangeEnabled(ResourceType.SHEEP, true, true);
				else if(sheep >= sheepTotal)
					getDiscardView().setResourceAmountChangeEnabled(ResourceType.SHEEP, false, true);
				
				if(wood+brick+sheep+wheat+ore >= amount)
					getDiscardView().setDiscardButtonEnabled(true);
				else
					getDiscardView().setDiscardButtonEnabled(false);
				break;
			case WHEAT:
				wheat++;
				getDiscardView().setResourceDiscardAmount(ResourceType.WHEAT, wheat);
				if(wheat < wheatTotal)
					getDiscardView().setResourceAmountChangeEnabled(ResourceType.WHEAT, true, true);
				else if(wheat >= wheatTotal)
					getDiscardView().setResourceAmountChangeEnabled(ResourceType.WHEAT, false, true);
				
				if(wood+brick+sheep+wheat+ore >= amount)
					getDiscardView().setDiscardButtonEnabled(true);
				else
					getDiscardView().setDiscardButtonEnabled(false);
				break;
			case ORE:
				ore++;
				getDiscardView().setResourceDiscardAmount(ResourceType.ORE, ore);
				if(ore < oreTotal)
					getDiscardView().setResourceAmountChangeEnabled(ResourceType.ORE, true, true);
				else if(ore >= oreTotal)
					getDiscardView().setResourceAmountChangeEnabled(ResourceType.ORE, false, true);
				
				if(wood+brick+sheep+wheat+ore >= amount)
					getDiscardView().setDiscardButtonEnabled(true);
				else
					getDiscardView().setDiscardButtonEnabled(false);
				break;
		}
	}

	@Override
	public void decreaseAmount(ResourceType resource) {
		switch(resource)
		{
			case WOOD:
				wood--;
				getDiscardView().setResourceDiscardAmount(ResourceType.WOOD, wood);
				if(wood > 0)
					getDiscardView().setResourceAmountChangeEnabled(ResourceType.WOOD, true, true);
				else if(wood <= 0)
					getDiscardView().setResourceAmountChangeEnabled(ResourceType.WOOD, true, false);
				
				if(wood+brick+sheep+wheat+ore >= amount)
					getDiscardView().setDiscardButtonEnabled(true);
				else
					getDiscardView().setDiscardButtonEnabled(false);
				break;
			case BRICK:
				brick--;
				getDiscardView().setResourceDiscardAmount(ResourceType.BRICK, brick);
				if(brick > 0)
					getDiscardView().setResourceAmountChangeEnabled(ResourceType.BRICK, true, true);
				else if(brick <= 0)
					getDiscardView().setResourceAmountChangeEnabled(ResourceType.BRICK, true, false);
				
				if(wood+brick+sheep+wheat+ore >= amount)
					getDiscardView().setDiscardButtonEnabled(true);
				else
					getDiscardView().setDiscardButtonEnabled(false);
				break;
			case SHEEP:
				sheep--;
				getDiscardView().setResourceDiscardAmount(ResourceType.SHEEP, sheep);
				if(sheep > 0)
					getDiscardView().setResourceAmountChangeEnabled(ResourceType.SHEEP, true, true);
				else if(sheep <= 0)
					getDiscardView().setResourceAmountChangeEnabled(ResourceType.SHEEP, true, false);
				
				if(wood+brick+sheep+wheat+ore >= amount)
					getDiscardView().setDiscardButtonEnabled(true);
				else
					getDiscardView().setDiscardButtonEnabled(false);
				break;
			case WHEAT:
				wheat--;
				getDiscardView().setResourceDiscardAmount(ResourceType.WHEAT, wheat);
				if(wheat > 0)
					getDiscardView().setResourceAmountChangeEnabled(ResourceType.WHEAT, true, true);
				else if(wheat <= 0)
					getDiscardView().setResourceAmountChangeEnabled(ResourceType.WHEAT, true, false);
				
				if(wood+brick+sheep+wheat+ore >= amount)
					getDiscardView().setDiscardButtonEnabled(true);
				else
					getDiscardView().setDiscardButtonEnabled(false);
				break;
			case ORE:
				ore--;
				getDiscardView().setResourceDiscardAmount(ResourceType.ORE, ore);
				if(ore > 0)
					getDiscardView().setResourceAmountChangeEnabled(ResourceType.ORE, true, true);
				else if(ore <= 0)
					getDiscardView().setResourceAmountChangeEnabled(ResourceType.ORE, true, false);
				
				if(wood+brick+sheep+wheat+ore >= amount)
					getDiscardView().setDiscardButtonEnabled(true);
				else
					getDiscardView().setDiscardButtonEnabled(false);
				break;
		}
		
	}

	@Override
	public void discard() {
		Facade.discard(wood,brick,sheep,wheat,ore);
		getDiscardView().closeModal();
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
		if(Facade.getTurnStatus() == "discarding" && !getDiscardView().isModalShowing())
		{		
			woodTotal = Facade.getResourceAmount(ResourceType.WOOD);
			brickTotal = Facade.getResourceAmount(ResourceType.BRICK);
			sheepTotal = Facade.getResourceAmount(ResourceType.SHEEP);
			wheatTotal = Facade.getResourceAmount(ResourceType.WHEAT);
			oreTotal = Facade.getResourceAmount(ResourceType.ORE);
			total = woodTotal+brickTotal+sheepTotal+wheatTotal+oreTotal;
			amount = total/2;

			getDiscardView().setStateMessage("0/"+Integer.toString(amount));
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
			
			if(woodTotal > 0)
				getDiscardView().setResourceAmountChangeEnabled(ResourceType.WOOD, true, false);
			else
				getDiscardView().setResourceAmountChangeEnabled(ResourceType.WOOD, false, false);
			
			if(brickTotal > 0)
				getDiscardView().setResourceAmountChangeEnabled(ResourceType.BRICK, true, false);
			else
				getDiscardView().setResourceAmountChangeEnabled(ResourceType.BRICK, true, false);
			
			if(sheepTotal > 0)
				getDiscardView().setResourceAmountChangeEnabled(ResourceType.SHEEP, true, false);
			else
				getDiscardView().setResourceAmountChangeEnabled(ResourceType.SHEEP, true, false);
			
			if(wheatTotal > 0)
				getDiscardView().setResourceAmountChangeEnabled(ResourceType.WHEAT, true, false);
			else
				getDiscardView().setResourceAmountChangeEnabled(ResourceType.WHEAT, true, false);
			
			if(oreTotal > 0)
				getDiscardView().setResourceAmountChangeEnabled(ResourceType.ORE, true, false);
			else
				getDiscardView().setResourceAmountChangeEnabled(ResourceType.ORE, true, false);
			
			getDiscardView().showModal();
		}
	}

}

