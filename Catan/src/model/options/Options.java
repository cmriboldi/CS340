package model.options;

public class Options
{
	public Options()
	{
		
	}
	
	/**
	 * Check if a road can be built
	 * @param player index
	 * @return True if allowed False otherwise
	 */
	public boolean CanBuildRoad()
	{
		return true;
	}
	
	/**
	 * Check if a town can be built
	 * @param player index
	 * @return True if allowed False otherwise
	 */
	public boolean CanBuildTown()
	{
		return true;
	}
	
	/**
	 * Check if a city can be built
	 * @param player index
	 * @return True if allowed False otherwise
	 */
	public boolean CanBuildCity()
	{
		return true;
	}
	
	/**
	 * Check if a player can trade
	 * @param player index
	 * @return True if allowed False otherwise
	 */
	public boolean CanTrade()
	{
		return true;
	}
	
	/**
	 * Check if a player can trade via port
	 * @param player index
	 * @return True if allowed False otherwise
	 */
	public boolean CanMaritimeTrade()
	{
		return true;
	}
	
	/**
	 * Check if it is a player turn
	 * @param player index
	 * @return True if allowed False otherwise
	 */
	public boolean CanPlay()
	{
		return true;
	}
	
	/**
	 * Check if a road can be placed in specific position
	 * @param player index
	 * @param Location
	 * @return True if allowed False otherwise
	 */
	public boolean CanPlaceRoad()
	{
		return true;
	}
	
	/**
	 * Check if a town can be placed in specific position
	 * @param player index
	 * @param Location
	 * @return True if allowed False otherwise
	 */
	public boolean CanPlaceTown()
	{
		return true;
	}
	
	/**
	 * Check if a city can be placed in specific position
	 * @param player index
	 * @param Location
	 * @return True if allowed False otherwise
	 */
	public boolean CanPlaceCity()
	{
		return true;
	}
	
	/**
	 * Check if a proposed trade can be sent or made
	 * @param player index
	 * @param Resources to give
	 * @param Resources to get
	 * @return True if allowed False otherwise
	 */
	public boolean ValidTrade()
	{
		return true;
	}
}

