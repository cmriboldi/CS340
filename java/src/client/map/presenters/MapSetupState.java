package client.map.presenters;

import client.data.RobPlayerInfo;
import client.map.view.IMapView;
import client.map.view.IRobView;
import clientfacade.Facade;
import serverProxy.ServerException;
import shared.definitions.CatanColor;
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
		//
	}

	@Override
	public boolean canPlaceRoad(EdgeLocation edgeLoc)
	{
		int localPlayerIndex = Facade.getLocalPlayerIndex();
		if(localPlayerIndex == -1)
			return false;

		return Facade.getCatanModel().getMapManager().canPlaceRoadSetup(edgeLoc, localPlayerIndex); //.getOptions().canPlaceRoad(localPlayerIndex, edgeLoc); // DURING SETUP
	}

	@Override
	public boolean canPlaceSettlement(VertexLocation vertLoc)
	{
		int localPlayerId = Facade.getLocalPlayerInfo().getId(); 
		return Facade.getCatanModel().getOptions().canPlaceTown(localPlayerId, vertLoc);  // DURING SETUP
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
		int localPlayerIndex = Facade.getLocalPlayerIndex();
		try {
			Facade.buildRoad(localPlayerIndex, edgeLoc, true);
		} catch (ServerException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void placeSettlement(VertexLocation vertLoc)
	{
		int localPlayerId = Facade.getLocalPlayerInfo().getId(); 
		try {
			Facade.buildTown(localPlayerId, vertLoc, true);
		} catch (ServerException e) {
			e.printStackTrace();
		}
	}



	@Override
	public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected, IMapView mapView)
	{
		System.out.format("MapSetupState:: startMove: start");

		CatanColor pieceColor = Facade.getCatanModel().getPlayerManager().getPlayerByIndex(Facade.getLocalPlayerIndex()).getColor();
		System.out.format("Map Setup ---> StartMove: pieceType {%s} - isFree {%s} - allowDisconnected {%s} - localPlayerColor {%s} - isCancelable {true}%n", pieceType, isFree, allowDisconnected, pieceColor);
		mapView.startDrop(pieceType, pieceColor, true);
	}

	@Override
	public void placeCity(VertexLocation vertLoc)
	{
		// do nothing
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

	@Override
	public void placeRobber(HexLocation hexLoc, IRobView RobView) {
		// Do nothing
		
	}

	@Override
	public void robPlayer(RobPlayerInfo victim) {
		// Do nothing 
		
	}
}
