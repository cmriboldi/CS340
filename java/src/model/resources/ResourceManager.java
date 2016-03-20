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
import shared.exceptions.resources.*;
import shared.exceptions.player.InvalidPlayerIndexException;

public class ResourceManager
{

	private Banker banker = null;
	private Trader trader = null;
	private boolean[] hasPlayerDiscarded = null;

	public ResourceManager()
	{
		PlayerResources newPlayerResources = new PlayerResources();
		this.banker = new Banker(newPlayerResources);
		this.trader = new Trader(newPlayerResources);
		hasPlayerDiscarded = new boolean[] {false, false, false, false};
	}
	
	public ResourceManager(ResourceList[] playerResources, ResourceList bankResources, TradeOffer tradeOffer, boolean[] hasPlayerDiscarded)
	{
		PlayerResources newPlayerResources = new PlayerResources(playerResources);
		banker = new Banker(newPlayerResources, bankResources);
		trader = new Trader(newPlayerResources, tradeOffer);
		this.hasPlayerDiscarded = hasPlayerDiscarded;
	}

	/**
	 * This function is the external access to purchasing a piece.
	 * 
	 * @param playerIndex The index of the player who is purchasing the piece.
	 * @param piece The PieceType which will be bought.
	 * @throws NotEnoughResourcesException 
	 * @throws InvalidPieceTypeException 
	 */
	public void buyPiece(int playerIndex, PieceType piece) throws NotEnoughPlayerResourcesException, InvalidPieceTypeException, NotEnoughResourcesException
	{
		banker.buyPiece(playerIndex, piece);
	}

	/**
	 * @param playerIndex The index of the player who is buying a development card.
	 * @throws NotEnoughResourcesException 
	 */
	public void buyDevCard(int playerIndex) throws NotEnoughPlayerResourcesException, NotEnoughResourcesException
	{
		banker.buyDevCard(playerIndex);
	}

	/**
	 * This is the external call for generating resources for the CatanModel.
	 */
	public void payOutResources(ResourceList[] resLists) throws NotEnoughBankResourcesException
	{
		this.banker.payPlayers(resLists);
	}

	/**
	 * Called when accepting the current Trade Offer.
	 * 
	 * @param int player index of the player accepting the trade offer. This index must be match the
	 *            index of the player receiving the offer.
	 * @throws NotEnoughPlayerResourcesException
	 * @throws InvalidPlayerIndex
	 */
	public void acceptPlayerTrade(int playerIndex) throws NotEnoughPlayerResourcesException, InvalidPlayerIndexException, TradeOfferNullException
	{
		try
		{
			trader.acceptPlayerTrade(playerIndex);
		} catch (Exception e)
		{
			throw e;
		}
		
	}

	/**
	 * @param resList ResourceList with the negative numbers coming from the player and the positive
	 *            numbers coming from the Bank.
	 * @param toPlayerIndex The index of the player who is trading with the bank.
	 * @throws NotEnoughPlayerResourcesException 
	 */
	public void tradeWithBank(ResourceList resList, int toPlayerIndex) throws NotEnoughBankResourcesException, NotEnoughPlayerResourcesException
	{
		banker.tradeWithBank(resList, toPlayerIndex);
	}

	/**
	 * @param playerIndex The index of the player who is using the card.
	 * @param resource The resource that the player is asking for.
	 */
	public void useMonopolyCard(int playerIndex, ResourceType resource)
	{
		trader.useMonopolyCard(playerIndex, resource);
	}

	/**
	 * @param playerIndex The index of the player who is using the card.
	 * @param resourcesAskedFor The ResourceList with the two resources requested.
	 * @throws InvalidNumberOfResourcesRequested 
	 */
	public void useYearOfPlentyCard(int playerIndex, ResourceList resourcesAskedFor) throws NotEnoughBankResourcesException, InvalidNumberOfResourcesRequested
	{
		banker.useYearOfPlentyCard(playerIndex, resourcesAskedFor);
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

	public boolean canTrade(int playerIndex) throws TradeOfferNullException
	{
		return trader.canTrade(playerIndex);
	}
	
	public boolean hasPlayerDiscarded(int playerIndex)
	{
		return hasPlayerDiscarded[playerIndex];
	}

	public void setHasPlayerDiscarded(boolean[] hasPlayerDiscarded)
	{
		this.hasPlayerDiscarded = hasPlayerDiscarded;
	}

	public boolean canAfford(int playerIndex, ResourceList resourceList)
	{
		return banker.canPlayerAfford(playerIndex, resourceList);
	}
	
	public ResourceList getResourcesForPlayer(int playerIndex)
	{
		return banker.getResourcesForPlayer(playerIndex);
	}
	
	public int getResourceCount(int playerIndex, ResourceType resource) {
		return banker.getResourceCount(playerIndex, resource);
	}
	
	public int getTotalResourceCount(int playerIndex) {
		return banker.getTotalResourceCount(playerIndex);
	}

	public TradeOffer getTradeOffer()
	{
		return trader.getTradeOffer();
	}

	public boolean canAcceptTrade(int playerIndex)
	{
		return trader.canAcceptTrade(playerIndex);
	}

	public boolean bankHasResource(ResourceType resource)
	{
		return banker.bankHasResource(resource);
	}
	
	public int getBankResourceCount(ResourceType resource)
	{
		return banker.getBankResourceCount(resource);
	}

	public void discardCards(ResourceList discardedCards, int playerIndex) throws NotEnoughResourcesException
	{
		banker.discardCards(discardedCards, playerIndex);
	}

	public void setTradeOffer(TradeOffer tradeOffer)
	{
		trader.setTradeOffer(tradeOffer);
	}

	public void declineTrade(int playerIndex)
	{
		trader.setTradeOffer(null);
	}

};
