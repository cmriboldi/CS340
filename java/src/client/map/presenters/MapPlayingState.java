package client.map.presenters;

import client.data.RobPlayerInfo;
import shared.definitions.PieceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

/**
 * This is the state that is the currentMapState when it’s player’s turn. In this state almost all
 * of the methods in the MapController will actually place settlements and roads (of course
 * depending on if they can.). The initFromModel() method will paint the entire board and allow
 * piece placement, the playing of development cards, and the offering of trades. This state will
 * transition the the MapInactiveState when the player ends their turn.
 * 
 * @author Christian Riboldi
 * @author Clayton Condie
 * @author Jacob Brewer
 * @author Joshua Powers
 * @author Joshua Van Steeter
 * @version 1.0 Build Feb, 2016.
 */
public class MapPlayingState implements MapControllerState
{
	/**
	 * Initializes the MapControllerState from the Model passed in.
	 */
	public void initFromModel()
	{
		//TODO Clayton
	}

	public boolean canPlaceRoad(EdgeLocation edgeLoc)
	{
		//TODO Clayton. Check UI placement location
		return true;
	}

	public boolean canPlaceSettlement(VertexLocation vertLoc)
	{
		//TODO Clayton
		return true;
	}

	public boolean canPlaceCity(VertexLocation vertLoc)
	{
		//TODO Clayton
		return true;
	}

	public boolean canPlaceRobber(HexLocation hexLoc)
	{
		//TODO 
		return true;
	}

	public void placeRoad(EdgeLocation edgeLoc)
	{
		//TODO
	}

	public void placeSettlement(VertexLocation vertLoc)
	{
		//TODO
	}

	public void placeCity(VertexLocation vertLoc)
	{
		//TODO
	}

	public void placeRobber(HexLocation hexLoc)
	{
		//TODO
	}

	public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected)
	{
		//TODO
	}

	public void cancelMove()
	{
		//TODO
	}

	public void playSoldierCard()
	{
		//TODO
	}

	public void playRoadBuildingCard()
	{
		//TODO
	}

	public void robPlayer(RobPlayerInfo victim)
	{
		//TODO
	}

	@Override
	public boolean canPlaySoldier() {
		//TODO
		return false;
	}

	@Override
	public boolean canPlayRoadBuildingCard() {
		// TODO
		return false;
	}

	@Override
	public boolean canRobPlayer() {
		// TODO
		return false;
	}
}
