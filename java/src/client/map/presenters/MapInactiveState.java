package client.map.presenters;

import client.data.RobPlayerInfo;
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
	public void initFromModel()
	{
		//TODO
	}

	public boolean canPlaceRoad(EdgeLocation edgeLoc)
	{
		return false;
	}

	public boolean canPlaceSettlement(VertexLocation vertLoc)
	{
		return false;
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
		// do nothing
	}

	public void placeSettlement(VertexLocation vertLoc)
	{
		// do nothing
	}

	public void placeCity(VertexLocation vertLoc)
	{
		// do nothing
	}


	public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected)
	{
		// do nothing
	}

	public void cancelMove()
	{
		// do nothing
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
