package test.JSONSerializer;

import client.data.GameInfo;
import com.google.inject.Guice;
import com.google.inject.Injector;
import model.CatanModel;
import org.junit.Before;
import org.junit.Test;
import server.AuthToken;
import server.exception.ServerException;
import server.facade.IServerFacade;
import server.facade.JSONSerializer;
import server.guice.VolatileRealModule;
import serverProxy.JSONDeserializer;
import shared.definitions.CatanColor;
import shared.exceptions.player.GeneralPlayerException;
import shared.exceptions.player.InvalidTurnStatusException;
import shared.exceptions.player.TurnIndexException;
import test.JsonFileLoader;

import java.io.IOException;


/**
 * Created by clayt on 3/20/2016.
 */
public class TestJSONSerializer {

    Injector injector;
    CatanModel model;
    IServerFacade facade;

    AuthToken bob;
    AuthToken kat;
    AuthToken sam;
    AuthToken jen;

    @Before
    public void setup() throws ServerException {
        injector = Guice.createInjector(new VolatileRealModule());

        facade = injector.getInstance(IServerFacade.class);

        GameInfo info = facade.createGame(false, false, false, "this is a game");

        facade.register("bob", "bob");
        //facade.register("kat", "kat");
        //facade.register("sam", "sam");
        //facade.register("jen", "jen");

        bob = new AuthToken("bob", "bob", 0, info.getId());
        //kat = new AuthToken("kat", "kat", 1, info.getId());
        //sam = new AuthToken("sam", "sam", 2, info.getId());
        //jen = new AuthToken("jen", "jen", 3, info.getId());

        facade.joinGame(bob, bob.getGameID(), CatanColor.BLUE);
        //facade.joinGame(kat, info.getId(), CatanColor.RED);
        //facade.joinGame(sam, info.getId(), CatanColor.GREEN);
        //facade.joinGame(jen, info.getId(), CatanColor.YELLOW);

        model = facade.getGameModel(bob);
    }

    @Test
    public void testSerialize()
    {
        JSONSerializer.serialize(model);
    }
    
    @Test
    public void testDefaultJson() throws IOException, GeneralPlayerException, InvalidTurnStatusException, TurnIndexException {
    	CatanModel model = JSONDeserializer.deserialize(JsonFileLoader.readFile("json/default.json"));
    	assert(JSONSerializer.serialize(model).equals(JsonFileLoader.readFile("json/defaultTest.json")));
    }
    
    @Test
    public void testEmptyJson() throws IOException, GeneralPlayerException, InvalidTurnStatusException, TurnIndexException {
    	JSONDeserializer.deserialize(JsonFileLoader.readFile("json/empty.json"));
    	assert(JSONSerializer.serialize(model).equals(JsonFileLoader.readFile("json/emptyTest.json")));
    }
}
