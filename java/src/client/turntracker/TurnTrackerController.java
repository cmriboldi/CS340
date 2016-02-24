package client.turntracker;

import java.util.Observable;
import java.util.Observer;

import shared.definitions.CatanColor;
import client.base.*;
import clientfacade.Facade;


/**
 * Implementation for the turn tracker controller
 */
public class TurnTrackerController extends Controller implements ITurnTrackerController, Observer {

	public TurnTrackerController(ITurnTrackerView view) {
		
		super(view);
		
		initFromModel();
	}
	
	@Override
	public ITurnTrackerView getView() {
		
		return (ITurnTrackerView)super.getView();
	}

	@Override
	public void endTurn() {

	}
	
	private void initFromModel() {
		//<temp>
		getView().setLocalPlayerColor(CatanColor.RED);
		//</temp>
	}

	@Override
	public void update(Observable o, Object arg) {
		
		int currentTurn = Facade.getCatanModel().getPlayerManager().getTurnTracker().getTurnIndex();
		
		
		TurnTrackerView tracker = (TurnTrackerView) getView(); 
		//Facade.getCatanModel().getPlayerManager().getTurnTracker()
		
	}

}

