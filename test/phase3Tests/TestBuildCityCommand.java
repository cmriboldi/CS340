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

public class TestBuildCityCommand {

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
    public void testBuildCityCommand() throws server.exception.ServerException {
        AuthToken commandAuth = new AuthToken("String", "string", 0, -1);
        VertexLocation loc = new VertexLocation(new HexLocation(1,2), VertexDirection.NorthWest);
        
        IJavaJSON commandJSON = new BuildCityJSON(0, loc);
        ICommand actualCommand = commandFactory.buildCommand(commandAuth, commandJSON);
        
        assertTrue(facade.getGameModel(commandAuth).getMapManager().getSettlements().containsKey(loc));
        assertTrue(!facade.getGameModel(commandAuth).getMapManager().getSettlements().get(loc).isCity());
        
        CatanModel model = (CatanModel)actualCommand.execute();
        
        assertTrue(model.getMapManager().getSettlements().get(loc).isCity());
    }
    
    @Test
    public void testBuildCityCommandFail() throws server.exception.ServerException {
        AuthToken commandAuth = new AuthToken("String", "string", 0, -1);
        VertexLocation loc = new VertexLocation(new HexLocation(0,0), VertexDirection.East);
        
        IJavaJSON commandJSON = new BuildCityJSON(0, loc);
        ICommand actualCommand = commandFactory.buildCommand(commandAuth, commandJSON);
        
        assertFalse(facade.getGameModel(commandAuth).getMapManager().getSettlements().containsKey(loc));
    }

}
