package model.options;

import model.map.EdgeValue;
import model.map.VertexObject;
import shared.locations.EdgeLocation;
import shared.locations.VertexLocation;

/**
 * The Options class is in charge of all canDo methods.
 * 
 * @author Christian Riboldi
 * @author Clayton Condie
 * @author Jacob Brewer
 * @author Joshua Powers
 * @author Joshua Van Steeter
 * @version 1.0 Build Jan, 2016.
 */
public class Options
{
	public Options()
	{

	}

	/**
	 * Check if a road can be built
	 * 
	 * @param player index
	 * @return True if allowed False otherwise
	 */
	public boolean canBuildRoad(int index)
	{
		return true;
	}

	/**
	 * Check if a town can be built
	 * 
	 * @param player index
	 * @return True if allowed False otherwise
	 */
	public boolean canBuildTown(int index)
	{
		return true;
	}

	/**
	 * Check if a city can be built
	 * 
	 * @param player index
	 * @return True if allowed False otherwise
	 */
	public boolean canBuildCity(int index)
	{
		return true;
	}

	/**
	 * Check if a player can trade
	 * 
	 * @param player index
	 * @return True if allowed False otherwise
	 */
	public boolean canTrade(int index)
	{
		return true;
	}

	/**
	 * Check if a player can trade via port
	 * 
	 * @param player index
	 * @return True if allowed False otherwise
	 */
	public boolean canMaritimeTrade(int index)
	{
		return true;
	}

	/**
	 * Check if it is a player turn
	 * 
	 * @param player index
	 * @return True if allowed False otherwise
	 */
	public boolean canPlay(int index)
	{
		return true;
	}

	/**
	 * Check if a road can be placed in specific position
	 * 
	 * @param player index
	 * @param Location
	 * @return True if allowed False otherwise
	 */
	public boolean canPlaceRoad(int index, EdgeLocation location)
	{
		return true;
	}

	/**
	 * Check if a town can be placed in specific position
	 * 
	 * @param player index
	 * @param Location
	 * @return True if allowed False otherwise
	 */
	public boolean canPlaceTown(int index, VertexLocation location)
	{
		return true;
	}

	/**
	 * Check if a city can be placed in specific position
	 * 
	 * @param player index
	 * @param Location
	 * @return True if allowed False otherwise
	 */
	public boolean canPlaceCity(int index, VertexLocation location)
	{
		return true;
	}
}
