package server.handler;

import com.google.inject.Inject;
import com.sun.net.httpserver.HttpExchange;
import model.CatanModel;
import server.AuthToken;
import server.command.CommandFactory;
import server.command.ICommand;
import server.database.IPersistencePlugin;
import server.exception.BadRequestException;
import server.exception.DatabaseException;
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
    private final IPersistencePlugin plugin;

    @Inject
    public MovesHandler(IServerFacade facade_p, IPersistencePlugin _plugin) {
        super(facade_p);
        commandFactory = new CommandFactory(facade);
        plugin = _plugin;
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
            
            plugin.startTransaction();

            respond200(httpExchange, JSONSerializer.serialize((CatanModel)command.execute()));
            
            plugin.getCommandDAO().addCommand(command); //Need to make sure that each of the addCommand methods check for the nth command and do the clearning needed.
            
            plugin.endTransaction(true);
            
            facade.recordCommand(token, command); // Probably don't need this because it's stored in the databases.
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
            try
			{
				plugin.endTransaction(false);
			} catch (DatabaseException e1)
			{
				e1.printStackTrace();
			}
            e.printStackTrace();
        }
    }
}
