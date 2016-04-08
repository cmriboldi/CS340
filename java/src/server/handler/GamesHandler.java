package server.handler;

import client.data.GameInfo;
import com.google.inject.Inject;
import com.sun.net.httpserver.HttpExchange;
import model.CatanModel;
import server.AuthToken;
import server.database.GameData;
import server.database.IPersistencePlugin;
import server.exception.BadRequestException;
import server.exception.InvalidCredentialsException;
import server.exception.ServerException;
import server.exception.UnauthorizedException;
import server.facade.IServerFacade;
import shared.communication.JSON.CreateGameJSON;
import shared.communication.JSON.JoinGameJSON;
import shared.communication.JSON.LoadJSON;
import shared.communication.JSON.SaveJSON;
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
    private IPersistencePlugin plugin;

    @Inject
    public GamesHandler(IServerFacade facade_p, IPersistencePlugin plugin)
    {
        super(facade_p);
        this.plugin = plugin;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException
    {
        try
        {
            String uri = httpExchange.getRequestURI().toString();
            System.out.println("GAMES_HANDLER: " + uri);

            switch(uri)
            {
                case "/games/list":
                    respond200(httpExchange, facade.listGames());
                    break;

                case "/games/create":
                    CreateGameJSON json = (CreateGameJSON) getRequest(httpExchange, CreateGameJSON.class);
                    GameInfo response = facade.createGame(json.isRandomTiles(), json.isRandomNumbers(), json.isRandomPorts(), json.getName());
                    respond200(httpExchange, response);

                    // account for persistence
                    if(plugin != null)
                    {
                        plugin.startTransaction();
                        CatanModel model = facade.getDatabase().getGameModel(response.getId());
                        plugin.getGameDAO().addGame(new GameData(response.getId(), response.getTitle(), model));
                        plugin.endTransaction(true);
                    }
                    break;

                case "/games/join":
                    AuthToken token = parseCookie(httpExchange);
                    JoinGameJSON joinJSON = (JoinGameJSON) getRequest(httpExchange, JoinGameJSON.class);
                    String cookie = facade.joinGame(token, joinJSON.getId(), CatanColor.toCatanColor(joinJSON.getColor()));
                    httpExchange.getResponseHeaders().add("Set-cookie", cookie);
                    success(httpExchange);

                    // account for persistence
                    if(plugin != null)
                    {
                        plugin.startTransaction();
                        CatanModel model = facade.getDatabase().getGameModel(token.getGameID());
                        plugin.getGameDAO().updateGame(token.getGameID());
                        plugin.endTransaction(true);
                    }
                    break;

                case "/games/save":
                    SaveJSON saveJSON = (SaveJSON) getRequest(httpExchange, SaveJSON.class);
                    facade.saveGame(saveJSON.getId(), saveJSON.getName());
                    success(httpExchange);
                    break;

                case "/games/load":
                    LoadJSON loadJSON = (LoadJSON) getRequest(httpExchange, LoadJSON.class);
                    facade.loadGame(loadJSON.getName());
                    success(httpExchange);
                    break;

                default:
                    respond404(httpExchange);
            }
        }
        catch (Exception e)
        {
            if(e.getClass().equals(BadRequestException.class))
                respond400(httpExchange, e.getMessage());
            else if(e.getClass().equals(InvalidCredentialsException.class))
                respond401(httpExchange, e.getMessage());
            else if(e.getClass().equals(UnauthorizedException.class))
                respond401(httpExchange, e.getMessage());
            else
                respond500(httpExchange);
            httpExchange.close();
            e.printStackTrace();
        }
    }
}
