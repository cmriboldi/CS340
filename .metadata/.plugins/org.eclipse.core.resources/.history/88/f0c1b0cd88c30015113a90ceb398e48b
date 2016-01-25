package serverProxy;

import java.util.List;

import model.resources.ResourceList;
import shared.communication.*;
import shared.definitions.*;
import shared.locations.*;

/**
 * The server interface that will be implemented by the real and mock server proxies
 * 
 * @author Christian Riboldi
 * @author Clayton Condie
 * @author Jacob Brewer
 * @author Joshua Powers
 * @author Joshua Van Steeter
 * @version 1.0 Build Winter 2016.
 */
public interface ServerProxy
{
	/**
	 * Validates the player's credentials, and logs them in to the server (i.e., sets their
	 * catan.user HTTP cookie)
	 * 
	 * @param username Catan account username
	 * @param password Catan account password
	 * @return True if login was successful or false if an error occcurred.
	 */
	public boolean userLogin(String username, String password);

	/**
	 * Creates a new player account, and logs them in to the server (i.e., sets their catan.user
	 * HTTP cookie)
	 * 
	 * @param username Catan account username
	 * @param password Catan account password
	 * @return True if registration was successful or false if an error occurred.
	 */
	public boolean userRegister(String username, String password);

	/**
	 * Get a list of all games in progress.
	 * 
	 * @return a list of game communication objects of available games
	 */
	public List<CommGame> listGames();

	/**
	 * Creates a new game.
	 * 
	 * @param randomTiles Whether the tiles should be randomly placed.
	 * @param randomNumbers Whether the numbers should be randomly placed.
	 * @param randomPorts Whether the ports should be randomly placed.
	 * @param name The name of the game.
	 * @return A game communication object of the newly created game.
	 */
	public CommGame createGame(boolean randomTiles, boolean randomNumbers, boolean randomPorts, String name);

	/**
	 * Adds (or re-adds) the player to the specified game, and sets their catan.game HTTP cookie
	 * 
	 * @param gameId The ID of the game to join.
	 * @param color ['red' or 'green' or 'blue' or 'yellow' or 'puce' or 'brown' or 'white' or
	 *            'purple' or 'orange']: What color you want to join (or rejoin) as.
	 * @return True if joining the game was successful, false if an error occurred.
	 */
	public boolean joinGame(int gameId, CatanColor color);

	/**
	 * Saves the current state of the specified game to a file. This method is for testing and
	 * debugging purposes. When a bug is found, you can use the /games/save method to save the state
	 * of the game to a file, and attach the file to a bug report. A developer can later restore the
	 * state of the game when the bug occurred by loading the previously saved file using the
	 * /games/load method. Game files are saved to and loaded from the server's saves/ directory.
	 * 
	 * @param gameId The ID of the game to save.
	 * @param fileName The file name you want to save it under.
	 * @return True if save is successful, false if an error occurred.
	 */
	public boolean saveGame(int gameId, String fileName);

	/**
	 * Loads a previously saved game file to restore the state of a game. This method is for testing
	 * and debugging purposes. When a bug is found, you can use the /games/save method to save the
	 * state of the game to a file, and attach the file to a bug report. A developer can later
	 * restore the state of the game when the bug occurred by loading the previously saved file
	 * using the /games/load method. Game files are saved to and loaded from the server's saves/
	 * directory.
	 * 
	 * @param fileName The name of the saved game file that you want to load. (The game's ID is
	 *            restored as well.)
	 * @return True if load is successful, false if an error occurred.
	 */
	public boolean loadGame(String fileName);

	/**
	 * Returns the current state of the game in JSON format. You must login and join a game before
	 * calling this method.
	 * 
	 * @param modelNumber The version number of the model that the caller already has. It goes up by
	 *            one for each command that is applied. If you send this parameter, you will get a
	 *            model back only if the current model is newer than the specified version number.
	 *            Otherwise, it returns the string "true" to notify the caller that it already has
	 *            the current model state.
	 * @return The current state of the game in a game model JSON object
	 */
	public GameModelJSON getGameModel(int modelNumber);

