package definitions;

import model.resources.ResourceList;

public enum Cost
{
	ROAD, SETTLEMENT, CITY, DEVCARD;
	
	private ResourceList cost;
	
	static
	{
		ROAD.cost = new ResourceList(1,0,0,0,1);
		SETTLEMENT.cost = new ResourceList(1,0,1,1,1);
		CITY.cost = new ResourceList(0,3,0,2,0);
		DEVCARD.cost = new ResourceList(0,1,1,1,0);
	}
	
	//int brick, int ore, int sheep, int wheat, int wood
	
	public ResourceList getCost()
	{
		return cost;
	}
}
