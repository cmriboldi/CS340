package server.handler;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * Created by Joshua on 3/10/2016.
 *
 * Handles all REST API calls with the path user/...
 * @author Christian Riboldi
 * @author Clayton Condie
 * @author Jacob Brewer
 * @author Joshua Powers
 * @author Joshua Van Steeter
 * @version 1.0 Build Winter 2016.
 */
public class UserHandler extends APIHandler
{
    @Override
    public void handle(HttpExchange httpExchange) throws IOException
    {
        httpExchange.getResponseHeaders().add("Location", "/docs/api/view/index.html");
        httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_MOVED_PERM, -1);
        System.out.println("Hey I'm actually getting touched");
        //respond404(httpExchange);
    }
}
