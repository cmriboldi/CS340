package test.model.map;

import model.CatanModel;
import model.map.Map;
import org.junit.Test;
import serverProxy.JSONDeserializer;
import shared.exceptions.player.GeneralPlayerException;
import shared.exceptions.player.InvalidTurnStatusException;
import shared.exceptions.player.TurnIndexException;
import shared.locations.*;
import test.TestJSON;

import static org.junit.Assert.assertEquals;

public class TestMap {

    @Test
    public void findVertNWedge() throws GeneralPlayerException, InvalidTurnStatusException, TurnIndexException {

        CatanModel model = JSONDeserializer.deserialize(TestJSON.get());
        Map map = model.mapManager.getMap();

        VertexLocation leftVert = map.findVertexLeft(new EdgeLocation(new HexLocation(0, 0), EdgeDirection.NorthWest));
        assertEquals("VertexLocation [hexLoc=HexLocation [x=-1, y=1], dir=NE]", leftVert.toString());

        VertexLocation rightVert = map.findVertexRight(new EdgeLocation(new HexLocation(0, 0), EdgeDirection.NorthWest));
        assertEquals("VertexLocation [hexLoc=HexLocation [x=0, y=0], dir=NW]", rightVert.toString());
    }

    @Test
    public void findVertNEedge() throws GeneralPlayerException, InvalidTurnStatusException, TurnIndexException {
        CatanModel model = JSONDeserializer.deserialize(TestJSON.get());
        Map map = model.mapManager.getMap();

        VertexLocation leftVert = map.findVertexLeft(new EdgeLocation(new HexLocation(0, 0), EdgeDirection.NorthEast));
        assertEquals("VertexLocation [hexLoc=HexLocation [x=0, y=0], dir=NE]", leftVert.toString());

        VertexLocation rightVert = map.findVertexRight(new EdgeLocation(new HexLocation(0, 0), EdgeDirection.NorthEast));
        assertEquals("VertexLocation [hexLoc=HexLocation [x=1, y=0], dir=NW]", rightVert.toString());
    }

    @Test
    public void findVertNedge() throws GeneralPlayerException, InvalidTurnStatusException, TurnIndexException {
        CatanModel model = JSONDeserializer.deserialize(TestJSON.get());
        Map map = model.mapManager.getMap();

        VertexLocation leftVert = map.findVertexLeft(new EdgeLocation(new HexLocation(0, 0), EdgeDirection.North));
        assertEquals("VertexLocation [hexLoc=HexLocation [x=0, y=0], dir=NW]", leftVert.toString());

        VertexLocation rightVert = map.findVertexRight(new EdgeLocation(new HexLocation(0, 0), EdgeDirection.North));
        assertEquals("VertexLocation [hexLoc=HexLocation [x=0, y=0], dir=NE]", rightVert.toString());
    }

    @Test
    public void findEdgesNEvertex() throws GeneralPlayerException, InvalidTurnStatusException, TurnIndexException {
        CatanModel model = JSONDeserializer.deserialize(TestJSON.get());
        Map map = model.mapManager.getMap();

        EdgeLocation[] edges = map.findEdges(new VertexLocation(new HexLocation(0, 0), VertexDirection.NorthEast));
        assertEquals("EdgeLocation [hexLoc=HexLocation [x=0, y=0], dir=N]", edges[0].toString());
        assertEquals("EdgeLocation [hexLoc=HexLocation [x=0, y=0], dir=NE]", edges[1].toString());
        assertEquals("EdgeLocation [hexLoc=HexLocation [x=1, y=-1], dir=NW]", edges[2].toString());
    }

    @Test
    public void findEdgesNWvertex() throws GeneralPlayerException, InvalidTurnStatusException, TurnIndexException {
        CatanModel model = JSONDeserializer.deserialize(TestJSON.get());
        Map map = model.mapManager.getMap();

        EdgeLocation[] edges = map.findEdges(new VertexLocation(new HexLocation(0, 0), VertexDirection.NorthWest));
        assertEquals("EdgeLocation [hexLoc=HexLocation [x=0, y=0], dir=N]", edges[0].toString());
        assertEquals("EdgeLocation [hexLoc=HexLocation [x=0, y=0], dir=NW]", edges[1].toString());
        assertEquals("EdgeLocation [hexLoc=HexLocation [x=-1, y=0], dir=NE]", edges[2].toString());

    }

}
