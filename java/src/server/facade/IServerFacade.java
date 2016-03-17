package server.facade;

import client.data.GameInfo;
import model.CatanModel;
import server.AuthToken;
import server.command.ICommand;
import server.database.IDatabase;
import server.exception.ServerException;
import shared.definitions.CatanColor;

/**
 * The IServerFacade interface defines the class that when implemented controls all interactions between the command classes, the handlers, and the database. 
 *
 * @author Christian Riboldi
 * @author Clayton Condie
 * @author Jacob Brewer
 * @author Joshua Powers
 * @author Joshua Van Steeter
 * @version 1.0 Build Mar, 2016.
 */
public interface IServerFacade
{
    /**
     * Logs the caller in to the server.
     * 
     * @pre username != null
     * @pre password != null
     * 
     * @param username
     * @param password
     * @throws ServerException
     */
    String login(String username, String password) throws ServerException;

    /**
     * Creates a new user account and logs the caller into the server as the new user.
     * 
     * @pre username != null
     * @pre password != null
     * 
     * @param username
     * @param password
     * @throws ServerException
     */
    String register(String username, String password) throws ServerException;

    /**
     * Returns information about all of the current games on the server.
     * 
     * @return A list of games that the user can potentially join.
     * @throws ServerException
     */
    GameInfo[] listGames() throws ServerException;

    /**
     * Creates a new game on the server.
     * 
     * @pre name != null
     * @pre randomTiles.getType().equals(boolean.class)
     * @pre randomNumbers.getType().equals(boolean.class)
     * @pre randomPorts.getType().equals(boolean.class)
     * 
     * @param randomTiles
     * @param randomNumbers
     * @param randomPorts
     * @param name
     * @return
     * @throws ServerException
     */
    GameInfo createGame(boolean randomTiles, boolean randomNumbers, boolean randomPorts, String name) throws ServerException;

    /**
     * Adds the player to the specified game.
     * 
     * @pre isValidId(token.getGameId)
     * @pre isUserLoggedIn()
     * @pre EnumUtils.isValidEnum(CatanColor.class, color)
     * @pre isUserPartOfGame() || !isGameFull()
     * 
     * @param token The user token that authenticates them to join the game
     * @param gameId The unique id of the game they are joining
     * @param color the CatanColor they are joining the game as
     * @throws ServerException
     */
	String joinGame(AuthToken token, int gameId, CatanColor color) throws ServerException;
    
    /**
     * This method is for testing and debugging purposes. Saves the state of the game to a file, with by the given name.
     * 
     * @pre isValidId(gameId)
     * @pre !fileName.equals(null) && !filename.equals("") 
     * 
     * @param gameId
     * @param fileName
     */
    void saveGame(int gameId, String fileName);
    
    /**
     * This method is for testing and debugging purposes. Loads the state of the game with the given name.
     * 
     * @pre fileExists(fileName)
     * 
     * @param fileName
     */
    void loadGame(String fileName);

    /**
     * Gets the CatanModel given the AuthToken for the user.
     * 
     * @pre The AuthToken needs to validate that the user has access to that game model.
     * 
     * @param token The AuthToken containing the user authorization and game data.
     * @return
     * @throws ServerException
     */
    CatanModel getGameModel(AuthToken token) throws ServerException;

    void updateGame(AuthToken token, CatanModel model) throws ServerException;

    /**
     * Adds an AI player to the game given in the AuthToken.
     * 
     * @pre partOfGame(token.getPlayerId)
     * 
     * @param token
     * @throws ServerException
     */
    void addAI(AuthToken token) throws ServerException;

    /**
     * Returns a list of AI that are part of a given game.
     * 
     * @pre partOfGame(token.getPlayerId)
     * 
     * @param token
     * @return
     * @throws ServerException
     */
    String[] listAI(AuthToken token) throws ServerException;

    boolean isValidUser(AuthToken token) throws ServerException;
}
