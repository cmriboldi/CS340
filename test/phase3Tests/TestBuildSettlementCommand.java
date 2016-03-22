package phase3Tests;

import static org.junit.Assert.*;

import client.data.GameInfo;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.*;


import org.junit.Before;
import org.junit.Test;

import server.AuthToken;
import server.command.CommandFactory;
import server.facade.IServerFacade;
import server.guice.VolatileRealModule;
import serverProxy.MockProxy;
import serverProxy.ServerException;
import serverProxy.ServerProxy;
import clientfacade.Facade;
import shared.definitions.CatanColor;

public class TestBuildSettlementCommand {

	private IServerFacade facade;
	private CommandFactory commandFactory;
	private Injector injector;

	private AuthToken brooke;
	private AuthToken pete;
	private AuthToken sam;
	private AuthToken mark;
	
	@Before
	public void setUp() throws Exception
	{
		//Set up the injector and the ServerFacade
		injector = Guice.createInjector(new VolatileRealModule());
		facade = injector.getInstance(IServerFacade.class);

		//Inject an instance of the CommandFactory ... this command factory will be connected to the same facade as above
		commandFactory = injector.getInstance(CommandFactory.class);

		//Create a default game to test against
		GameInfo info = facade.createGame(false, false, false, "Default");
	}

	@After
	public void tearDown() throws Exception
	{

	}

	
	
	// ========================= TESTS ================================ //

	@Test
	public void basicCommand()
	{
		commandFactory.buildCommand()
	}

	
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
