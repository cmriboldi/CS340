package test.phase3Tests;

import static org.junit.Assert.*;

import client.data.GameInfo;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.*;


import org.junit.Before;
import org.junit.Test;

import server.AuthToken;
import server.command.CommandFactory;
import server.command.ICommand;
import server.facade.IServerFacade;
import server.guice.*;
import serverProxy.MockProxy;
import serverProxy.ServerException;
import serverProxy.ServerProxy;
import clientfacade.Facade;
import model.*;
import model.resources.*;
import shared.communication.JSON.*;
import shared.definitions.*;
import shared.locations.*;

public class TestDiscardCardsCommand {

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
    public void testEmptyDiscard() throws server.exception.ServerException {
        AuthToken commandAuth = new AuthToken("String", "string", 0, -1);
        EdgeLocation edge = new EdgeLocation(new HexLocation(0,0), EdgeDirection.South);
        
        IJavaJSON commandJSON = new DiscardCardsJSON(0, new ResourceList(0,0,0,0,0));
        ICommand actualCommand = commandFactory.buildCommand(commandAuth, commandJSON);
        
        int count = facade.getGameModel(commandAuth).resourceManager.getResourcesForPlayer(0).getResourceCount();
        
        CatanModel model = (CatanModel)actualCommand.execute();
        
        assertTrue(model.resourceManager.getResourcesForPlayer(0).getResourceCount() == count);
    }
    
    @Test
    public void testDiscard() throws server.exception.ServerException {
        AuthToken commandAuth = new AuthToken("String", "string", 0, -1);
        EdgeLocation edge = new EdgeLocation(new HexLocation(0,0), EdgeDirection.South);
        
        IJavaJSON commandJSON = new DiscardCardsJSON(0, new ResourceList(10,10,5,0,0));
        ICommand actualCommand = commandFactory.buildCommand(commandAuth, commandJSON);
        
        int count = facade.getGameModel(commandAuth).resourceManager.getResourcesForPlayer(0).getResourceCount();
        
        CatanModel model = (CatanModel)actualCommand.execute();
        
        assertTrue(model.resourceManager.getResourcesForPlayer(0).getResourceCount() == count-25);
    }
    
    @Test
    public void testDiscardBrick() throws server.exception.ServerException {
        AuthToken commandAuth = new AuthToken("String", "string", 0, -1);
        EdgeLocation edge = new EdgeLocation(new HexLocation(0,0), EdgeDirection.South);
        
        IJavaJSON commandJSON = new DiscardCardsJSON(0, new ResourceList(10,0,0,0,0));
        ICommand actualCommand = commandFactory.buildCommand(commandAuth, commandJSON);
        
        int count = facade.getGameModel(commandAuth).resourceManager.getResourcesForPlayer(0).getResourceCount();
        
        CatanModel model = (CatanModel)actualCommand.execute();
        
        assertTrue(model.resourceManager.getResourcesForPlayer(0).getResourceTypeCount(ResourceType.BRICK) == 0);
    }
    
    @Test
    public void testDiscardOre() throws server.exception.ServerException {
        AuthToken commandAuth = new AuthToken("String", "string", 0, -1);
        EdgeLocation edge = new EdgeLocation(new HexLocation(0,0), EdgeDirection.South);
        
        IJavaJSON commandJSON = new DiscardCardsJSON(0, new ResourceList(0,10,0,0,0));
        ICommand actualCommand = commandFactory.buildCommand(commandAuth, commandJSON);
        
        int count = facade.getGameModel(commandAuth).resourceManager.getResourcesForPlayer(0).getResourceCount();
        
        CatanModel model = (CatanModel)actualCommand.execute();
        
        assertTrue(model.resourceManager.getResourcesForPlayer(0).getResourceTypeCount(ResourceType.ORE) == 0);
    }
    
    @Test
    public void testDiscardSheep() throws server.exception.ServerException {
        AuthToken commandAuth = new AuthToken("String", "string", 0, -1);
        EdgeLocation edge = new EdgeLocation(new HexLocation(0,0), EdgeDirection.South);
        
        IJavaJSON commandJSON = new DiscardCardsJSON(0, new ResourceList(0,0,10,0,0));
        ICommand actualCommand = commandFactory.buildCommand(commandAuth, commandJSON);
        
        int count = facade.getGameModel(commandAuth).resourceManager.getResourcesForPlayer(0).getResourceCount();
        
        CatanModel model = (CatanModel)actualCommand.execute();
        
        assertTrue(model.resourceManager.getResourcesForPlayer(0).getResourceTypeCount(ResourceType.SHEEP) == 0);
    }
    
    @Test
    public void testDiscardWheat() throws server.exception.ServerException {
        AuthToken commandAuth = new AuthToken("String", "string", 0, -1);
        EdgeLocation edge = new EdgeLocation(new HexLocation(0,0), EdgeDirection.South);
        
        IJavaJSON commandJSON = new DiscardCardsJSON(0, new ResourceList(0,0,0,10,0));
        ICommand actualCommand = commandFactory.buildCommand(commandAuth, commandJSON);
        
        int count = facade.getGameModel(commandAuth).resourceManager.getResourcesForPlayer(0).getResourceCount();
        
        CatanModel model = (CatanModel)actualCommand.execute();
        
        assertTrue(model.resourceManager.getResourcesForPlayer(0).getResourceTypeCount(ResourceType.WHEAT) == 0);
    }
    
    @Test
    public void testDiscardWood() throws server.exception.ServerException {
        AuthToken commandAuth = new AuthToken("String", "string", 0, -1);
        EdgeLocation edge = new EdgeLocation(new HexLocation(0,0), EdgeDirection.South);
        
        IJavaJSON commandJSON = new DiscardCardsJSON(0, new ResourceList(0,0,0,0,10));
        ICommand actualCommand = commandFactory.buildCommand(commandAuth, commandJSON);
        
        int count = facade.getGameModel(commandAuth).resourceManager.getResourcesForPlayer(0).getResourceCount();
        
        CatanModel model = (CatanModel)actualCommand.execute();
        
        assertTrue(model.resourceManager.getResourcesForPlayer(0).getResourceTypeCount(ResourceType.WOOD) == 0);
    }

}
