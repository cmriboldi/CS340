package model.map;

//JAVA imports

import java.util.HashMap;
import java.util.ArrayList;

//Project Imports
import shared.locations.*;

/**
 * The Map class contains all information dealing with the map.
 *
 * @author Christian Riboldi
 * @author Clayton Condie
 * @author Jacob Brewer
 * @author Joshua Powers
 * @author Joshua Van Steeter
 * @version 1.0 Build Jan, 2016.
 */
public class Map {
    /**
     * container for all the tiles on the board
     */
    private HashMap<HexLocation, Hex> hexes;

    /**
     * container for all the settlements that exist on the board
     */
    private HashMap<VertexLocation, VertexObject> settlements;

    /**
     * container for all the ports that exist on the board
     */
    private HashMap<VertexLocation, VertexObject> ports;

    /**
     * container for all the roads that exist on the board
     */
    private HashMap<EdgeLocation, EdgeObject> roads;

    /**
     * container for the robber
     */
    HexLocation robber;

    /////////////////////////////////////////////////////////////////////
    //----- Constructor Methods
    //----- Function: Implicit
    /////////////////////////////////////////////////////////////////////

    /**
     * Default constructor, initializes each variable to empty hashmaps
     */
    public Map() {
        hexes = new HashMap<HexLocation, Hex>();
        settlements = new HashMap<VertexLocation, VertexObject>();
        ports = new HashMap<VertexLocation, VertexObject>();
        roads = new HashMap<EdgeLocation, EdgeObject>();
    }


    /**
     * Constructor that fills the Map with MashMaps through the constructor (via the deserializer)
     *
     * @param hexes_t
     * @param settlements_t
     * @param ports_t
     * @param roads_t
     */
    public Map(HashMap<HexLocation, Hex> hexes_t, HashMap<VertexLocation, VertexObject> settlements_t,
               HashMap<VertexLocation, VertexObject> ports_t, HashMap<EdgeLocation, EdgeObject> roads_t,
               HexLocation robber_t) {
        hexes = hexes_t;
        settlements = settlements_t;
        ports = ports_t;
        roads = roads_t;
        robber = robber_t;
    }


    /////////////////////////////////////////////////////////////////////
    //----- Navigation Methods
    //----- Function: Find component X neighboring component Y
    /////////////////////////////////////////////////////////////////////

    /**
     * Returns the location of the 4 edges attached to the given edge
     *
     * @param edge_t The given edge
     * @return
     */
    public EdgeLocation[] findAdjEdges(EdgeLocation edge_t) {

        return null;
    }


    /**
     * Returns the location of the 2 vertices attached to the given edge
     *
     * @param edge_t The given edge
     * @return
     */
    public VertexLocation[] findAdjVertex(EdgeLocation edge_t) {

        return null;
    }


    /**
     * Returns the location of the 3 edges attached to the given vertex
     *
     * @param vertex_t The given vertex
     * @return
     */
    public EdgeLocation[] findAdjEdges(VertexLocation vertex_t) {

        EdgeLocation[] edges = new EdgeLocation[3];

        if (vertex_t.getNormalizedLocation().getDir() == VertexDirection.NorthWest) {
            //return current hex location, N
            //return current hex location, NW

            //check for third edge (or neighboring hex), watch for boundary of map
            //return currentHex.getNeighbor(NW), NE
        }

        if (vertex_t.getNormalizedLocation().getDir() == VertexDirection.NorthEast) {
            //return current hex location, N
            //return current hex location, NE

            //check for third edge (or neighboring hex), watch for boundary of map
            //return currentHex.getNeighbor(NE), NW
        }

        return edges;
    }


    /**
     * Returns the location of the 6 edges attached to a given hex
     *
     * @param hex_t The given hex
     * @return
     */
    public EdgeLocation[] findHexEdges(HexLocation hex_t) {

        return null;
    }


    /**
     * Returns the location of the 6 vertices attached to a given hex
     *
     * @param hex_t The given hex
     * @return
     */
    public VertexLocation[] findHexVertex(HexLocation hex_t) {

        return null;
    }


    /////////////////////////////////////////////////////////////////////////////////
    //----- Functional Methods
    //----- Function: These methods DO STUFF, often using the navigational methods
    /////////////////////////////////////////////////////////////////////////////////

    /**
     * Queries the HashMap of hexes looking for tiles with a specific number value. Could
     * potentially be hard coded at map creation
     *
     * @param number
     * @return An ArrayList of HexLocations of hexes assigned the given number.
     */
    public ArrayList<HexLocation> queryNumber(int number) {

        return null;
    }
}
