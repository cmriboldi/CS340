package model.map;

//JAVA imports

import model.resources.ResourceList;
import shared.definitions.HexType;
import shared.definitions.PortType;
import shared.definitions.ResourceType;
import shared.locations.*;

import java.util.*;

//Project Imports

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
@SuppressWarnings("ALL")

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
    private HexLocation robber;

    /**
     * Represents the ratuys if the game board
     */
    private int radius;

    /**
     * Represents the number a player must hit to win the longestRoad card
     */
    private int longestRoadToMeet;

    private int indexOfLongestRoadOwner;

    /////////////////////////////////////////////////////////////////////
    //----- Constructor Methods
    //----- Function: Implicit
    /////////////////////////////////////////////////////////////////////

    /**
     * Default constructor, initializes each variable to empty hashmaps
     */
    public Map() {
        hexes = new HashMap<>();
        settlements = new HashMap<>();
        ports = new HashMap<>();
        roads = new HashMap<>();
        longestRoadToMeet = 5;
        indexOfLongestRoadOwner = -1;
    }

    /**
     * Constructor used by the server to create a new map
     *
     * @param randomTile
     * @param randomNumbers
     * @param randomPorts
     */
    public Map(boolean randomTile, boolean randomNumbers, boolean randomPorts) {
        hexes = new HashMap<>();
        settlements = new HashMap<>();
        ports = new HashMap<>();
        roads = new HashMap<>();
        radius = 3;
        longestRoadToMeet = 5;
        indexOfLongestRoadOwner = -1;

        //list of all the possible xy combinations
        List<HexLocation> hexLocs = generateHexLocs();

        //list of all possible port locations
        List<EdgeLocation> portLocs = generatePortLocs();

        //one desert, two sheep brick ore wheat wood
        List<HexType> hexList = new ArrayList<HexType>();
        hexList.addAll(Arrays.asList(
                HexType.BRICK, HexType.BRICK, HexType.BRICK,
                HexType.SHEEP, HexType.SHEEP, HexType.SHEEP, HexType.SHEEP,
                HexType.ORE, HexType.ORE, HexType.ORE,
                HexType.WOOD, HexType.WOOD, HexType.WOOD, HexType.WOOD,
                HexType.WHEAT, HexType.WHEAT, HexType.WHEAT, HexType.WHEAT)
        );

        //only one 2 and 12, no 7, two of 3-11
        List<Integer> numbers = new ArrayList<>();
        numbers.addAll(Arrays.asList(2, 12, 3, 3, 4, 4, 5, 5, 6, 6, 8, 8, 9, 9, 10, 10, 11, 11));

        //
        List<PortType> portTypes = new ArrayList<>();
        portTypes.addAll(Arrays.asList(PortType.BRICK, PortType.ORE, PortType.SHEEP, PortType.WHEAT, PortType.WOOD,
                PortType.THREE, PortType.THREE, PortType.THREE, PortType.THREE));

        if (randomTile) {
            // if the tiles should be randomized
            //-- generate the desert port first, it does not get a number
            Random rand = new Random();
            HexLocation randHexLoc = removeAndReturn(hexLocs, rand.nextInt(hexLocs.size()));

            if (randomNumbers) {
                hexes.put(randHexLoc, new Hex(randHexLoc.getX(), randHexLoc.getY(), HexType.DESERT, -1));
                robber = randHexLoc;

                while (hexLocs.size() > 0) {
                    rand = new Random();
                    randHexLoc = removeAndReturn(hexLocs, rand.nextInt(hexLocs.size()));
                    int randNumber = removeAndReturn(numbers, rand.nextInt(numbers.size()));
                    HexType randHexType = removeAndReturn(hexList, rand.nextInt(hexList.size()));

                    hexes.put(randHexLoc, new Hex(randHexLoc.getX(), randHexLoc.getY(), randHexType, randNumber));
                }
            }

            if (!randomNumbers) {
                hexes.put(new HexLocation(0, -2), new Hex(0, -2, HexType.DESERT, -1));
                robber = new HexLocation(0, -2);
                hexes.put(new HexLocation(0, -1), new Hex(0, -1, removeAndReturn(hexList, rand.nextInt(hexList.size())), 3));
                hexes.put(new HexLocation(0, 0), new Hex(0, 0, removeAndReturn(hexList, rand.nextInt(hexList.size())), 11));
                hexes.put(new HexLocation(0, 1), new Hex(0, 1, removeAndReturn(hexList, rand.nextInt(hexList.size())), 4));
                hexes.put(new HexLocation(0, 2), new Hex(0, 2, removeAndReturn(hexList, rand.nextInt(hexList.size())), 8));
                hexes.put(new HexLocation(1, -2), new Hex(1, -2, removeAndReturn(hexList, rand.nextInt(hexList.size())), 4));
                hexes.put(new HexLocation(1, -1), new Hex(1, -1, removeAndReturn(hexList, rand.nextInt(hexList.size())), 9));
                hexes.put(new HexLocation(1, 0), new Hex(1, 0, removeAndReturn(hexList, rand.nextInt(hexList.size())), 5));
                hexes.put(new HexLocation(1, 1), new Hex(1, 1, removeAndReturn(hexList, rand.nextInt(hexList.size())), 10));
                hexes.put(new HexLocation(-1, -1), new Hex(-1, -1, removeAndReturn(hexList, rand.nextInt(hexList.size())), 8));
                hexes.put(new HexLocation(-1, 0), new Hex(-1, 0, removeAndReturn(hexList, rand.nextInt(hexList.size())), 10));
                hexes.put(new HexLocation(-1, 1), new Hex(-1, 1, removeAndReturn(hexList, rand.nextInt(hexList.size())), 9));
                hexes.put(new HexLocation(-1, 2), new Hex(-1, 2, removeAndReturn(hexList, rand.nextInt(hexList.size())), 3));
                hexes.put(new HexLocation(-2, 0), new Hex(-2, 0, removeAndReturn(hexList, rand.nextInt(hexList.size())), 5));
                hexes.put(new HexLocation(-2, 1), new Hex(-2, 1, removeAndReturn(hexList, rand.nextInt(hexList.size())), 3));
                hexes.put(new HexLocation(-2, 2), new Hex(-2, 2, removeAndReturn(hexList, rand.nextInt(hexList.size())), 6));
                hexes.put(new HexLocation(2, 0), new Hex(2, 0, removeAndReturn(hexList, rand.nextInt(hexList.size())), 6));
                hexes.put(new HexLocation(2, -1), new Hex(2, -1, removeAndReturn(hexList, rand.nextInt(hexList.size())), 12));
                hexes.put(new HexLocation(2, -2), new Hex(2, -2, removeAndReturn(hexList, rand.nextInt(hexList.size())), 11));
            }
        }

        if (!randomTile) {
            //if the tiles should be standard
            //place the desert tile first, the desert tile is independent of randomized numbers
            Random rand = new Random();
            hexes.put(new HexLocation(0, -2), new Hex(0, -2, HexType.DESERT, -1));
            robber = new HexLocation(0, -2);

            if (randomNumbers) {
                //if the tiles should be standard and the numbers should be random
                hexes.put(new HexLocation(0, -1), new Hex(0, -1, HexType.WOOD, removeAndReturn(numbers, rand.nextInt(numbers.size()))));
                hexes.put(new HexLocation(0, 0), new Hex(0, 0, HexType.WHEAT, removeAndReturn(numbers, rand.nextInt(numbers.size()))));
                hexes.put(new HexLocation(0, 1), new Hex(0, 1, HexType.WOOD, removeAndReturn(numbers, rand.nextInt(numbers.size()))));
                hexes.put(new HexLocation(0, 2), new Hex(0, 2, HexType.WHEAT, removeAndReturn(numbers, rand.nextInt(numbers.size()))));
                hexes.put(new HexLocation(1, -2), new Hex(1, -2, HexType.BRICK, removeAndReturn(numbers, rand.nextInt(numbers.size()))));
                hexes.put(new HexLocation(1, -1), new Hex(1, -1, HexType.ORE, removeAndReturn(numbers, rand.nextInt(numbers.size()))));
                hexes.put(new HexLocation(1, 0), new Hex(1, 0, HexType.BRICK, removeAndReturn(numbers, rand.nextInt(numbers.size()))));
                hexes.put(new HexLocation(1, 1), new Hex(1, 1, HexType.SHEEP, removeAndReturn(numbers, rand.nextInt(numbers.size()))));
                hexes.put(new HexLocation(-1, -1), new Hex(-1, -1, HexType.BRICK, removeAndReturn(numbers, rand.nextInt(numbers.size()))));
                hexes.put(new HexLocation(-1, 0), new Hex(-1, 0, HexType.SHEEP, removeAndReturn(numbers, rand.nextInt(numbers.size()))));
                hexes.put(new HexLocation(-1, 1), new Hex(-1, 1, HexType.SHEEP, removeAndReturn(numbers, rand.nextInt(numbers.size()))));
                hexes.put(new HexLocation(-1, 2), new Hex(-1, 2, HexType.ORE, removeAndReturn(numbers, rand.nextInt(numbers.size()))));
                hexes.put(new HexLocation(-2, 0), new Hex(-2, 0, HexType.ORE, removeAndReturn(numbers, rand.nextInt(numbers.size()))));
                hexes.put(new HexLocation(-2, 1), new Hex(-2, 1, HexType.WHEAT, removeAndReturn(numbers, rand.nextInt(numbers.size()))));
                hexes.put(new HexLocation(-2, 2), new Hex(-2, 2, HexType.WOOD, removeAndReturn(numbers, rand.nextInt(numbers.size()))));
                hexes.put(new HexLocation(2, 0), new Hex(2, 0, HexType.WHEAT, removeAndReturn(numbers, rand.nextInt(numbers.size()))));
                hexes.put(new HexLocation(2, -1), new Hex(2, -1, HexType.SHEEP, removeAndReturn(numbers, rand.nextInt(numbers.size()))));
                hexes.put(new HexLocation(2, -2), new Hex(2, -2, HexType.WOOD, removeAndReturn(numbers, rand.nextInt(numbers.size()))));
            }

            if (!randomNumbers) {
                //if everything (other than ports) should be normal
                hexes.put(new HexLocation(0, -1), new Hex(0, -1, HexType.WOOD, 3));
                hexes.put(new HexLocation(0, 0), new Hex(0, 0, HexType.WHEAT, 11));
                hexes.put(new HexLocation(0, 1), new Hex(0, 1, HexType.WOOD, 4));
                hexes.put(new HexLocation(0, 2), new Hex(0, 2, HexType.WHEAT, 8));
                hexes.put(new HexLocation(1, -2), new Hex(1, -2, HexType.BRICK, 4));
                hexes.put(new HexLocation(1, -1), new Hex(1, -1, HexType.ORE, 9));
                hexes.put(new HexLocation(1, 0), new Hex(1, 0, HexType.BRICK, 5));
                hexes.put(new HexLocation(1, 1), new Hex(1, 1, HexType.SHEEP, 10));
                hexes.put(new HexLocation(-1, -1), new Hex(-1, -1, HexType.BRICK, 8));
                hexes.put(new HexLocation(-1, 0), new Hex(-1, 0, HexType.SHEEP, 10));
                hexes.put(new HexLocation(-1, 1), new Hex(-1, 1, HexType.SHEEP, 9));
                hexes.put(new HexLocation(-1, 2), new Hex(-1, 2, HexType.ORE, 3));
                hexes.put(new HexLocation(-2, 0), new Hex(-2, 0, HexType.ORE, 5));
                hexes.put(new HexLocation(-2, 1), new Hex(-2, 1, HexType.WHEAT, 3));
                hexes.put(new HexLocation(-2, 2), new Hex(-2, 2, HexType.WOOD, 6));
                hexes.put(new HexLocation(2, 0), new Hex(2, 0, HexType.WHEAT, 6));
                hexes.put(new HexLocation(2, -1), new Hex(2, -1, HexType.SHEEP, 12));
                hexes.put(new HexLocation(2, -2), new Hex(2, -2, HexType.WOOD, 11));
            }
        }

        if (randomPorts) {
            Random rand = new Random();
            //if the ports should be randomized
            ports.put(new EdgeLocation(new HexLocation(-3, 0), EdgeDirection.SouthEast), new Port(-3, 0, EdgeDirection.SouthEast, removeAndReturn(portTypes, rand.nextInt(portTypes.size())), 2));
            ports.put(new EdgeLocation(new HexLocation(-3, 2), EdgeDirection.NorthEast), new Port(-3, 2, EdgeDirection.NorthEast, removeAndReturn(portTypes, rand.nextInt(portTypes.size())), 2));
            ports.put(new EdgeLocation(new HexLocation(-2, 3), EdgeDirection.NorthEast), new Port(-2, 3, EdgeDirection.NorthEast, removeAndReturn(portTypes, rand.nextInt(portTypes.size())), 2));
            ports.put(new EdgeLocation(new HexLocation(0, 3), EdgeDirection.North), new Port(-0, 3, EdgeDirection.North, removeAndReturn(portTypes, rand.nextInt(portTypes.size())), 2));
            ports.put(new EdgeLocation(new HexLocation(2, 1), EdgeDirection.NorthWest), new Port(2, 1, EdgeDirection.NorthWest, removeAndReturn(portTypes, rand.nextInt(portTypes.size())), 2));
            ports.put(new EdgeLocation(new HexLocation(3, -1), EdgeDirection.NorthWest), new Port(3, -1, EdgeDirection.NorthWest, removeAndReturn(portTypes, rand.nextInt(portTypes.size())), 2));
            ports.put(new EdgeLocation(new HexLocation(3, -3), EdgeDirection.SouthWest), new Port(3, -3, EdgeDirection.SouthWest, removeAndReturn(portTypes, rand.nextInt(portTypes.size())), 2));
            ports.put(new EdgeLocation(new HexLocation(1, -3), EdgeDirection.South), new Port(1, -3, EdgeDirection.South, removeAndReturn(portTypes, rand.nextInt(portTypes.size())), 2));
            ports.put(new EdgeLocation(new HexLocation(-1, -2), EdgeDirection.South), new Port(-1, -2, EdgeDirection.South, removeAndReturn(portTypes, rand.nextInt(portTypes.size())), 2));

            for(Port currentPort : ports.values()){
                if(currentPort.getType() == PortType.THREE)
                    currentPort.setRatio(3);
            }
        }

        if (!randomPorts) {
            //if the ports should be standard
            ports.put(new EdgeLocation(new HexLocation(-3, 0), EdgeDirection.SouthEast), new Port(-3, 0, EdgeDirection.SouthEast, PortType.THREE, 3));
            ports.put(new EdgeLocation(new HexLocation(-3, 2), EdgeDirection.NorthEast), new Port(-3, 2, EdgeDirection.NorthEast, PortType.WOOD, 2));
            ports.put(new EdgeLocation(new HexLocation(-2, 3), EdgeDirection.NorthEast), new Port(-2, 3, EdgeDirection.NorthEast, PortType.BRICK, 2));
            ports.put(new EdgeLocation(new HexLocation(0, 3), EdgeDirection.North), new Port(-0, 3, EdgeDirection.North, PortType.THREE, 3));
            ports.put(new EdgeLocation(new HexLocation(2, 1), EdgeDirection.NorthWest), new Port(2, 1, EdgeDirection.NorthWest, PortType.THREE, 3));
            ports.put(new EdgeLocation(new HexLocation(3, -1), EdgeDirection.NorthWest), new Port(3, -1, EdgeDirection.NorthWest, PortType.SHEEP, 2));
            ports.put(new EdgeLocation(new HexLocation(3, -3), EdgeDirection.SouthWest), new Port(3, -3, EdgeDirection.SouthWest, PortType.THREE, 3));
            ports.put(new EdgeLocation(new HexLocation(1, -3), EdgeDirection.South), new Port(1, -3, EdgeDirection.South, PortType.ORE, 2));
            ports.put(new EdgeLocation(new HexLocation(-1, -2), EdgeDirection.South), new Port(-1, -2, EdgeDirection.South, PortType.WHEAT, 2));
        }
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

    private class xyPair {
        public int x;
        public int y;

        public xyPair(int x_p, int y_p) {
            x = x_p;
            y = y_p;
        }
    }

    private <T> T removeAndReturn(List<T> list, int index) {
        T returnThis = list.get(index);
        list.remove(index);

        return returnThis;
    }

    private List<HexLocation> generateHexLocs() {
        List<HexLocation> returnThis = new ArrayList<>();
        returnThis.add(new HexLocation(0, 0));
        returnThis.add(new HexLocation(0, 1));
        returnThis.add(new HexLocation(0, 2));
        returnThis.add(new HexLocation(0, -1));
        returnThis.add(new HexLocation(0, -2));

        returnThis.add(new HexLocation(1, -2));
        returnThis.add(new HexLocation(1, -1));
        returnThis.add(new HexLocation(1, 0));
        returnThis.add(new HexLocation(1, 1));

        returnThis.add(new HexLocation(-1, -1));
        returnThis.add(new HexLocation(-1, 0));
        returnThis.add(new HexLocation(-1, 1));
        returnThis.add(new HexLocation(-1, 2));

        returnThis.add(new HexLocation(2, -2));
        returnThis.add(new HexLocation(2, -1));
        returnThis.add(new HexLocation(2, 0));

        returnThis.add(new HexLocation(-2, 0));
        returnThis.add(new HexLocation(-2, 1));
        returnThis.add(new HexLocation(-2, 2));

        return returnThis;
    }

    private List<EdgeLocation> generatePortLocs() {
        List<EdgeLocation> returnThis = new ArrayList<>();

        returnThis.add(new EdgeLocation(new HexLocation(-3, 0), EdgeDirection.SouthEast));
        returnThis.add(new EdgeLocation(new HexLocation(-3, 2), EdgeDirection.NorthEast));
        returnThis.add(new EdgeLocation(new HexLocation(-2, 3), EdgeDirection.NorthEast));
        returnThis.add(new EdgeLocation(new HexLocation(0, 3), EdgeDirection.North));
        returnThis.add(new EdgeLocation(new HexLocation(2, 1), EdgeDirection.NorthWest));
        returnThis.add(new EdgeLocation(new HexLocation(3, -1), EdgeDirection.NorthWest));
        returnThis.add(new EdgeLocation(new HexLocation(3, -3), EdgeDirection.SouthWest));
        returnThis.add(new EdgeLocation(new HexLocation(-1, -3), EdgeDirection.South));
        returnThis.add(new EdgeLocation(new HexLocation(-1, -2), EdgeDirection.South));

        return returnThis;
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
        List<EdgeLocation> edges = new ArrayList<>();

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
        List<EdgeLocation> edges = new ArrayList<>();

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

    private Set<Hex> findHexes(VertexLocation vertLoc) {
        //Normalize the input vertex
        vertLoc = vertLoc.getNormalizedLocation();

        //Initiailze the return Set
        Set<Hex> returnThis = new HashSet<>();

        if (vertLoc.getDir() == VertexDirection.NorthEast) {
            //if the inputed vertex is in the NE corner
            //check for the native hex (won't be present if an ocean is to the south)
            if (hexes.containsKey(new HexLocation(vertLoc.getHexLoc().getX(), vertLoc.getHexLoc().getY()))) {
                returnThis.add(hexes.get(new HexLocation(vertLoc.getHexLoc().getX(), vertLoc.getHexLoc().getY())));
            }

            //check for the hex up and to the right
            if (hexes.containsKey(new HexLocation(vertLoc.getHexLoc().getX() + 1, vertLoc.getHexLoc().getY() - 1))) {
                returnThis.add(hexes.get(new HexLocation(vertLoc.getHexLoc().getX() + 1, vertLoc.getHexLoc().getY() - 1)));
            }

            //check for the hex directly above
            if (hexes.containsKey(new HexLocation(vertLoc.getHexLoc().getX(), vertLoc.getHexLoc().getY() - 1))) {
                returnThis.add(hexes.get(new HexLocation(vertLoc.getHexLoc().getX(), vertLoc.getHexLoc().getY() - 1)));
            }

        }

        if (vertLoc.getDir() == VertexDirection.NorthWest) {
            //if the inputed vertex is in the NW corner
            //check for the native hex (won't be present if an ocean is to the south)
            if (hexes.containsKey(new HexLocation(vertLoc.getHexLoc().getX(), vertLoc.getHexLoc().getY()))) {
                returnThis.add(hexes.get(new HexLocation(vertLoc.getHexLoc().getX(), vertLoc.getHexLoc().getY())));
            }

            //check for the hex up and to the left
            if (hexes.containsKey(new HexLocation(vertLoc.getHexLoc().getX() - 1, vertLoc.getHexLoc().getY()))) {
                returnThis.add(hexes.get(new HexLocation(vertLoc.getHexLoc().getX() - 1, vertLoc.getHexLoc().getY())));
            }

            //check for the hex directly above
            if (hexes.containsKey(new HexLocation(vertLoc.getHexLoc().getX(), vertLoc.getHexLoc().getY() - 1))) {
                returnThis.add(hexes.get(new HexLocation(vertLoc.getHexLoc().getX(), vertLoc.getHexLoc().getY() - 1)));
            }
        }
        return returnThis;
    }

    /////////////////////////////////////////////////////////////////////////////////
    //----- Functional Methods
    //----- Function: These methods DO STUFF, often using the navigational methods
    /////////////////////////////////////////////////////////////////////////////////

    public ResourceList[] distributeResources(int number) {

        //initialize the list of resourceLists to return
        ResourceList[] resourceLists = new ResourceList[4];
        for (int i = 0; i < resourceLists.length; i++)
            resourceLists[i] = new ResourceList(0, 0, 0, 0, 0);

        //get hexes that match the inputed number
        Set<Hex> matchingHexes = new HashSet<>();
        for (Hex currentHex : hexes.values()) {
            if (currentHex.getNumber() == number)
                matchingHexes.add(currentHex);
        }

        //identify any settlements attached to said hexes, and increment the resource for the owning player
        Set<Settlement> matchingSettlements = new HashSet<>();
        for (Hex currentHex : matchingHexes) {
            int playerIndex;

            //check NE
            VertexLocation NEVertex = (new VertexLocation(currentHex.location, VertexDirection.NorthEast)).getNormalizedLocation();
            if (settlements.containsKey(NEVertex)) {
                playerIndex = settlements.get(NEVertex).getPlayer();
                resourceLists[playerIndex].addResource(ResourceType.valueOf(currentHex.resourceHexType.toString()), 1);
            }
            //check NW
            VertexLocation NWVertex = (new VertexLocation(currentHex.location, VertexDirection.NorthWest)).getNormalizedLocation();
            if (settlements.containsKey(NWVertex)) {
                playerIndex = settlements.get(NWVertex).getPlayer();
                resourceLists[playerIndex].addResource(ResourceType.valueOf(currentHex.resourceHexType.toString()), 1);
            }
            //check W
            VertexLocation WVertex = (new VertexLocation(currentHex.location, VertexDirection.West)).getNormalizedLocation();
            if (settlements.containsKey(WVertex)) {
                playerIndex = settlements.get(WVertex).getPlayer();
                resourceLists[playerIndex].addResource(ResourceType.valueOf(currentHex.resourceHexType.toString()), 1);
            }
            //check E
            VertexLocation EVertex = (new VertexLocation(currentHex.location, VertexDirection.East)).getNormalizedLocation();
            if (settlements.containsKey(EVertex)) {
                playerIndex = settlements.get(EVertex).getPlayer();
                resourceLists[playerIndex].addResource(ResourceType.valueOf(currentHex.resourceHexType.toString()), 1);
            }
            //check SE
            VertexLocation SEVertex = (new VertexLocation(currentHex.location, VertexDirection.SouthEast)).getNormalizedLocation();
            if (settlements.containsKey(SEVertex)) {
                playerIndex = settlements.get(SEVertex).getPlayer();
                resourceLists[playerIndex].addResource(ResourceType.valueOf(currentHex.resourceHexType.toString()), 1);
            }
            //check SW
            VertexLocation SWVertex = (new VertexLocation(currentHex.location, VertexDirection.SouthWest)).getNormalizedLocation();
            if (settlements.containsKey(SWVertex)) {
                playerIndex = settlements.get(SWVertex).getPlayer();
                resourceLists[playerIndex].addResource(ResourceType.valueOf(currentHex.resourceHexType.toString()), 1);
            }
        }

        return resourceLists;
    }

    public ResourceList[] distributeSetupResources() {
        //initialize the list of resourceLists to return
        ResourceList[] resourceLists = new ResourceList[4];
        for (int i = 0; i < resourceLists.length; i++)
            resourceLists[i] = new ResourceList(0, 0, 0, 0, 0);

        //for each settlemet
        for (Settlement currentSettlement : settlements.values()) {
            //get each hex adj to the settlement
            Set<Hex> adjHexes = findHexes(currentSettlement.location);
            //for each hex, add one of the resource to the owning player
            for (Hex currentHex : adjHexes) {
                if (currentHex.resourceHexType != HexType.DESERT)
                    resourceLists[currentSettlement.getPlayer()].addResource(ResourceType.valueOf(currentHex.resourceHexType.toString()), 1);
            }
        }

        return resourceLists;
    }


    /*
     * Checks to see if both starting settlments have a road adjacent to them.
     * If they don't return false
     */
    public boolean canPlaceDuring2ndRoundSetup(EdgeLocation edge, int player) {
        if (!canPlaceRoad(edge, player)) {
            return false;
        }

        edge = edge.getNormalizedLocation();

        for (Settlement settle : settlements.values()) {

            if (settle.player == player) {
                VertexLocation settleLoc = settle.location;
                List<EdgeLocation> firstEdges = findEdges(settleLoc);
                boolean hasAdjacentRoad = false;

                for (EdgeLocation edge_t : firstEdges) {
                    if (edge_t.equals(edge))
                        hasAdjacentRoad = true;

                    if (roads.containsKey(edge_t))
                        if (roads.get(edge_t).owner == player)
                            hasAdjacentRoad = true;
                }

                //if the player owns no adjoining edges, return false
                if (!hasAdjacentRoad)
                    return false;

            }
        }

        return true; // both starting settlements have adjacent roads. Woohoo!

    }

    public void placeRoad(EdgeLocation edge, int player) {
        System.out.println("MAP : placeRoad");

        roads.put(edge, new Road(edge, player));

        Set<Road> playerRoads = new HashSet<>();
        for (Road currentRoad : roads.values()) {
            if (currentRoad.getOwner() == player)
                playerRoads.add(currentRoad);
        }
        if (playerRoads.size() == longestRoadToMeet) {
            longestRoadToMeet++;
            indexOfLongestRoadOwner = player;
        }
    }

    public int getIndexOfLongestRoadOwner() {
        return indexOfLongestRoadOwner;
    }

    public void placeLocalRoad(EdgeLocation edge, int player) {
        if (!canPlaceRoad(edge, player))
            return;

        Road newRoad = new Road(edge, player);
        roads.put(edge, newRoad);
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

        if (settlements.containsKey(left)) {
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
        List<EdgeLocation> adjEdges = new ArrayList<>();

        if (!leftSetPresent)
            adjEdges.addAll(findEdges(left, edge));

        if (!rightSetPresent)
            adjEdges.addAll(findEdges(right, edge));

        //for each edge adj to the edge in question
        for (EdgeLocation adjEdge : adjEdges) {
            //does roads contain each adj edge (does there exist roads next to the edge)
            if (roads.containsKey(adjEdge)) {
                //does that road belong to the given player
                if (roads.get(adjEdge).owner == player)
                    return true;
            }
        }

        return false;
    }

    public HashSet<Integer> getPlayersOnHex(HexLocation hex) {
        //collect the 6 vertexes
        ArrayList<VertexLocation> vertexes = new ArrayList<>();
        HashSet<Integer> returnThis = new HashSet<>();

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
        List<EdgeLocation> firstEdgesPlayer = new ArrayList<>();

        //----- check for an occupied vertex
        if (settlements.containsKey(vertex))
            return false;

        //----- check for ocean tiles
        //--- check that at least one hex adj to this vertex is in the hexes map
        HexLocation one = vertex.getHexLoc();
        HexLocation two = vertex.getHexLoc().getNeighborLoc(EdgeDirection.North);
        HexLocation three;

        if (vertex.getDir() == VertexDirection.NorthEast)
            three = vertex.getHexLoc().getNeighborLoc(EdgeDirection.NorthEast);
        else
            three = vertex.getHexLoc().getNeighborLoc(EdgeDirection.NorthWest);

        if (!hexes.containsKey(one) && !hexes.containsKey(two) && !hexes.containsKey(three))
            return false;

        //----- must be adj to a player road
        //--- EXCEPTION: setup phase
        if (!setUp) {
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
        Set<VertexLocation> firstVertex = new HashSet<>();
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

    public void placeSettlement(VertexLocation vertLoc, int playerIndex) {
        settlements.put(vertLoc, new Settlement(vertLoc, playerIndex));
    }

    public void upgradeSettlement(VertexLocation vertLoc, int playerIndex) {
        settlements.get(vertLoc).makeCity();
    }

    public boolean canPlaceRobber(HexLocation hexLoc) {
        return hexLoc != robber && hexes.containsKey(hexLoc);
    }

    public void placeRobber(HexLocation hexLoc) {
        robber = hexLoc;
    }

    public boolean canPlaceCity(VertexLocation location, int player) {
        if (!settlements.containsKey(location))
            return false;

        if (settlements.get(location).player != player)
            return false;

        return !settlements.get(location).isCity;

    }

    public Set<PortType> canMaritimeTrade(int player) {

        HashMap<VertexLocation, PortType> filterMap = new HashMap<>();
        Set<PortType> openPorts = new HashSet<>();

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
