package test.phase3Tests;

import static org.junit.Assert.*;
import client.data.GameInfo;

import com.google.inject.Guice;
import com.google.inject.Injector;

import model.CatanModel;

import org.junit.*;

import server.AuthToken;
import server.command.CommandFactory;
import server.command.ICommand;
import server.facade.IServerFacade;
import server.guice.VolatileMockModule;
import server.guice.VolatileRealModule;
import serverProxy.MockProxy;
import serverProxy.ServerException;
import serverProxy.ServerProxy;
import clientfacade.Facade;
import shared.communication.JSON.BuildRoadJSON;
import shared.communication.JSON.BuildSettlementJSON;
import shared.communication.JSON.IJavaJSON;
import shared.communication.JSON.SoldierJSON;
import shared.communication.JSON.YearOfPlentyJSON;
import shared.definitions.CatanColor;
import shared.definitions.ResourceType;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

public class TestRollNumberCommand {

    private IServerFacade facade;
    private CommandFactory commandFactory;
    private Injector injector;

    private AuthToken brooke;
    private AuthToken pete;
    private AuthToken sam;
    private AuthToken mark;

    @Before
    public void setUp() throws Exception {
        //Set up the injector and the ServerFacade
        injector = Guice.createInjector(new VolatileMockModule());
        facade = injector.getInstance(IServerFacade.class);

        //Inject an instance of the CommandFactory ... this command factory will be connected to the same facade as above
        commandFactory = injector.getInstance(CommandFactory.class);
    }

    @After
    public void tearDown() throws Exception {

    }


    // ========================= TESTS ================================ //

    @Test
    public void testSoldierPlacement() throws server.exception.ServerException {
        AuthToken commandAuth = new AuthToken("String", "string", 0, -1);
  
        IJavaJSON commandJSON = new SoldierJSON(0, 2, new HexLocation(0,0));

        
        ICommand actualCommand = commandFactory.buildCommand(commandAuth, commandJSON);
        
        assertEquals(facade.getGameModel(commandAuth).getResourceManager().getResourcesForPlayer(0).getResourceTypeCount(ResourceType.WOOD), 10);
        assertEquals(facade.getGameModel(commandAuth).getResourceManager().getResourcesForPlayer(0).getResourceTypeCount(ResourceType.BRICK), 10);
        assertEquals(facade.getGameModel(commandAuth).getResourceManager().getResourcesForPlayer(0).getResourceTypeCount(ResourceType.ORE), 10);
        assertEquals(facade.getGameModel(commandAuth).getResourceManager().getResourcesForPlayer(0).getResourceTypeCount(ResourceType.SHEEP), 10);
        assertEquals(facade.getGameModel(commandAuth).getResourceManager().getResourcesForPlayer(0).getResourceTypeCount(ResourceType.WHEAT), 10);
        assertEquals(facade.getGameModel(commandAuth).getResourceManager().getTotalResourceCount(0),50); 
        
        
        CatanModel model = (CatanModel)actualCommand.execute();
        
        assertEquals(facade.getGameModel(commandAuth).getResourceManager().getTotalResourceCount(0),50); 
        
        assertTrue(true); 
         }
    
    
    
    

}
