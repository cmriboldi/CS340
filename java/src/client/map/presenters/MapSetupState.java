package client.map.presenters;

import client.data.RobPlayerInfo;
import client.map.view.IMapView;
import client.map.view.IRobView;
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
	@Override
	public void initFromModel()
	{
		//TODO
	}

	@Override
	public boolean canPlaceRoad(EdgeLocation edgeLoc)
	{
		//TODO
		return true;
	}

	@Override
	public boolean canPlaceSettlement(VertexLocation vertLoc)
	{
		//TODO
		return true;
	}

	@Override
	public boolean canPlaceCity(VertexLocation vertLoc)
	{
		return false;
	}

	@Override
	public boolean canPlaceRobber(HexLocation hexLoc)
	{
		return false;
	}

	@Override
	public void placeRoad(EdgeLocation edgeLoc)
	{
		// TODO 
	}

	@Override
	public void placeSettlement(VertexLocation vertLoc)
	{
		//TODO Clayton
	}

	@Override
	public void placeCity(VertexLocation vertLoc)
	{
		// do nothing
	}

	@Override
	public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected, IMapView mapView)
	{
		// ??? Not sure what this does. 
	}

	@Override
	public void cancelMove()
	{
		// ??? not sure what this does
	}

	@Override
	public void playSoldierCard()
	{
		// do nothing
	}

	@Override
	public void playRoadBuildingCard()
	{
		// do nothing	
	}


	/*
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
	}*/

	@Override
	public void placeRobber(HexLocation hexLoc, IRobView RobView) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void robPlayer(RobPlayerInfo victim) {
		// TODO Auto-generated method stub
		
	}
}
