package test.model.resources;

import static org.junit.Assert.*;

import org.junit.*;

import model.CatanModel;
import model.resources.ResourceManager;
import serverProxy.JSONDeserializer;
import serverProxy.JsonLoader;

public class ResourceManagerDeserialTest
{
	private ResourceManager resManager;
	
	@Before
	public void setUp() throws Exception
	{
		String json = JsonLoader.readFile("json/default.json");
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
		//assert values are not null this is tested much more thoroughly in the Options class. I just needed an initial test to make sure it wasn't null.
		assertNotEquals(resManager,null);
	}

}
