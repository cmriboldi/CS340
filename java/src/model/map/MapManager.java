package model.map;

//Project Imports

import shared.definitions.PortType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import model.resources.ResourceList;

//Java imports
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

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

    public MapManager(boolean randomTile, boolean randomNumbers, boolean randomPorts) {
        map = new Map(randomTile, randomNumbers, randomPorts);
    }

    public MapManager(HashMap<HexLocation, Hex> hexes_t, HashMap<VertexLocation, Settlement> settlements_t,
                      HashMap<EdgeLocation, Port> ports_t, HashMap<EdgeLocation, Road> roads_t,
                      HexLocation robber_t, int radius_t) {

        map = new Map(hexes_t, settlements_t, ports_t, roads_t, robber_t, radius_t);
    }

    public Map getMap() {
        return map;
    }

    /**
     * @return the index of the player with the longest road
     */
    @Override
    public int findLargestRoad() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void placeRoad(EdgeLocation edge, int player) {
        System.out.println("MAP MANAGER : placeRoad");
        map.placeRoad(edge, player);
    }

    public void placeLocalRoad(EdgeLocation edge, int player) {
        map.placeLocalRoad(edge, player);
    }

    @Override
    public boolean canPlaceRoad(EdgeLocation edge, int player) {
        return map.canPlaceRoad(edge, player);
    }

    public boolean canPlaceSettlement(VertexLocation vert, int playerIndex) {
        return map.canPlaceSettlement(vert, playerIndex, false);
    }

    public boolean canPlaceSettlementSetup(VertexLocation vertLoc, int playerIndex) {
        return map.canPlaceSettlement(vertLoc, playerIndex, true);
    }

    public Set<PortType> canMaritimeTrade(int player) {
        return map.canMaritimeTrade(player);
    }

    @Override
    public void placeSettlement(VertexLocation vertex, int player) {
        map.placeSettlement(vertex, player);
    }

    public HashSet<Integer> getPlayersOnHex(HexLocation hex) {
        return map.getPlayersOnHex(hex);
    }

    public boolean canPlaceCity(VertexLocation location, int player) {
        return map.canPlaceCity(location, player);
    }

    @Override
    public void upgradeSettlement(VertexLocation vertex, int player) {
        map.upgradeSettlement(vertex, player);
    }

    public boolean canPlaceRobber(HexLocation hexLoc) {
        return map.canPlaceRobber(hexLoc) && !hexLoc.equals(getRobber());
    }

    @Override
    public ResourceList[] distributeResources(int number) {
        return map.distributeResources(number);
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
