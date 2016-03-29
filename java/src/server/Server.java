package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.logging.*;

import client.data.GameInfo;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.sun.net.httpserver.*;
import server.database.IDatabase;
import server.database.VolatileDatabase;
import server.exception.ServerException;
import server.facade.IServerFacade;
import server.facade.ServerFacade;
import server.guice.VolatileRealModule;
import server.handler.*;
import shared.definitions.CatanColor;

/**
 * Created by Joshua on 3/9/2016.
 * <p/>
 * The HTTP Server that hosts the Catan Game API
 *
 * @author Christian Riboldi
 * @author Clayton Condie
 * @author Jacob Brewer
 * @author Joshua Powers
 * @author Joshua Van Steeter
 * @version 1.0 Build Winter 2016.
 */
public class Server {
    private static int SERVER_PORT_NUMBER = 8081;
    private static final int MAX_WAITING_CONNECTIONS = 10;

    private HttpHandler userHandler;
    private HttpHandler gamesHandler;
    private HttpHandler gameHandler;
    private HttpHandler movesHandler;
    private HttpHandler swaggerHandler;

    private HttpServer server;

    private Server()
    {
        return;
    }


    public static void main(String[] args) throws ServerException
    {
        new Server().run(args);
    }

    private static String getURL()
    {
        String result = "";
        try
        {
            result = "http://" + InetAddress.getLocalHost().getHostName() + ":" + SERVER_PORT_NUMBER;
        }
        catch (UnknownHostException e)
        {
            e.printStackTrace();
        }
        return result;
    }


    private void run(String[] args) throws ServerException
    {
        //parse command line arguments
        try
        {
            if(args.length > 0)
            {
                SERVER_PORT_NUMBER = Integer.parseInt(args[0]);
            }
        }
        catch(Exception e)
        {
            System.out.println("COMMAND LINE ARGUMENT MUST BE A VALID PORT NUMBER!");
            e.printStackTrace();
            return;
        }
        //Create the new Guice injector
        Injector injector = Guice.createInjector(new VolatileRealModule());

        //initialize the http handlers
        userHandler = injector.getInstance(UserHandler.class);
        gamesHandler = injector.getInstance(GamesHandler.class);
        gameHandler = injector.getInstance(GameHandler.class);
        movesHandler = injector.getInstance(MovesHandler.class);
        swaggerHandler = injector.getInstance(SwaggerHandler.class);




        IServerFacade facade = injector.getInstance(IServerFacade.class);

        facade.register("Pete", "pete");
        facade.register("Sam", "sam");
        facade.register("Brooke", "brooke");
        facade.register("Mark", "mark");

//        GameInfo info = facade.createGame(false, false, false, "Nothing Random");
//
//        facade.joinGame(new AuthToken("Pete", "pete", 1, -1), info.getId(), CatanColor.RED);
//        facade.joinGame(new AuthToken("Sam", "sam", 2, -1), info.getId(), CatanColor.ORANGE);
//        facade.joinGame(new AuthToken("Brooke", "brooke", 3, -1), info.getId(), CatanColor.BLUE);
//        facade.joinGame(new AuthToken("Mark", "mark", 4, -1), info.getId(), CatanColor.GREEN);
        




        try
        {
            server = HttpServer.create(new InetSocketAddress(SERVER_PORT_NUMBER),
                    MAX_WAITING_CONNECTIONS);
        }
        catch (IOException e)
        {
//            logger.log(Level.SEVERE, e.getMessage(), e);
            e.printStackTrace();
            return;
        }

        server.setExecutor(null); // use the default executor

        server.createContext("/user", userHandler);
        server.createContext("/games", gamesHandler);
        server.createContext("/game", gameHandler);
        server.createContext("/moves", movesHandler);
        server.createContext("/docs/api/data", new SwaggerHandler.JSONAppender(""));
        server.createContext("/docs/api/view", new SwaggerHandler.BasicFile(""));
        server.createContext("/", swaggerHandler);

//        logger.info("Starting HTTP Server");

        server.start();
    }


}

