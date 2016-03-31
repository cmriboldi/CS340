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
import shared.communication.JSON.RoadBuildingJSON;
import shared.communication.JSON.RollNumberJSON;
import shared.communication.JSON.SoldierJSON;
import shared.communication.JSON.YearOfPlentyJSON;
import shared.definitions.CatanColor;
import shared.definitions.ResourceType;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

public class TestRoadBuildingCommand {

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
    public void testRoadBuilding1() throws server.exception.ServerException {
        AuthToken commandAuth = new AuthToken("String", "string", 0, -1);
        
       EdgeLocation edge1 =  new EdgeLocation (new HexLocation(0,-2),EdgeDirection.SouthWest);
       EdgeLocation edge2 =  new EdgeLocation (new HexLocation(0,-2),EdgeDirection.NorthWest);
  
        IJavaJSON commandJSON = new RoadBuildingJSON(0, edge1, edge2); 
       
        ICommand actualCommand = commandFactory.buildCommand(commandAuth, commandJSON);
        
        assertEquals(facade.getGameModel(commandAuth).getResourceManager().getResourcesForPlayer(0).getResourceTypeCount(ResourceType.WOOD), 10);
        assertEquals(facade.getGameModel(commandAuth).getResourceManager().getResourcesForPlayer(0).getResourceTypeCount(ResourceType.BRICK), 10);
        assertEquals(facade.getGameModel(commandAuth).getResourceManager().getResourcesForPlayer(0).getResourceTypeCount(ResourceType.ORE), 10);
        assertEquals(facade.getGameModel(commandAuth).getResourceManager().getResourcesForPlayer(0).getResourceTypeCount(ResourceType.SHEEP), 10);
        assertEquals(facade.getGameModel(commandAuth).getResourceManager().getResourcesForPlayer(0).getResourceTypeCount(ResourceType.WHEAT), 10);
        assertEquals(facade.getGameModel(commandAuth).getResourceManager().getTotalResourceCount(0),50); 
        
        assertEquals(facade.getGameModel(commandAuth).getResourceManager().getResourcesForPlayer(1).getResourceTypeCount(ResourceType.WOOD), 2);
        assertEquals(facade.getGameModel(commandAuth).getResourceManager().getResourcesForPlayer(1).getResourceTypeCount(ResourceType.BRICK), 0);
        assertEquals(facade.getGameModel(commandAuth).getResourceManager().getResourcesForPlayer(1).getResourceTypeCount(ResourceType.ORE), 1);
        assertEquals(facade.getGameModel(commandAuth).getResourceManager().getResourcesForPlayer(1).getResourceTypeCount(ResourceType.SHEEP), 2);
        assertEquals(facade.getGameModel(commandAuth).getResourceManager().getResourcesForPlayer(1).getResourceTypeCount(ResourceType.WHEAT), 1);
        assertEquals(facade.getGameModel(commandAuth).getResourceManager().getTotalResourceCount(1),6); 
        
        assertEquals(facade.getGameModel(commandAuth).getResourceManager().getResourcesForPlayer(2).getResourceTypeCount(ResourceType.WOOD), 1);
        assertEquals(facade.getGameModel(commandAuth).getResourceManager().getResourcesForPlayer(2).getResourceTypeCount(ResourceType.BRICK), 2);
        assertEquals(facade.getGameModel(commandAuth).getResourceManager().getResourcesForPlayer(2).getResourceTypeCount(ResourceType.ORE), 0);
        assertEquals(facade.getGameModel(commandAuth).getResourceManager().getResourcesForPlayer(2).getResourceTypeCount(ResourceType.SHEEP), 2);
        assertEquals(facade.getGameModel(commandAuth).getResourceManager().getResourcesForPlayer(2).getResourceTypeCount(ResourceType.WHEAT), 1);
        assertEquals(facade.getGameModel(commandAuth).getResourceManager().getTotalResourceCount(2),6); 
        
        assertEquals(facade.getGameModel(commandAuth).getResourceManager().getResourcesForPlayer(3).getResourceTypeCount(ResourceType.WOOD), 2);
        assertEquals(facade.getGameModel(commandAuth).getResourceManager().getResourcesForPlayer(3).getResourceTypeCount(ResourceType.BRICK), 1);
        assertEquals(facade.getGameModel(commandAuth).getResourceManager().getResourcesForPlayer(3).getResourceTypeCount(ResourceType.ORE), 1);
        assertEquals(facade.getGameModel(commandAuth).getResourceManager().getResourcesForPlayer(3).getResourceTypeCount(ResourceType.SHEEP), 1);
        assertEquals(facade.getGameModel(commandAuth).getResourceManager().getResourcesForPlayer(3).getResourceTypeCount(ResourceType.WHEAT), 1);
        assertEquals(facade.getGameModel(commandAuth).getResourceManager().getTotalResourceCount(3),6);
        
        //assertFalse(facade.getGameModel(commandAuth).getMapManager().getRoads().containsKey(edge));
        
        assertFalse(facade.getGameModel(commandAuth).getMapManager().getRoads().containsKey(edge1));
        assertFalse(facade.getGameModel(commandAuth).getMapManager().getRoads().containsKey(edge2));
        
        CatanModel model = (CatanModel)actualCommand.execute();
        
        assertTrue(model.getMapManager().getRoads().containsKey(edge1));
        assertTrue(model.getMapManager().getRoads().containsKey(edge2));
        
        // resources didn't change
        assertEquals(model.getResourceManager().getResourcesForPlayer(0).getResourceTypeCount(ResourceType.WOOD), 10);
        assertEquals(model.getResourceManager().getResourcesForPlayer(0).getResourceTypeCount(ResourceType.BRICK), 10);
        assertEquals(model.getResourceManager().getResourcesForPlayer(0).getResourceTypeCount(ResourceType.ORE), 10);
        assertEquals(model.getResourceManager().getResourcesForPlayer(0).getResourceTypeCount(ResourceType.SHEEP), 10);
        assertEquals(model.getResourceManager().getResourcesForPlayer(0).getResourceTypeCount(ResourceType.WHEAT), 10);
        assertEquals(model.getResourceManager().getTotalResourceCount(0),50); 
        
        assertEquals(model.getResourceManager().getResourcesForPlayer(1).getResourceTypeCount(ResourceType.WOOD), 2);
        assertEquals(model.getResourceManager().getResourcesForPlayer(1).getResourceTypeCount(ResourceType.BRICK), 0);
        assertEquals(model.getResourceManager().getResourcesForPlayer(1).getResourceTypeCount(ResourceType.ORE), 1);
        assertEquals(model.getResourceManager().getResourcesForPlayer(1).getResourceTypeCount(ResourceType.SHEEP), 2);
        assertEquals(model.getResourceManager().getResourcesForPlayer(1).getResourceTypeCount(ResourceType.WHEAT), 1);
        assertEquals(model.getResourceManager().getTotalResourceCount(1),6); 
        
        assertEquals(model.getResourceManager().getResourcesForPlayer(2).getResourceTypeCount(ResourceType.WOOD), 1);
        assertEquals(model.getResourceManager().getResourcesForPlayer(2).getResourceTypeCount(ResourceType.BRICK), 2);
        assertEquals(model.getResourceManager().getResourcesForPlayer(2).getResourceTypeCount(ResourceType.ORE), 0);
        assertEquals(model.getResourceManager().getResourcesForPlayer(2).getResourceTypeCount(ResourceType.SHEEP), 2);
        assertEquals(model.getResourceManager().getResourcesForPlayer(2).getResourceTypeCount(ResourceType.WHEAT), 1);
        assertEquals(model.getResourceManager().getTotalResourceCount(2),6); 
        
        assertEquals(model.getResourceManager().getResourcesForPlayer(3).getResourceTypeCount(ResourceType.WOOD), 2);
        assertEquals(model.getResourceManager().getResourcesForPlayer(3).getResourceTypeCount(ResourceType.BRICK), 1);
        assertEquals(model.getResourceManager().getResourcesForPlayer(3).getResourceTypeCount(ResourceType.ORE), 1);
        assertEquals(model.getResourceManager().getResourcesForPlayer(3).getResourceTypeCount(ResourceType.SHEEP), 1);
        assertEquals(model.getResourceManager().getResourcesForPlayer(3).getResourceTypeCount(ResourceType.WHEAT), 1);
        assertEquals(model.getResourceManager().getTotalResourceCount(3),6);
        
       }
    
    
    /*
    @Test
    public void testRoadBuilding2() throws server.exception.ServerException {
        AuthToken commandAuth = new AuthToken("String", "string", 0, -1);
        
       EdgeLocation edge1 =  new EdgeLocation (new HexLocation(0,1),EdgeDirection.SouthWest);
       EdgeLocation edge2 =  new EdgeLocation (new HexLocation(0,1),EdgeDirection.NorthWest);
  
        IJavaJSON commandJSON = new RoadBuildingJSON(3, edge1, edge2); 
       
        ICommand actualCommand = commandFactory.buildCommand(commandAuth, commandJSON);
        
        assertEquals(facade.getGameModel(commandAuth).getResourceManager().getResourcesForPlayer(0).getResourceTypeCount(ResourceType.WOOD), 10);
        assertEquals(facade.getGameModel(commandAuth).getResourceManager().getResourcesForPlayer(0).getResourceTypeCount(ResourceType.BRICK), 10);
        assertEquals(facade.getGameModel(commandAuth).getResourceManager().getResourcesForPlayer(0).getResourceTypeCount(ResourceType.ORE), 10);
        assertEquals(facade.getGameModel(commandAuth).getResourceManager().getResourcesForPlayer(0).getResourceTypeCount(ResourceType.SHEEP), 10);
        assertEquals(facade.getGameModel(commandAuth).getResourceManager().getResourcesForPlayer(0).getResourceTypeCount(ResourceType.WHEAT), 10);
        assertEquals(facade.getGameModel(commandAuth).getResourceManager().getTotalResourceCount(0),50); 
        
        assertEquals(facade.getGameModel(commandAuth).getResourceManager().getResourcesForPlayer(1).getResourceTypeCount(ResourceType.WOOD), 2);
        assertEquals(facade.getGameModel(commandAuth).getResourceManager().getResourcesForPlayer(1).getResourceTypeCount(ResourceType.BRICK), 0);
        assertEquals(facade.getGameModel(commandAuth).getResourceManager().getResourcesForPlayer(1).getResourceTypeCount(ResourceType.ORE), 1);
        assertEquals(facade.getGameModel(commandAuth).getResourceManager().getResourcesForPlayer(1).getResourceTypeCount(ResourceType.SHEEP), 2);
        assertEquals(facade.getGameModel(commandAuth).getResourceManager().getResourcesForPlayer(1).getResourceTypeCount(ResourceType.WHEAT), 1);
        assertEquals(facade.getGameModel(commandAuth).getResourceManager().getTotalResourceCount(1),6); 
        
        assertEquals(facade.getGameModel(commandAuth).getResourceManager().getResourcesForPlayer(2).getResourceTypeCount(ResourceType.WOOD), 1);
        assertEquals(facade.getGameModel(commandAuth).getResourceManager().getResourcesForPlayer(2).getResourceTypeCount(ResourceType.BRICK), 2);
        assertEquals(facade.getGameModel(commandAuth).getResourceManager().getResourcesForPlayer(2).getResourceTypeCount(ResourceType.ORE), 0);
        assertEquals(facade.getGameModel(commandAuth).getResourceManager().getResourcesForPlayer(2).getResourceTypeCount(ResourceType.SHEEP), 2);
        assertEquals(facade.getGameModel(commandAuth).getResourceManager().getResourcesForPlayer(2).getResourceTypeCount(ResourceType.WHEAT), 1);
        assertEquals(facade.getGameModel(commandAuth).getResourceManager().getTotalResourceCount(2),6); 
        
        assertEquals(facade.getGameModel(commandAuth).getResourceManager().getResourcesForPlayer(3).getResourceTypeCount(ResourceType.WOOD), 2);
        assertEquals(facade.getGameModel(commandAuth).getResourceManager().getResourcesForPlayer(3).getResourceTypeCount(ResourceType.BRICK), 1);
        assertEquals(facade.getGameModel(commandAuth).getResourceManager().getResourcesForPlayer(3).getResourceTypeCount(ResourceType.ORE), 1);
        assertEquals(facade.getGameModel(commandAuth).getResourceManager().getResourcesForPlayer(3).getResourceTypeCount(ResourceType.SHEEP), 1);
        assertEquals(facade.getGameModel(commandAuth).getResourceManager().getResourcesForPlayer(3).getResourceTypeCount(ResourceType.WHEAT), 1);
        assertEquals(facade.getGameModel(commandAuth).getResourceManager().getTotalResourceCount(3),6);
        
        
        assertFalse(facade.getGameModel(commandAuth).getMapManager().getRoads().containsKey(edge1));
        assertFalse(facade.getGameModel(commandAuth).getMapManager().getRoads().containsKey(edge2));
        
        CatanModel model = (CatanModel)actualCommand.execute();
        
        assertTrue(model.getMapManager().getRoads().containsKey(edge1));
        //assertTrue(model.getMapManager().getRoads().containsKey(edge2));
        
        // resources didn't change
        assertEquals(model.getResourceManager().getResourcesForPlayer(0).getResourceTypeCount(ResourceType.WOOD), 10);
        assertEquals(model.getResourceManager().getResourcesForPlayer(0).getResourceTypeCount(ResourceType.BRICK), 10);
        assertEquals(model.getResourceManager().getResourcesForPlayer(0).getResourceTypeCount(ResourceType.ORE), 10);
        assertEquals(model.getResourceManager().getResourcesForPlayer(0).getResourceTypeCount(ResourceType.SHEEP), 10);
        assertEquals(model.getResourceManager().getResourcesForPlayer(0).getResourceTypeCount(ResourceType.WHEAT), 10);
        assertEquals(model.getResourceManager().getTotalResourceCount(0),50); 
        
        assertEquals(model.getResourceManager().getResourcesForPlayer(1).getResourceTypeCount(ResourceType.WOOD), 2);
        assertEquals(model.getResourceManager().getResourcesForPlayer(1).getResourceTypeCount(ResourceType.BRICK), 0);
        assertEquals(model.getResourceManager().getResourcesForPlayer(1).getResourceTypeCount(ResourceType.ORE), 1);
        assertEquals(model.getResourceManager().getResourcesForPlayer(1).getResourceTypeCount(ResourceType.SHEEP), 2);
        assertEquals(model.getResourceManager().getResourcesForPlayer(1).getResourceTypeCount(ResourceType.WHEAT), 1);
        assertEquals(model.getResourceManager().getTotalResourceCount(1),6); 
        
        assertEquals(model.getResourceManager().getResourcesForPlayer(2).getResourceTypeCount(ResourceType.WOOD), 1);
        assertEquals(model.getResourceManager().getResourcesForPlayer(2).getResourceTypeCount(ResourceType.BRICK), 2);
        assertEquals(model.getResourceManager().getResourcesForPlayer(2).getResourceTypeCount(ResourceType.ORE), 0);
        assertEquals(model.getResourceManager().getResourcesForPlayer(2).getResourceTypeCount(ResourceType.SHEEP), 2);
        assertEquals(model.getResourceManager().getResourcesForPlayer(2).getResourceTypeCount(ResourceType.WHEAT), 1);
        assertEquals(model.getResourceManager().getTotalResourceCount(2),6); 
        
        assertEquals(model.getResourceManager().getResourcesForPlayer(3).getResourceTypeCount(ResourceType.WOOD), 2);
        assertEquals(model.getResourceManager().getResourcesForPlayer(3).getResourceTypeCount(ResourceType.BRICK), 1);
        assertEquals(model.getResourceManager().getResourcesForPlayer(3).getResourceTypeCount(ResourceType.ORE), 1);
        assertEquals(model.getResourceManager().getResourcesForPlayer(3).getResourceTypeCount(ResourceType.SHEEP), 1);
        assertEquals(model.getResourceManager().getResourcesForPlayer(3).getResourceTypeCount(ResourceType.WHEAT), 1);
        assertEquals(model.getResourceManager().getTotalResourceCount(3),6);
        
       }*/
    

}
