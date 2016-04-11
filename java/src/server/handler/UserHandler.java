package server.handler;

import com.google.inject.Inject;
import com.sun.net.httpserver.HttpExchange;
import server.data.UserData;
import plugin.IPersistencePlugin;
import server.exception.BadRequestException;
import server.exception.DatabaseException;
import server.exception.InvalidCredentialsException;
import server.facade.IServerFacade;
import shared.communication.JSON.LoginJSON;

import java.io.IOException;

/**
 * Created by Joshua on 3/10/2016.
 *
 * Handles all REST API calls with the path user/...
 * @author Christian Riboldi
 * @author Clayton Condie
 * @author Jacob Brewer
 * @author Joshua Powers
 * @author Joshua Van Steeter
 * @version 1.0 Build Winter 2016.
 */
public class UserHandler extends APIHandler
{
    private IPersistencePlugin plugin;

    @Inject
    public UserHandler(IServerFacade facade_p, IPersistencePlugin plugin)
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
            LoginJSON json;
            String response;
            System.out.println("USER_HANDLER: " + uri);

            switch(uri)
            {
                case "/user/register":
                    json = (LoginJSON) getRequest(httpExchange, LoginJSON.class);
                    response = facade.register(json.getUsername(), json.getPassword());
                    httpExchange.getResponseHeaders().add("Set-cookie", response);

                    //account for persistence
                    plugin.startTransaction();
                    plugin.getUserDAO().addUser(new UserData(json.getUsername(), json.getPassword()));
                    plugin.endTransaction(true);

                    success(httpExchange);
                    break;

                case "/user/login":
                    json = (LoginJSON) getRequest(httpExchange, LoginJSON.class);
                    response = facade.login(json.getUsername(), json.getPassword());
                    httpExchange.getResponseHeaders().add("Set-cookie", response);
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
            if(e.getClass().equals(InvalidCredentialsException.class))
                respond401(httpExchange, e.getMessage());
            try
            {
                plugin.endTransaction(false);
            }
            catch (DatabaseException e1)
            {
                e1.printStackTrace();
            }
            httpExchange.close();
            e.printStackTrace();
        }
    }
}
