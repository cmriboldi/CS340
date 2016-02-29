package client.map.presenters;

import client.data.RobPlayerInfo;
import client.map.view.IMapView;
import client.map.view.IRobView;
import shared.definitions.PieceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

/**
 * This is the state of the game for all players who aren’t currently in the MapPlayingState, or
 * MapSetupState. The initFromModel() method will paint the map however the only enabled option will
 * be the chat option and the makeTradeOffer option. All options which concern the map will be
 * disabled. This means that almost all of the methods for the MapController not do anything because
 * it’s not the player’s turn.
 * 
 * @author Christian Riboldi
 * @author Clayton Condie
 * @author Jacob Brewer
 * @author Joshua Powers
 * @author Joshua Van Steeter
 * @version 1.0 Build Feb, 2016.
 */
public class MapInactiveState implements MapControllerState
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
		return false;
	}

	@Override
	public boolean canPlaceSettlement(VertexLocation vertLoc)
	{
		return false;
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
		// do nothing
	}

	@Override
	public void placeSettlement(VertexLocation vertLoc)
	{
		// do nothing
	}

	@Override
	public void placeCity(VertexLocation vertLoc)
	{
		// do nothing
	}

	@Override
	public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected, IMapView mapView)
	{
		// do nothing
	}

	@Override
	public void cancelMove()
	{
		// do nothing
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
	public void robPlayer(RobPlayerInfo victim, IRobView RobView) {
		// TODO Auto-generated method stub
		
	}
}
