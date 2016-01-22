package model.options;

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
	public boolean canBuildRoad()
	{
		return true;
	}

	/**
	 * Check if a town can be built
	 * 
	 * @param player index
	 * @return True if allowed False otherwise
	 */
	public boolean canBuildTown()
	{
		return true;
	}

	/**
	 * Check if a city can be built
	 * 
	 * @param player index
	 * @return True if allowed False otherwise
	 */
	public boolean canBuildCity()
	{
		return true;
	}

	/**
	 * Check if a player can trade
	 * 
	 * @param player index
	 * @return True if allowed False otherwise
	 */
	public boolean canTrade()
	{
		return true;
	}

	/**
	 * Check if a player can trade via port
	 * 
	 * @param player index
	 * @return True if allowed False otherwise
	 */
	public boolean canMaritimeTrade()
	{
		return true;
	}

	/**
	 * Check if it is a player turn
	 * 
	 * @param player index
	 * @return True if allowed False otherwise
	 */
	public boolean canPlay()
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
	public boolean canPlaceRoad()
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
	public boolean canPlaceTown()
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
	public boolean canPlaceCity()
	{
		return true;
	}

	/**
	 * Check if a proposed trade can be sent or made
	 * 
	 * @param player index
	 * @param Resources to give
	 * @param Resources to get
	 * @return True if allowed False otherwise
	 */
	public boolean isValidTrade()
	{
		return true;
	}
}
