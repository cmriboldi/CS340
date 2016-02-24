package client.turntracker;

import java.util.Observable;
import java.util.Observer;

import model.players.Player;
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
		
		TurnTrackerView trackerView = (TurnTrackerView) getView(); //TODO
		
		int currentTurnIndex = Facade.getCatanModel().getPlayerManager().getTurnTracker().getTurnIndex();
		Player player0 = Facade.getCatanModel().getPlayerManager().getCatanPlayers()[0];
		Player player1 = Facade.getCatanModel().getPlayerManager().getCatanPlayers()[1];
		Player player2 = Facade.getCatanModel().getPlayerManager().getCatanPlayers()[2];
		Player player3 = Facade.getCatanModel().getPlayerManager().getCatanPlayers()[3];
		
		boolean player0Turn = false; 
		if (player0.getPlayerIndex() == currentTurnIndex)
		{
			player0Turn = true; 
		}
		
		trackerView.updatePlayer(0, player0.getPoints(), player0Turn, false, false);//player0.get, longestRoad
		
		
		//Facade.getCatanModel().getPlayerManager().getTurnTracker()
		
	}

}

