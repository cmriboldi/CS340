package resources;

/** The ResourceManger class manages all transactions other packages need to make concerning resources.
* @author Christian Riboldi
* @author Clayton Condie
* @author Jacob Brewer
* @author Joshua Powers
* @author Joshua Van Steeter
* @version 1.0 Build Jan, 2016.
*/

public class ResourceManager 
{
	
	private Banker banker = null;
	private Trader trader = null;
	private ResourceGenerator resGenerator = null;
	
	
	public ResourceManager()
	{
		
	}
	
	
	/**
	 * @param resLists This is an ResourceList where the positive numbers in the arrays are the resources going to one player and the negative numbers are coming from another player.  
	 * @param toPlayerIndex The index of the player who is receiving the positive numbered resources.
	 * @param fromPlayerIndex The index of the player who is receiving the negative numbered resources.
	 */
	public void tradeWithPlayer(ResourceList resList, int toPlayerIndex, int fromPlayerIndex)
	{
		
	}
	
	/**
	 * @param resList ResourceList with the positive numbers going to the player and the negative numbers coming from the Bank.
	 * @param toPlayerIndex The index of the player who is trading with the bank.
	 */
	public void tradeWithBank(ResourceList resList, int toPlayerIndex)
	{
		
	}
	
	/**
	 * @param playerIndex The index of the player who is buying a road.
	 */
	public void buyRoad(int playerIndex)
	{
		
	}
	
	/**
	 * @param playerIndex The index of the player who is buying a settlement.
	 */
	public void buySettlement(int playerIndex)
	{
		
	}
	
	/**
	 * @param playerIndex The index of the player who is buying a city.
	 */
	public void buyCity(int playerIndex) 
	{
		
	}
	
	/**
	 * @param playerIndex The index of the player who is buying a development card.
	 */
	public void buyDevCard(int playerIndex)
	{
		
	}
	
	/**
	 * This is the external call for generating resources for the CatanModel.
	 */
	public void generateResources()
	{
		
	}
	
}
