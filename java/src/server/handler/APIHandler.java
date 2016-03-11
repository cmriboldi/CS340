package server.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import server.exception.ServerException;
import server.facade.FacadeProxy;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;

/**
 * Created by Joshua on 3/10/2016.
 *
 * Implements all simple methods that all handlers in the Catan Server will need to use.
 */
public abstract class APIHandler implements HttpHandler
{
    /**
     * If the operation was successful, respond with a 200 response code and the JSON object requested
     *
     * @param exchange The exchange object passed in by the 'handles' method
     * @param response The JSON object to be returned to the client
     */
    void respond200(HttpExchange exchange, Object response)
    {
        try
        {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(bos);
            out.writeObject(response);
            exchange.getResponseBody().write(bos.toByteArray());
            exchange.getResponseBody().close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * If the client made a bad, or otherwise incompatible request, respond with an error code 400
     *
     * @param exchange The exchange object passed in by the 'handles' method
     */
    void respond400(HttpExchange exchange)
    {
        exchange.close();
    }

    /**
     * If the client makes a request for a path that does not exist, respond with error code 404
     *
     * @param exchange The exchange object passed in by the 'handles' method
     */
    void respond404(HttpExchange exchange)
    {
        try {
            FacadeProxy.login("", "");
        } catch (ServerException e) {
            e.printStackTrace();
        }
        try {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
            exchange.getResponseBody().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        exchange.close();
    }

    /**
     * If the client makes a request and the server broke, respond with error code 500
     *
     * @param exchange The exchange object passed in by the 'handles' method
     */
    void respond500(HttpExchange exchange)
    {
        exchange.close();
    }
}
