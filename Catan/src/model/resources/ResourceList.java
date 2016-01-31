package model.resources;

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

	}

	/**
	 * Increase the number of ore by the given quantity.
	 * 
	 * @param quantity
	 */
	public void addOre(int quantity)
	{

	}

	/**
	 * Increase the number of wheat by the given quantity.
	 * 
	 * @param quantity
	 */
	public void addWheat(int quantity)
	{

	}

	/**
	 * Increase the number of wood by the given quantity.
	 * 
	 * @param quantity
	 */
	public void addWood(int quantity)
	{

	}

	/**
	 * Increase the number of sheep by the given quantity.
	 * 
	 * @param quantity
	 */
	public void addSheep(int quantity)
	{

	}

	/**
	 * Decrease the number of brick by the given quantity.
	 * 
	 * @param quantity
	 */
	public void removeBrick(int quantity) throws NotEnoughResourcesException
	{

	}

	/**
	 * Decrease the number of ore by the given quantity.
	 * 
	 * @param quantity
	 */
	public void removeOre(int quantity) throws NotEnoughResourcesException
	{

	}

	/**
	 * Decrease the number of wheat by the given quantity.
	 * 
	 * @param quantity
	 */
	public void removeWheat(int quantity) throws NotEnoughResourcesException
	{

	}

	/**
	 * Decrease the number of wood by the given quantity.
	 * 
	 * @param quantity
	 */
	public void removeWood(int quantity) throws NotEnoughResourcesException
	{

	}

	/**
	 * Decrease the number of sheep by the given quantity.
	 * 
	 * @param quantity
	 */
	public void removeSheep(int quantity) throws NotEnoughResourcesException
	{

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

	private int getResourceCount()
	{
		return this.brick + this.ore + this.sheep + this.wheat + this.wood;
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

}
