package resources;

/** The Trader handles all Player to Player transactions.
* @author Christian Riboldi
* @author Clayton Condie
* @author Jacob Brewer
* @author Joshua Powers
* @author Joshua Van Steeter
* @version 1.0 Build Jan, 2016.
*/

public class Trader
{

	private ResourceList[] playerResources = null;
	
	public Trader() 
	{
		
	}
	
	/**
	 * This function is were trading Player to Player is implemented.
	 * @param resLists This is an ResourceList where the positive numbers in the arrays are the resources going to one player and the negative numbers are coming from another player.  
	 * @param toPlayerIndex The index of the player who is receiving the positive numbered resources.
	 * @param fromPlayerIndex The index of the player who is receiving the negative numbered resources.
	 */
	public void tradeWithPlayer(ResourceList resList, int fromPlayerIndex, int toPlayerIndex)
	{
		
	}
	
}
