package test.model.resources;

import static org.junit.Assert.*;

import org.junit.*;

import model.CatanModel;
import model.resources.ResourceManager;
import serverProxy.JSONDeserializer;
import test.JsonFileLoader;
import test.TestJSON;

public class ResourceManagerDeserialTest
{
	private ResourceManager resManager;
	
	@Before
	public void setUp() throws Exception
	{
		String json = JsonFileLoader.readFile("json/default.json");
		CatanModel cm = JSONDeserializer.deserialize(json);
		resManager = cm.resourceManager;
	}

	@After
	public void tearDown() throws Exception
	{
		resManager = null;
	}

	@Test
	public void test()
	{
		//assert values are not null
		assertNotEquals(resManager,null);
	}

}
