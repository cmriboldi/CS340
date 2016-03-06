package model.resources;

import shared.definitions.Cost;
import shared.definitions.PieceType;
import shared.definitions.ResourceType;
import shared.exceptions.resources.*;

/**
 * The Banker handles all transactions in or out of the Bank
 * 
 * @author Christian Riboldi
 * @author Clayton Condie
 * @author Jacob Brewer
 * @author Joshua Powers
 * @author Joshua Van Steeter
 * @version 1.0 Build Jan, 2016.
 */
public class Banker
{

	private ResourceList bank;
	private PlayerResources playerResources;

	public Banker(PlayerResources playerResources, ResourceList bankResources)
	{
		this.bank = bankResources;
		this.playerResources = playerResources;
	}

	/**
	 * The banker pays out the desired resources to each of the players.
	 * 
	 * @param resLists An array of ResourceLists where the index of the ResourceList is the same as
	 *            the index of the Player being paid.
	 */
	public void payPlayers(ResourceList[] resLists) throws NotEnoughBankResourcesException
	{
		for(int index = 0; index < 4; index++)
		{
			if(!bank.greaterThan(resLists[index]))
			{
				throw new NotEnoughBankResourcesException("There are not enough resources in the bank to give to player " + index + ".");
			}
			
			playerResources.addResourcesToPlayer(resLists[index], index);
			bank.minus(resLists[index]);
		}
	}

	/**
	 * This function is the implementation for buying pieces from the bank.
	 * 
	 * @param playerIndex The index of the player who is purchasing the piece.
	 * @param piece The PieceType which will be bought.
	 * @throws InvalidPieceTypeException 
	 * @throws NotEnoughResourcesException 
	 */
	public void buyPiece(int playerIndex, PieceType piece) throws NotEnoughPlayerResourcesException, InvalidPieceTypeException, NotEnoughResourcesException
	{
		Cost product = null;
		switch (piece)
		{
		case CITY:
			product = Cost.CITY;
			break;
		case ROAD:
			product = Cost.ROAD;
			break;
		case SETTLEMENT:
			product = Cost.SETTLEMENT;
			break;
		default:
			throw new InvalidPieceTypeException("Invalid piece trying to be purchased in the buyPiece function.");
		}
		
		if(product == null || !canPlayerAfford(playerIndex, product))
		{
			throw new NotEnoughPlayerResourcesException("Not enough player resources to purchase this piece.");
		}
		playerResources.takeResourcesFromPlayer(product.getCost(), playerIndex);
		bank.plus(product.getCost());
	}

	/**
	 * Purchases a development card by paying the bank.
	 * 
	 * @param playerIndex Index of the player who is buying.
	 * @throws NotEnoughResourcesException 
	 */
	public void buyDevCard(int playerIndex) throws NotEnoughPlayerResourcesException, NotEnoughResourcesException
	{
		if(!canPlayerAfford(playerIndex, Cost.DEVCARD))
		{
			throw new NotEnoughPlayerResourcesException("Not enough player resources to purchase a development card.");
		}
		playerResources.takeResourcesFromPlayer(Cost.DEVCARD.getCost(), playerIndex);
		bank.plus(Cost.DEVCARD.getCost());
	}

	/**
	 * This moves the requested resources to the player who is trading with the bank.
	 * 
	 * @param resList ResourceList with the positive numbers going to the player and the negative
	 *            numbers coming from the Bank.
	 * @param toPlayerIndex The index of the player who is trading with the bank.
	 * @throws NotEnoughPlayerResourcesException 
	 */
	public void tradeWithBank(ResourceList resList, int toPlayerIndex) throws NotEnoughBankResourcesException, NotEnoughPlayerResourcesException
	{
		if(!bank.greaterThan(resList))
		{
			throw new NotEnoughBankResourcesException("There are not enough resources in the bank make that trade");
		} else if(!playerResources.getResourcesForPlayer(toPlayerIndex).greaterThan(resList.invert()))
		{
			throw new NotEnoughPlayerResourcesException("The player doesn't have enough resources to offer that trade.");
		}
		playerResources.addResourcesToPlayer(resList, toPlayerIndex);
		bank.minus(resList);
	}

	/**
	 * @param playerIndex The index of the player who is using the card.
	 * @param resourcesAskedFor The ResourceList with the two resources requested.
	 * @throws InvalidNumberOfResourcesRequested 
	 */
	public void useYearOfPlentyCard(int playerIndex, ResourceList resourcesAskedFor)
			throws NotEnoughBankResourcesException, InvalidNumberOfResourcesRequested
	{
		if (!bank.greaterThan(resourcesAskedFor))
		{
			throw new NotEnoughBankResourcesException("There are not enough resources in the bank for to use this monopoly card.");
		} else if (resourcesAskedFor.getResourceCount() != 2)
		{
			throw new InvalidNumberOfResourcesRequested("You must request two resources when you use the year of plenty card.");
		}
		
		playerResources.addResourcesToPlayer(resourcesAskedFor, playerIndex);
		bank.minus(resourcesAskedFor);
	}

	public boolean canPlayerAfford(int playerIndex, Cost product)
	{
		return playerResources.canPlayerAfford(playerIndex, product.getCost());
	}

	public boolean canPlayerAfford(int playerIndex, ResourceList resourceList)
	{
		return playerResources.canPlayerAfford(playerIndex, resourceList);
	}

	public ResourceList getResourcesForPlayer(int playerIndex)
	{
		return playerResources.getResourcesForPlayer(playerIndex);
	}

	public int getResourceCount(int playerIndex, ResourceType resource)
	{
		return playerResources.getResourceCount(playerIndex, resource);
	}

	public int getTotalResourceCount(int playerIndex)
	{
		return playerResources.getTotalResourceCount(playerIndex);
	}

	public boolean bankHasResource(ResourceType resource)
	{
		return this.bank.getResourceTypeCount(resource) > 0;
	}

}
