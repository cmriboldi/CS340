package model.resources;

public class PlayerResources
{

	private ResourceList[] playerResources;

	public PlayerResources()
	{

	}

	/**
	 * Adds the specified amount of resources to a given player.
	 * 
	 * @param resList A ResourceList with the positive amounts going to be added to the player.
	 * @param playerIndex The index of the player who will get the resources.
	 */
	public void addResourcesToPlayer(ResourceList resList, int playerIndex)
	{

	}

	/**
	 * Takes the specified amount of resources from a given player.
	 * 
	 * @param resList A ResourceList with the positive amounts needed to be taken from the player.
	 * @param playerIndex The index of the player who will lose the resources.
	 */
	public void takeResourcesFromPlayer(ResourceList resList, int playerIndex)
	{

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
}
