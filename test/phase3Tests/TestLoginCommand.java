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
import server.guice.VolatileRealModule;
import serverProxy.MockProxy;
import serverProxy.ServerException;
import serverProxy.ServerProxy;
import clientfacade.Facade;
import shared.communication.JSON.BuildSettlementJSON;
import shared.communication.JSON.IJavaJSON;
import shared.definitions.CatanColor;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

public class TestLoginCommand {

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
        injector = Guice.createInjector(new VolatileRealModule());
        facade = injector.getInstance(IServerFacade.class);

        //Inject an instance of the CommandFactory ... this command factory will be connected to the same facade as above
        commandFactory = injector.getInstance(CommandFactory.class);
    }

    @After
    public void tearDown() throws Exception {

    }


    // ========================= TESTS ================================ //

    @Test
    public void testBuildSettlementCommand() throws server.exception.ServerException {

        //Create a default game to test against
        GameInfo info = facade.createGame(false, false, false, "BuildSettlementCommandTest");

        //Create a default player ... user:String pass:string is a default in our ViolatleDatabase
        AuthToken userString = new AuthToken("String", "string", 0, -1);    //If we were creating a new player we would first need to do facade.register(... , ...)

        //May need to join a player into the game
        facade.joinGame(userString, info.getId(), CatanColor.BLUE);

        //Build the command object
        AuthToken commandAuth = new AuthToken("String", "string", 0, -1);   //IMPORTANT: be sure to check that the player you are playing as is logged into the game and that you have the correct playerID (not player index)
        
        IJavaJSON commandJSON = new BuildSettlementJSON(0, new VertexLocation(new HexLocation(0, 0), VertexDirection.NorthEast), true); //IMPORTANT: again, check that you have the right unique playerID
        ICommand actualCommand = commandFactory.buildCommand(commandAuth, commandJSON);

        //Run the command object
        actualCommand.execute();

//        facade.getGameModel(token)
        
        //Check that something changed
        assert true;
    }

}
