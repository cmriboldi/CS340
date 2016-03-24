package model.resources;

import shared.definitions.ResourceType;
import shared.exceptions.resources.NotEnoughResourcesException;

/**
 * The ResourceList is an object keeps track of how many resources it contains.
 * 
 * @author Christian Riboldi
 * @author Clayton Condie
 * @author Jacob Brewer
 * @author Joshua Powers
 * @author Joshua Van Steeter
 * @version 1.0 Build Jan, 2016.
 */
public class ResourceList implements Comparable<ResourceList>
{

	private int brick;
	private int ore;
	private int wheat;
	private int wood;
	private int sheep;

	public ResourceList()
	{
		brick = 0;
		ore = 0;
		wheat = 0;
		wood = 0;
		sheep = 0;
	}

	public ResourceList(int brick, int ore, int sheep, int wheat, int wood)
	{
		this.brick = brick;
		this.ore = ore;
		this.sheep = sheep;
		this.wheat = wheat;
		this.wood = wood;
	}

	/**
	 * Increase the number of brick by the given quantity.
	 * 
	 * @param quantity
	 */
	public void addBrick(int quantity)
	{
		brick += quantity;
	}

	/**
	 * Increase the number of ore by the given quantity.
	 * 
	 * @param quantity
	 */
	public void addOre(int quantity)
	{
		ore += quantity;
	}

	/**
	 * Increase the number of wheat by the given quantity.
	 * 
	 * @param quantity
	 */
	public void addWheat(int quantity)
	{
		wheat += quantity;
	}

	/**
	 * Increase the number of wood by the given quantity.
	 * 
	 * @param quantity
	 */
	public void addWood(int quantity)
	{
		wood += quantity;
	}

	/**
	 * Increase the number of sheep by the given quantity.
	 * 
	 * @param quantity
	 */
	public void addSheep(int quantity)
	{
		sheep += quantity;
	}

	/**
	 * Decrease the number of brick by the given quantity.
	 * 
	 * @param quantity
	 */
	public void removeBrick(int quantity) throws NotEnoughResourcesException
	{
		brick -= quantity;
	}

	/**
	 * Decrease the number of ore by the given quantity.
	 * 
	 * @param quantity
	 */
	public void removeOre(int quantity) throws NotEnoughResourcesException
	{
		ore -= quantity;
	}

	/**
	 * Decrease the number of wheat by the given quantity.
	 * 
	 * @param quantity
	 */
	public void removeWheat(int quantity) throws NotEnoughResourcesException
	{
		wheat -= quantity;
	}

	/**
	 * Decrease the number of wood by the given quantity.
	 * 
	 * @param quantity
	 */
	public void removeWood(int quantity) throws NotEnoughResourcesException
	{
		wood -= quantity;
	}

	/**
	 * Decrease the number of sheep by the given quantity.
	 * 
	 * @param quantity
	 */
	public void removeSheep(int quantity) throws NotEnoughResourcesException
	{
		sheep -= quantity;
	}
	
	public void addResource(ResourceType resource, int amount)
	{
		switch (resource)
		{
		case BRICK:
			this.brick += amount;
			break;
		case ORE:
			ore += amount;
			break;
		case SHEEP:
			sheep += amount;
			break;
		case WHEAT:
			wheat += amount;
			break;
		case WOOD:
			wood += amount;
			break;
		}
	}
	
	public void removeResource(ResourceType resource, int amount)
	{
		switch (resource)
		{
		case BRICK:
			this.brick -= amount;
			break;
		case ORE:
			ore -= amount;
			break;
		case SHEEP:
			sheep -= amount;
			break;
		case WHEAT:
			wheat -= amount;
			break;
		case WOOD:
			wood -= amount;
			break;
		}
	}
	
	public int getResourceTypeCount(ResourceType resource)
	{
		int resourceCount = 0;
		switch (resource)
		{
		case BRICK:
			resourceCount = this.brick;
			break;
		case ORE:
			resourceCount = this.ore;
			break;
		case SHEEP:
			resourceCount = this.sheep;
			break;
		case WHEAT:
			resourceCount = this.wheat;
			break;
		case WOOD:
			resourceCount = this.wood;
			break;
		}
		return resourceCount;
	}

	public int getResourceCount()
	{
		return this.brick + this.ore + this.sheep + this.wheat + this.wood;
	}

	public void plus(ResourceList rs)
	{
		this.brick += rs.brick;
		this.ore += rs.ore;
		this.wheat += rs.wheat;
		this.wood += rs.wood;
		this.sheep += rs.sheep;
	}

	public void minus(ResourceList rs)
	{
		this.brick -= rs.brick;
		this.ore -= rs.ore;
		this.wheat -= rs.wheat;
		this.wood -= rs.wood;
		this.sheep -= rs.sheep;
	}

	public boolean greaterThanOrEqual(ResourceList rs)
	{
		return this.compareTo(rs) >= 0 ? true : false;
	}

	public boolean greaterThan(ResourceList rs)
	{
		return this.compareTo(rs) > 0 ? true : false;
	}

	public boolean lessThan(ResourceList rs)
	{
		return this.compareTo(rs) < 0 ? true : false;
	}

	@Override
	public int compareTo(ResourceList rs)
	{
		int compareValue = 0;

		ResourceList thisCopy = new ResourceList(this.brick, this.ore, this.sheep, this.wheat, this.wood);

		thisCopy.minus(rs);

		int smallestResCount = Math.min(thisCopy.brick,
				Math.min(thisCopy.ore, Math.min(thisCopy.sheep, Math.min(thisCopy.wheat, thisCopy.wood))));
		
		if (smallestResCount < 0)
		{
			compareValue = -1;
		} else if (thisCopy.getResourceCount() > 0)
		{
			compareValue = 1;
		}

		return compareValue;
	}

	public ResourceList invert()
	{
		ResourceList invertedResList = new ResourceList(this.brick, this.ore, this.sheep, this.wheat, this.wood);
		invertedResList.brick *= -1;
		invertedResList.ore *= -1;
		invertedResList.wheat *= -1;
		invertedResList.wood *= -1;
		invertedResList.sheep *= -1;
		return invertedResList;
	}

	@Override
	public String toString()
	{
		return "ResourceList [brick=" + brick + ", ore=" + ore + ", wheat=" + wheat + ", wood=" + wood + ", sheep="
				+ sheep + "]";
	}

	public void removeNegatives()
	{
		for(ResourceType resource : ResourceType.values())
		{
			if(this.getResourceTypeCount(resource) < 0)
			{
				switch (resource)
				{
				case BRICK:
					this.brick = 0;
					break;
				case ORE:
					this.ore = 0;
					break;
				case SHEEP:
					this.sheep = 0;
					break;
				case WHEAT:
					this.wheat = 0;
					break;
				case WOOD:
					this.wood = 0;
					break;
				}
			}
		}
	}

	public ResourceType removeRandomResource() throws NotEnoughResourcesException
	{
		ResourceType resource = null;
		if(this.getResourceCount() <= 0) {
			throw new NotEnoughResourcesException("Not enough resources to rob from player.");
		}
		while(resource == null) {
			ResourceType random = ResourceType.values()[(int)(Math.random()*(ResourceType.values().length))];
			if(this.getResourceTypeCount(random) > 0) {
				resource = random;
			}
		}
		this.removeResource(resource, 1);
		return resource;
	}


}
