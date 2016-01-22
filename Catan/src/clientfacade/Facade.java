package clientfacade;

import java.util.List;
import shared.communication.*;

public class Facade
{
	public Facade()
	{

	}

	/**
	 * Validates user credentials
	 * 
	 * @param userName
	 * @param password
	 * @return The authenticated User object
	 * @throws ClientException
	 */
	public CommUser authenticateUser()
	{
		return null;
	}

	/**
	 * Registers a new user
	 * 
	 * @param userName
	 * @param password
	 * @param passConfirm
	 * @return User object associated with the new registered user
	 * @throws ClientException
	 */
	public CommUser registerUser()
	{
		return null;
	}

	/**
	 * Generates a game list for the Client
	 * 
	 * @return List<Game>
	 * @throws ClientException
	 */
	public List<CommGame> listGames()
	{
		return null;
	}

	/**
	 * Creates a new game with a specific user
	 * 
	 * @param userName
	 * @return Game
	 * @throws ClientException
	 */
	public CommGame createGame()
	{
		return null;
	}

	/**
	 * Joins a new game from the game list
	 * 
	 * @param userName
	 * @return Game
	 * @throws ClientException
	 */
	public CommGame joinGame()
	{
		return null;
	}

	/**
	 * Generates a new map for a new game
	 * 
	 * @param randomNumbers
	 * @param randomHexes
	 * @param randomPorts
	 * @return The CatanMap object containing Map information
	 * @throws ClientException
	 */
	public void newMap(boolean randomNumbers, boolean randomHexes, boolean randomPorts)
	{
		
	}

	/**
	 * Set up a new player for a user. Creates association for user, player and color
	 * 
	 * @param userName
	 * @param color
	 * @return Game
	 * @throws ClientException
	 */
	public void newPlayer(String color)
	{

	}

	/**
	 * Get the current game model for the Client
	 * 
	 * @param userName
	 * @return Game
	 * @throws ClientException
	 */
	public CommGame getModel()
	{
		return null;
	}

	/**
	 * Advances a player through the phases of his turn, and updates a players options
	 * 
	 * @param userName
	 * @return
	 * @throws ClientException
	 */
	public void advancePhase()
	{

	}

	/**
	 * Advances the turn from one player to another, and updates a players options
	 * 
	 * @return
	 * @throws ClientException
	 */
	public void advanceTurn()
	{

	}

	/**
	 * Rolls the dice and determines resource allocations and robber usage
	 * 
	 * @return
	 * @throws ClientException
	 */
	public void roll()
	{

	}

	/**
	 * Allocates resource to the players
	 * 
	 * @param userName
	 * @return
	 * @throws ClientException
	 */
	public void generateResource()
	{

	}

	/**
	 * Handles the allocation of games resources for initial setup
	 * 
	 * @return
	 * @throws ClientException
	 */
	public void generateInitResources()
	{

	}

	/**
	 * Set up a trade between players
	 * 
	 * @param fromPlayer
	 * @param toPlayer
	 * @param toGive
	 * @param toAsk
	 * @return
	 * @throws ClientException
	 */
	public void domesticTrade()
	{

	}

	/**
	 * Trades a players resources through an available port
	 * 
	 * @param userName
	 * @param giving
	 * @param getting
	 * @return
	 * @throws ClientException
	 */
	public void portTrade()
	{

	}

	/**
	 * Chat a message to other players
	 * 
	 * @param userName
	 * @param message
	 * @return
	 * @throws ClientException
	 */
	public void chat(String message)
	{

	}

	/**
	 * Logs a players move in the log
	 * 
	 * @param userName
	 * @param message
	 * @return
	 * @throws ClientException
	 */
	public void log()
	{

	}

	/**
	 * Buys a development card for a specific player
	 * 
	 * @param userName
	 * @return True if successful False otherwise
	 * @throws ClientException
	 */
	public boolean buyDevCard()
	{
		return true;
	}

	/**
	 * Builds a road for a specific player
	 * 
	 * @param userName
	 * @return True if successful False otherwise
	 * @throws ClientException
	 */
	public boolean buildRoad()
	{
		return true;
	}

	/**
	 * Builds a town for a specific player
	 * 
	 * @param userName
	 * @return True if successful False otherwise
	 * @throws ClientException
	 */
	public boolean buildTown()
	{
		return true;
	}

	/**
	 * Builds a city for a specific player
	 * 
	 * @param userName
	 * @return True if successful False otherwise
	 * @throws ClientException
	 */
	public boolean puiltCity()
	{
		return true;
	}

	/**
	 * Places the robber in a hex specified by a specific player
	 * 
	 * @param userName
	 * @return True if successful False otherwise
	 * @throws ClientException
	 */
	public boolean placeRobber()
	{
		return true;
	}

}
