package server.handler;

import com.google.inject.Inject;
import com.sun.net.httpserver.HttpExchange;
import model.CatanModel;
import server.AuthToken;
import server.command.CommandFactory;
import server.command.ICommand;
import server.exception.BadRequestException;
import server.exception.InvalidCredentialsException;
import server.exception.ServerException;
import server.exception.UnauthorizedException;
import server.facade.IServerFacade;
import server.facade.JSONSerializer;
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
    private final CommandFactory commandFactory;

    @Inject
    public MovesHandler(IServerFacade facade_p) {
        super(facade_p);
        commandFactory = new CommandFactory(facade);
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
            System.out.println("MOVES_HANDLER: " + uri);
            Class<?> type = IJavaJSON.getTypeFromURI(uri);
            if(type == null)
                respond404(httpExchange, "Invalid Command type");

            IJavaJSON json = (IJavaJSON) getRequest(httpExchange, type);
            ICommand command = commandFactory.buildCommand(token, json);

            respond200(httpExchange, JSONSerializer.serialize((CatanModel)command.execute()));
            facade.recordCommand(token, command);
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
                respond400(httpExchange, e.getMessage());
            httpExchange.close();
            e.printStackTrace();
        }
    }
}
