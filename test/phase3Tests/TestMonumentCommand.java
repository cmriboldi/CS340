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

public class TestMonumentCommand {

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
    public void testMonumentForPlayer0() throws server.exception.ServerException {
        AuthToken commandAuth = new AuthToken("String", "string", 0, -1);
        EdgeLocation edge = new EdgeLocation(new HexLocation(0,0), EdgeDirection.South);
        
        IJavaJSON commandJSON = new MonumentJSON(0);
        ICommand actualCommand = commandFactory.buildCommand(commandAuth, commandJSON);
        
        assertEquals(facade.getGameModel(commandAuth).getPlayerManager().getPlayerPoints(0), 2);
        
        CatanModel model = (CatanModel)actualCommand.execute();
        
        assertEquals(model.getPlayerManager().getPlayerPoints(0), 3);
    }
    
    
    @Test
    public void testMonumentForPlayer1() throws server.exception.ServerException {
        AuthToken commandAuth = new AuthToken("String", "string", 0, -1);
        EdgeLocation edge = new EdgeLocation(new HexLocation(0,0), EdgeDirection.South);
        
        IJavaJSON commandJSON = new MonumentJSON(1);
        ICommand actualCommand = commandFactory.buildCommand(commandAuth, commandJSON);
        
        assertEquals(facade.getGameModel(commandAuth).getPlayerManager().getPlayerPoints(1), 2);
        
        CatanModel model = (CatanModel)actualCommand.execute();
        
        //assertEquals(model.getPlayerManager().getPlayerPoints(1), 3);
    }
    
    @Test
    public void testMonumentForPlayer2() throws server.exception.ServerException {
        AuthToken commandAuth = new AuthToken("String", "string", 0, -1);
        EdgeLocation edge = new EdgeLocation(new HexLocation(0,0), EdgeDirection.South);
        
        IJavaJSON commandJSON = new MonumentJSON(2);
        ICommand actualCommand = commandFactory.buildCommand(commandAuth, commandJSON);
        
        assertEquals(facade.getGameModel(commandAuth).getPlayerManager().getPlayerPoints(2), 2);
        
        CatanModel model = (CatanModel)actualCommand.execute();
        
        //assertEquals(model.getPlayerManager().getPlayerPoints(2), 3);
    }
    
    @Test
    public void testMonumentForPlayer3() throws server.exception.ServerException {
        AuthToken commandAuth = new AuthToken("String", "string", 0, -1);
        
        IJavaJSON commandJSON = new MonumentJSON(3);
        ICommand actualCommand = commandFactory.buildCommand(commandAuth, commandJSON);
        
        assertEquals(facade.getGameModel(commandAuth).getPlayerManager().getPlayerPoints(3), 2);
        
        CatanModel model = (CatanModel)actualCommand.execute();
        
        //assertEquals(model.getPlayerManager().getPlayerPoints(3), 3);
    }
    
    

}
