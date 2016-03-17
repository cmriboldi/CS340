package server.handler;

import com.google.inject.Inject;
import com.sun.net.httpserver.HttpExchange;
import server.AuthToken;

import server.exception.BadRequestException;
import server.exception.InvalidCredentialsException;
import server.exception.ServerException;
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

            String uri = httpExchange.getRequestURI().toString();
            System.out.println("GAME_HANDLER: " + uri);

            switch(uri)
            {
                case "/game/model":
                    success(httpExchange);
                    //Not implemented yet, waiting on Model Serializer
                    //respond200(httpExchange, facade.getGameModel(token));
                    break;

                default:
                    respond404(httpExchange);
            }
        }
        catch (ServerException e)
        {
            if(e.getClass().equals(BadRequestException.class))
                respond400(httpExchange);
            if(e.getClass().equals(InvalidCredentialsException.class))
                respond401(httpExchange);
            httpExchange.close();
            e.printStackTrace();
        }
    }
}
