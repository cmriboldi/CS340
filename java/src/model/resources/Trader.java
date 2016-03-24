package model.resources;

import shared.definitions.ResourceType;
import shared.exceptions.player.InvalidPlayerIndexException;
import shared.exceptions.resources.NotEnoughPlayerResourcesException;
import shared.exceptions.resources.NotEnoughResourcesException;
import shared.exceptions.resources.TradeOfferNullException;

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
	private TradeOffer tradeOffer = null;

	public Trader(PlayerResources playerResources)
	{
		this.playerResources = playerResources;
		this.tradeOffer = new TradeOffer();
	}

	public Trader(PlayerResources playerResources, TradeOffer tradeOffer)
	{
		this.playerResources = playerResources;
		this.tradeOffer = tradeOffer;
	}

	/**
	 * This function is called when a new Trade Offer wants to be created and accepted.
	 * 
	 * @param tradeOffer
	 * @throws NotEnoughPlayerResourcesException
	 */
	public void tradeWithPlayer(TradeOffer tradeOffer) throws NotEnoughPlayerResourcesException
	{
		this.tradeOffer = tradeOffer;
		try
		{
			acceptPlayerTrade(tradeOffer.getReceiver());
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * @param playerIndex The index of the player who is using the card.
	 * @param resource The resource that the player is asking for.
	 */
	public void useMonopolyCard(int playerIndex, ResourceType resource)
	{
		System.out.println("resourse is: " + resource.toString());
		
		for(int index = 0; index < 4; index++)
		{
			ResourceList monopolizedResources = new ResourceList();
			int resourceCount = playerResources.getResourceCount(index, resource);
			monopolizedResources.addResource(resource, resourceCount);
			if(index != playerIndex) {
				try
				{
					playerResources.takeResourcesFromPlayer(monopolizedResources, index);
					playerResources.addResourcesToPlayer(monopolizedResources, playerIndex);
				} catch (NotEnoughResourcesException e)
				{
					System.out.println(e);
					e.printStackTrace();
				}
			}
		}
		
	}

	public void acceptPlayerTrade(int playerIndex) throws InvalidPlayerIndexException, NotEnoughPlayerResourcesException, TradeOfferNullException
	{
		if(!hasTradeOffer())
		{
			throw new TradeOfferNullException("There is no trade offer initialized.");
		}
		
		if(playerIndex != tradeOffer.getReceiver())
		{
			throw new InvalidPlayerIndexException("The player index must match the index of the trade offer reciever.");
		}
		
		boolean senderCanAfforTrade = playerResources.canPlayerAfford(tradeOffer.getSender(), tradeOffer.getResourcesOffer());
		boolean recieverCanAfforTrade = playerResources.canPlayerAfford(tradeOffer.getReceiver(), tradeOffer.getResourcesOffer().invert());
	
		if(!senderCanAfforTrade)
		{
			throw new NotEnoughPlayerResourcesException("The player offering the trade doesn't have enough resources.");
		} else if(!recieverCanAfforTrade)
		{
			throw new NotEnoughPlayerResourcesException("The player accepting the trade doesn't have enough resources.");
		}
		
		playerResources.addResourcesToPlayer(tradeOffer.getResourcesOffer(), tradeOffer.getReceiver());
		playerResources.addResourcesToPlayer(tradeOffer.getResourcesOffer().invert(), tradeOffer.getSender());
		
	}

	public boolean canTrade(int playerIndex) throws TradeOfferNullException
	{
		if(tradeOffer == null)
		{
			throw new TradeOfferNullException("There is no trade offer initialized.");
		}
		return tradeOffer.getReceiver() == playerIndex || tradeOffer.getSender() == playerIndex;
	}

	public TradeOffer getTradeOffer()
	{
		return tradeOffer;
	}

	public boolean canAcceptTrade(int playerIndex)
	{
		return playerResources.canPlayerAfford(tradeOffer.getReceiver(), tradeOffer.getResourcesOffer().invert());
	}

	public void setTradeOffer(TradeOffer tradeOffer)
	{
		this.tradeOffer = tradeOffer;
	}

	public void robPlayer(int victimIndex, int playerIndex) throws NotEnoughResourcesException
	{
		if(playerResources.getTotalResourceCount(victimIndex) > 0) {
			ResourceType resource = playerResources.takeRandomResource(victimIndex);
			ResourceList rs = new ResourceList();
			rs.addResource(resource, 1);
			playerResources.addResourcesToPlayer(rs, playerIndex);
		}
	}

	public boolean hasTradeOffer()
	{
		if(tradeOffer == null)
			return false;
		return (this.tradeOffer.getReceiver() != -1);
	}

}
