package server.handler;

import com.sun.net.httpserver.HttpExchange;
import server.AuthToken;
import server.exception.ServerException;
import server.facade.FacadeHolder;
import server.facade.IServerFacade;
import shared.communication.JSON.*;

import java.io.IOException;

/**
 * Created by Joshua on 3/10/2016.
 *
 * Handles all REST API calls with the path games/...
 * @author Christian Riboldi
 * @author Clayton Condie
 * @author Jacob Brewer
 * @author Joshua Powers
 * @author Joshua Van Steeter
 * @version 1.0 Build Winter 2016.
 */
public class GamesHandler extends APIHandler
{
    @Override
    public void handle(HttpExchange httpExchange) throws IOException
    {
        try
        {
            String uri = httpExchange.getRequestURI().toString();
            IServerFacade facade = FacadeHolder.getFacade();
            IJavaJSON json;
            String response;

            switch(uri)
            {
                case "/games/list":
                    /*facade.listGames();
                    httpExchange.getResponseHeaders().add("Set-cookie", response);
                    success(httpExchange);*/
                    break;

                case "/games/create":
                    /*json = (LoginJSON) getRequest(httpExchange, LoginJSON.class);
                    response = facade.login(((LoginJSON)json).getUsername(), ((LoginJSON)json).getPassword());
                    httpExchange.getResponseHeaders().add("Set-cookie", response);
                    success(httpExchange);*/
                    break;

                case "/games/join":
                    json = (JoinGameJSON) getRequest(httpExchange, JoinGameJSON.class);
                    AuthToken token = parseCookie(httpExchange);
                    System.out.println("Authtoken Parsed:\nname: " + token.getName() + "\npassword: " + token.getPassword() + "\nplayerid: " + token.getPlayerID() + "\ngameId: " + token.getGameID());
                    success(httpExchange);
                    break;
            }
        }
        catch (ServerException e)
        {
            e.printStackTrace();
        }
    }
}
