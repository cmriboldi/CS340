package model.resources;

import shared.definitions.ResourceType;
import shared.exceptions.resources.NotEnoughResourcesException;

public class PlayerResources
{

	private ResourceList[] playerResources;

	public PlayerResources()
	{

	}

	public PlayerResources(ResourceList[] playerResources)
	{
		this.playerResources = playerResources;
	}

	/**
	 * Adds the specified amount of resources to a given player.
	 * 
	 * @param resList A ResourceList with the positive amounts going to be added to the player.
	 * @param playerIndex The index of the player who will get the resources.
	 */
	public void addResourcesToPlayer(ResourceList resList, int playerIndex)
	{
		playerResources[playerIndex].plus(resList);
	}

	/**
	 * Takes the specified amount of resources from a given player.
	 * 
	 * @param resList A ResourceList with the positive amounts needed to be taken from the player.
	 * @param playerIndex The index of the player who will lose the resources.
	 */
	public void takeResourcesFromPlayer(ResourceList resList, int playerIndex) throws NotEnoughResourcesException
	{
		if(playerResources[playerIndex].lessThan(resList))
		{
			throw new NotEnoughResourcesException("There were not enough resources in the players stack to take more.");
		}
		playerResources[playerIndex].minus(resList);
	}

	/**
	 * Get's the resources for the given player.
	 * 
	 * @param playerIndex Index of the player.
	 * @return ResourceList for the given player.
	 */
	public ResourceList getResourcesForPlayer(int playerIndex)
	{
		return playerResources[playerIndex];
	}

	public boolean canPlayerAfford(int playerIndex, ResourceList resourceList)
	{
		boolean canAfford = false;
		
		if(playerResources[playerIndex].greaterThanOrEqual(resourceList)) 
		{
			canAfford = true;
		}
		
		return canAfford;
	}

	public int getResourceCount(int playerIndex, ResourceType resource)
	{
		return playerResources[playerIndex].getResourceTypeCount(resource);
	}

	public int getResourceTypeCount(int playerIndex, ResourceType resource)
	{
		return playerResources[playerIndex].getResourceTypeCount(resource);
	}
}
