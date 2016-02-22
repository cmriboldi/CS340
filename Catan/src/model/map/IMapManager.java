package model.map;

import java.util.ArrayList;
import java.util.HashMap;

import model.resources.ResourceList;
import shared.exceptions.map.IllegalPlacementException;
import shared.exceptions.map.OwnershipException;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

public interface IMapManager 
{
	/** The Map which all the public functions will interact with */
	Map map = new Map();

	/** Gets the map ... TESTING PURPOSES ONLY */
	public Map getMap();


	/**
	 * Queries the Map and determines which player possess the longest road
	 * 
	 * @return integer representation of the player with the longest road
	 */
	public int findLargestRoad();

	/**
	 * Attempts to place a road at the given edge location, to the player indicated, will throw an
	 * exception if something goes awry
	 * 
	 * @param edge
	 * @param player
	 */
	public void placeRoad(EdgeLocation edge, int player) throws IllegalPlacementException;

	/**
	 * canDo representation of the function placeRoad
	 * @param edge
	 * @param player
     * @return
     */
	public boolean canPlaceRoad(EdgeLocation edge, int player);

	/**
	 * Places a settlement on the given vertex, belonging to the indicated player
	 * 
	 * @param vertex
	 * @param player
	 */
	public void placeSettlement(VertexLocation vertex, int player) throws IllegalPlacementException;

	/**
	 * Upgrades the state of the indicated vertex from settlement to city, the player id plays a
	 * minor role of action validation, may be removed in the future
	 * 
	 * @param vertex
	 * @param player
	 */
	public void upgradeSettlement(VertexLocation vertex, int player) throws OwnershipException;

	/**
	 * Passes the "dice" roll in as a parameter and returns an ArrayList of ResourceLists that the
	 * resource manager can use to update the resource states.
	 * 
	 * @param number
	 * @return
	 */
	public ArrayList<ResourceList> distributeResources(int number);

	/**
	 * Used by the facade to update the position of the robber token.
	 * 
	 * @param hex
	 */
	public void placeRobber(HexLocation hex);


	public HashMap<HexLocation, Hex> getHexes();

	public HashMap<VertexLocation, Settlement> getSettlements();

	public HashMap<EdgeLocation, Port> getPorts();

	public HashMap<EdgeLocation, Road> getRoads();

	public HexLocation getRobber();





}
