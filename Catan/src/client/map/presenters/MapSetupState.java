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
public class MapSetupState extends MapControllerState
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
