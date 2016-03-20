package JSONSerializer;

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

/**
 * Created by clayt on 3/20/2016.
 */
public class TestJSONSerializer {

    Injector injector;
    CatanModel model;
    IServerFacade facade;
    AuthToken token = new AuthToken("bob", "bob", 0, 0);

    @Before
    public void setup() throws ServerException {
        injector = Guice.createInjector(new VolatileRealModule());

        facade = injector.getInstance(IServerFacade.class);

        GameInfo info = facade.createGame(false, false, false, "this is a game");

        token.setGameID(info.getId());

        model = facade.getGameModel(token);
    }

    @Test
    public void testSerialize()
    {
        System.out.println(JSONSerializer.serialize(model));
    }
}