	/**
	 * Clears out the command history of the current game For the default games created by the
	 * server, this method reverts the game to the state immediately after the initial placement
	 * round. For user-created games, this method reverts the game to the very beginning (i.e.,
	 * before the initial placement round). This method returns the client model JSON for the game
	 * after it has been reset. You must login and join a game before calling this method.
	 * 
	 * @return The 'Client Model' JSON (identical to getGameModel())
	 */
	public GameModelJSON resetGame();

	/**
	 * Executes the specified command list in the current game. This method can be used for testing
	 * and debugging. Typically, the command list passed in was previously obtained by calling the
	 * /game/command (GET) method. This method returns the client model JSON for the game after the
	 * command list has been applied. You must login and join a game before calling this method.
	 * 
	 * @param commands The list of commands to be executed
	 * @return The 'Client Model' JSON (identical to getGameModel())
	 */
	public GameModelJSON setCommands(List<Command> commands);

	/**
	 * Returns a list of commands that have been executed in the current game. This method can be
	 * used for testing and debugging. The command list returned by this method can be passed to the
	 * /game/command (POST) method. For the default games created by the server, this method returns
	 * a list of all commands that have been executed after the initial placement round. For
	 * user-created games, this method returns a list of all commands that have been executed in the
	 * game since the very beginning (i.e., before the initial placement round). You must login and
	 * join a game before calling this method.
	 * 
	 * @return Returns the list of Commands that have occurred since the game was initialized
	 */
	public List<Command> getCommands();

	/**
	 * Adds an AI player to the current game. You must login and join a game before calling this
	 * method.
	 * 
	 * @param AIType The type of AI player to add (currently, LARGEST_ARMY is the only supported
	 *            type). Only strings returned by /game/listAI are valid.
	 * @return True if method was successful, false if an error occured.
	 */
	public boolean addAI(String AIType);

	/**
	 * Returns a list of supported AI player types (currently, LARGEST_ARMY is the only supported
	 * type).
	 * 
	 * @return A list of support AI player types
	 */
	public List<String> listAI();

	/**
	 * Sends a chat message.
	 * 
	 * @param playerIndex Who's sending this chat message.
	 * @param content The message to be posted
	 * @return The 'Client Model' JSON (identical to getGameModel())
	 */
	public GameModelJSON sendChat(int playerIndex, String content);

	/**
	 * @param playerIndex Who's sending this command (0-3).
	 * @param number What number was rolled (2-12).
	 * @return The 'Client Model' JSON (identical to getGameModel()))
	 */
	public GameModelJSON rollNumber(int playerIndex, int number);

	/**
	 * @param playerIndex Who's doing the robbing
	 * @param victimIndex The order index of the player to rob.
	 * @param hexLocation The new HexLocation of the robber
	 * @return The 'Client Model' JSON (identical to getGameModel())
	 */
	public GameModelJSON robPlayer(int playerIndex, int victimIndex, HexLocation hexLocation);

	/**
	 * Used to finish your turn.
	 * 
	 * @param playerIndex Who's sending this command (0-3)
	 * @return The 'Client Model' JSON (identical to getGameModel())
	 */
	public GameModelJSON finishTurn(int playerIndex);

	/**
	 * Used to buy a development card.
	 * 
	 * @param playerIndex Who's buying this dev card
	 * @return The 'Client Model' JSON (identical to getGameModel())
	 */
	public GameModelJSON buyDevCard(int playerIndex);

	/**
	 * Plays a 'Year of Plenty' card from your hand to gain the two specified ResourceTypes.
	 * 
	 * @param playerIndex Who's playing this dev card
	 * @param resource1
	 * @param resource2
	 * @return The 'Client Model' JSON (identical to getGameModel())
	 */
	public GameModelJSON yearOfPlenty(int playerIndex, ResourceType resource1, ResourceType resource2);

	/**
	 * Plays a 'Road Building' card from your hand to build two roads at the specified HexLocations.
	 * 
	 * @param playerIndex Who's playing this dev card
	 * @param spot1 Edge to build a road on
	 * @param spot2 Edge to build a road on
	 * @return The 'Client Model' JSON (identical to getGameModel())
	 */
	public GameModelJSON roadBuilding(int playerIndex, EdgeLocation spot1, EdgeLocation spot2);

