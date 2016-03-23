package test;

import client.data.GameInfo;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import server.AuthToken;
import server.exception.ServerException;
import server.facade.IServerFacade;
import server.guice.VolatileMockModule;
import server.guice.VolatileRealModule;
import server.handler.*;
import shared.definitions.CatanColor;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

/**
 * Created by Joshua on 3/22/2016.
 */
public class TestServer
{
    private static int SERVER_PORT_NUMBER = 8081;
    private static final int MAX_WAITING_CONNECTIONS = 10;

    private HttpHandler userHandler;
    private HttpHandler gamesHandler;
    private HttpHandler gameHandler;
    private HttpHandler movesHandler;
    private HttpHandler swaggerHandler;

    private HttpServer server;

    public TestServer()
    {

    }

    private static String getURL()
    {
        String result = "";
        try {
            result = "http://" + InetAddress.getLocalHost().getHostName() + ":" + SERVER_PORT_NUMBER;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void run() throws ServerException
    {
        //Create the new Guice injector
        Injector injector = Guice.createInjector(new VolatileMockModule());

        //initialize the http handlers
        userHandler = injector.getInstance(UserHandler.class);
        gamesHandler = injector.getInstance(GamesHandler.class);
        gameHandler = injector.getInstance(GameHandler.class);
        movesHandler = injector.getInstance(MovesHandler.class);
        swaggerHandler = injector.getInstance(SwaggerHandler.class);

        IServerFacade facade = injector.getInstance(IServerFacade.class);

        facade.register("Brooke", "brooke");
        facade.register("Sam", "sam");
        facade.register("Mark", "mark");
        facade.register("Pete", "pete");

        GameInfo info = facade.createGame(false, false, false, "Nothing Random");

        facade.joinGame(new AuthToken("Brooke", "brooke", 1, -1), info.getId(), CatanColor.BLUE);
        facade.joinGame(new AuthToken("Sam", "sam", 2, -1), info.getId(), CatanColor.RED);
        facade.joinGame(new AuthToken("Mark", "mark", 3, -1), info.getId(), CatanColor.ORANGE);
        facade.joinGame(new AuthToken("Pete", "pete", 4, -1), info.getId(), CatanColor.GREEN);

        try
        {
            server = HttpServer.create(new InetSocketAddress(SERVER_PORT_NUMBER),
                    MAX_WAITING_CONNECTIONS);
        }
        catch (IOException e)
        {
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

        System.out.println("---!!!!!!!!!!!!!!!!!!!!!!!!  About to start  !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!---");

        server.start();
    }

    public void stop()
    {
        server.stop(0);
        System.out.println("---!!!!!!!!!!!!!!!!!!!!!!!!  Server has Stopped  !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!---");
    }
}
