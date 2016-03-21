package server.handler;

import com.google.inject.Inject;
import com.sun.net.httpserver.HttpExchange;
import model.CatanModel;
import server.AuthToken;

import server.exception.BadRequestException;
import server.exception.InvalidCredentialsException;
import server.exception.ServerException;
import server.facade.IServerFacade;
import server.facade.JSONSerializer;

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
    public GameHandler(IServerFacade facade_p)
    {
        super(facade_p);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException
    {
        try
        {
            AuthToken token = parseCookie(httpExchange);
            if(!facade.isValidUser(token))
                throw new InvalidCredentialsException("Invalid user credentials to issue command");

            String uri = httpExchange.getRequestURI().getPath();
            System.out.println("GAME_HANDLER: " + uri);

            switch(uri)
            {
                case "/game/model":
                    //success(httpExchange);
                    //Not implemented yet, waiting on Model Serializer
                    String query = httpExchange.getRequestURI().getQuery();
                    if(query != null && query.matches(".*version=\\d*"))
                    {
                        System.out.println("GAME_HANDLER QUERY: " + query);
                        int version = Integer.parseInt(query.replaceAll(".*version=", ""));
                        Object response = facade.getGameModel(token, version);
                        if(response.getClass().equals(CatanModel.class))
                            respond200(httpExchange, JSONSerializer.serialize((CatanModel)response));
                        else
                            respond200(httpExchange, response);
                        break;
                    }
                    System.out.println("About to Serialize");
                    String response = JSONSerializer.serialize(facade.getGameModel(token));
                    System.out.println("Finished Serializing");
                    httpExchange.sendResponseHeaders(200, response.length());
                    httpExchange.getResponseBody().write(response.getBytes());
                    httpExchange.close();
                    break;

                case "/game/addAI":
                    respond403(httpExchange, "Adding AI has not yet been implemented");
                    break;

                case "/game/listAI":
                    respond200(httpExchange, facade.listAI(token));
                    break;

                default:
                    respond404(httpExchange);
            }
        }
        catch (ServerException e)
        {
            if(e.getClass().equals(BadRequestException.class))
                respond400(httpExchange, e.getMessage());
            if(e.getClass().equals(InvalidCredentialsException.class))
                respond401(httpExchange, e.getMessage());
            httpExchange.close();
            e.printStackTrace();
        }
    }
}
