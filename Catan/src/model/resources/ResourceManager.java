package model.resources;

/** The ResourceManger class manages all transactions other packages need to make concerning resources.
* @author Christian Riboldi
* @author Clayton Condie
* @author Jacob Brewer
* @author Joshua Powers
* @author Joshua Van Steeter
* @version 1.0 Build Jan, 2016.
*/

import shared.definitions.*;
import shared.exceptions.resources.NotEnoughPlayerResourcesException;
import shared.exceptions.resources.NotEnoughBankResourcesException;

public class ResourceManager
{

	private Banker banker = null;
	private Trader trader = null;

	public ResourceManager(ResourceList[] playerResources, ResourceList bankResources, TradeOffer tradeOffer)
	{
		PlayerResources newPlayerResources = new PlayerResources(playerResources);
		banker = new Banker(newPlayerResources, bankResources);
		trader = new Trader(newPlayerResources, tradeOffer);
	}

	/**
	 * This function is the external access to purchasing a piece.
	 * 
	 * @param playerIndex The index of the player who is purchasing the piece.
	 * @param piece The PieceType which will be bought.
	 */
	public void buyPiece(int playerIndex, PieceType piece) throws NotEnoughPlayerResourcesException
	{

	}

	/**
	 * @param playerIndex The index of the player who is buying a development card.
	 */
	public void buyDevCard(int playerIndex) throws NotEnoughPlayerResourcesException
	{

	}

	/**
	 * This is the external call for generating resources for the CatanModel.
	 */
	public void generateResources() throws NotEnoughBankResourcesException
	{

	}

	/**
	 * @param resLists This is an ResourceList where the positive numbers in the arrays are the
	 *            resources going to one player and the negative numbers are coming from another
	 *            player.
	 * @param toPlayerIndex The index of the player who is receiving the positive numbered
	 *            resources.
	 * @param fromPlayerIndex The index of the player who is receiving the negative numbered
	 *            resources.
	 */
	public void tradeWithPlayer(ResourceList resList, int toPlayerIndex, int fromPlayerIndex) throws NotEnoughPlayerResourcesException
	{

	}

	/**
	 * @param resList ResourceList with the positive numbers going to the player and the negative
	 *            numbers coming from the Bank.
	 * @param toPlayerIndex The index of the player who is trading with the bank.
	 */
	public void tradeWithBank(ResourceList resList, int toPlayerIndex) throws NotEnoughBankResourcesException
	{

	}

	/**
	 * @param playerIndex The index of the player who is using the card.
	 * @param resource The resource that the player is asking for.
	 */
	public void useMonopolyCard(int playerIndex, ResourceType resource)
	{

	}

	/**
	 * @param playerIndex The index of the player who is using the card.
	 * @param resourcesAskedFor The ResourceList with the two resources requested.
	 */
	public void useYearOfPlentyCard(int playerIndex, ResourceList resourcesAskedFor) throws NotEnoughBankResourcesException
	{

	}
	
	/**
	 * Check if a player can afford a road
	 * 
	 * @param player index
	 * @return True if allowed False otherwise
	 */
	public boolean canAffordRoad(int playerIndex)
	{
		return banker.canPlayerAfford(playerIndex, Cost.ROAD);
	}

	/**
	 * Check if a player can afford a town
	 * 
	 * @param player index
	 * @return True if allowed False otherwise
	 */
	public boolean canAffordTown(int playerIndex)
	{
		return banker.canPlayerAfford(playerIndex, Cost.SETTLEMENT);
	}

	/**
	 * Check if a player can afford a city
	 * 
	 * @param player index
	 * @return True if allowed False otherwise
	 */
	public boolean canAffordCity(int playerIndex)
	{
		return banker.canPlayerAfford(playerIndex, Cost.CITY);
	}
	
	/**
	 * Check if a player can afford a development card
	 * 
	 * @param player index
	 * @return True if allowed False otherwise
	 */
	public boolean canAffordDevCard(int playerIndex)
	{
		return banker.canPlayerAfford(playerIndex, Cost.DEVCARD);
	}

};
