package server.exception;

/**
 * Created by Joshua on 3/15/2016.
 */
public class BadRequestException extends ServerException
{
    public  BadRequestException(String message)
    {
        super(message);
    }
}
