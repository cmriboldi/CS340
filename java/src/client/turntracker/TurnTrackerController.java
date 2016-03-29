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
	
	boolean initialized = false; 

	public TurnTrackerController(ITurnTrackerView view) {
		
		super(view);
		
		Facade.addObserverStatic(this);
		
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
			getView().updateGameState("Finishing Turn !!!" , false);
		} catch (ServerException e) {
			e.printStackTrace();
		} 
		
	}
	
	private void initFromModel() {
		getView().setLocalPlayerColor(CatanColor.WHITE);
	}

	private void intializeFirstTime() {
		
		
		TurnTrackerView trackerView = (TurnTrackerView) getView(); //TODO
		int localPlayerIndex = Facade.getLocalPlayerIndex(); 
		int currentTurnIndex = Facade.getCatanModel().getPlayerManager().getTurnTracker().getTurnIndex();
		CatanColor localColor = Facade.getCatanModel().getPlayerManager().getCatanPlayers()[localPlayerIndex].getColor(); 
		
		Player player0 = Facade.getCatanModel().getPlayerManager().getCatanPlayers()[0];
		Player player1 = Facade.getCatanModel().getPlayerManager().getCatanPlayers()[1];
		Player player2 = Facade.getCatanModel().getPlayerManager().getCatanPlayers()[2];
		Player player3 = Facade.getCatanModel().getPlayerManager().getCatanPlayers()[3];
			
		trackerView.initializePlayer(0, player0.getName(), player0.getColor());
		trackerView.initializePlayer(1, player1.getName(), player1.getColor());
		trackerView.initializePlayer(2, player2.getName(), player2.getColor());
		trackerView.initializePlayer(3, player3.getName(), player3.getColor());

		getView().setLocalPlayerColor(localColor);	
		
	}
	
	@Override
	public void update(Observable o, Object arg) {
		
		if (!Facade.hasGameStarted())
			return; 
		
		if (!initialized){
			intializeFirstTime(); 
			initialized = true; 
		}
		
		// ================================= INITIALIZE ======================================= // 
		int localPlayerIndex = Facade.getLocalPlayerIndex(); 
		TurnTrackerView trackerView = (TurnTrackerView) getView(); //TODO
		int currentTurnIndex = Facade.getCatanModel().getPlayerManager().getTurnTracker().getTurnIndex();
		CatanColor localColor = Facade.getCatanModel().getPlayerManager().getCatanPlayers()[localPlayerIndex].getColor(); 
		
		Player player0 = Facade.getCatanModel().getPlayerManager().getCatanPlayers()[0];
		Player player1 = Facade.getCatanModel().getPlayerManager().getCatanPlayers()[1];
		Player player2 = Facade.getCatanModel().getPlayerManager().getCatanPlayers()[2];
		Player player3 = Facade.getCatanModel().getPlayerManager().getCatanPlayers()[3];

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
		trackerView.updatePlayer(0, player0.getPoints(), player0Turn, player0LargestArmy, player0LongestRoad, player0.getColor());
		trackerView.updatePlayer(1, player1.getPoints(), player1Turn, player1LargestArmy, player1LongestRoad, player1.getColor());
		trackerView.updatePlayer(2, player2.getPoints(), player2Turn, player2LargestArmy, player2LongestRoad, player2.getColor());
		trackerView.updatePlayer(3, player3.getPoints(), player3Turn, player3LargestArmy, player3LongestRoad, player3.getColor());
		
		
		if (Facade.getMaxPoints() >= 10) 
		{
		getView().updateGameState("GAME OVER", false);
		}
		else if (localPlayerIndex != currentTurnIndex)
		{
			getView().updateGameState("Waiting for other players ...", false);
		}
		else
		{			
			
			switch (Facade.getTurnStatus()) {
			case FIRST_ROUND:
				getView().updateGameState("First Round", false);
				break;
			case SECOND_ROUND:
				getView().updateGameState("Second Round", false);
				break;
			case ROLLING:
				getView().updateGameState("Rolling", false);
				break;
			case DISCARDING:
				getView().updateGameState("Discarding", false);
				break;
			case PLAYING:
				getView().updateGameState("Click to end your turn", true);
				break;
			case ROBBING:
				getView().updateGameState("Robbing", false);
				break;
			default:
				break;
			}
			
			
		}
		
	}

}

