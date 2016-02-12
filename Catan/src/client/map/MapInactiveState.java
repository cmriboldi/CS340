package client.map;

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
public class MapInactiveState extends MapControllerState
{
	protected void initFromModel()
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
