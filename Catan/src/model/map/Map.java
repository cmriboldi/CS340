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
public class Map
{
	/** container for all the tiles on the board */
	private HashMap<HexLocation, Hex> hexes;

	/** container for all the settlements that exist on the board */
	private HashMap<VertexLocation, VertexObject> settlements;

	/** container for all the ports that exist on the board */
	private HashMap<VertexLocation, VertexObject> ports;

	/** container for all the roads that exist on the board */
	private HashMap<EdgeLocation, EdgeValue> roads;

	
	
	
	/**
	 * Default constructor, initializes each variable to empty hashmaps
	 */
	public Map()
	{
		hexes = new HashMap<HexLocation, Hex>();
		settlements = new HashMap<VertexLocation, VertexObject>();
		ports = new HashMap<VertexLocation, VertexObject>();
		roads = new HashMap<EdgeLocation, EdgeValue>();
	}
	
	/**
	 * Constructor that fills the Map with MashMaps through the constructor (via the deserializer)
	 * @param hexes_t
	 * @param settlements_t
	 * @param ports_t
	 * @param roads_t
	 */
	public Map(HashMap<HexLocation, Hex> hexes_t, HashMap<VertexLocation, VertexObject> settlements_t,
			HashMap<VertexLocation, VertexObject> ports_t, HashMap<EdgeLocation, EdgeValue> roads_t)
	{
		hexes = hexes_t;
		settlements = settlements_t;
		ports = ports_t;
		roads = roads_t;
	}
	
	public EdgeLocation[] findAdjEdges(EdgeLocation edge_t)
	{
		return null;
	}
	
	public VertexLocation[] findAdjVertex(EdgeLocation edge_t)
	{
		return null;
	}
	
	public EdgeLocation[] findAdjEdges(VertexLocation vertex_t)
	{
		return null;
	}
	
	public EdgeLocation[] findHexEdges(HexLocation hex_t)
	{
		return null;
	}
	
	public VertexLocation[] findHexVertex(HexLocation hex_t)
	{
		return null;
	}
	
	
	/**
	 * Queries the HashMap of hexes looking for tiles with a specific number value. Could
	 * potentially be hard coded at map creation
	 * 
	 * @param number
	 * @return An ArrayList of HexLocations of hexes assigned the given number.
	 */
	public ArrayList<HexLocation> queryNumber(int number)
	{
		return null;
	}
}
