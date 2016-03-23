package model.map;

import model.CatanModel;
import model.map.Map;
import model.map.Port;
import model.map.Road;
import model.map.Settlement;
import model.resources.ResourceList;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import serverProxy.JSONDeserializer;
import shared.definitions.PortType;
import shared.exceptions.player.GeneralPlayerException;
import shared.exceptions.player.InvalidTurnStatusException;
import shared.exceptions.player.TurnIndexException;
import shared.locations.*;
import test.TestJSON;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class TestMap {

    static Map map;

    @Before
    public void setUp() throws GeneralPlayerException, InvalidTurnStatusException, TurnIndexException {
        CatanModel model = JSONDeserializer.deserialize("{\"deck\":{\"yearOfPlenty\":2,\"monopoly\":2,\"soldier\":14,\"roadBuilding\":2,\"monument\":5},\"map\":{\"hexes\":[{\"location\":{\"x\":0,\"y\":-2}},{\"resource\":\"brick\",\"location\":{\"x\":1,\"y\":-2},\"number\":4},{\"resource\":\"wood\",\"location\":{\"x\":2,\"y\":-2},\"number\":11},{\"resource\":\"brick\",\"location\":{\"x\":-1,\"y\":-1},\"number\":8},{\"resource\":\"wood\",\"location\":{\"x\":0,\"y\":-1},\"number\":3},{\"resource\":\"ore\",\"location\":{\"x\":1,\"y\":-1},\"number\":9},{\"resource\":\"sheep\",\"location\":{\"x\":2,\"y\":-1},\"number\":12},{\"resource\":\"ore\",\"location\":{\"x\":-2,\"y\":0},\"number\":5},{\"resource\":\"sheep\",\"location\":{\"x\":-1,\"y\":0},\"number\":10},{\"resource\":\"wheat\",\"location\":{\"x\":0,\"y\":0},\"number\":11},{\"resource\":\"brick\",\"location\":{\"x\":1,\"y\":0},\"number\":5},{\"resource\":\"wheat\",\"location\":{\"x\":2,\"y\":0},\"number\":6},{\"resource\":\"wheat\",\"location\":{\"x\":-2,\"y\":1},\"number\":2},{\"resource\":\"sheep\",\"location\":{\"x\":-1,\"y\":1},\"number\":9},{\"resource\":\"wood\",\"location\":{\"x\":0,\"y\":1},\"number\":4},{\"resource\":\"sheep\",\"location\":{\"x\":1,\"y\":1},\"number\":10},{\"resource\":\"wood\",\"location\":{\"x\":-2,\"y\":2},\"number\":6},{\"resource\":\"ore\",\"location\":{\"x\":-1,\"y\":2},\"number\":3},{\"resource\":\"wheat\",\"location\":{\"x\":0,\"y\":2},\"number\":8}],\"roads\":[{\"owner\":1,\"location\":{\"direction\":\"S\",\"x\":-1,\"y\":-1}},{\"owner\":3,\"location\":{\"direction\":\"SW\",\"x\":-1,\"y\":1}},{\"owner\":3,\"location\":{\"direction\":\"SW\",\"x\":2,\"y\":-2}},{\"owner\":2,\"location\":{\"direction\":\"S\",\"x\":1,\"y\":-1}},{\"owner\":0,\"location\":{\"direction\":\"S\",\"x\":0,\"y\":1}},{\"owner\":2,\"location\":{\"direction\":\"S\",\"x\":0,\"y\":0}},{\"owner\":1,\"location\":{\"direction\":\"SW\",\"x\":-2,\"y\":1}},{\"owner\":0,\"location\":{\"direction\":\"SW\",\"x\":2,\"y\":0}}],\"cities\":[],\"settlements\":[{\"owner\":3,\"location\":{\"direction\":\"SW\",\"x\":-1,\"y\":1}},{\"owner\":3,\"location\":{\"direction\":\"SE\",\"x\":1,\"y\":-2}},{\"owner\":2,\"location\":{\"direction\":\"SW\",\"x\":0,\"y\":0}},{\"owner\":2,\"location\":{\"direction\":\"SW\",\"x\":1,\"y\":-1}},{\"owner\":1,\"location\":{\"direction\":\"SW\",\"x\":-2,\"y\":1}},{\"owner\":0,\"location\":{\"direction\":\"SE\",\"x\":0,\"y\":1}},{\"owner\":1,\"location\":{\"direction\":\"SW\",\"x\":-1,\"y\":-1}},{\"owner\":0,\"location\":{\"direction\":\"SW\",\"x\":2,\"y\":0}}],\"radius\":3,\"ports\":[{\"ratio\":3,\"direction\":\"NW\",\"location\":{\"x\":2,\"y\":1}},{\"ratio\":2,\"resource\":\"brick\",\"direction\":\"NE\",\"location\":{\"x\":-2,\"y\":3}},{\"ratio\":3,\"direction\":\"SW\",\"location\":{\"x\":3,\"y\":-3}},{\"ratio\":3,\"direction\":\"N\",\"location\":{\"x\":0,\"y\":3}},{\"ratio\":2,\"resource\":\"wood\",\"direction\":\"NE\",\"location\":{\"x\":-3,\"y\":2}},{\"ratio\":3,\"direction\":\"SE\",\"location\":{\"x\":-3,\"y\":0}},{\"ratio\":2,\"resource\":\"wheat\",\"direction\":\"S\",\"location\":{\"x\":-1,\"y\":-2}},{\"ratio\":2,\"resource\":\"ore\",\"direction\":\"S\",\"location\":{\"x\":1,\"y\":-3}},{\"ratio\":2,\"resource\":\"sheep\",\"direction\":\"NW\",\"location\":{\"x\":3,\"y\":-1}}],\"robber\":{\"x\":0,\"y\":-2}},\"players\":[{\"resources\":{\"brick\":0,\"wood\":1,\"sheep\":1,\"wheat\":1,\"ore\":0},\"oldDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"newDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"roads\":13,\"cities\":4,\"settlements\":3,\"soldiers\":0,\"victoryPoints\":2,\"monuments\":0,\"playedDevCard\":false,\"discarded\":false,\"playerID\":0,\"playerIndex\":0,\"name\":\"Sam\",\"color\":\"orange\"},{\"resources\":{\"brick\":1,\"wood\":0,\"sheep\":1,\"wheat\":0,\"ore\":1},\"oldDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"newDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"roads\":13,\"cities\":4,\"settlements\":3,\"soldiers\":0,\"victoryPoints\":2,\"monuments\":0,\"playedDevCard\":false,\"discarded\":false,\"playerID\":1,\"playerIndex\":1,\"name\":\"Brooke\",\"color\":\"blue\"},{\"resources\":{\"brick\":0,\"wood\":1,\"sheep\":1,\"wheat\":1,\"ore\":0},\"oldDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"newDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"roads\":13,\"cities\":4,\"settlements\":3,\"soldiers\":0,\"victoryPoints\":2,\"monuments\":0,\"playedDevCard\":false,\"discarded\":false,\"playerID\":10,\"playerIndex\":2,\"name\":\"Pete\",\"color\":\"orange\"},{\"resources\":{\"brick\":0,\"wood\":1,\"sheep\":1,\"wheat\":0,\"ore\":1},\"oldDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"newDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"roads\":13,\"cities\":4,\"settlements\":3,\"soldiers\":0,\"victoryPoints\":2,\"monuments\":0,\"playedDevCard\":false,\"discarded\":false,\"playerID\":11,\"playerIndex\":3,\"name\":\"Mark\",\"color\":\"green\"}],\"log\":{\"lines\":[{\"source\":\"Sam\",\"message\":\"Sam built a road\"},{\"source\":\"Sam\",\"message\":\"Sam built a settlement\"},{\"source\":\"Sam\",\"message\":\"Sam's turn just ended\"},{\"source\":\"Brooke\",\"message\":\"Brooke built a road\"},{\"source\":\"Brooke\",\"message\":\"Brooke built a settlement\"},{\"source\":\"Brooke\",\"message\":\"Brooke's turn just ended\"},{\"source\":\"Pete\",\"message\":\"Pete built a road\"},{\"source\":\"Pete\",\"message\":\"Pete built a settlement\"},{\"source\":\"Pete\",\"message\":\"Pete's turn just ended\"},{\"source\":\"Mark\",\"message\":\"Mark built a road\"},{\"source\":\"Mark\",\"message\":\"Mark built a settlement\"},{\"source\":\"Mark\",\"message\":\"Mark's turn just ended\"},{\"source\":\"Mark\",\"message\":\"Mark built a road\"},{\"source\":\"Mark\",\"message\":\"Mark built a settlement\"},{\"source\":\"Mark\",\"message\":\"Mark's turn just ended\"},{\"source\":\"Pete\",\"message\":\"Pete built a road\"},{\"source\":\"Pete\",\"message\":\"Pete built a settlement\"},{\"source\":\"Pete\",\"message\":\"Pete's turn just ended\"},{\"source\":\"Brooke\",\"message\":\"Brooke built a road\"},{\"source\":\"Brooke\",\"message\":\"Brooke built a settlement\"},{\"source\":\"Brooke\",\"message\":\"Brooke's turn just ended\"},{\"source\":\"Sam\",\"message\":\"Sam built a road\"},{\"source\":\"Sam\",\"message\":\"Sam built a settlement\"},{\"source\":\"Sam\",\"message\":\"Sam's turn just ended\"}]},\"chat\":{\"lines\":[]},\"bank\":{\"brick\":23,\"wood\":21,\"sheep\":20,\"wheat\":22,\"ore\":22},\"turnTracker\":{\"status\":\"Rolling\",\"currentTurn\":0,\"longestRoad\":-1,\"largestArmy\":-1},\"winner\":-1,\"version\":0}");
        map = model.mapManager.getMap();
        printMap(map);
    }

    @Test
    public void distributeResources()
    {
        ResourceList[] returnValues = map.distributeResources(6);
        for(ResourceList list : returnValues)
            System.out.println(list.toString());
    }

    @Test
    public void findVertNWedge() throws GeneralPlayerException, InvalidTurnStatusException, TurnIndexException {

        VertexLocation leftVert = map.findVertexLeft(new EdgeLocation(new HexLocation(0, 0), EdgeDirection.NorthWest));
        assertEquals("VertexLocation [hexLoc=HexLocation [x=-1, y=1], dir=NE]", leftVert.toString());

        VertexLocation rightVert = map.findVertexRight(new EdgeLocation(new HexLocation(0, 0), EdgeDirection.NorthWest));
        assertEquals("VertexLocation [hexLoc=HexLocation [x=0, y=0], dir=NW]", rightVert.toString());
    }

    @Test
    public void findVertNEedge() throws GeneralPlayerException, InvalidTurnStatusException, TurnIndexException {

        VertexLocation leftVert = map.findVertexLeft(new EdgeLocation(new HexLocation(0, 0), EdgeDirection.NorthEast));
        assertEquals("VertexLocation [hexLoc=HexLocation [x=0, y=0], dir=NE]", leftVert.toString());

        VertexLocation rightVert = map.findVertexRight(new EdgeLocation(new HexLocation(0, 0), EdgeDirection.NorthEast));
        assertEquals("VertexLocation [hexLoc=HexLocation [x=1, y=0], dir=NW]", rightVert.toString());
    }

    @Test
    public void findVertNedge() throws GeneralPlayerException, InvalidTurnStatusException, TurnIndexException {

        VertexLocation leftVert = map.findVertexLeft(new EdgeLocation(new HexLocation(0, 0), EdgeDirection.North));
        assertEquals("VertexLocation [hexLoc=HexLocation [x=0, y=0], dir=NW]", leftVert.toString());

        VertexLocation rightVert = map.findVertexRight(new EdgeLocation(new HexLocation(0, 0), EdgeDirection.North));
        assertEquals("VertexLocation [hexLoc=HexLocation [x=0, y=0], dir=NE]", rightVert.toString());
    }

    @Test
    public void findEdgesNEvertex() throws GeneralPlayerException, InvalidTurnStatusException, TurnIndexException {

        List<EdgeLocation> edges = map.findEdges(new VertexLocation(new HexLocation(0, 0), VertexDirection.NorthEast));
        assertEquals("EdgeLocation [hexLoc=HexLocation [x=0, y=0], dir=N]", edges.get(0).toString());
        assertEquals("EdgeLocation [hexLoc=HexLocation [x=0, y=0], dir=NE]", edges.get(1).toString());
        assertEquals("EdgeLocation [hexLoc=HexLocation [x=1, y=-1], dir=NW]", edges.get(2).toString());
    }

    @Test
    public void findEdgesNWvertex() throws GeneralPlayerException, InvalidTurnStatusException, TurnIndexException {

        List<EdgeLocation> edges = map.findEdges(new VertexLocation(new HexLocation(0, 0), VertexDirection.NorthWest));
        assertEquals("EdgeLocation [hexLoc=HexLocation [x=0, y=0], dir=N]", edges.get(0).toString());
        assertEquals("EdgeLocation [hexLoc=HexLocation [x=0, y=0], dir=NW]", edges.get(1).toString());
        assertEquals("EdgeLocation [hexLoc=HexLocation [x=-1, y=0], dir=NE]", edges.get(2).toString());
    }

    @Test
    public void canPlaceRoadSettlmentValid() throws GeneralPlayerException, InvalidTurnStatusException, TurnIndexException {

        EdgeLocation edge = new EdgeLocation(new HexLocation(0,1), EdgeDirection.NorthWest);
        int player = 2;
        assertEquals(true, map.canPlaceRoad(edge, player));
    }

    @Test
    public void canPlaceRoadSettlementInvalid() throws GeneralPlayerException, InvalidTurnStatusException, TurnIndexException {

        EdgeLocation edge = new EdgeLocation(new HexLocation(0,1), EdgeDirection.NorthWest);
        int player = 3;
        assertEquals(false, map.canPlaceRoad(edge, player));
    }

    @Test
    public void canPlaceRoadRoadAdjValid(){

        EdgeLocation edge = new EdgeLocation(new HexLocation(0,-1), EdgeDirection.NorthWest);
        int player = 1;
        assertEquals(true, map.canPlaceRoad(edge, player));
    }

    @Test
    public void canPlaceRoadRoadAdjInvalid(){

        EdgeLocation edge = new EdgeLocation(new HexLocation(-1,1), EdgeDirection.NorthWest);
        int player = 1;
        assertEquals(false, map.canPlaceRoad(edge, player));
    }

    @Test
    public void canPlaceRoadOnRoadInvalid(){
        EdgeLocation edge = new EdgeLocation(new HexLocation(1,0), EdgeDirection.North);
        int player = 3;
        assertEquals(false, map.canPlaceRoad(edge, player));
    }

    @Test
    public void canPlaceSettlementValid(){
        VertexLocation vertex = new VertexLocation(new HexLocation(0,-1), VertexDirection.NorthWest);
        int player = 1;

        Road secondRoad = new Road(new EdgeLocation(new HexLocation(0, -1), EdgeDirection.NorthWest), 1);
        map.getRoads().put(secondRoad.location, secondRoad);

        assertEquals(true, map.canPlaceSettlement(vertex, player, false));
    }

    //Tests when a settlement is places within two vertexes of another settlement of the same player
    @Test
    public void canPlaceSettlementInvalidSettlementAdjacency(){
        VertexLocation vertex = new VertexLocation(new HexLocation(-1, 0), VertexDirection.NorthEast);
        int player = 1;

        assertEquals(false, map.canPlaceSettlement(vertex, player, false));
    }

    //Tests placing a settlement at the correct distance without an adjoining road
    @Test
    public void canPlaceSettlementInvalidNoRoad(){
        VertexLocation vertex = new VertexLocation(new HexLocation(0,0 ), VertexDirection.NorthWest);
        int player = 1;
        
        assertEquals(false, map.canPlaceSettlement(vertex, player, false));
    }

    @Test
    public void canBuildCityValid(){
        VertexLocation location = new VertexLocation(new HexLocation(1,-1), VertexDirection.NorthEast);
        int player = 3;

        assertEquals(true, map.canPlaceCity(location, player));
    }

    @Test
    public void canBuildCityInvalidPlayer(){
        VertexLocation location = new VertexLocation(new HexLocation(1,-1), VertexDirection.NorthEast);
        int player = 0;

        assertEquals(false, map.canPlaceCity(location, player));
    }

    @Test
    public void canBuildCityInvalidNoSettlement(){
        VertexLocation location = new VertexLocation(new HexLocation(0,0), VertexDirection.NorthWest);
        int player = 2;

        assertEquals(false, map.canPlaceCity(location, player));
    }

    @Test
    public void canMaritimeTradeValid(){
        //Ore Settlement
        Settlement portSettle1 = new Settlement(new VertexLocation(new HexLocation(1, -2), VertexDirection.NorthEast), 0);
        //Wheat Settlement
        Settlement portSettle2 = new Settlement(new VertexLocation(new HexLocation(-1, -1), VertexDirection.NorthEast), 0);
        //Three Settlement
        Settlement portSettle3 = new Settlement(new VertexLocation(new HexLocation(-2, 0), VertexDirection.NorthEast), 0);

        map.getSettlements().put(portSettle1.location, portSettle1);
        map.getSettlements().put(portSettle2.location, portSettle2);
        map.getSettlements().put(portSettle3.location, portSettle3);

        Set<PortType> tradeOptions = map.canMaritimeTrade(0);

        assertEquals(true, tradeOptions.contains(PortType.ORE));
        assertEquals(true, tradeOptions.contains(PortType.WHEAT));
        assertEquals(true, tradeOptions.contains(PortType.THREE));
        assertEquals(3, tradeOptions.size());
    }

    @Test
    public void canMaritimeTradeInvalidNoPorts(){
        //Ore Settlement
        Settlement portSettle1 = new Settlement(new VertexLocation(new HexLocation(0, 0), VertexDirection.NorthEast), 3);

        map.getSettlements().put(portSettle1.location, portSettle1);

        Set<PortType> tradeOptions = map.canMaritimeTrade(3);

        assertEquals(0, tradeOptions.size());
        assertEquals(false, tradeOptions.contains(PortType.BRICK));
        assertEquals(false, tradeOptions.contains(PortType.ORE));
        assertEquals(false, tradeOptions.contains(PortType.SHEEP));
        assertEquals(false, tradeOptions.contains(PortType.WHEAT));
        assertEquals(false, tradeOptions.contains(PortType.WOOD));
        assertEquals(false, tradeOptions.contains(PortType.THREE));

    }


    public static void printMap(Map map){

        Set<EdgeLocation> roadKeys = map.getRoads().keySet();
        for(Iterator<EdgeLocation> i = roadKeys.iterator(); i.hasNext();){
            EdgeLocation next = i.next();
            System.out.println(map.getRoads().get(next).toString());
        }

        System.out.println();

        Set<VertexLocation> settleKeys = map.getSettlements().keySet();
        for(Iterator<VertexLocation> i = settleKeys.iterator(); i.hasNext();){
            VertexLocation next = i.next();
            System.out.println(map.getSettlements().get(next).toString());
        }

        System.out.println();

        Set<EdgeLocation> portKeys = map.getPorts().keySet();
        for(Iterator<EdgeLocation> i = portKeys.iterator(); i.hasNext();) {
            EdgeLocation next = i.next();
            System.out.println(map.getPorts().get(next).toString());
        }

        System.out.println();
    }

}
