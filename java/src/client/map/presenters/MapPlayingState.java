package client.map.presenters;

import client.data.RobPlayerInfo;
import client.map.view.IMapView;
import client.map.view.IRobView;
import clientfacade.Facade;
import serverProxy.ServerException;
import shared.definitions.CatanColor;
import shared.definitions.PieceType;
import shared.definitions.TurnType;
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
		int localPlayerIndex = Facade.getLocalPlayerIndex();
		if(localPlayerIndex == -1)
			return false;

		return Facade.getCatanModel().getOptions().canPlaceRoad(localPlayerIndex, edgeLoc);
	}

	@Override
	public boolean canPlaceSettlement(VertexLocation vertLoc)
	{
		int localPlayerIndex = Facade.getLocalPlayerIndex();
		if(localPlayerIndex == -1)
			return false;

		return Facade.getCatanModel().getOptions().canPlaceTown(localPlayerIndex, vertLoc);
	}

	@Override
	public boolean canPlaceCity(VertexLocation vertLoc)
	{
		int localPlayerIndex = Facade.getLocalPlayerIndex();
		return Facade.getCatanModel().getOptions().canPlaceCity(localPlayerIndex, vertLoc);
	}

	@Override
	public boolean canPlaceRobber(HexLocation hexLoc)
	{
		return Facade.getCatanModel().getOptions().canPlaceRobber(hexLoc);
	}

	@Override
	public void placeRoad(EdgeLocation edgeLoc) throws ServerException
	{
		// Doesn't need to check the canDo method?
		int localPlayerIndex = Facade.getLocalPlayerIndex();
		Facade.buildRoad(localPlayerIndex, edgeLoc, false);
	}

	@Override
	public void placeSettlement(VertexLocation vertLoc) throws ServerException {
		int localPlayerIndex = Facade.getLocalPlayerIndex();
		Facade.buildTown(localPlayerIndex, vertLoc, false);
	}

	@Override
	public void placeCity(VertexLocation vertLoc) throws ServerException {
		int localPlayerIndex = Facade.getLocalPlayerIndex();
		Facade.buildCity (localPlayerIndex, vertLoc);
	}

	private HexLocation robLocation;
	
	@Override
	public void placeRobber(HexLocation hexLoc, IRobView RobView)
	{
		robLocation = hexLoc;
		RobView.setPlayers(Facade.getPlayersOnHex(hexLoc));
		if(!RobView.isModalShowing())
			RobView.showModal();
	}
	
	@Override
	public void robPlayer(RobPlayerInfo victim, IRobView RobView)
	{
		try {
			if(Facade.getTurnStatus() == TurnType.ROBBING)
				Facade.robPlayer(victim.getPlayerIndex(), robLocation);
			else
				Facade.playSoldierCard(victim.getPlayerIndex(), robLocation);
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected, IMapView mapView)
	{
		CatanColor pieceColor = Facade.getPlayerColor(); 
		
		mapView.startDrop(pieceType, pieceColor, true);
	}

	@Override
	public void cancelMove()
	{
		//TODO
	}

	@Override
	public void playSoldierCard(IMapView mapView)
	{
		mapView.startDrop(PieceType.ROBBER, null, false);
	}

	@Override
	public void playRoadBuildingCard(IMapView mapView)
	{
    	CatanColor playerColor = Facade.getPlayerColor();
    	mapView.startDrop(PieceType.ROAD, playerColor, false);
	}

}
