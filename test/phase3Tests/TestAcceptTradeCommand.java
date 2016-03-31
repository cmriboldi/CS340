package test.phase3Tests;

import static org.junit.Assert.*;

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
import model.CatanModel;
import shared.communication.JSON.*;
import shared.definitions.*;
import shared.locations.*;

public class TestAcceptTradeCommand {

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
    public void testAcceptTradeCommandBrick() throws server.exception.ServerException {
        AuthToken commandAuth = new AuthToken("String", "string", 0, -1);
        
        IJavaJSON commandJSON = new AcceptTradeJSON(0, false);
        ICommand actualCommand = commandFactory.buildCommand(commandAuth, commandJSON);
        int brick = facade.getGameModel(commandAuth).resourceManager.getResourceCount(0, ResourceType.BRICK);
        int ore = facade.getGameModel(commandAuth).resourceManager.getResourceCount(0, ResourceType.ORE);
        int sheep = facade.getGameModel(commandAuth).resourceManager.getResourceCount(0, ResourceType.SHEEP);
        int wheat = facade.getGameModel(commandAuth).resourceManager.getResourceCount(0, ResourceType.WHEAT);
        int wood = facade.getGameModel(commandAuth).resourceManager.getResourceCount(0, ResourceType.WOOD);
        
        CatanModel model = (CatanModel)actualCommand.execute();
        
        assertTrue(model.resourceManager.getResourceCount(0, ResourceType.BRICK) == brick);
    }
    
    @Test
    public void testAcceptTradeCommandOre() throws server.exception.ServerException {
        AuthToken commandAuth = new AuthToken("String", "string", 0, -1);
        
        IJavaJSON commandJSON = new AcceptTradeJSON(0, false);
        ICommand actualCommand = commandFactory.buildCommand(commandAuth, commandJSON);
        int brick = facade.getGameModel(commandAuth).resourceManager.getResourceCount(0, ResourceType.BRICK);
        int ore = facade.getGameModel(commandAuth).resourceManager.getResourceCount(0, ResourceType.ORE);
        int sheep = facade.getGameModel(commandAuth).resourceManager.getResourceCount(0, ResourceType.SHEEP);
        int wheat = facade.getGameModel(commandAuth).resourceManager.getResourceCount(0, ResourceType.WHEAT);
        int wood = facade.getGameModel(commandAuth).resourceManager.getResourceCount(0, ResourceType.WOOD);
        
        CatanModel model = (CatanModel)actualCommand.execute();
        
        assertTrue(model.resourceManager.getResourceCount(0, ResourceType.ORE) == ore);
    }
    
    @Test
    public void testAcceptTradeCommandSheep() throws server.exception.ServerException {
        AuthToken commandAuth = new AuthToken("String", "string", 0, -1);
        
        IJavaJSON commandJSON = new AcceptTradeJSON(0, false);
        ICommand actualCommand = commandFactory.buildCommand(commandAuth, commandJSON);
        int brick = facade.getGameModel(commandAuth).resourceManager.getResourceCount(0, ResourceType.BRICK);
        int ore = facade.getGameModel(commandAuth).resourceManager.getResourceCount(0, ResourceType.ORE);
        int sheep = facade.getGameModel(commandAuth).resourceManager.getResourceCount(0, ResourceType.SHEEP);
        int wheat = facade.getGameModel(commandAuth).resourceManager.getResourceCount(0, ResourceType.WHEAT);
        int wood = facade.getGameModel(commandAuth).resourceManager.getResourceCount(0, ResourceType.WOOD);
        
        CatanModel model = (CatanModel)actualCommand.execute();
        
        assertTrue(model.resourceManager.getResourceCount(0, ResourceType.SHEEP) == sheep);
    }
    
    @Test
    public void testAcceptTradeCommandWheat() throws server.exception.ServerException {
        AuthToken commandAuth = new AuthToken("String", "string", 0, -1);
        
        IJavaJSON commandJSON = new AcceptTradeJSON(0, false);
        ICommand actualCommand = commandFactory.buildCommand(commandAuth, commandJSON);
        int brick = facade.getGameModel(commandAuth).resourceManager.getResourceCount(0, ResourceType.BRICK);
        int ore = facade.getGameModel(commandAuth).resourceManager.getResourceCount(0, ResourceType.ORE);
        int sheep = facade.getGameModel(commandAuth).resourceManager.getResourceCount(0, ResourceType.SHEEP);
        int wheat = facade.getGameModel(commandAuth).resourceManager.getResourceCount(0, ResourceType.WHEAT);
        int wood = facade.getGameModel(commandAuth).resourceManager.getResourceCount(0, ResourceType.WOOD);
        
        CatanModel model = (CatanModel)actualCommand.execute();
        
        assertTrue(model.resourceManager.getResourceCount(0, ResourceType.WHEAT) == wheat);
    }
    
    @Test
    public void testAcceptTradeCommandWood() throws server.exception.ServerException {
        AuthToken commandAuth = new AuthToken("String", "string", 0, -1);
        
        IJavaJSON commandJSON = new AcceptTradeJSON(0, false);
        ICommand actualCommand = commandFactory.buildCommand(commandAuth, commandJSON);
        int brick = facade.getGameModel(commandAuth).resourceManager.getResourceCount(0, ResourceType.BRICK);
        int ore = facade.getGameModel(commandAuth).resourceManager.getResourceCount(0, ResourceType.ORE);
        int sheep = facade.getGameModel(commandAuth).resourceManager.getResourceCount(0, ResourceType.SHEEP);
        int wheat = facade.getGameModel(commandAuth).resourceManager.getResourceCount(0, ResourceType.WHEAT);
        int wood = facade.getGameModel(commandAuth).resourceManager.getResourceCount(0, ResourceType.WOOD);
        
        CatanModel model = (CatanModel)actualCommand.execute();
        
        assertTrue(model.resourceManager.getResourceCount(0, ResourceType.WOOD) == wood);
    }

}