	/**
	 * Plays a 'Soldier' from your hand, selecting the new robber position and player to rob.
	 * 
	 * @param playerIndex Who's playing this dev card
	 * @param victimIndex The index of the player to rob.
	 * @param hexLocation The new HexLocation of the robber
	 * @return The 'Client Model' JSON (identical to getGameModel())
	 */
	public GameModelJSON soldier(int playerIndex, int victimIndex, HexLocation hexLocation);

	/**
	 * Plays a 'Monopoly' card from your hand to monopolize the specified ResourceType.
	 * 
	 * @param playerIndex Who's playing this dev card
	 * @param resource The ResourceType that all players must give to the player who played the dev
	 *            card
	 * @return The 'Client Model' JSON (identical to getGameModel())
	 */
	public GameModelJSON monopoly(int playerIndex, ResourceType resource);

	/**
	 * Plays a 'Monument' card from your hand to give you a victory point.
	 * 
	 * @param playerIndex Who's playing this dev card
	 * @return The 'Client Model' JSON (identical to getGameModel())
	 */
	public GameModelJSON monument(int playerIndex);

	/**
	 * Builds a road at the specified HexLocation. (Set 'free' to true during initial setup.)
	 * 
	 * @param playerIndex Who's placing the road
	 * @param roadLocation Where the road will be placed
	 * @param free Whether this is placed for free (setup).
	 * @return The 'Client Model' JSON (identical to getGameModel())
	 */
	public GameModelJSON buildRoad(int playerIndex, EdgeLocation roadLocation, boolean free);

	/**
	 * Builds a settlement at the specified HexLocation. (Set 'free' to true during initial setup.)
	 * 
	 * @param playerIndex Who's placing the settlement
	 * @param vertexLocation Where the settlement will be placed.
	 * @param free Whether this is placed for free (setup).
	 * @return The 'Client Model' JSON (identical to getGameModel())
	 */
	public GameModelJSON buildSettlement(int playerIndex, VertexLocation vertexLocation, boolean free);

	/**
	 * Builds a city at the specified HexLocation.
	 * 
	 * @param playerIndex Who's placing the city
	 * @param vertexLocation
	 * @return The 'Client Model' JSON (identical to getGameModel())
	 */
	public GameModelJSON buildCity(int playerIndex, VertexLocation vertexLocation);

	/**
	 * Offers a domestic trade to another player.
	 * 
	 * @param playerIndex Who's sending the offer
	 * @param receiver Who you're offering the trade to
	 * @param offer What you get(+) and what you give (-)
	 * @return The 'Client Model' JSON (identical to getGameModel())
	 */
	public GameModelJSON offerTrade(int playerIndex, int receiver, ResourceList offer);

	/**
	 * Used to accept or reject a trade offered to you.
	 * 
	 * @param playerIndex Who's accepting/rejecting this trade.
	 * @param willAccept Whether you accept the trade or not.
	 * @return The 'Client Model' JSON (identical to getGameModel())
	 */
	public GameModelJSON acceptTrade(int playerIndex, boolean willAccept);

	/**
	 * Used to execute a maritime trade.
	 * 
	 * @param playerIndex Who's doing the trading
	 * @param ratio The ratio of the trade you're doing as an integer
	 * @param input What type of ResourceType you're giving
	 * @param output What type of ResourceType you're receiving
	 * @return The 'Client Model' JSON (identical to getGameModel())
	 */
	public GameModelJSON maritimeTrade(int playerIndex, int ratio, ResourceType input, ResourceType output);

	/**
	 * Discards the specified ResourceType cards.
	 * 
	 * @param playerIndex Who's discarding
	 * @param discardedCards List of ResourceTypes being discarded
	 * @return The 'Client Model' JSON (identical to getGameModel())
	 */
	public GameModelJSON discardCards(int playerIndex, ResourceList discardedCards);

	/**
	 * Sets the server's log level. (ALL, SEVERE, WARNING ,INFO, CONFIG, FINE, FINER, FINEST, OFF)
	 * 
	 * @param logLevel The level you want the server to log at
	 * @return True if the change was successful, false if an error occurred.
	 */
	public boolean changeLogLevel(LogLevel logLevel);
}
