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
    int roadBuilderCount = 0; 
    boolean playedRoadBuilder = false; 

    boolean playedRoadBuilding = false; 


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


        initFromModel();

        try {
            determineState();
        } catch (InvalidMapStateException e) {
            e.printStackTrace();
        }
        

        if(Facade.getTurnStatus().equals("Robbing") && Facade.isMyturn())
        {
        	getView().startDrop(PieceType.ROBBER, null, false);
        }
        
        if (playedRoadBuilding == true)
        {
        	getView().startDrop(PieceType.ROAD, Facade.getLocalPlayerInfo().getColor(), false);
        	playedRoadBuilding = false; 
        }
        
        
        if (currentState.getClass().toString().equals(new MapSetupState().getClass().toString()))
        {
        	//MODAL ERROR HERE
        	//currentState.startMove(pieceType, isFree, allowDisconnected, mapView);
        	
        	int roadsRemaining = Facade.getCatanModel().getPlayerManager().getCatanPlayers()[Facade.getLocalPlayerIndex()].getRoadsRemaining();  
        	int settlementsRemaining = Facade.getCatanModel().getPlayerManager().getCatanPlayers()[Facade.getLocalPlayerIndex()].getSettlementsRemaining();
        	String status = Facade.getCatanModel().getPlayerManager().getTurnTracker().getStatus(); 
        	
        	if (status.toLowerCase().equals("firstround")&&roadsRemaining == 15&&settlementsRemaining == 5)
        	{
            	currentState.startMove(PieceType.SETTLEMENT, true, false, getView());
        	}
        	else if (status.toLowerCase().equals("firstround")&&roadsRemaining == 15&&settlementsRemaining == 4)
        	{
            	currentState.startMove(PieceType.ROAD, true, true, getView());
        	}
        	else if (status.toLowerCase().equals("secondround")&&roadsRemaining == 14&&settlementsRemaining == 4)
        	{
            	currentState.startMove(PieceType.SETTLEMENT, true, false, getView());
        	}
        	else if (status.toLowerCase().equals("secondround")&&roadsRemaining == 14&&settlementsRemaining == 3)
        	{
            	currentState.startMove(PieceType.ROAD, true, true, getView());
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
            HashMap<HexLocation, Hex> hexes = model.getMapManager().getHexes();
            for (HexLocation hexLoc : hexes.keySet()) {
                Hex hex = hexes.get(hexLoc);
                getView().addHex(hexLoc, hex.getResource());
                int hexNumber = hex.getNumber();
                if((hexNumber >= 2) && (hexNumber <= 12) && (hexNumber != 7))
                    getView().addNumber(hexLoc, hex.getNumber());
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

        return returnThis;
    }

    public boolean canPlaceSettlement(VertexLocation vertLoc) {
        boolean returnThis = currentState.canPlaceSettlement(vertLoc);

        return returnThis;
    }

    public boolean canPlaceCity(VertexLocation vertLoc) {
        boolean returnThis = currentState.canPlaceCity(vertLoc);

        return returnThis;
    }

    public boolean canPlaceRobber(HexLocation hexLoc) {
        return currentState.canPlaceRobber(hexLoc);
    }

    public void placeRoad(EdgeLocation edgeLoc) {

        try {
            currentState.placeRoad(edgeLoc);
        } catch (ServerException e) {
            e.printStackTrace();
        }
    }

    public void placeSettlement(VertexLocation vertLoc) {
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
        currentState.startMove(pieceType, isFree, allowDisconnected, getView());
    }

    public void cancelMove() {
        currentState.cancelMove();
    }

    public void playSoldierCard() {
    	System.out.println("HEREEEEEEEEEEEEE #2");
        currentState.playSoldierCard(getView());
    }

    public void playRoadBuildingCard() {

    	playedRoadBuilding = true; 
    	getView().startDrop(PieceType.ROAD, Facade.getLocalPlayerInfo().getColor(), false);
    	        
    	//currentState.playRoadBuildingCard();
    }

    public void robPlayer(RobPlayerInfo victim) {
        currentState.robPlayer(victim, getRobView());
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

