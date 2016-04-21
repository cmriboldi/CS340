package server;

import java.io.File;
import java.io.IOException;
import java.net.*;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.sun.net.httpserver.*;

import jcommander.JCommander;
import jcommander.Parameter;
import plugin.*;
//import plugin.MongoDB.MongoClient;
import server.exception.ServerException;
import server.facade.IServerFacade;
import server.guice.PersistantModule;
import server.guice.PersistantMongoModule;
import server.guice.PersistantSQLModule;
import server.guice.VolatileRealModule;
import server.handler.*;

import com.mongodb.MongoClient;
import com.mongodb.DB;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

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
    	System.out.println("BEGIN SERVER RUN"); 
    	
    	MongoClient mongoclient = new MongoClient();

    	
        Injector injector = null;
        File jarFolder = new File(".\\java\\src\\plugin\\jars");

        //Parse the command line arguments - with JCommander you can access command line arguments like this ... arguments.port
        ArgParse arguments = new ArgParse();
        new JCommander(arguments, args);

        //assign command line arguments
        if(arguments.port != 8081)
            SERVER_PORT_NUMBER = arguments.port;

        //Case to use the locally defined SQL plugin
        if(arguments.localPluginID.equals("SQL")){
            injector = Guice.createInjector(new PersistantSQLModule());
            System.out.println("SYSTEM IS USING THE LOCAL SQL PLUGIN");
        }

        //Case to use the locally defined Mongo plugin
        if(arguments.localPluginID.equals("Mongo")){
        	System.out.println("SYSTEM IS USING THE LOCAL MONGO PLUGIN");
            injector = Guice.createInjector(new PersistantMongoModule());            
        }

        //Case for a loaded plugin
        if(arguments.pluginID != null){
            injector = Guice.createInjector(new PersistantModule());

            //Get an instance of the pluginClassRepresentation, this with either be bound to the requested plugin OR a default class ... later to be bound to IPerPlug
            IPluginClass plugType = injector.getInstance(IPluginClass.class);

            //if a plugin argument is present
            if(!arguments.pluginID.equals("")){
                //search the fixed plugin folder for the pluginID
                try {
                    System.out.println("SEARCHING FOR PLUGIN: <" + arguments.pluginID + "> in pluginFolder: " + jarFolder.getCanonicalPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    //Grab all the files in the plugin folder
                    File[] pluginFiles = jarFolder.listFiles();

                    //Check to make sure it's not empty
                    if(pluginFiles != null){
                        System.out.println("PLUGIN FOLDER IS NOT EMPTY");
                        //Iterate through all the files looking for the file matching the pluginID
                        for(File current : jarFolder.listFiles()){
                            System.out.println("\tSEARHCING FOR PLUGIN: " + current.getName());
                            //If we find a file with a name matching the requested plugin
                            if(current.getName().equals(arguments.pluginID + ".jar")){
                                //Bind the plugin
                                System.out.println("PLUGIN FOUND");

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
                    else
                    {
                        System.out.println("PLUGIN FOLDER IS EMPTY - BINDING VOLATILE DATABASE");
                        injector = Guice.createInjector(new VolatileRealModule());
                    }

                } catch (ClassNotFoundException e) {
                    System.out.println("PLUGIN FAILED TO LOAD - ClassNotFoundException");
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    System.out.println("PLUGIN FAILED TO LOAD - MalformedURLException");
                    e.printStackTrace();
                }
            }
        }

        if(injector == null)
            injector = Guice.createInjector(new VolatileRealModule());

        //set the checkInSize into the object the plugins will use to access the checkInSize
        IPluginData plugData = injector.getInstance(IPluginData.class);
        plugData.setCheckinSize(arguments.checkinSize);

        IPersistencePlugin plugin = injector.getInstance(IPersistencePlugin.class);

        if(arguments.clear == true)
            plugin.clear();

        plugin.thaw();

        //initialize the http handlers
        userHandler = injector.getInstance(UserHandler.class);
        gamesHandler = injector.getInstance(GamesHandler.class);
        gameHandler = injector.getInstance(GameHandler.class);
        movesHandler = injector.getInstance(MovesHandler.class);
        swaggerHandler = injector.getInstance(SwaggerHandler.class);

        IServerFacade facade = injector.getInstance(IServerFacade.class);

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
        private String pluginID = null;

        @Parameter(names = {"-?", "--help"}, help = true)
        private boolean help;

        @Parameter(names = {"--local"}, description = "used to specify a local plugin defined in the project classes")
        private String localPluginID = "";

        @Parameter(names = "-n", description = "used to specify how many commands to store in the persistant database in between model serializing")
        private int checkinSize = 10;

        @Parameter(names = "--clear", description = "clear the database?")
        private boolean clear = false;

    }


}

