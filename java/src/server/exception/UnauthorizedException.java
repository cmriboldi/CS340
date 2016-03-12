package server.exception;

import server.Server;

/**
 * Created by Joshua on 3/12/2016.
 */
public class UnauthorizedException extends ServerException
{
    public UnauthorizedException(String message)
    {
        super(message);
    }
}
