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
import model.CatanModel;
import shared.communication.JSON.*;
import shared.definitions.*;
import shared.locations.*;

public class TestOfferTradeCommand {

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
    public void testBuildRoadCommandPlacement() throws server.exception.ServerException {
        AuthToken commandAuth = new AuthToken("String", "string", 0, -1);
        EdgeLocation edge = new EdgeLocation(new HexLocation(0,0), EdgeDirection.South);
        
        IJavaJSON commandJSON = new BuildRoadJSON(0, edge, true);
        ICommand actualCommand = commandFactory.buildCommand(commandAuth, commandJSON);
        
        assertFalse(facade.getGameModel(commandAuth).getMapManager().getRoads().containsKey(edge));
        
        CatanModel model = (CatanModel)actualCommand.execute();
        
        assertTrue(model.getMapManager().getRoads().containsKey(edge));
    }
    
    @Test
    public void testBuildRoadCommandDecrement() throws server.exception.ServerException {
        AuthToken commandAuth = new AuthToken("String", "string", 0, -1);
        EdgeLocation edge = new EdgeLocation(new HexLocation(0,0), EdgeDirection.South);
        
        IJavaJSON commandJSON = new BuildRoadJSON(0, edge, true);
        ICommand actualCommand = commandFactory.buildCommand(commandAuth, commandJSON);
        
        assertFalse(facade.getGameModel(commandAuth).getMapManager().getRoads().containsKey(edge));
        
        CatanModel model = (CatanModel)actualCommand.execute();
        
        assertTrue(model.getMapManager().getRoads().containsKey(edge));
    }

}
