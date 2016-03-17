package server.handler;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.inject.Inject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import server.AuthToken;
import server.exception.BadRequestException;
import server.exception.InternalErrorException;
import server.exception.ServerException;
import server.facade.IServerFacade;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URLDecoder;

/**
 * Created by Joshua on 3/10/2016.
 *
 * Implements all simple methods that all handlers in the Catan Server will need to use.
 *
 * @author Christian Riboldi
 * @author Clayton Condie
 * @author Jacob Brewer
 * @author Joshua Powers
 * @author Joshua Van Steeter
 * @version 1.0 Build Winter 2016.
 */
public abstract class APIHandler implements HttpHandler
{
    protected final IServerFacade facade;

    @Inject
    public APIHandler(IServerFacade facade_p)
    {
        facade = facade_p;
    }

    protected void success(HttpExchange exchange)
    {
        String response = "Success";
        try
        {
            exchange.getResponseHeaders().set("Content-Type", "text/html");
            exchange.sendResponseHeaders(200, 0);
            exchange.getResponseBody().write(response.getBytes());
            exchange.getResponseBody().flush();
            exchange.getResponseBody().close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * If the operation was successful, respond with a 200 response code and the JSON object requested
     *
     * @param exchange The exchange object passed in by the 'handles' method
     * @param response The JSON object to be returned to the client
     */
    protected void respond200(HttpExchange exchange, Object response) throws InternalErrorException {
        try
        {
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            String json = new Gson().toJson(response);
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, json.getBytes().length);
            exchange.getResponseBody().write(json.getBytes());
            exchange.getResponseBody().close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new InternalErrorException("Error in APIHandler respond200");
        }
    }

    /**
     * If the client made a bad, or otherwise incompatible request, respond with an error code 400
     *
     * @param exchange The exchange object passed in by the 'handles' method
     */
    protected void respond400(HttpExchange exchange)
    {
        try
        {
            exchange.sendResponseHeaders(400, -1);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        exchange.close();
    }

    protected void respond401(HttpExchange exchange)
    {
        try
        {
            exchange.sendResponseHeaders(401, -1);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        exchange.close();
    }

    /**
     * If the client makes a request for a path that does not exist, respond with error code 404
     *
     * @param exchange The exchange object passed in by the 'handles' method
     */
    protected void respond404(HttpExchange exchange)
    {
        exchange.close();
    }

    /**
     * If the client makes a request and the server broke, respond with error code 500
     *
     * @param exchange The exchange object passed in by the 'handles' method
     */
    protected void respond500(HttpExchange exchange)
    {
        try
        {
            exchange.sendResponseHeaders(500, -1);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        exchange.close();
    }

    protected Object getRequest(HttpExchange exchange, Class<?> type) throws ServerException
    {
        try
        {
            InputStreamReader isr = new InputStreamReader(exchange.getRequestBody());
            BufferedReader reader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null)
            {
                sb.append(line);
            }

            return new Gson().fromJson(sb.toString(), type);
        }
        catch (IOException e)
        {
            throw new InternalErrorException("When reading the request, something broke");
        }
        catch (JsonSyntaxException e)
        {
            throw new BadRequestException("Request included invalid JSON syntax");
        }
    }

    protected AuthToken parseCookie(HttpExchange exchange) throws InternalErrorException
    {
        try
        {
            String cookie = exchange.getRequestHeaders().getFirst("Cookie");
            AuthToken token = new AuthToken();
//            System.out.println("\nCookie: " + cookie);
            if(!cookie.matches(".*catan.user=.*"))
            {
                return null;
            }
            String userCookie = cookie.replaceAll(";", "").replaceAll("catan.user=", "").replaceAll("catan.game=\\d++", "").replaceAll("\\s", "");
//            System.out.println("User cookie: " + userCookie);

            JsonObject json = new Gson().fromJson(URLDecoder.decode(userCookie, "UTF-8"), JsonObject.class);
//            System.out.println("Decoded cookie: " + URLDecoder.decode(userCookie, "UTF-8"));
            token.setPlayerID(Integer.parseInt(json.get("playerID").toString()));
            token.setName(json.get("name").toString().replace("\"", ""));
            token.setPassword(json.get("password").toString().replace("\"", ""));
            if(cookie.matches(".*catan.game=.*"))
            {
//                System.out.println("Cookie is now: " + cookie);
//                String parsingSucks = cookie.replaceAll("catan.user=[\\w%]*;?", "").replaceAll("catan.game=", "").replaceAll("\\s", "").replaceAll(";", "");
//                System.out.println("After the grinder: " + parsingSucks);
                int gameId = Integer.parseInt(cookie.replaceAll("catan.user=[\\w%]*;?", "").replaceAll("catan.game=", "").replaceAll("\\s", "").replaceAll(";", ""));
                token.setGameID(gameId);
            }
            return token;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new InternalErrorException("Error thrown in APIHandler parseCookie()");
        }
    }
}
