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
    private HashMap<VertexLocation, Settlement> settlements;

    /**
     * container for all the ports that exist on the board
     */
    private HashMap<EdgeLocation, Port> ports;

    /**
     * container for all the roads that exist on the board
     */
    private HashMap<EdgeLocation, Road> roads;

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
        settlements = new HashMap<VertexLocation, Settlement>();
        ports = new HashMap<EdgeLocation, Port>();
        roads = new HashMap<EdgeLocation, Road>();
    }


    /**
     * Constructor that fills the Map with MashMaps through the constructor (via the deserializer)
     *
     * @param hexes_t       hashMap of Hex Objects
     * @param settlements_t hashMap of Settlement Objects
     * @param ports_t       hashMap of Port Objects
     * @param roads_t       hasMap of edgeValue Objects
     */
    public Map(HashMap<HexLocation, Hex> hexes_t, HashMap<VertexLocation, Settlement> settlements_t,
               HashMap<EdgeLocation, Port> ports_t, HashMap<EdgeLocation, Road> roads_t,
               HexLocation robber_t, int radius_t)
    {
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

    /**
     * Identifies and returns the leftmost vertex attached to the given edge
     * @param edge_t The edge
     * @return The location of the leftmost vertex
     */
    public VertexLocation findVertexLeft(EdgeLocation edge_t) {
        //Normalize the input edge
        edge_t = edge_t.getNormalizedLocation();

        //If the edge is a NorthWest edge, return the NorthEast vertex of the Hex to the SouthWest
        if(edge_t.getDir() == EdgeDirection.NorthWest)
            return new VertexLocation(edge_t.getHexLoc().getNeighborLoc(EdgeDirection.SouthWest), VertexDirection.NorthEast);

        //If the edge is a North edge, return the NorthWest Vertex of the Hex
        if(edge_t.getDir() == EdgeDirection.North)
            return new VertexLocation(edge_t.getHexLoc(), VertexDirection.NorthWest);

        //If the edge is a NorthEast edge, return the NorthEast Vertex of the Hex
        if(edge_t.getDir() == EdgeDirection.NorthEast)
            return new VertexLocation(edge_t.getHexLoc(), VertexDirection.NorthEast);

        //Should Never get here
        return null;
    }

    /**
     * Identifies and returns the rightmost vertex attached to the given edge
     * @param edge_t The edge
     * @return The location of the rightmost vertex
     */
    public VertexLocation findVertexRight(EdgeLocation edge_t) {
        //Normalize the input edge
        edge_t = edge_t.getNormalizedLocation();

        //If the edge is a NorthWest edge, return the NorthWest Vertex of the Hex
        if(edge_t.getDir() == EdgeDirection.NorthWest)
            return new VertexLocation(edge_t.getHexLoc(), VertexDirection.NorthWest);

        //If the edge is a North edge, return the NorthEast Vertex of Hex
        if(edge_t.getDir() == EdgeDirection.North)
            return new VertexLocation(edge_t.getHexLoc(), VertexDirection.NorthEast);

        //If the edge is a NorthEast edge, return the NorthWest Vertex of the Hex to the SouthEast
        if(edge_t.getDir() == EdgeDirection.NorthEast)
            return new VertexLocation(edge_t.getHexLoc().getNeighborLoc(EdgeDirection.SouthEast), VertexDirection.NorthWest);

        //Should Never get here
        return null;
    }


    public EdgeLocation[] findEdges(VertexLocation vertex_t){
        //Normalize the input vertex
        vertex_t = vertex_t.getNormalizedLocation();

        //Initialize the return array;
        EdgeLocation[] edges = new EdgeLocation[3];

        //If the vertex is a NorthEast Vertex, return the local N and NE edges and the NE neighbor's NW edge


        //If the vertex is a NorthWest Vertex, return the local N and NW edges and the NW neighbor's NE edge


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
