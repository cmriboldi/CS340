package server.exception;

/**
 * Created by jvanstee on 3/14/2016.
 */
public class InvalidCredentialsException extends ServerException
{
    public InvalidCredentialsException(String message)
    {
        super(message);
    }
}
