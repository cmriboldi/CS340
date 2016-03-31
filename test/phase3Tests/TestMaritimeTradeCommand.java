package test.phase3Tests;

import static org.junit.Assert.*;
import client.data.GameInfo;

import com.google.inject.Guice;
import com.google.inject.Injector;

import org.junit.*;

import server.AuthToken;
import server.command.CommandFactory;
import server.command.ICommand;
import server.facade.IServerFacade;
import server.guice.*;
import serverProxy.MockProxy;
import serverProxy.ServerException;
import serverProxy.ServerProxy;
import clientfacade.Facade;
import model.CatanModel;
import shared.communication.JSON.*;
import shared.definitions.*;
import shared.locations.*;

public class TestMaritimeTradeCommand {

    private IServerFacade facade;
    private CommandFactory commandFactory;
    private Injector injector;

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
    public void maritime4() throws server.exception.ServerException {
        AuthToken commandAuth = new AuthToken("String", "string", 0, -1);
        //EdgeLocation edge = new EdgeLocation(new HexLocation(0,0), EdgeDirection.South);
        
        IJavaJSON commandJSON = new MaritimeTradeJSON(0, 4, "wood", "ore"); 
        
        assertEquals(facade.getGameModel(commandAuth).getResourceManager().getResourcesForPlayer(0).getResourceTypeCount(ResourceType.WOOD), 10);
        assertEquals(facade.getGameModel(commandAuth).getResourceManager().getResourcesForPlayer(0).getResourceTypeCount(ResourceType.BRICK), 10);
        assertEquals(facade.getGameModel(commandAuth).getResourceManager().getResourcesForPlayer(0).getResourceTypeCount(ResourceType.ORE), 10);
        assertEquals(facade.getGameModel(commandAuth).getResourceManager().getResourcesForPlayer(0).getResourceTypeCount(ResourceType.SHEEP), 10);
        assertEquals(facade.getGameModel(commandAuth).getResourceManager().getResourcesForPlayer(0).getResourceTypeCount(ResourceType.WHEAT), 10);
        assertEquals(facade.getGameModel(commandAuth).getResourceManager().getTotalResourceCount(0),50); 
        
        ICommand actualCommand = commandFactory.buildCommand(commandAuth, commandJSON);
                
        CatanModel model = (CatanModel)actualCommand.execute();
        
        assertEquals(model.getResourceManager().getResourcesForPlayer(0).getResourceTypeCount(ResourceType.WOOD), 6);
        assertEquals(model.getResourceManager().getResourcesForPlayer(0).getResourceTypeCount(ResourceType.BRICK), 10);
        assertEquals(model.getResourceManager().getResourcesForPlayer(0).getResourceTypeCount(ResourceType.ORE), 11);
        assertEquals(model.getResourceManager().getResourcesForPlayer(0).getResourceTypeCount(ResourceType.SHEEP), 10);
        assertEquals(model.getResourceManager().getResourcesForPlayer(0).getResourceTypeCount(ResourceType.WHEAT), 10);
        assertEquals(model.getResourceManager().getTotalResourceCount(0),47); 
        
    }
    
    @Test
    public void maritime2() throws server.exception.ServerException {
        AuthToken commandAuth = new AuthToken("String", "string", 0, -1);
        //EdgeLocation edge = new EdgeLocation(new HexLocation(0,0), EdgeDirection.South);
        
        IJavaJSON commandJSON = new MaritimeTradeJSON(0, 2, "sheep", "brick"); 
        
        assertEquals(facade.getGameModel(commandAuth).getResourceManager().getResourcesForPlayer(0).getResourceTypeCount(ResourceType.WOOD), 10);
        assertEquals(facade.getGameModel(commandAuth).getResourceManager().getResourcesForPlayer(0).getResourceTypeCount(ResourceType.BRICK), 10);
        assertEquals(facade.getGameModel(commandAuth).getResourceManager().getResourcesForPlayer(0).getResourceTypeCount(ResourceType.ORE), 10);
        assertEquals(facade.getGameModel(commandAuth).getResourceManager().getResourcesForPlayer(0).getResourceTypeCount(ResourceType.SHEEP), 10);
        assertEquals(facade.getGameModel(commandAuth).getResourceManager().getResourcesForPlayer(0).getResourceTypeCount(ResourceType.WHEAT), 10);
        assertEquals(facade.getGameModel(commandAuth).getResourceManager().getTotalResourceCount(0),50); 
        
        ICommand actualCommand = commandFactory.buildCommand(commandAuth, commandJSON);
                
        CatanModel model = (CatanModel)actualCommand.execute();
        
        assertEquals(model.getResourceManager().getResourcesForPlayer(0).getResourceTypeCount(ResourceType.WOOD), 10);
        assertEquals(model.getResourceManager().getResourcesForPlayer(0).getResourceTypeCount(ResourceType.BRICK), 11);
        assertEquals(model.getResourceManager().getResourcesForPlayer(0).getResourceTypeCount(ResourceType.ORE), 10);
        assertEquals(model.getResourceManager().getResourcesForPlayer(0).getResourceTypeCount(ResourceType.SHEEP), 8);
        assertEquals(model.getResourceManager().getResourcesForPlayer(0).getResourceTypeCount(ResourceType.WHEAT), 10);
        assertEquals(model.getResourceManager().getTotalResourceCount(0),49); 
        
    }

}
