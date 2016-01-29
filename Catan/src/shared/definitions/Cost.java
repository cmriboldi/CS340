package shared.definitions;

import java.awt.Color;

import model.resources.ResourceList;

public enum Cost
{
	ROAD, SETTLEMENT, CITY, DEVCARD;
	
	private ResourceList cost;
	
	static
	{
		ROAD.cost = new ResourceList();
		SETTLEMENT.cost = new ResourceList();
		CITY.cost = new ResourceList();
		DEVCARD.cost = new ResourceList();
	}
	
	public ResourceList getCost()
	{
		return cost;
	}
}
