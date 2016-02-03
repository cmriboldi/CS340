package test.server;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import serverProxy.RealProxy;
import serverProxy.ServerException;
import shared.definitions.CatanColor;

public class RealProxyTest 
{
	private static RealProxy server;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		server = new RealProxy();
		server.userRegister("JUnitTest", "junittest");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception 
	{
		server = new RealProxy();
		server.userLogin("JUnitTest", "junittest");
	}

	@Before
	public void setUp() throws Exception 
	{
		server.userLogin("Pete", "pete");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws ServerException 
	{
		RealProxy server = new RealProxy();
		server.userLogin("Pete", "pete");
		server.joinGame(0, CatanColor.ORANGE);
		server.getGameModel(0);
		assert(true);
	}
}
