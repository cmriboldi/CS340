package server.facade;

import client.data.GameInfo;
import model.CatanModel;
import server.AuthToken;
import server.exception.FacadeNotInitializedException;
import server.exception.ServerException;
import shared.definitions.CatanColor;

/**
 * The FacadeProxy is the static access object to whatever facade is defined for this build
 *
 * @author Christian Riboldi
 * @author Clayton Condie
 * @author Jacob Brewer
 * @author Joshua Powers
 * @author Joshua Van Steeter
 * @version 1.0 Build Winter 2016.
 */
public class FacadeHolder
{
    private static FacadeHolder _instance;
    private static IServerFacade facade;

    private FacadeHolder(IServerFacade facade)
    {
        this.facade = facade;
    }

    private static FacadeHolder instance() throws ServerException
    {
        if(_instance == null)
            throw new FacadeNotInitializedException("Server Facade has not been initialized");
        return _instance;
    }

    /**
     * Initializes the Facade Proxy Singleton with the dependency injected facade
     *
     * @param facade The facade the server will use
     */
    public static void setFacade(IServerFacade facade)
    {
        _instance = new FacadeHolder(facade);
    }

    public static IServerFacade getFacade() throws ServerException
    {
        return instance().facade;
    }

//    /**
//     * Logs the caller in to the server.
//     *
//     * @pre username != null
//     * @pre password != null
//     *
//     * @param username
//     * @param password
//     * @throws ServerException
//     */
//    public static void login(String username, String password) throws ServerException
//    {
//        instance().facade.login(username, password);
//    }
//
//    /**
//     * Creates a new user account and logs the caller into the server as the new user.
//     *
//     * @pre username != null
//     * @pre password != null
//     *
//     * @param username
//     * @param password
//     * @throws ServerException
//     */
//    public static void register(String username, String password) throws ServerException
//    {
//        instance().facade.register(username, password);
//    }
//
//    /**
//     * Returns information about all of the current games on the server.
//     *
//     * @return A list of games that the user can potentially join.
//     * @throws ServerException
//     */
//    public static GameInfo[] listGames() throws ServerException
//    {
//        return instance().facade.listGames();
//    }
//
//    /**
//     * Creates a new game on the server.
//     *
//     * @pre name != null
//     * @pre randomTiles.getType().equals(boolean.class)
//     * @pre randomNumbers.getType().equals(boolean.class)
//     * @pre randomPorts.getType().equals(boolean.class)
//     *
//     * @param randomTiles
//     * @param randomNumbers
//     * @param randomPorts
//     * @param name
//     * @return
//     * @throws ServerException
//     */
//    public static GameInfo createGame(boolean randomTiles, boolean randomNumbers, boolean randomPorts, String name) throws ServerException
//    {
//        return instance().facade.createGame(randomTiles, randomNumbers, randomPorts, name);
//    }
//
//    /**
//     * Adds the player to the specified game.
//     *
//     * @pre isValidId(token.getGameId)
//     * @pre isUserLoggedIn()
//     * @pre EnumUtils.isValidEnum(CatanColor.class, color)
//     * @pre isUserPartOfGame() || !isGameFull()
//     *
//     * @param token
//     * @param color
//     * @throws ServerException
//     */
//    public static void joinGame(AuthToken token, CatanColor color) throws ServerException
//    {
//        instance().facade.joinGame(token, color);
//    }
//
//    /**
//     * This method is for testing and debugging purposes. Saves the state of the game to a file, with by the given name.
//     *
//     * @pre isValidId(gameId)
//     * @pre !fileName.equals(null) && !filename.equals("")
//     *
//     * @param gameId
//     * @param fileName
//     */
//    public static void saveGame(int gameId, String fileName) throws ServerException
//    {
//        instance().facade.saveGame(gameId, fileName);
//    }
//
//    /**
//     * This method is for testing and debugging purposes. Loads the state of the game with the given name.
//     *
//     * @pre fileExists(fileName)
//     *
//     * @param fileName
//     */
//    public  static void loadGame(String fileName) throws ServerException
//    {
//        instance().facade.loadGame(fileName);
//    }
//
//    /**
//     * Gets the CatanModel given the AuthToken for the user.
//     *
//     * @pre The AuthToken needs to validate that the user has access to that game model.
//     *
//     * @param token The AuthToken containing the user authorization and game data.
//     * @return
//     * @throws ServerException
//     */
//    public static CatanModel getGameModel(AuthToken token) throws ServerException
//    {
//        return instance().facade.getGameModel(token);
//    }
//
//    /**
//     * Adds an AI player to the game given in the AuthToken.
//     *
//     * @pre partOfGame(token.getPlayerId)
//     *
//     * @param token
//     * @throws ServerException
//     */
//    public void addAI(AuthToken token) throws ServerException
//    {
//        instance().facade.addAI(token);
//    }
//
//    /**
//     * Returns a list of AI that are part of a given game.
//     *
//     * @pre partOfGame(token.getPlayerId)
//     *
//     * @param token
//     * @return
//     * @throws ServerException
//     */
//    public String[] listAI(AuthToken token) throws ServerException
//    {
//        return instance().facade.listAI(token);
//    }
}
