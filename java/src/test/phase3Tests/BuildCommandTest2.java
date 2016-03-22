package test.phase3Tests;

import static org.junit.Assert.*;
import org.junit.*;


import org.junit.Before;
import org.junit.Test;

import serverProxy.MockProxy;
import serverProxy.ServerException;
import serverProxy.ServerProxy;
import clientfacade.Facade;

public class BuildCommandTest2 {

	private ServerProxy server;
	
	
	@Before
	public void setUp() throws Exception
	{
		server = new MockProxy();
		server.getGameModel(0);
        
	}

	@After
	public void tearDown() throws Exception
	{
		server = null; 
	}

	
	
	// ========================= TESTS ================================ //
	
	
	@Test
	public void testNotNull() throws ServerException {
		
		assertNotEquals(Facade.getCatanModel(), null);
	}
	
	
	@Test
	public void testInitializatoin() throws ServerException {
		
		assertEquals(Facade.getCatanModel().getPlayerManager().getInitializedPlayerCount(), 4); 
		
		
	}
	
	
	@Test
	public void test2() throws ServerException {
		
		//Question --> How do I access the command objects to see if they work properly?
		//Facade.getCommandObjects ??? 
		
		assertNotEquals(Facade.getCatanModel(), null);
		
	}
	
	
	@Test
	public void test3() throws ServerException {
		
		assertNotEquals(Facade.getCatanModel(), null);
	}
	

}
