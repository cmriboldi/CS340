package client.map.presenters;

import client.data.RobPlayerInfo;
import client.map.view.IMapView;
import client.map.view.IRobView;
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
	
	@Override
	public boolean canPlaceRoad(EdgeLocation edgeLoc)
	{
		int localPlayerId = Facade.getLocalPlayerInfo().getId(); 
		return Facade.getCatanModel().getOptions().canPlaceRoad(localPlayerId, edgeLoc); 
	}

	@Override
	public boolean canPlaceSettlement(VertexLocation vertLoc)
	{
		int localPlayerId = Facade.getLocalPlayerInfo().getId(); 
		return Facade.getCatanModel().getOptions().canPlaceTown(localPlayerId, vertLoc); 
	}

	@Override
	public boolean canPlaceCity(VertexLocation vertLoc)
	{
		int localPlayerId = Facade.getLocalPlayerInfo().getId(); 
		return Facade.getCatanModel().getOptions().canPlaceCity(localPlayerId, vertLoc); 
	}

	@Override
	public boolean canPlaceRobber(HexLocation hexLoc)
	{
		int localPlayerId = Facade.getLocalPlayerInfo().getId(); 
		return Facade.getCatanModel().getOptions().canPlaceRobber(localPlayerId); 	
	}

	@Override
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

	@Override
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

	@Override
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

	@Override
	public void placeRobber(HexLocation hexLoc, IRobView RobView)
	{
		
		//RobView.setPlayers(Facade.getPlayersOnHex(hexLoc));
		if(!RobView.isModalShowing())
			RobView.showModal();
	}
	
	@Override
	public void robPlayer(RobPlayerInfo victim)
	{
		try {
			Facade.robPlayer(victim.getPlayerIndex());
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected, IMapView mapView)
	{
		//getView().startDrop()
	}

	@Override
	public void cancelMove()
	{
		//TODO
	}

	@Override
	public void playSoldierCard()
	{
		//TODO
	}

	@Override
	public void playRoadBuildingCard()
	{
		//TODO
	}

	/*
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
	}*/
}
