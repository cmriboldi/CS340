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
public class ResourceList
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

}
