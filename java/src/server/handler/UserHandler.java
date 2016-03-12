package server.handler;

import com.sun.net.httpserver.HttpExchange;
import server.data.UserInfo;
import server.exception.ServerException;
import server.facade.FacadeHolder;
import server.facade.IServerFacade;
import shared.communication.JSON.IJavaJSON;
import shared.communication.JSON.LoginJSON;

import java.io.IOException;
import java.net.HttpURLConnection;

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

            switch(uri)
            {
                case "/user/register":
                    LoginJSON json = (LoginJSON) getRequest(httpExchange, LoginJSON.class);
                    String response = facade.register(json.getUsername(), json.getPassword());
                    httpExchange.getResponseHeaders().add("Set-cookie", response);
                    success(httpExchange);
                    break;
            }

            System.out.println("Printing stuff out here: " + uri);
            httpExchange.close();
            System.out.println("Hey I'm actually getting touched");

        }
        catch (ServerException e)
        {
            e.printStackTrace();
        }




        //respond404(httpExchange);
    }
}
