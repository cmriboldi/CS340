package model.resources;

import shared.definitions.ResourceType;
import shared.exceptions.resources.NotEnoughPlayerResourcesException;

/**
 * The Trader handles all Player to Player transactions.
 * 
 * @author Christian Riboldi
 * @author Clayton Condie
 * @author Jacob Brewer
 * @author Joshua Powers
 * @author Joshua Van Steeter
 * @version 1.0 Build Jan, 2016.
 */

public class Trader
{

	private PlayerResources playerResources = null;

	public Trader()
	{

	}

	/**
	 * This function is were trading Player to Player is implemented.
	 * 
	 * @param resLists This is an ResourceList where the positive numbers in the arrays are the
	 *            resources going to one player and the negative numbers are coming from another
	 *            player.
	 * @param toPlayerIndex The index of the player who is receiving the positive numbered
	 *            resources.
	 * @param fromPlayerIndex The index of the player who is receiving the negative numbered
	 *            resources.
	 */
	public void tradeWithPlayer(ResourceList resList, int fromPlayerIndex, int toPlayerIndex) throws NotEnoughPlayerResourcesException
	{

	}

	/**
	 * @param playerIndex The index of the player who is using the card.
	 * @param resource The resource that the player is asking for.
	 */
	public void useMonopolyCard(int playerIndex, ResourceType resource)
	{

	}

}
