package client.map.presenters;

import client.data.RobPlayerInfo;
import clientfacade.Facade;
import serverProxy.ServerException;
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
		int localPlayerId = Facade.getLocalPlayerInfo().getId(); 
		return Facade.getCatanModel().getOptions().canPlaceRoad(localPlayerId, edgeLoc); 
	}

	public boolean canPlaceSettlement(VertexLocation vertLoc)
	{
		int localPlayerId = Facade.getLocalPlayerInfo().getId(); 
		return Facade.getCatanModel().getOptions().canPlaceTown(localPlayerId, vertLoc); 
	}

	public boolean canPlaceCity(VertexLocation vertLoc)
	{
		int localPlayerId = Facade.getLocalPlayerInfo().getId(); 
		return Facade.getCatanModel().getOptions().canPlaceCity(localPlayerId, vertLoc); 
	}

	public boolean canPlaceRobber(HexLocation hexLoc)
	{
		int localPlayerId = Facade.getLocalPlayerInfo().getId(); 
		return Facade.getCatanModel().getOptions().canPlaceRobber(localPlayerId); 	
	}

	public void placeRoad(EdgeLocation edgeLoc) throws ServerException
	{
		// Doesn't need to check the canDo method? 
		int localPlayerId = 0;
		try {
			localPlayerId = Facade.getLocalPlayerInfo().getId();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Facade.buildRoad(localPlayerId,edgeLoc, false);
	}

	public void placeSettlement(VertexLocation vertLoc) 
	{
		int localPlayerId = Facade.getLocalPlayerInfo().getId(); 
		try {
			Facade.buildTown(localPlayerId, vertLoc, false);
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void placeCity(VertexLocation vertLoc)
	{
		int localPlayerId = Facade.getLocalPlayerInfo().getId(); 
		try {
			Facade.buildCity (localPlayerId, vertLoc);
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void placeRobber(HexLocation hexLoc)
	{
		int localPlayerId = Facade.getLocalPlayerInfo().getId(); 
		
		//JOSH's Code goes here
		
		//Facade.placeRobber(localPlayerId, victimIndex, hexLoc); // not called here because needs to be done
																	//because it's getting called by rob view

	}
	
	public void robPlayer(RobPlayerInfo victim)
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
