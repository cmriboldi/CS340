package client.map.presenters;

import client.data.RobPlayerInfo;
import shared.definitions.PieceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

/**
 * This is the state where a player can place one road and one settlement for free. This a state
 * that only happens twice for each player. Most of the methods and options will be disabled in this
 * state because the player can only place one settlement then an adjoining road. The
 * initFromModel() method will paint the entire board, however it will only allow players to select
 * and place a settlement followed by the placement of a road. This state transitions into the
 * MapInactiveState after the player ends their turn.
 * 
 * @author Christian Riboldi
 * @author Clayton Condie
 * @author Jacob Brewer
 * @author Joshua Powers
 * @author Joshua Van Steeter
 * @version 1.0 Build Feb, 2016.
 */
public class MapSetupState implements MapControllerState
{
	/**
	 * Initializes the MapControllerState from the Model passed in.
	 */
	public void initFromModel()
	{
		//TODO
	}

	public boolean canPlaceRoad(EdgeLocation edgeLoc)
	{
		//TODO
		return true;
	}

	public boolean canPlaceSettlement(VertexLocation vertLoc)
	{
		//TODO
		return true;
	}

	public boolean canPlaceCity(VertexLocation vertLoc)
	{
		return false;
	}

	public boolean canPlaceRobber(HexLocation hexLoc)
	{
		return false;
	}

	public void placeRoad(EdgeLocation edgeLoc)
	{
		// TODO 
	}

	public void placeSettlement(VertexLocation vertLoc)
	{
		//TODO Clayton
	}

	public void placeCity(VertexLocation vertLoc)
	{
		// do nothing
	}

	public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected)
	{
		// ??? Not sure what this does. 
	}

	public void cancelMove()
	{
		// ??? not sure what this does
	}

	public void playSoldierCard()
	{
		// do nothing
	}

	public void playRoadBuildingCard()
	{
		// do nothing	
	}


	
	@Override
	public boolean canPlaySoldier() {
		return false;
	}

	@Override
	public boolean canPlayRoadBuildingCard() {
		return false;
	}

	@Override
	public boolean canRobPlayer() {
		return false;
	}

	@Override
	public void placeRobber(HexLocation hexLoc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void robPlayer(RobPlayerInfo victim) {
		// TODO Auto-generated method stub
		
	}
}
