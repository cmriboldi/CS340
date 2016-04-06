package server.command;

import server.AuthToken;
import server.exception.ServerException;

/**
 * Created by Joshua on 3/9/2016. ICommand is the abstracted class used to run commands through the Handlers.
 * Each implementation of ICommand may vary greatly in their individual methods and variables.
 */
public abstract class ICommand
{
    protected AuthToken authToken;

    public ICommand(AuthToken authToken)
    {
        this.authToken = authToken;
    }
    /**
     * execute acts as a hook for whatever outside object wants to interact with the Command Object
     * @return An Object representation of whatever end product of the Command object's execution
     * @throws ServerException
     */
    public abstract Object execute() throws ServerException;

    public int getGameID()
    {
        return authToken.getGameID();
    }
}