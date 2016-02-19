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
public class MapPlayingState extends MapControllerState
{
	/**
	 * Initializes the MapControllerState from the Model passed in.
	 */
	public void initFromModel()
	{

	}

	public boolean canPlaceRoad(EdgeLocation edgeLoc)
	{
		return true;
	}

	public boolean canPlaceSettlement(VertexLocation vertLoc)
	{
		return true;
	}

	public boolean canPlaceCity(VertexLocation vertLoc)
	{
		return true;
	}

	public boolean canPlaceRobber(HexLocation hexLoc)
	{
		return true;
	}

	public void placeRoad(EdgeLocation edgeLoc)
	{

	}

	public void placeSettlement(VertexLocation vertLoc)
	{

	}

	public void placeCity(VertexLocation vertLoc)
	{

	}

	public void placeRobber(HexLocation hexLoc)
	{

	}

	public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected)
	{

	}

	public void cancelMove()
	{

	}

	public void playSoldierCard()
	{

	}

	public void playRoadBuildingCard()
	{

	}

	public void robPlayer(RobPlayerInfo victim)
	{

	}
}
