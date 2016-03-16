package server.handler;

import com.sun.net.httpserver.HttpExchange;
import server.data.UserInfo;
import server.exception.InvalidCredentialsException;
import server.exception.ServerException;
import server.facade.FacadeHolder;
import server.facade.IServerFacade;
import shared.communication.JSON.IJavaJSON;
import shared.communication.JSON.LoginJSON;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URLDecoder;

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
    @Override
    public void handle(HttpExchange httpExchange) throws IOException
    {
        try
        {
            String uri = httpExchange.getRequestURI().toString();
            IServerFacade facade = FacadeHolder.getFacade();
            LoginJSON json;
            String response;
            System.out.println("handler: " + uri); 

            switch(uri)
            {
                case "/user/register":
                    json = (LoginJSON) getRequest(httpExchange, LoginJSON.class);
                    response = facade.register(((LoginJSON)json).getUsername(), ((LoginJSON)json).getPassword());
                    httpExchange.getResponseHeaders().add("Set-cookie", response);
                    success(httpExchange);
                    break;

                case "/user/login":
                    json = (LoginJSON) getRequest(httpExchange, LoginJSON.class);
                    response = facade.login(((LoginJSON)json).getUsername(), ((LoginJSON)json).getPassword());
                    httpExchange.getResponseHeaders().add("Set-cookie", response);
                    success(httpExchange);
                    break;
            }
            System.out.println("Printing stuff out here: " + uri);
            System.out.println("Hey I'm actually getting touched");
        }
        catch (ServerException e)
        {
            if(e.getClass().equals(InvalidCredentialsException.class))
                respond401(httpExchange);
            e.printStackTrace();
        }
    }
}
