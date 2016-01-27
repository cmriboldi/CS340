package test.model.development;

import static org.junit.Assert.*;
import org.junit.*;
import model.development.DevCardManager;

public class DevCardManagerTest
{
	private DevCardManager devManager;
	
	@Before
	public void setUp()
	{
		devManager = new DevCardManager();
	}
	
	@After
	public void tearDown()
	{
		devManager = null;
	}
	
	@Test
	public void test()
	{
		fail("Not yet implemented");
	}

}
