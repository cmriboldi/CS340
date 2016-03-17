package server.handler;

import com.google.inject.Inject;
import com.sun.net.httpserver.HttpExchange;
import server.facade.IServerFacade;

import java.io.IOException;

/**
 * Created by Joshua on 3/10/2016.
 *
 * Handles all REST API calls with the path game/...
 * @author Christian Riboldi
 * @author Clayton Condie
 * @author Jacob Brewer
 * @author Joshua Powers
 * @author Joshua Van Steeter
 * @version 1.0 Build Winter 2016.
 */
public class GameHandler extends APIHandler
{
    @Inject
    public GameHandler(IServerFacade facade_p) {
        super(facade_p);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

    }
}
