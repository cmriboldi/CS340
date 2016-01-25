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
