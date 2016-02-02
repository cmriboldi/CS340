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

    /**
     * Represents the ratuys if the game board
     */
    private int radius;

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
     * @param hexes_t       hashMap of Hex Objects
     * @param settlements_t hashMap of Settlement Objects
     * @param ports_t       hashMap of Port Objects
     * @param roads_t       hasMap of edgeValue Objects
     */
    public Map(HashMap<HexLocation, Hex> hexes_t, HashMap<VertexLocation, VertexObject> settlements_t,
               HashMap<VertexLocation, VertexObject> ports_t, HashMap<EdgeLocation, EdgeObject> roads_t,
               HexLocation robber_t, int radius_t) {
        hexes = hexes_t;
        settlements = settlements_t;
        ports = ports_t;
        roads = roads_t;
        robber = robber_t;
        radius = radius_t;
    }


    /////////////////////////////////////////////////////////////////////
    //----- Navigation Methods
    //----- Function: Find component X neighboring component Y
    /////////////////////////////////////////////////////////////////////

    public VertexLocation findVertexLeft(EdgeLocation edge_t) {

        return null;
    }

    public VertexLocation findVertexRight(EdgeLocation edge_t) {
        return null;
    }

    public EdgeLocation[] findEdges(VertexLocation vertex_t){
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
     * @param number the number to look for inside each of the hexes
     * @return An ArrayList of HexLocations of hexes assigned the given number.
     */
    public ArrayList<HexLocation> queryNumber(int number) {

        return null;
    }
}
