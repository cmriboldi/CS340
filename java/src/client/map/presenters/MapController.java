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
        System.out.println("(((Map Update)))");

        initFromModel();

        try {
            determineState();
        } catch (InvalidMapStateException e) {
            e.printStackTrace();
        }
        
        if (currentState.getClass().toString().equals(new MapSetupState().getClass().toString()))
        {
        	//MODAL ERROR HERE
        	//currentState.startMove(pieceType, isFree, allowDisconnected, mapView);
        	
        	int roadsRemaining = Facade.getCatanModel().getPlayerManager().getCatanPlayers()[Facade.getLocalPlayerIndex()].getRoadsRemaining();  
        	int settlementsRemaining = Facade.getCatanModel().getPlayerManager().getCatanPlayers()[Facade.getLocalPlayerIndex()].getRoadsRemaining();
        	String status = Facade.getCatanModel().getPlayerManager().getTurnTracker().getStatus(); 
        	
        	System.out.println("SETUPT:: " + settlementsRemaining + " " + roadsRemaining + " " + status);
        	
        	if (status.toLowerCase().equals("firstround")&&roadsRemaining == 15)
        	{
        		System.out.println("(1)"); 
            	currentState.startMove(PieceType.ROAD, true, true, getView());
        	}
        	else if (status.toLowerCase().equals("firstround")&&roadsRemaining == 14&&settlementsRemaining == 5)
        	{
        		System.out.println("(2)"); 
            	currentState.startMove(PieceType.SETTLEMENT, true, false, getView());
        	}
        	else if (status.toLowerCase().equals("secondround")&&roadsRemaining == 14)
        	{
        		System.out.println("(3)"); 
            	currentState.startMove(PieceType.ROAD, true, true, getView());
        	}
        	else if (status.toLowerCase().equals("secondround")&&roadsRemaining == 13&&settlementsRemaining == 4)
        	{
        		System.out.println("(4)"); 
            	currentState.startMove(PieceType.SETTLEMENT, true, false, getView());
        	}
        	else
        	{
        		try {
					Facade.getProxy().finishTurn(Facade.getLocalPlayerIndex());
				} catch (ServerException e) {
					e.printStackTrace();
				}
        	}
        }

        System.out.println("CLASS:" + currentState.getClass().toString());

    }


    // Test for 'Rolling' or 'Robbing' or 'Playing' or 'Discarding' or 'FirstRound' or 'SecondRound']

    public void determineState() throws InvalidMapStateException {
        int localPlayerIndex = Facade.getLocalPlayerInfo().getPlayerIndex();
        int indexOfPlayingClient = Facade.getCatanModel().getPlayerManager().getTurnTracker().getTurnIndex();
        String status = Facade.getCatanModel().getPlayerManager().getTurnTracker().getStatus();

        System.out.println("@@Determining State");


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

            System.out.println("@@@NEW MAP!");
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
        }
    }

    public boolean canPlaceRoad(EdgeLocation edgeLoc) {
        boolean returnThis = currentState.canPlaceRoad(edgeLoc);
        System.out.format("\tMapController:: canPlaceRoad: edgeLoc {%s} - canPlaceRoad {%s}%n", edgeLoc, returnThis);

        return returnThis;
    }

    public boolean canPlaceSettlement(VertexLocation vertLoc) {
        System.out.println("MAP CONTROLLER --->  canPlaceSettlement");
        return currentState.canPlaceSettlement(vertLoc);
    }

    public boolean canPlaceCity(VertexLocation vertLoc) {
        boolean returnThis = currentState.canPlaceCity(vertLoc);
        System.out.format("\tMapController:: canPlaceRoad: vertLoc {%s} - canPlaceCity {%s}%n", vertLoc, returnThis);

        return returnThis;
    }

    public boolean canPlaceRobber(HexLocation hexLoc) {
        return currentState.canPlaceRobber(hexLoc);
    }

    public void placeRoad(EdgeLocation edgeLoc) {
        System.out.println("MAP CONTROLLER --->  PLACE ROAD");

        try {
            currentState.placeRoad(edgeLoc);
        } catch (ServerException e) {
            e.printStackTrace();
        }
    }

    public void placeSettlement(VertexLocation vertLoc) {
        System.out.println("MAP CONTROLLER --->  PLACE SETTLEMENT");
        try {
            currentState.placeSettlement(vertLoc);
        } catch (ServerException e) {
            e.printStackTrace();
        }
    }

    public void placeCity(VertexLocation vertLoc) throws ServerException {
        currentState.placeCity(vertLoc);
    }


    public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected) {
        System.out.format("MapController:: startMove: PieceType {%s} - isFree {%s} - allowDisconnected {%s}%n", pieceType, isFree, allowDisconnected);
        currentState.startMove(pieceType, isFree, allowDisconnected, getView());
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
        currentState.placeRobber(hexLoc, getRobView());
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

