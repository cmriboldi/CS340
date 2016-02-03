package model.players;

import shared.exceptions.player.*;

/**
 * The PlayerManager generally manages turns and player game flow
 * 
 * @author Christian Riboldi
 * @author Clayton Condie
 * @author Jacob Brewer
 * @author Joshua Powers
 * @author Joshua Van Steeter
 * @version 1.0 Build Jan, 2016.
 */
public class PlayerManager
{

	/** An array of the current players logged in to play Catan */
	public Player[] CatanPlayers;

	public Player[] getCatanPlayers() {
		return CatanPlayers;
	}

	public void setCatanPlayers(Player[] catanPlayers) {
		CatanPlayers = catanPlayers;
	}

	
	
	public PlayerTurnTracker turnTracker = null; 
	

	public PlayerTurnTracker getTurnTracker() {
		return turnTracker;
	}

	public void setTurnTracker(PlayerTurnTracker turnTracker) {
		this.turnTracker = turnTracker;
	}


}
