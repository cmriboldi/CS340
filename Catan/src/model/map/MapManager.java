package model.map;

//Project Imports
import shared.locations.EdgeLocation;
import shared.locations.VertexLocation;

public class MapManager 
{
	
	/** The Map which all the public functions will interact with */
	private Map map;
	
	
	/**
	 * Queries the Map and determines which player possess the longest road
	 * @return integer representation of the player with the longest road
	 */
	public int findLargestRoad()
	{
		return 0;
	}
	
	/**
	 * Attempts to place a road at the given edge location, to the player indicated,
	 * will throw an exception if something goes awry
	 * @param edge 
	 * @param player
	 */
	public void placeRoad(EdgeLocation edge, int player)
	{

	}
	
	/**
	 * Places a settlement on the given vertex, belonging to the indicated player
	 * @param vertex
	 * @param player
	 */
	public void placeSettlement(VertexLocation vertex, int player)
	{
		
	}
	
	/**
	 * Upgrades the state of the indicated vertex from settlement to city, the player id 
	 * plays a minor role of action validation, may be removed in the future
	 * @param vertex
	 * @param player
	 */
	public void upgradeSettlement(VertexLocation vertex, int player)
	{
		
	}
	
	/**
	 * Passes the "dice" roll in as a parameter and returns an communication object the resourceManager 
	 * can use to update the resource states.
	 * @param number
	 * @return
	 */
	public Object distributeResources(int number)
	{
		return null;
	}
	
	

}
