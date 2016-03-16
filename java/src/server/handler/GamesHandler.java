package server.handler;

import com.sun.net.httpserver.HttpExchange;
import server.AuthToken;
import server.exception.ServerException;
import server.exception.UnauthorizedException;
import server.facade.FacadeHolder;
import server.facade.IServerFacade;
import shared.communication.JSON.*;
import shared.definitions.CatanColor;

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
                    AuthToken token = parseCookie(httpExchange);
                    JoinGameJSON json = (JoinGameJSON) getRequest(httpExchange, JoinGameJSON.class);
                    System.out.println("Authtoken Parsed:\nname: " + token.getName() + "\npassword: " + token.getPassword() + "\nplayerid: " + token.getPlayerID() + "\ngameId: " + token.getGameID());
                    response = facade.joinGame(token, json.getId(), CatanColor.toCatanColor(json.getColor()));
                    System.out.println("---Finally out of the facade!!!---");
                    System.out.println("Response:" + response);
                    httpExchange.getResponseHeaders().add("Set-cookie", response);
                    success(httpExchange);
                    break;
            }
        }
        catch (ServerException e)
        {
            if(e.getClass().equals(UnauthorizedException.class))
            {
                respond401(httpExchange);
            }
            e.printStackTrace();
        }
    }
}
