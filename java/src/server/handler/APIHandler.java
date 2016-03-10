package server.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

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
    void responsd200(HttpExchange exchange, Object response)
    {
        exchange.close();
    }

    /**
     * If the client made a bad, or otherwise incompatible request, respond with an error code 400
     *
     * @param exchange The exchange object passed in by the 'handles' method
     */
    void responsd400(HttpExchange exchange)
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
