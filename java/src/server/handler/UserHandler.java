package server.handler;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

/**
 * Created by Joshua on 3/10/2016.
 *
 * Handles all REST API calls with the path user/...
 */
public class UserHandler extends APIHandler
{
    @Override
    public void handle(HttpExchange httpExchange) throws IOException
    {
        System.out.println("Hey I'm actually getting touched");
        respond404(httpExchange);
    }
}
