package test.model.map;

import model.CatanModel;
import model.map.Map;
import org.junit.Test;
import serverProxy.JSONDeserializer;
import shared.exceptions.player.GeneralPlayerException;
import shared.exceptions.player.InvalidTurnStatusException;
import shared.exceptions.player.TurnIndexException;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import test.TestJSON;

public class TestMap {

    @Test
    public void findEdgesNWEdge() throws GeneralPlayerException, InvalidTurnStatusException, TurnIndexException {

        CatanModel model = JSONDeserializer.deserialize(TestJSON.get());
        Map map = model.mapManager.getMap();

        VertexLocation leftVert = map.findVertexLeft(new EdgeLocation(new HexLocation(0,0), EdgeDirection.NorthWest));
        System.out.println(leftVert.toString());

    }

}
