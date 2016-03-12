package server.handler;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import server.AuthToken;
import server.exception.InternalErrorException;
import server.exception.ServerException;
import server.facade.FacadeHolder;
import server.facade.IServerFacade;
import shared.communication.JSON.*;

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
        exchange.close();
    }

    /**
     * If the client makes a request for a path that does not exist, respond with error code 404
     *
     * @param exchange The exchange object passed in by the 'handles' method
     */
    protected void respond404(HttpExchange exchange)
    {
        IServerFacade facade;
        try
        {
            facade = FacadeHolder.getFacade();
            facade.login("", "");
        }
        catch (ServerException e)
        {
            e.printStackTrace();
        }
        try
        {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, -1);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        exchange.close();
    }

    /**
     * If the client makes a request and the server broke, respond with error code 500
     *
     * @param exchange The exchange object passed in by the 'handles' method
     */
    protected void respond500(HttpExchange exchange)
    {
        exchange.close();
    }

    protected Object getRequest(HttpExchange exchange, Class<?> type)
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
            e.printStackTrace();
        }

        return null;
    }

    protected AuthToken parseCookie(HttpExchange exchange) throws InternalErrorException
    {
        try
        {
            String cookie = exchange.getRequestHeaders().getFirst("Cookie");
            AuthToken token = new AuthToken();
            System.out.println("\nCookie: " + cookie);
            if(!cookie.matches(".*catan.user=.*"))
            {
                return null;
            }
            String userCookie = cookie.replaceAll("catan.user=", "").replaceAll(".*catan.game=.*;", "");
            System.out.println("User cookie: " + userCookie);

            JsonObject json = new Gson().fromJson(URLDecoder.decode(userCookie, "UTF-8"), JsonObject.class);
            System.out.println("Decoded cookie: " + URLDecoder.decode(userCookie, "UTF-8"));
            token.setPlayerID(Integer.parseInt(json.get("playerID").toString()));
            token.setName(json.get("name").toString());
            token.setPassword(json.get("password").toString());
            if(cookie.matches(".*catan.game=.*"))
            {
                System.out.println("Cookie is now: " + cookie);
                String parsingSucks = cookie.replaceAll("catan.game=", "").replaceAll("catan.user=.*;?+", "").replaceAll(";", "");
                System.out.println("After the grinder: " + parsingSucks);
                int gameId = Integer.parseInt(cookie.replaceAll("catan.game=", "").replaceAll("catan.user=.*;?+", "").replaceAll(";", "").replaceAll("\\s", ""));
                token.setPlayerID(gameId);
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
