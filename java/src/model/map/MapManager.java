package model.map;

//Project Imports

import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import model.resources.ResourceList;

//Java imports
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The MapManager class manages all interactions with the Map.
 *
 * @author Christian Riboldi
 * @author Clayton Condie
 * @author Jacob Brewer
 * @author Joshua Powers
 * @author Joshua Van Steeter
 * @version 1.0 Build Jan, 2016.
 */
public class MapManager implements IMapManager {
    private Map map;


    public MapManager(HashMap<HexLocation, Hex> hexes_t, HashMap<VertexLocation, Settlement> settlements_t,
                      HashMap<EdgeLocation, Port> ports_t, HashMap<EdgeLocation, Road> roads_t,
                      HexLocation robber_t, int radius_t) {

        map = new Map(hexes_t, settlements_t, ports_t, roads_t, robber_t, radius_t);
    }

    @Override
    public Map getMap() {
        return map;
    }

    /**
     *
     * @return the index of the player with the longest road
     */
    @Override
    public int findLargestRoad() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void placeRoad(EdgeLocation edge, int player) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean canPlaceRoad(EdgeLocation edge, int player) {
        return map.canPlaceRoad(edge, player);
    }

    @Override
    public void placeSettlement(VertexLocation vertex, int player) {
        // TODO Auto-generated method stub

    }

    @Override
    public void upgradeSettlement(VertexLocation vertex, int player) {
        // TODO Auto-generated method stub

    }

    @Override
    public ArrayList<ResourceList> distributeResources(int number) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void placeRobber(HexLocation hex) {
        // TODO Auto-generated method stub

    }

    public int getMapRadius() {
        return map.getRadius();
    }

    @Override
    public HashMap<HexLocation, Hex> getHexes() {
        return map.getHexes();
    }

    @Override
    public HashMap<VertexLocation, Settlement> getSettlements() {
        return map.getSettlements();
    }

    @Override
    public HashMap<EdgeLocation, Port> getPorts() {
        return map.getPorts();
    }

    @Override
    public HashMap<EdgeLocation, Road> getRoads() {
        return map.getRoads();
    }

    @Override
    public HexLocation getRobber() {
        return map.getRobber();
    }


}
