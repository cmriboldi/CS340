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
		
		Facade.addObserverStatic(this);
		
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
		getView().setLocalPlayerColor(CatanColor.WHITE);
		//</temp>
	}

	@Override
	public void update(Observable o, Object arg) {
		
		if (!Facade.hasGameStarted())
			return; 
		
		System.out.println("(((Update TurnTracker)))"); 
		
		// ================================= INITIALIZE ======================================= // 
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
		getView().setLocalPlayerColor(localColor);
		
		// ================================= UPDATE ======================================= // 

		boolean player0Turn = false; 
		boolean player1Turn = false; 
		boolean player2Turn = false; 
		boolean player3Turn = false; 
		if (player0.getPlayerIndex() == currentTurnIndex)
			player0Turn = true; 
		if (player1.getPlayerIndex() == currentTurnIndex)
			player1Turn = true; 
		if (player2.getPlayerIndex() == currentTurnIndex)
			player2Turn = true; 
		if (player3.getPlayerIndex() == currentTurnIndex)
			player3Turn = true; 

		int indexOfLargestArmy = Facade.getCatanModel().getPlayerManager().getIndexOfLargestArmy(); 
		int indexOfLongestRoad = Facade.getCatanModel().getPlayerManager().getIndexOfLongestRoad(); 
		
		boolean player0LongestRoad = false; 
		if (indexOfLongestRoad == 0) player0LongestRoad = true; 
		boolean player1LongestRoad= false; 
		if (indexOfLongestRoad == 1) player1LongestRoad = true; 
		boolean player2LongestRoad = false; 
		if (indexOfLongestRoad == 2) player2LongestRoad = true; 
		boolean player3LongestRoad = false;
		if (indexOfLongestRoad == 3) player3LongestRoad = true; 

		
		boolean player0LargestArmy = false;
		if (indexOfLargestArmy == 0) player0LargestArmy = true; 
		boolean player1LargestArmy = false;
		if (indexOfLargestArmy == 1) player1LargestArmy = true; 
		boolean player2LargestArmy = false;
		if (indexOfLargestArmy == 2) player2LargestArmy = true; 
		boolean player3LargestArmy = false;
		if (indexOfLargestArmy == 3) player3LargestArmy = true; 

	
		//trackerView.updatePlayer(playerIndex, points, highlight, largestArmy, longestRoad);
		trackerView.updatePlayer(0, player0.getPoints(), player0Turn, player0LargestArmy, player0LongestRoad);
		trackerView.updatePlayer(1, player1.getPoints(), player1Turn, player1LargestArmy, player1LongestRoad);
		trackerView.updatePlayer(2, player2.getPoints(), player2Turn, player2LargestArmy, player2LongestRoad);
		trackerView.updatePlayer(3, player3.getPoints(), player3Turn, player3LargestArmy, player3LongestRoad);
		
		
		//int currentTurnIndex = Facade.getCatanModel().getPlayerManager().getTurnTracker().getTurnIndex(); 
		if (localPlayerIndex == currentTurnIndex && 
				Facade.getCatanModel().getPlayerManager().getTurnTracker().getStatus().equals("Playing"))
		{
			getView().updateGameState("Finish Turn", true);
		}
		else
		{
			getView().updateGameState("Waiting for Other Players", false);
		}
		
		//Facade.getCatanModel().getPlayerManager().getTurnTracker().
		
		
		
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

