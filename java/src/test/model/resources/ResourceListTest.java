package test.model.resources;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model.resources.ResourceList;
import shared.definitions.ResourceType;

public class ResourceListTest
{

	ResourceList resList = null;
	
	@Before
	public void setUp() throws Exception
	{
		resList = new ResourceList();
	}

	@After
	public void tearDown() throws Exception
	{
		resList = null;
	}

	@Test
	public void test()
	{
		assertEquals(resList.getResourceCount(), 0);
		assertEquals(resList.getResourceTypeCount(ResourceType.BRICK), 0);
		assertEquals(resList.getResourceTypeCount(ResourceType.ORE), 0);
		assertEquals(resList.getResourceTypeCount(ResourceType.SHEEP), 0);
		assertEquals(resList.getResourceTypeCount(ResourceType.WHEAT), 0);
		assertEquals(resList.getResourceTypeCount(ResourceType.WOOD), 0);
	}

}
