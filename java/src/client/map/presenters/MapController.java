package client.map.presenters;

import java.util.*;

import client.map.view.IMapView;
import client.map.view.IRobView;
import clientfacade.Facade;
import com.sun.javafx.geom.Edge;
import model.CatanModel;
import model.map.Hex;
import model.map.Port;
import model.map.Road;
import model.map.Settlement;
import shared.definitions.*;
import shared.locations.*;
import client.base.*;
import client.data.*;


/**
 * Implementation for the map controller
 */
public class MapController extends Controller implements IMapController, Observer {

    private IRobView robView;
    private MapControllerState currentState;

    public MapController(IMapView view, IRobView robView) {

        super(view);

        setRobView(robView);

        initFromModel();
    }

    public IMapView getView() {

        return (IMapView) super.getView();
    }

    private IRobView getRobView() {
        return robView;
    }

    private void setRobView(IRobView robView) {
        this.robView = robView;
    }

    @Override
    public void update(Observable o, Object arg) {
        initFromModel();
    }


    protected void initFromModel() {

        //CLEAR THE VIEW

        //Acquire new model
        CatanModel model = Facade.getCatanModel();

        if (model != null) {
            //initialize from said model
            /*
            1. Build Hexes, tile numbers, resources
			2. Place settlements
			3. Place roads
			4. Place ports
			5. Place robber
			 */

            //Add the hexes from the CatanModel, and add the tile numbers
            //POTENTIAL NEED: HANDLE ROBBER TILE
            HashMap<HexLocation, Hex> hexes = model.getMapManager().getHexes();
            for (HexLocation hexLoc : hexes.keySet()) {
                Hex hex = hexes.get(hexLoc);
                getView().addHex(hexLoc, HexType.valueOf(hex.getResource()));
                getView().addNumber(hexLoc, hex.getNumber());
            }

            //Add the settlements AND cities from the CatanModel
            //NEEDS TO INCORPORATE CITY FUNCTIONS
            HashMap<VertexLocation, Settlement> settlements = model.getMapManager().getSettlements();
            for (VertexLocation verLoc : settlements.keySet()) {
                Settlement settlement = settlements.get(verLoc);
                int playerId = settlement.getPlayer();
                CatanColor settlementColor = Facade.getCatanModel().getPlayerManager().getCatanPlayers()[playerId].getColor();
                getView().placeSettlement(verLoc, settlementColor);
            }

            //Add the roads from the CatanModel
            HashMap<EdgeLocation, Road> roads = model.getMapManager().getRoads();
            for (EdgeLocation edge : roads.keySet()) {

                Road road = roads.get(edge);
                int playerId = road.getOwner();
                CatanColor roadColor = Facade.getCatanModel().getPlayerManager().getCatanPlayers()[playerId].getColor();
                getView().placeRoad(edge, roadColor);
            }

            //Add the ports from the CatanModel
            HashMap<EdgeLocation, Port> ports = model.getMapManager().getPorts();
            for (EdgeLocation edge : ports.keySet()) {
                Port port = ports.get(edge);
                PortType portType = port.getType();
                getView().addPort(edge, portType);
            }

            //Add the Robber
            HexLocation robber = model.getMapManager().getRobber();
            getView().placeRobber(robber);


        } else {
            //<temp>
        /*

		    Random rand = new Random();

        for (int x = 0; x <= 3; ++x) {

			int maxY = 3 - x;
			for (int y = -3; y <= maxY; ++y) {
				int r = rand.nextInt(HexType.values().length);
				HexType hexType = HexType.values()[r];
				HexLocation hexLoc = new HexLocation(x, y);
				getView().addHex(hexLoc, hexType);
				getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.NorthWest),
						CatanColor.RED);
				getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.SouthWest),
						CatanColor.BLUE);
				getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.South),
						CatanColor.ORANGE);
				getView().placeSettlement(new VertexLocation(hexLoc,  VertexDirection.NorthWest), CatanColor.GREEN);
				getView().placeCity(new VertexLocation(hexLoc,  VertexDirection.NorthEast), CatanColor.PURPLE);
			}

			if (x != 0) {
				int minY = x - 3;
				for (int y = minY; y <= 3; ++y) {
					int r = rand.nextInt(HexType.values().length);
					HexType hexType = HexType.values()[r];
					HexLocation hexLoc = new HexLocation(-x, y);
					getView().addHex(hexLoc, hexType);
					getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.NorthWest),
							CatanColor.RED);
					getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.SouthWest),
							CatanColor.BLUE);
					getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.South),
							CatanColor.ORANGE);
					getView().placeSettlement(new VertexLocation(hexLoc,  VertexDirection.NorthWest), CatanColor.GREEN);
					getView().placeCity(new VertexLocation(hexLoc,  VertexDirection.NorthEast), CatanColor.PURPLE);
				}
			}
		}

		PortType portType = PortType.BRICK;
		getView().addPort(new EdgeLocation(new HexLocation(0, 3), EdgeDirection.North), portType);
		getView().addPort(new EdgeLocation(new HexLocation(0, -3), EdgeDirection.South), portType);
		getView().addPort(new EdgeLocation(new HexLocation(-3, 3), EdgeDirection.NorthEast), portType);
		getView().addPort(new EdgeLocation(new HexLocation(-3, 0), EdgeDirection.SouthEast), portType);
		getView().addPort(new EdgeLocation(new HexLocation(3, -3), EdgeDirection.SouthWest), portType);
		getView().addPort(new EdgeLocation(new HexLocation(3, 0), EdgeDirection.NorthWest), portType);

		getView().placeRobber(new HexLocation(0, 0));

		getView().addNumber(new HexLocation(-2, 0), 2);
		getView().addNumber(new HexLocation(-2, 1), 3);
		getView().addNumber(new HexLocation(-2, 2), 4);
		getView().addNumber(new HexLocation(-1, 0), 5);
		getView().addNumber(new HexLocation(-1, 1), 6);
		getView().addNumber(new HexLocation(1, -1), 8);
		getView().addNumber(new HexLocation(1, 0), 9);
		getView().addNumber(new HexLocation(2, -2), 10);
		getView().addNumber(new HexLocation(2, -1), 11);
		getView().addNumber(new HexLocation(2, 0), 12);

		*/
            //</temp>
        }


    }

    public boolean canPlaceRoad(EdgeLocation edgeLoc) {

        return true;
    }

    public boolean canPlaceSettlement(VertexLocation vertLoc) {

        return true;
    }

    public boolean canPlaceCity(VertexLocation vertLoc) {

        return true;
    }

    public boolean canPlaceRobber(HexLocation hexLoc) {

        return true;
    }

    public void placeRoad(EdgeLocation edgeLoc) {

        getView().placeRoad(edgeLoc, CatanColor.ORANGE);
    }

    public void placeSettlement(VertexLocation vertLoc) {

        getView().placeSettlement(vertLoc, CatanColor.ORANGE);
    }

    public void placeCity(VertexLocation vertLoc) {

        getView().placeCity(vertLoc, CatanColor.ORANGE);
    }

    public void placeRobber(HexLocation hexLoc) {

        getView().placeRobber(hexLoc);

        getRobView().showModal();
    }

    public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected) {

        getView().startDrop(pieceType, CatanColor.ORANGE, true);
    }

    public void cancelMove() {

    }

    public void playSoldierCard() {

    }

    public void playRoadBuildingCard() {

    }

    public void robPlayer(RobPlayerInfo victim) {

    }

    public MapControllerState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(MapControllerState currentState) {
        this.currentState = currentState;
    }
}

