package model.map;

//JAVA imports

import java.util.*;

//Project Imports
import shared.definitions.PortType;
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

    /**
     * Identifies and returns the leftmost vertex attached to the given edge
     *
     * @param edge_t The edge
     * @return The location of the leftmost vertex
     */
    public VertexLocation findVertexLeft(EdgeLocation edge_t) {
        //Normalize the input edge
        edge_t = edge_t.getNormalizedLocation();

        //If the edge is a NorthWest edge, return the NorthEast vertex of the Hex to the SouthWest
        if (edge_t.getDir() == EdgeDirection.NorthWest)
            return new VertexLocation(edge_t.getHexLoc().getNeighborLoc(EdgeDirection.SouthWest), VertexDirection.NorthEast);

        //If the edge is a North edge, return the NorthWest Vertex of the Hex
        if (edge_t.getDir() == EdgeDirection.North)
            return new VertexLocation(edge_t.getHexLoc(), VertexDirection.NorthWest);

        //If the edge is a NorthEast edge, return the NorthEast Vertex of the Hex
        if (edge_t.getDir() == EdgeDirection.NorthEast)
            return new VertexLocation(edge_t.getHexLoc(), VertexDirection.NorthEast);

        //Should Never get here
        return null;
    }

    /**
     * Identifies and returns the rightmost vertex attached to the given edge
     *
     * @param edge_t The edge
     * @return The location of the rightmost vertex
     */
    public VertexLocation findVertexRight(EdgeLocation edge_t) {
        //Normalize the input edge
        edge_t = edge_t.getNormalizedLocation();

        //If the edge is a NorthWest edge, return the NorthWest Vertex of the Hex
        if (edge_t.getDir() == EdgeDirection.NorthWest)
            return new VertexLocation(edge_t.getHexLoc(), VertexDirection.NorthWest);

        //If the edge is a North edge, return the NorthEast Vertex of Hex
        if (edge_t.getDir() == EdgeDirection.North)
            return new VertexLocation(edge_t.getHexLoc(), VertexDirection.NorthEast);

        //If the edge is a NorthEast edge, return the NorthWest Vertex of the Hex to the SouthEast
        if (edge_t.getDir() == EdgeDirection.NorthEast)
            return new VertexLocation(edge_t.getHexLoc().getNeighborLoc(EdgeDirection.SouthEast), VertexDirection.NorthWest);

        //Should Never get here
        return null;
    }


    public List<EdgeLocation> findEdges(VertexLocation vertex_t) {
        //Normalize the input vertex
        vertex_t = vertex_t.getNormalizedLocation();

        //Initialize the return array;
        List<EdgeLocation> edges = new ArrayList<EdgeLocation>();

        //If the vertex is a NorthEast Vertex, return the local N and NE edges and the NE neighbor's NW edge
        if (vertex_t.getDir() == VertexDirection.NorthEast) {
            edges.add(new EdgeLocation(vertex_t.getHexLoc(), EdgeDirection.North));
            edges.add(new EdgeLocation(vertex_t.getHexLoc(), EdgeDirection.NorthEast));
            edges.add(new EdgeLocation(vertex_t.getHexLoc().getNeighborLoc(EdgeDirection.NorthEast), EdgeDirection.NorthWest));
            return edges;
        }


        //If the vertex is a NorthWest Vertex, return the local N and NW edges and the NW neighbor's NE edge
        if (vertex_t.getDir() == VertexDirection.NorthWest) {
            edges.add(new EdgeLocation(vertex_t.getHexLoc(), EdgeDirection.North));
            edges.add(new EdgeLocation(vertex_t.getHexLoc(), EdgeDirection.NorthWest));
            edges.add(new EdgeLocation(vertex_t.getHexLoc().getNeighborLoc(EdgeDirection.NorthWest), EdgeDirection.NorthEast));
            return edges;
        }
        return null;
    }

    public List<EdgeLocation> findEdges(VertexLocation vertex_t, EdgeLocation edge_t) {
        //Normalize the input vertex
        vertex_t = vertex_t.getNormalizedLocation();

        //Normalize the input edge
        edge_t = edge_t.getNormalizedLocation();
        EdgeLocation temp_edge;

        //Initialize the return array;
        List<EdgeLocation> edges = new ArrayList<EdgeLocation>();

        //If the vertex is a NorthEast Vertex, return the local N and NE edges and the NE neighbor's NW edge
        if (vertex_t.getDir() == VertexDirection.NorthEast) {
            temp_edge = new EdgeLocation(vertex_t.getHexLoc(), EdgeDirection.North);
            if (temp_edge != edge_t)
                edges.add(temp_edge);

            temp_edge = new EdgeLocation(vertex_t.getHexLoc(), EdgeDirection.NorthEast);
            if (temp_edge != edge_t)
                edges.add(temp_edge);

            temp_edge = new EdgeLocation(vertex_t.getHexLoc().getNeighborLoc(EdgeDirection.NorthEast), EdgeDirection.NorthWest);
            if (temp_edge != edge_t)
                edges.add(temp_edge);

            return edges;
        }

        //If the vertex is a NorthWest Vertex, return the local N and NW edges and the NW neighbor's NE edge
        if (vertex_t.getDir() == VertexDirection.NorthWest) {

            temp_edge = new EdgeLocation(vertex_t.getHexLoc(), EdgeDirection.North);
            if (temp_edge != edge_t)
                edges.add(temp_edge);

            temp_edge = new EdgeLocation(vertex_t.getHexLoc(), EdgeDirection.NorthWest);
            if (temp_edge != edge_t)
                edges.add(temp_edge);

            temp_edge = new EdgeLocation(vertex_t.getHexLoc().getNeighborLoc(EdgeDirection.NorthWest), EdgeDirection.NorthEast);
            if (temp_edge != edge_t)
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
    
    /*
     * Checks to see if both starting settlments have a road adjacent to them.
     * If they don't return false
     */
    public boolean canPlaceDuring2ndRoundSetup(EdgeLocation edge, int player)
    {    	
    	if (canPlaceRoad(edge, player) == false)
    	{
    		return false; 
    	}
    	
        edge = edge.getNormalizedLocation();
            		
    	for (Settlement settle : settlements.values())
    	{
 
    		if (settle.player == player){
    			VertexLocation settleLoc = settle.location;
    	        List<EdgeLocation> firstEdges = findEdges(settleLoc);
    	        boolean hasAdjacentRoad = false; 
    	        
    	        for (EdgeLocation edge_t : firstEdges) {
    	        	if (edge_t.equals(edge))
    	        		hasAdjacentRoad = true; 
    	        	
                    if (roads.containsKey(edge_t))
                        if (roads.get(edge_t).owner == player )
                        	hasAdjacentRoad = true; 
                }

                //if the player owns no adjoining edges, return false
                if (hasAdjacentRoad == false)
                    return false;
    	        
    		}
    	}
   
    	return true; // both starting settlements have adjacent roads. Woohoo!
    	
    }

    public boolean canPlaceRoad(EdgeLocation edge, int player) {
        //----- normalize the edge
        edge = edge.getNormalizedLocation();

        //----- is there a road already present at edge
        if (roads.containsKey(edge))
            return false;

        //----- check for ocean roads
        //check that at least one hex adj to the edge location is a non water tile (because we don't store the ocean tiles
        //check that there exists in hexes at least ONE of the hex locations
        HexLocation one = edge.getHexLoc();
        HexLocation two = one.getNeighborLoc(edge.getDir());

        if (!hexes.containsKey(one) && !hexes.containsKey(two))
            return false;


        //----- is there a player settlement adj to the edge
        VertexLocation left = findVertexLeft(edge);
        boolean leftSetPresent = false;
        VertexLocation right = findVertexRight(edge);
        boolean rightSetPresent = false;

        if (settlements.containsKey(left))
        {
            leftSetPresent = true;
            if (settlements.get(left).player == player)
                return true;
        }



        if (settlements.containsKey(right)) {
            rightSetPresent = true;
            if (settlements.get(right).player == player)
                return true;
        }

        //----- is there a player owned road adj to the edgj
        //--- must consider the cases where a different player owns a settlement on either left or right
        List<EdgeLocation> adjEdges = new ArrayList<EdgeLocation>();

        if (!leftSetPresent)
            adjEdges.addAll(findEdges(left, edge));

        if (!rightSetPresent)
            adjEdges.addAll(findEdges(right, edge));

        //for each edge adj to the edge in question
        for (int i = 0; i < adjEdges.size(); i++) {
            //does roads contain each adj edge (does there exist roads next to the edge)
            if (roads.containsKey(adjEdges.get(i))) {
                //does that road belong to the given player
                if (roads.get(adjEdges.get(i)).owner == player)
                    return true;
            }
        }

        return false;
    }

   /* public boolean canPlaceRoadSetup(EdgeLocation edge, int player) {
        //normalize the edge
        edge = edge.getNormalizedLocation();

        //--- check for ocean roads
        //check that at least one hex adj to the edge location is a non water tile (because we don't store the ocean tiles
        //check that there exists in hexes at least ONE of the hex locations
        HexLocation one = edge.getHexLoc();
        HexLocation two = one.getNeighborLoc(edge.getDir());

        if (!hexes.containsKey(one) && !hexes.containsKey(two))
            return false;

        //is there a road already present at edge
        if (roads.containsKey(edge))
            return false;

        //is there a road adj to this road ... that the placing player owns
        VertexLocation left = findVertexLeft(edge);
        VertexLocation right = findVertexRight(edge);

        List<EdgeLocation> adjEdges = new ArrayList<EdgeLocation>();
        adjEdges.addAll(findEdges(left, edge));
        adjEdges.addAll(findEdges(right, edge));

        //for each edge adj to the edge in question
        for (int i = 0; i < adjEdges.size(); i++) {
            //does roads contain each adj edge (does there exist roads next to the edge)
            if (roads.containsKey(adjEdges.get(i))) {
                if(roads.get(adjEdges.get(i)).owner == player)
                    return false;
            }
        }

        //check that at least one of the vertexes on this edge can have a settlement attached to it
        if(!canPlaceSettlementSetup(left, player) && !canPlaceSettlementSetup(right, player))
            return false;

        return true;
    }*/

    public HashSet<Integer> getPlayersOnHex(HexLocation hex) {
        //collect the 6 vertexes
        ArrayList<VertexLocation> vertexes = new ArrayList<VertexLocation>();
        HashSet<Integer> returnThis = new HashSet<Integer>();

        VertexLocation northEast = new VertexLocation(hex, VertexDirection.NorthEast);
        VertexLocation northWest = new VertexLocation(hex, VertexDirection.NorthWest);
        VertexLocation east = new VertexLocation(hex, VertexDirection.East);
        VertexLocation west = new VertexLocation(hex, VertexDirection.West);
        VertexLocation southEast = new VertexLocation(hex, VertexDirection.SouthEast);
        VertexLocation southWest = new VertexLocation(hex, VertexDirection.SouthWest);

        vertexes.add(northEast.getNormalizedLocation());
        vertexes.add(northWest.getNormalizedLocation());
        vertexes.add(east.getNormalizedLocation());
        vertexes.add(west.getNormalizedLocation());
        vertexes.add(southWest.getNormalizedLocation());
        vertexes.add(southEast.getNormalizedLocation());

        for (VertexLocation vert : vertexes) {
            if (settlements.containsKey(vert))
                returnThis.add(settlements.get(vert).getPlayer());
        }
        return returnThis;
    }

    public boolean canPlaceSettlement(VertexLocation vertex, int playerIndex, boolean setUp) {
        List<EdgeLocation> firstEdges = findEdges(vertex);
        List<EdgeLocation> firstEdgesPlayer = new ArrayList<EdgeLocation>();

        //----- check for an occupied vertex
        if(settlements.containsKey(vertex))
            return false;

        //----- check for ocean tiles
        //--- check that at least one hex adj to this vertex is in the hexes map
        HexLocation one = vertex.getHexLoc();
        HexLocation two = vertex.getHexLoc().getNeighborLoc(EdgeDirection.North);
        HexLocation three;

        if(vertex.getDir() == VertexDirection.NorthEast)
            three = vertex.getHexLoc().getNeighborLoc(EdgeDirection.NorthEast);
        else
            three = vertex.getHexLoc().getNeighborLoc(EdgeDirection.NorthWest);

        if(!hexes.containsKey(one) && !hexes.containsKey(two) && !hexes.containsKey(three))
            return false;

        //----- must be adj to a player road
        //--- EXCEPTION: setup phase
        if(!setUp) {
            for (EdgeLocation edge_t : firstEdges) {
                if (roads.containsKey(edge_t))
                    if (roads.get(edge_t).owner == playerIndex)
                        firstEdgesPlayer.add(edge_t);
            }

            //if the player owns no adjoining edges, return false
            if (firstEdgesPlayer.size() == 0)
                return false;
        }

        //----- can not be within 2 edges of another settlement
        //--- in other words, there can't be a settlement 1 vertex away
        Set<VertexLocation> firstVertex = new HashSet<VertexLocation>();
        for (EdgeLocation edge_t : firstEdges) {
            firstVertex.add(findVertexLeft(edge_t));
            firstVertex.add(findVertexRight(edge_t));
        }
        //--remove the original vertex
        firstVertex.remove(vertex);
        //--check for settlements
        for (VertexLocation vertex_t : firstVertex) {
            if (settlements.containsKey(vertex_t))
                return false;
        }

        return true;
    }

    public boolean canPlaceRobber(HexLocation hexLoc)
    {
        if(hexLoc == robber)
            return false;

        if(!hexes.containsKey(hexLoc))
            return false;

        return true;
    }

/*
    public boolean canPlaceSettlement(VertexLocation vertex, int player) {

        //----- check for a player owned edge adjacent to the given vertex
        List<EdgeLocation> firstEdges = findEdges(vertex);
        List<EdgeLocation> firstEdgesPlayer = new ArrayList<EdgeLocation>();
        for (EdgeLocation edge_t : firstEdges) {
            if (roads.containsKey(edge_t))
                if (roads.get(edge_t).owner == player)
                    firstEdgesPlayer.add(edge_t);
        }

        //if the player owns no adjoining edges, return false
        if (firstEdgesPlayer.size() == 0)
            return false;

        //---check for ANY settlements one edge away from given vertex
        //--collect all the vertexes
        Set<VertexLocation> firstVertex = new HashSet<VertexLocation>();
        for (EdgeLocation edge_t : firstEdges) {
            firstVertex.add(findVertexLeft(edge_t));
            firstVertex.add(findVertexRight(edge_t));
        }
        //--remove the original vertex
        firstVertex.remove(vertex);
        //--check for settlements
        for (VertexLocation vertex_t : firstVertex) {
            if (settlements.containsKey(vertex_t))
                return false;
        }

        //--acquire player owned edges 1 edge away from the given vertex
        //-collect the vertexes connected to player roads
        Set<VertexLocation> firstVertexPlayer = new HashSet<VertexLocation>();
        for (EdgeLocation edge_t : firstEdgesPlayer) {
            firstVertexPlayer.add(findVertexLeft(edge_t));
            firstVertexPlayer.add(findVertexRight(edge_t));
        }
        firstVertexPlayer.remove(vertex);
        //-collect second edges connected to player roads via vertex
        Set<EdgeLocation> secondEdges = new HashSet<EdgeLocation>();
        for (VertexLocation vertex_t : firstVertexPlayer) {
            secondEdges.addAll(findEdges(vertex_t));
        }
        secondEdges.removeAll(firstEdges);

        //--if any of the edges 2 edges away from the vertex (connected to the player roads) return true
        for (EdgeLocation edge_t : secondEdges) {
            if (roads.containsKey(edge_t))
                if (roads.get(edge_t).owner == player)
                    return true;
        }

        return false;
    }
*/

/*
    boolean canPlaceSettlementSetup(VertexLocation vertLoc, int playerIndex) {
        //---check for a player owned edge adjacent to the given vertex
        List<EdgeLocation> firstEdges = findEdges(vertLoc);
        List<EdgeLocation> firstEdgesPlayer = new ArrayList<EdgeLocation>();
        for (EdgeLocation edge_t : firstEdges) {
            if (roads.containsKey(edge_t))
                if (roads.get(edge_t).owner == playerIndex)
                    firstEdgesPlayer.add(edge_t);
        }

        //-if the player owns no adjoinging edges, return false
        if (firstEdgesPlayer.size() == 0) {
            System.out.format("Map : canPlaceSettmentSetup : return false : no adjoining edges");
            return false;
        }

        return true;
    }
*/

    public boolean canPlaceCity(VertexLocation location, int player) {
        if (!settlements.containsKey(location))
            return false;

        if (settlements.get(location).player != player)
            return false;

        if (settlements.get(location).isCity)
            return false;

        return true;
    }

    public Set<PortType> canMaritimeTrade(int player) {

        HashMap<VertexLocation, PortType> filterMap = new HashMap<VertexLocation, PortType>();
        Set<PortType> openPorts = new HashSet<PortType>();

        //---scan all the port edges and collect the vertexes
        //--classify the vertexes by port type (4 vertexes per type)
        Set<EdgeLocation> portKeys = ports.keySet();
        for (EdgeLocation portEdge : portKeys) {
            VertexLocation leftPort = findVertexLeft(portEdge);
            VertexLocation rightPort = findVertexRight(portEdge);
            PortType type = ports.get(portEdge).type;

            filterMap.put(leftPort, type);
            filterMap.put(rightPort, type);
        }

        //---look at all the port vertexes and compare with the settlement vertexes
        Set<VertexLocation> portVertexes = filterMap.keySet();
        for (VertexLocation portVertex : portVertexes) {
            //if there is a settlement on a portVertex
            if (settlements.containsKey(portVertex))
                //...and that settlement belonds to the player
                if (settlements.get(portVertex).player == player)
                    //add the port type connected to that port and add it to the return set
                    openPorts.add(filterMap.get(portVertex));


        }

        //if no ports are open then this will be empty
        return openPorts;
    }


    /////////////////////////////////////////////////////////////////////////////////
    //----- Getters and Setters
    //----- Function: implicit
    /////////////////////////////////////////////////////////////////////////////////

    public HashMap<EdgeLocation, Road> getRoads() {
        return roads;
    }

    public HashMap<VertexLocation, Settlement> getSettlements() {
        return settlements;
    }

    public HashMap<EdgeLocation, Port> getPorts() {
        return ports;
    }

    public HashMap<HexLocation, Hex> getHexes() {
        return hexes;
    }

    public HexLocation getRobber() {
        return robber;
    }

    public int getRadius() {
        return radius;
    }

}
