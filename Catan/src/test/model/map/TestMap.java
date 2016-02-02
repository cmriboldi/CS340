package test.model.map;

import com.sun.javafx.geom.Edge;
import model.CatanModel;
import model.map.Map;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import serverProxy.JSONDeserializer;
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

    @BeforeClass
    public static void setUp() throws GeneralPlayerException, InvalidTurnStatusException, TurnIndexException {
        CatanModel model = JSONDeserializer.deserialize(TestJSON.get());
        map = model.mapManager.getMap();
        printMap(map);
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
    }

}
