package model.map;

//JAVA imports

import java.util.*;

//Project Imports
import shared.locations.*;
import sun.security.provider.certpath.Vertex;

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


    public List<EdgeLocation> findEdges(VertexLocation vertex_t){
        //Normalize the input vertex
        vertex_t = vertex_t.getNormalizedLocation();

        //Initialize the return array;
        List<EdgeLocation> edges = new ArrayList<EdgeLocation>();

        //If the vertex is a NorthEast Vertex, return the local N and NE edges and the NE neighbor's NW edge
        if(vertex_t.getDir() == VertexDirection.NorthEast){
            edges.add(new EdgeLocation(vertex_t.getHexLoc(), EdgeDirection.North));
            edges.add(new EdgeLocation(vertex_t.getHexLoc(), EdgeDirection.NorthEast));
            edges.add(new EdgeLocation(vertex_t.getHexLoc().getNeighborLoc(EdgeDirection.NorthEast), EdgeDirection.NorthWest));
            return edges;
        }


        //If the vertex is a NorthWest Vertex, return the local N and NW edges and the NW neighbor's NE edge
        if(vertex_t.getDir() == VertexDirection.NorthWest){
            edges.add(new EdgeLocation(vertex_t.getHexLoc(), EdgeDirection.North));
            edges.add(new EdgeLocation(vertex_t.getHexLoc(), EdgeDirection.NorthWest));
            edges.add(new EdgeLocation(vertex_t.getHexLoc().getNeighborLoc(EdgeDirection.NorthWest), EdgeDirection.NorthEast));
            return edges;
        }
        return null;
    }

    public List<EdgeLocation> findEdges(VertexLocation vertex_t, EdgeLocation edge_t){
        //Normalize the input vertex
        vertex_t = vertex_t.getNormalizedLocation();

        //Normalize the input edge
        edge_t = edge_t.getNormalizedLocation();
        EdgeLocation temp_edge;

        //Initialize the return array;
        List<EdgeLocation> edges = new ArrayList<EdgeLocation>();

        //If the vertex is a NorthEast Vertex, return the local N and NE edges and the NE neighbor's NW edge
        if(vertex_t.getDir() == VertexDirection.NorthEast){
            temp_edge = new EdgeLocation(vertex_t.getHexLoc(), EdgeDirection.North);
            if(temp_edge != edge_t)
                edges.add(temp_edge);

            temp_edge = new EdgeLocation(vertex_t.getHexLoc(), EdgeDirection.NorthEast);
            if(temp_edge != edge_t)
                edges.add(temp_edge);

            temp_edge = new EdgeLocation(vertex_t.getHexLoc().getNeighborLoc(EdgeDirection.NorthEast), EdgeDirection.NorthWest);
            if(temp_edge != edge_t)
                edges.add(temp_edge);

            return edges;
        }

        //If the vertex is a NorthWest Vertex, return the local N and NW edges and the NW neighbor's NE edge
        if(vertex_t.getDir() == VertexDirection.NorthWest){

            temp_edge = new EdgeLocation(vertex_t.getHexLoc(), EdgeDirection.North);
            if(temp_edge != edge_t)
                edges.add(temp_edge);

            temp_edge = new EdgeLocation(vertex_t.getHexLoc(), EdgeDirection.NorthWest);
            if(temp_edge != edge_t)
                edges.add(temp_edge);

            temp_edge = new EdgeLocation(vertex_t.getHexLoc().getNeighborLoc(EdgeDirection.NorthWest), EdgeDirection.NorthEast);
            if(temp_edge != edge_t)
                edges.add(temp_edge);

            return edges;
        }

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

    public boolean canPlaceRoad(EdgeLocation edge, int player) {
        //normalize the edge
        edge = edge.getNormalizedLocation();

        //is there a road already present at edge
        if(roads.containsKey(edge))
            return false;

        //is there a player settlement adj to the edge
        VertexLocation left = findVertexLeft(edge);
        VertexLocation right = findVertexRight(edge);

        if(settlements.containsKey(left))
            if(settlements.get(left).player == player){
                return true;
            }

        if(settlements.containsKey(right)){
            if(settlements.get(right).player == player) {
                return true;
            }
        }

        //is there a player owned road adj to the edgj
        List<EdgeLocation> adjEdges = new ArrayList<EdgeLocation>();
        adjEdges.addAll(findEdges(left, edge));
        adjEdges.addAll(findEdges(right, edge));

        //for each edge adj to the edge in question
        for(int i = 0; i < adjEdges.size(); i++){
            //does roads contain each adj edge (does there exist roads next to the edge)
            if(roads.containsKey(adjEdges.get(i))){
                //does that road belong to the given player
                if(roads.get(adjEdges.get(i)).owner == player)
                    return true;
            }
        }

        return false;
    }

    public boolean canPlaceSettlement(VertexLocation vertex, int player){

        List<EdgeLocation> firstEdges = findEdges(vertex);
        List<EdgeLocation> firstEdgesPlayer = new ArrayList<>();
        Set<VertexLocation> firstVertexes = new HashSet<>();

        //get all of the edges attached to the vertex
        //check if any of them have roads belonging to the player
        for(int i = 0; i < firstEdges.size(); i++){
            //grab the first set of vertexies for later
            firstVertexes.add(findVertexLeft(firstEdges.get(i)));

            if(roads.containsKey(firstEdges.get(i))){
                //if any do, save then in a separate list
                if(roads.get(firstEdges.get(i)).owner == player)
                    firstEdgesPlayer.add(firstEdges.get(i));
            }
        }
        //if none do, return false
        if(firstEdgesPlayer.size() == 0)
            return false;

        //using the three vertecies attached to those edges
            //remove the inital edge
            firstVertexes.remove(vertex);

        //check if any have ANY settlements
        for(Iterator<VertexLocation> it = firstVertexes.iterator(); it.hasNext();){
            if(settlements.containsKey(it.next()))
                //if ANY do, return false
                return false;
        }

        //check edges adjoining any edges with play roads on them for additional roads belonging to the player
            //if none of them do, return false

        //check those edges for vertecies with settlements belonging to the player
            //if yes, return true


        return false;
    }


    /////////////////////////////////////////////////////////////////////////////////
    //----- Getters and Setters
    //----- Function: implicit
    /////////////////////////////////////////////////////////////////////////////////

    public HashMap<EdgeLocation, Road> getRoads(){
        return roads;
    }

    public HashMap<VertexLocation, Settlement> getSettlements(){
        return settlements;
    }

}
