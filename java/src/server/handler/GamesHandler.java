package server.handler;

import client.data.GameInfo;
import com.google.inject.Inject;
import com.sun.net.httpserver.HttpExchange;
import server.AuthToken;
import server.exception.ServerException;
import server.exception.UnauthorizedException;
import server.facade.IServerFacade;
import shared.communication.JSON.CreateGameJSON;
import shared.communication.JSON.JoinGameJSON;
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
    @Inject
    public GamesHandler(IServerFacade facade_p) {
        super(facade_p);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException
    {
        try
        {
            String uri = httpExchange.getRequestURI().toString();

            switch(uri)
            {
                case "/games/list":
                    respond200(httpExchange, facade.listGames());
                    break;

                case "/games/create":
                    CreateGameJSON json = (CreateGameJSON) getRequest(httpExchange, CreateGameJSON.class);
                    GameInfo response = facade.createGame(json.isRandomTiles(), json.isRandomNumbers(), json.isRandomPorts(), json.getName());
                    respond200(httpExchange, response);
                    break;

                case "/games/join":
                    AuthToken token = parseCookie(httpExchange);
                    JoinGameJSON joinJSON = (JoinGameJSON) getRequest(httpExchange, JoinGameJSON.class);
                    System.out.println("Authtoken Parsed:\nname: " + token.getName() + "\npassword: " + token.getPassword() + "\nplayerid: " + token.getPlayerID() + "\ngameId: " + token.getGameID());
                    String cookie = facade.joinGame(token, joinJSON.getId(), CatanColor.toCatanColor(joinJSON.getColor()));
                    System.out.println("---Finally out of the facade!!!---");
                    System.out.println("Response:" + cookie);
                    httpExchange.getResponseHeaders().add("Set-cookie", cookie);
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
