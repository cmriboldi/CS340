package model.players;

import shared.definitions.TurnType;
import shared.exceptions.player.*;

/**
 * The PlayerTurnTracker tracks whose turn it is, and who will go next
 * 
 * @author Christian Riboldi
 * @author Clayton Condie
 * @author Jacob Brewer
 * @author Joshua Powers
 * @author Joshua Van Steeter
 * @version 1.0 Build Jan, 2016.
 */
public class PlayerTurnTracker
{
	int turnIndex = -1; 
	TurnType status = null;
	
	public PlayerTurnTracker()
	{
		status = TurnType.FIRST_ROUND; 
		turnIndex = 0;
	}
	
	
	public void finishTurn() throws TurnIndexException
	{
		advanceTurn();
		setStatus(TurnType.ROLLING);
	}
	
	public PlayerTurnTracker(int turnIndex, TurnType status) throws TurnIndexException, InvalidTurnStatusException, GeneralPlayerException
	{
		if(status == null) {
			throw new InvalidTurnStatusException();
		}
		
		this.status = status; 
		this.turnIndex = turnIndex; 
		
		if (turnIndex < 0 | turnIndex > 3) {
			throw new TurnIndexException();
		}
	}

	public int getTurnIndex() {
		return turnIndex;
	}

	public void setTurnIndex(int turnIndex) {
		this.turnIndex = turnIndex;
	}

	public TurnType getStatus() {
		return status;
	}

	public void setStatus(TurnType status) {
		this.status = status;
	}

	/** Advances the turn index when a turn is over **/
	public void advanceTurn() throws TurnIndexException
	{
		if (turnIndex < 0 | turnIndex > 3)
			throw new TurnIndexException(); 
		
		if (turnIndex == 3)
			turnIndex = 0; 
		else
			turnIndex ++; 
		
		if (turnIndex < 0 | turnIndex > 3)
			throw new TurnIndexException();
	}

}
