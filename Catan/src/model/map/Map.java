package model.map;


//JAVA imports
import java.util.HashMap;
import java.util.ArrayList;

//Project Imports
import shared.locations.*;


public class Map 
{
	/** container for all the tiles on the board */
	HashMap<HexLocation, Hex> hexes;
	
	/** container for all the settlements that exist on the board */
	ArrayList<VertexObject> settlements;
	
	/** container for all the ports that exist on the board */
	ArrayList<VertexObject> ports;
	
	/** container for all the roads that exist on the board */
	ArrayList<EdgeValue> roads;
	
	
	/**
	 * Queries the HashMap of hexes looking for tiles with a specific number value.
	 * Could potentially be hard coded at map creation
	 * @param number
	 * @return An ArrayList of HexLocations of hexes assigned the given number.
	 */
	public ArrayList<HexLocation> queryNumber(int number)
	{
		
		return null;
	}
	
	
}
