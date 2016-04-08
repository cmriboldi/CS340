package server;

import java.io.File;
import java.io.IOException;
import java.net.*;
import java.util.logging.*;

import client.data.GameInfo;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.sun.net.httpserver.*;
import jcommander.JCommander;
import jcommander.Parameter;
import server.database.EmptyPlugin;
import server.database.IDatabase;
import server.database.IPersistencePlugin;
import server.database.IPluginClass;
import server.database.VolatileDatabase;
import server.exception.ServerException;
import server.facade.IServerFacade;
import server.facade.ServerFacade;
import server.guice.PersistantModule;
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
        File jarFolder = new File(".\\java\\src\\plugin\\jars");

        //Parse the command line arguments - with JCommander you can access command line arguments like this ... arguments.port
        ArgParse arguments = new ArgParse();
        new JCommander(arguments, args);


        //Create the new Guice injector
        Injector injector = Guice.createInjector(new PersistantModule());

        //Get an instance of the pluginClassRepresentation, this with either be bound to the requested plugin OR a default class ... later to be bound to IPerPlug
        IPluginClass plugType = injector.getInstance(IPluginClass.class);


        //assign command line arguments
        if(arguments.port != 8081)
            SERVER_PORT_NUMBER = arguments.port;

        //if a plugin argument is present
        if(!arguments.pluginID.equals("")){
            //search the fixed plugin folder for the pluginID
            try {
                //Grab all the files in the plugin folder
                File[] pluginFiles = jarFolder.listFiles();

                //Check to make sure it's not empty
                if(pluginFiles != null){
                    //Iterate through all the files looking for the file matching the pluginID
                    for(File current : jarFolder.listFiles()){
                        //If we find a file with a name matching the requested plugin
                        if(current.getName().equals(arguments.pluginID + ".jar")){
                            //Bind the plugin
                            URL pluginURL = current.toURI().toURL();
                            URLClassLoader classLoader = new URLClassLoader(new URL[]{pluginURL}, this.getClass().getClassLoader());
                            Class pluginClass = classLoader.loadClass(arguments.pluginID);
                            plugType.setClassType(pluginClass);
                            System.out.println("PLUGIN BOUND: " + current.getName());
                        }
                    }

                    //if no plugin was bound
                    if(plugType.getClassType() == null){
                        System.out.println("PLUGIN WAS NOT FOUND - EMPTY PLUGIN BOUND INSTEAD");
                        plugType.setClassType(EmptyPlugin.class);
                    }
                }

            } catch (ClassNotFoundException e) {
                System.out.println("PLUGIN FAILED TO LOAD - ClassNotFoundException");
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        
        IPersistencePlugin plugin = injector.getInstance(IPersistencePlugin.class);
        plugin.thaw();

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

//        logger.info("Starting HTTP Server");

        server.start();
    }

    public class ArgParse{

        @Parameter(names = {"-p", "--port"}, description = "used to specify a custom port for the server to run on")
        private int port = 8081;

        @Parameter(names = {"-u", "--plugin"}, description = "used to specify a plugin for the persistent database, JAR files only, don't include the file extension in the argument")
        private String pluginID = "";

        @Parameter(names = {"-?", "--help"}, help = true)
        private boolean help;
    }


}

