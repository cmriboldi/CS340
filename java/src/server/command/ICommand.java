package server.command;

/**
 * Created by Joshua on 3/9/2016. ICommand is the abstracted class used to run commands through the Handlers.
 * Each implementation of ICommand may vary greatly in their individual methods and variables.
 */
public interface ICommand
{
    /**
     * execute acts as a hook for whatever outside object wants to interact with the Command Object
     * @return An Object representation of whatever end product of the Command object's execution
     */
    Object execute();
}