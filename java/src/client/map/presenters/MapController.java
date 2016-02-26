package client.map.presenters;

import java.util.*;

import client.map.view.IMapView;
import client.map.view.IRobView;
import clientfacade.Facade;
//import com.sun.javafx.geom.Edge;
import model.CatanModel;
import model.map.Hex;
import model.map.Port;
import model.map.Road;
import model.map.Settlement;
import serverProxy.ServerException;
import shared.definitions.*;
import shared.exceptions.map.InvalidMapStateException;
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

        Facade.addObserverStatic(this);

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

        //System.out.println("LOG::MapController.update()::start");

        initFromModel();

        try {
            determineState();
        } catch (InvalidMapStateException e) {
            e.printStackTrace();
        }

    }

    // Test for 'Rolling' or 'Robbing' or 'Playing' or 'Discarding' or 'FirstRound' or 'SecondRound']

    public void determineState() throws InvalidMapStateException {
        int localPlayerIndex = Facade.getLocalPlayerInfo().getPlayerIndex();
        int indexOfPlayingClient = Facade.getCatanModel().getPlayerManager().getTurnTracker().getTurnIndex();
        String status = Facade.getCatanModel().getPlayerManager().getTurnTracker().getStatus();


        if (localPlayerIndex != indexOfPlayingClient | status.equals("Discarding")) {
            currentState = new MapInactiveState();
        } else if (status.equals("FirstRound") | status.equals("SecondRound")) {
            currentState = new MapSetupState();
        } else if (status.equals("Rolling") | status.equals("Robbing") | status.equals("Playing") | status.equals("Robbing")) {
            currentState = new MapPlayingState();
        } else {
            throw new InvalidMapStateException();
        }
    }


    protected void initFromModel() {

        //CLEAR THE VIEW
        //System.out.println("LOG::MapController.initFromModel()::start");

        //Acquire new model
        CatanModel model = Facade.getCatanModel();

        if (model != null) {
            //System.out.println("LOG::MapController.initFromModel(): model is not null");
            //initialize from said model
            /*
            1. Build Hexes, tile numbers, resources
			2. Place settlements
			3. Place roads
			4. Place ports
			5. Place robber
			 */


            //Add the hexes from the CatanModel, and add the tile numbers
            HashMap<HexLocation, Hex> hexes = model.getMapManager().getHexes();
            for (HexLocation hexLoc : hexes.keySet()) {
                Hex hex = hexes.get(hexLoc);
                getView().addHex(hexLoc, hex.getResource());
                //System.out.println("LOG::MapController.initFromModel::loadingHex\n\thexLoc: " + hexLoc.toString() + "\n\tresource: " + hex.getResource() + "\n\tnumber: " + hex.getResource());
                //getView().addNumber(hexLoc, hex.getNumber());
            }

            //Generate and add the ocean tiles
            HashSet<Hex> oceanTiles = genOcean(model.getMapManager().getMapRadius());
            for (Hex tile : oceanTiles) {
                getView().addHex(tile.location, tile.getResource());
            }


            //Add the settlements AND cities from the CatanModel
            HashMap<VertexLocation, Settlement> settlements = model.getMapManager().getSettlements();
            for (VertexLocation verLoc : settlements.keySet()) {
                Settlement settlement = settlements.get(verLoc);
                int playerId = settlement.getPlayer();
                CatanColor settlementColor = model.getPlayerManager().getCatanPlayers()[playerId].getColor();
                boolean isCity = settlement.isCity();

                if (isCity)
                    getView().placeCity(verLoc, settlementColor);
                else
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
        return currentState.canPlaceRoad(edgeLoc);
    }

    public boolean canPlaceSettlement(VertexLocation vertLoc) {
        return currentState.canPlaceSettlement(vertLoc);
    }

    public boolean canPlaceCity(VertexLocation vertLoc) {
        return currentState.canPlaceCity(vertLoc);
    }

    public boolean canPlaceRobber(HexLocation hexLoc) {
        return currentState.canPlaceRobber(hexLoc);
    }

    public void placeRoad(EdgeLocation edgeLoc) {
        try {
            currentState.placeRoad(edgeLoc);
        } catch (ServerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void placeSettlement(VertexLocation vertLoc) {
        try {
            currentState.placeSettlement(vertLoc);
        } catch (ServerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void placeCity(VertexLocation vertLoc) throws ServerException {
        currentState.placeCity(vertLoc);
    }


    public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected) {
        currentState.startMove(pieceType, isFree, allowDisconnected);
    }

    public void cancelMove() {
        currentState.cancelMove();
    }

    public void playSoldierCard() {
        currentState.playSoldierCard();
    }

    public void playRoadBuildingCard() {
        currentState.playRoadBuildingCard();
    }

    public void robPlayer(RobPlayerInfo victim) {
        currentState.robPlayer(victim);
    }

    public MapControllerState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(MapControllerState currentState) {
        this.currentState = currentState;
    }

    @Override
    public void placeRobber(HexLocation hexLoc) {
        currentState.placeRobber(hexLoc);
    }

    private HashSet<Hex> genOcean(int radius) {
        HashSet<Hex> oceanTiles = new HashSet<Hex>();

        int xWait = 0;
        int yWait = radius;

        boolean xBehaviorPos = false;
        boolean yBehaviorPos = false;

        int currentX = 0;
        int currentY = radius;

        int oceanTileCount = 6 * radius;
        for (int i = 0; i < oceanTileCount; i++) {
            //create a oceanHex
            oceanTiles.add(new Hex(currentX, currentY, "WATER", 0));

            //alter X
            if (xWait == 0) {
                //change x based on current xBehavior
                if (xBehaviorPos) {
                    currentX++;
                    if (currentX == radius) {
                        xWait = radius;
                        xBehaviorPos = false;
                    }
                } else {
                    currentX--;
                    if (currentX == -radius) {
                        xWait = radius;
                        xBehaviorPos = true;
                    }

                }
            } else {
                xWait--;
            }

            //alterY
            if (yWait == 0) {
                //change y based on current xBehavior
                if (yBehaviorPos) {
                    currentY++;
                    if (currentY == radius) {
                        yWait = radius;
                        yBehaviorPos = false;
                    }
                } else {
                    currentY--;
                    if (currentY == -radius) {
                        yWait = radius;
                        yBehaviorPos = true;
                    }

                }
            } else {
                yWait--;
            }
        }

        return oceanTiles;
    }

}

