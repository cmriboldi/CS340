package model.players;

import java.util.ArrayList;

import model.CatanModel;
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
	
	// 3 Turn Phases
	// roll
	// trade
	// build
	// 1. Rolling —> 2. Discarding (may be skipped) —> 3. Robbing (may be skipped) —> 4. Trade/Build/Play_Dev_Card

	
	int turnIndex = -1; 
	
	String status = null; 
	
	CatanModel catanModel = null; 
	

	public PlayerTurnTracker(CatanModel catanModel, int turnIndex, String status) throws TurnIndexException, InvalidTurnStatusException, GeneralPlayerException
	{
		this.status = status; 
		this.turnIndex = turnIndex; 
		this.catanModel = catanModel; 
		
		if (turnIndex < 0 | turnIndex > 3)
			throw new TurnIndexException(); 
		
		if (!(status.toLowerCase().equals("rolling") | status.toLowerCase().equals("discarding") | status.toLowerCase().equals("robbing") | status.toLowerCase().equals("playing")))       
			throw new InvalidTurnStatusException(); 
		
		if (catanModel == null)
		{
			throw new GeneralPlayerException(); 
		}
	}
	
	
	/**
	 * Queue that tracks the turn flow. Index 0 is the player with the current turn. Not that the
	 * queue is constructed using an ArrayList
	 **/
	ArrayList<Integer> TurnQueue;

	/** Advances the Queue when a turn is over **/
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
	
	public void advancePhase()
	{
		
	}
	

	
	
	
	
	

}
