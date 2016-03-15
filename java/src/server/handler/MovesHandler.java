package server.handler;

import com.sun.net.httpserver.HttpExchange;
import server.AuthToken;
import server.command.CommandFactory;
import server.command.ICommand;
import server.exception.BadRequestException;
import server.exception.InvalidCredentialsException;
import server.exception.ServerException;
import server.facade.FacadeHolder;
import server.facade.IServerFacade;
import shared.communication.JSON.*;

import java.io.IOException;

/**
 * Created by Joshua on 3/10/2016.
 *
 * Handles all REST API calls with the path moves/...
 * @author Christian Riboldi
 * @author Clayton Condie
 * @author Jacob Brewer
 * @author Joshua Powers
 * @author Joshua Van Steeter
 * @version 1.0 Build Winter 2016.
 */
public class MovesHandler extends APIHandler
{
    @Override
    public void handle(HttpExchange httpExchange) throws IOException
    {
        try
        {
            System.out.println("In MovesHandler");
            AuthToken token = parseCookie(httpExchange);
            System.out.println("Parsed the cookie");
            if(!FacadeHolder.getFacade().isValidUser(token))
                throw new InvalidCredentialsException("Invalid user credentials to issue command");

            String uri = httpExchange.getRequestURI().toString();
            System.out.println("URI=" + uri);
            Class<?> type = IJavaJSON.getTypeFromURI(uri);
            System.out.println("Type= " + type.toString());

            IJavaJSON json = (IJavaJSON) getRequest(httpExchange, type);
            System.out.println("Got the request json");
            ICommand command = CommandFactory.buildCommand(token, json);
            System.out.println("Command type returned: " + command.getClass());
            respond200(httpExchange, command.execute());
        }
        catch (ServerException e)
        {
            if(e.getClass().equals(BadRequestException.class))
                respond400(httpExchange);
            if(e.getClass().equals(InvalidCredentialsException.class))
                respond401(httpExchange);
        }
    }
}
