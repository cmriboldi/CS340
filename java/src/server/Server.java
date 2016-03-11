package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.logging.*;
import com.sun.net.httpserver.*;
import server.database.IDatabase;
import server.database.VolatileDatabase;
import server.exception.ServerException;
import server.facade.IServerFacade;
import server.facade.ServerFacade;
import server.handler.GamesHandler;
import server.handler.MovesHandler;
import server.handler.UserHandler;

/**
 * Created by Joshua on 3/9/2016.
 *
 * The HTTP Server that hosts the Catan Game API
 *
 * @author Christian Riboldi
 * @author Clayton Condie
 * @author Jacob Brewer
 * @author Joshua Powers
 * @author Joshua Van Steeter
 * @version 1.0 Build Winter 2016.
 */
public class Server
{
    private static int SERVER_PORT_NUMBER = 8081;
    private static final int MAX_WAITING_CONNECTIONS = 10;

//    private static Logger logger;

//    static
//    {
//        try
//        {
//            initLog();
//        }
//        catch (IOException e)
//        {
//            System.out.println("Could not initialize log: " + e.getMessage());
//        }
//    }
//
//    private static void initLog() throws IOException
//    {
//        Level logLevel = Level.FINE;
//
//        logger = Logger.getLogger("contactmanager");
//        logger.setLevel(logLevel);
//        logger.setUseParentHandlers(false);
//
//        Handler consoleHandler = new ConsoleHandler();
//        consoleHandler.setLevel(logLevel);
//        consoleHandler.setFormatter(new SimpleFormatter());
//        logger.addHandler(consoleHandler);
//
//        FileHandler fileHandler = new FileHandler("log.txt", false);
//        fileHandler.setLevel(logLevel);
//        fileHandler.setFormatter(new SimpleFormatter());
//        logger.addHandler(fileHandler);
//    }

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

    private HttpServer server;

    private Server()
    {
        return;
    }

    private void run(String[] args)
    {
        // Parse Arguments


        //******************** Not yet implemented ************************


//        logger.info("Initializing Model");

        // For now, we will use defaults
        try
        {
            DIFactory factory = new DIFactory();
            factory.bind(IDatabase.class, VolatileDatabase.class);
            factory.bind(IServerFacade.class, ServerFacade.class);

            factory.buildFacade();
        }
        catch (ServerException e)
        {
//            logger.log(Level.SEVERE, e.getMessage(), e);
            e.printStackTrace();
            return;
        }

//        logger.info("Initializing HTTP Server");

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

//        logger.info("Starting HTTP Server");

        server.start();
    }

    private HttpHandler userHandler = new UserHandler();
    private HttpHandler gamesHandler = new GamesHandler();
    private HttpHandler gameHandler = new GamesHandler();
    private HttpHandler movesHandler = new MovesHandler();

    public static void main(String[] args)
    {
        new Server().run(args);
    }
}

