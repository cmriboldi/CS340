package test.model.resources;

import static org.junit.Assert.*;

import org.junit.*;

import model.CatanModel;
import model.resources.ResourceManager;
import serverProxy.JSONDeserializer;
import test.TestJSON;

public class ResourceManagerDeserialTest
{
	private ResourceManager resManager;
	
	@Before
	public void setUp() throws Exception
	{
		CatanModel cm = JSONDeserializer.deserialize(TestJSON.get());
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
		fail("Not yet implemented");
	}

}
