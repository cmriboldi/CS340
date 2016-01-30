package model.resources;

import shared.definitions.Cost;
import shared.definitions.PieceType;
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

	}

	/**
	 * This function is the implementation for buying pieces from the bank.
	 * 
	 * @param playerIndex The index of the player who is purchasing the piece.
	 * @param piece The PieceType which will be bought.
	 */
	public void buyPiece(int playerIndex, PieceType piece) throws NotEnoughPlayerResourcesException
	{

	}

	/**
	 * Purchases a development card by paying the bank.
	 * 
	 * @param playerIndex Index of the player who is buying.
	 */
	public void buyDevCard(int playerIndex) throws NotEnoughPlayerResourcesException
	{

	}

	/**
	 * This moves the requested resources to the player who is trading with the bank.
	 * 
	 * @param resList ResourceList with the positive numbers going to the player and the negative
	 *            numbers coming from the Bank.
	 * @param toPlayerIndex The index of the player who is trading with the bank.
	 */
	public void tradeWithBank(ResourceList resList, int toPlayerIndex) throws NotEnoughBankResourcesException
	{

	}

	/**
	 * @param playerIndex The index of the player who is using the card.
	 * @param resourcesAskedFor The ResourceList with the two resources requested.
	 */
	public void useYearOfPlentyCard(int playerIndex, ResourceList resourcesAskedFor) throws NotEnoughBankResourcesException
	{

	}

	public boolean canPlayerAfford(int playerIndex, Cost product)
	{
		boolean canAfford = false;
		if(playerResources.getResourcesForPlayer(playerIndex).greaterThan(product.getCost())) {
			canAfford = true;
		}
		
		return canAfford;
	}

}
