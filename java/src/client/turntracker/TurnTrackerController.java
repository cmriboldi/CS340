package client.turntracker;

import java.util.Observable;
import java.util.Observer;

import model.players.Player;
import serverProxy.ServerException;
import shared.definitions.CatanColor;
import client.base.*;
import clientfacade.Facade;


/**
 * Implementation for the turn tracker controller
 */
public class TurnTrackerController extends Controller implements ITurnTrackerController, Observer {

	public TurnTrackerController(ITurnTrackerView view) {
		
		super(view);
		
		System.out.println("1. turn tracker -- new contorller");
		initFromModel();
	}
	
	@Override
	public ITurnTrackerView getView() {
		
		return (ITurnTrackerView)super.getView();
	}

	@Override
	public void endTurn() {

		try {
			Facade.getProxy().finishTurn(Facade.getLocalPlayerIndex());
		} catch (ServerException e) {
			e.printStackTrace();
		} 
		
	}
	
	private void initFromModel() {
		
		
		System.out.println("2. turn tracker -- initFromModel");
		getView().setLocalPlayerColor(CatanColor.RED);
		//</temp>
	}

	@Override
	public void update(Observable o, Object arg) {
		
		System.out.println("------------->> Update TurnTracker"); 
		
		TurnTrackerView trackerView = (TurnTrackerView) getView(); //TODO

		int currentTurnIndex = Facade.getCatanModel().getPlayerManager().getTurnTracker().getTurnIndex();
		Player player0 = Facade.getCatanModel().getPlayerManager().getCatanPlayers()[0];
		Player player1 = Facade.getCatanModel().getPlayerManager().getCatanPlayers()[1];
		Player player2 = Facade.getCatanModel().getPlayerManager().getCatanPlayers()[2];
		Player player3 = Facade.getCatanModel().getPlayerManager().getCatanPlayers()[3];
		
		trackerView.initializePlayer(0, player0.getName(), player0.getColor());
		trackerView.initializePlayer(1, player1.getName(), player1.getColor());
		trackerView.initializePlayer(2, player2.getName(), player2.getColor());
		trackerView.initializePlayer(3, player3.getName(), player3.getColor());

		int localPlayerIndex = Facade.getLocalPlayerIndex(); 
		CatanColor localColor = Facade.getCatanModel().getPlayerManager().getCatanPlayers()[localPlayerIndex].getColor(); 
		
		System.out.println("Local Color: " + localColor.getClass().toString());
		
		
		
		/*
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
		*/
		
		//Facade.getCatanModel().getPlayerManager().getTurnTracker()
		
	}

}

