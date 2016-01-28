package clientfacade;

import java.util.List;

import model.CatanModel;
import model.resources.ResourceList;
import shared.communication.*;

/**
 * The Facade class controls all interations between the GUI and the CatanModel
 * 
 * @author Christian Riboldi
 * @author Clayton Condie
 * @author Jacob Brewer
 * @author Joshua Powers
 * @author Joshua Van Steeter
 * @version 1.0 Build Jan, 2016.
 */
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
	 * @throws InvalidCredentials
	 */
	public CommUser authenticateUser(String userName, String password)
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
	 * @throws ServerException
	 */
	public CommUser registerUser(String userName, String password, String passConfirm)
	{
		return null;
	}

	/**
	 * Generates a game list for the Client
	 * 
	 * @return A list of all the current games
	 * @throws ServerException
	 */
	public List<CommGame> listGames()
	{
		return null;
	}

	/**
	 * Creates a new game with a specific user
	 * 
	 * @param userName
	 * @return
	 * @throws ServerException
	 */
	public void createGame(String userName)
	{
		
	}

	/**
	 * Joins a new game from the game list
	 * 
	 * @param userName
	 * @return The CatanModel of the game
	 * @throws ServerException
	 */
	public CatanModel joinGame(String userName)
	{
		return null;
	}

	/**
	 * Generates a new map for a new game
	 * 
	 * @param randomNumbers
	 * @param randomHexes
	 * @param randomPorts
	 * @return
	 * @throws ServerException
	 */
	public void newMap(boolean randomNumbers, boolean randomHexes, boolean randomPorts)
	{

	}

	/**
	 * Get the current game model for the Client
	 * 
	 * @return The current CatanModel of the game
	 * @throws ServerException
	 */
	public CatanModel getModel()
	{
		return null;
	}

	/**
	 * Advances a player through the phases of his turn, and updates a players options
	 * 
	 * @param Index of player
	 * @return
	 * @throws ServerException
	 */
	public void advancePhase(int index)
	{

	}

	/**
	 * Advances the turn from one player to another, and updates a players options
	 * 
	 * @return
	 * @throws ServerException
	 */
	public void advanceTurn()
	{

	}

	/**
	 * Rolls the dice and determines resource allocations and robber usage
	 * 
	 * @return
	 * @throws ServerException
	 */
	public void roll()
	{

	}

	/**
	 * Set up a trade between players
	 * 
	 * @param index of trading player
	 * @param index of tradee player
	 * @param List of giving resources
	 * @param List of asking resources
	 * @return
	 * @throws ServerException
	 */
	public void domesticTrade(int traderIndex, int tradeeIndex, ResourceList toGive, ResourceList toAsk)
	{

	}

	/**
	 * Trades a players resources through an available port
	 * 
	 * @param index of player trading
	 * @param List of giving resources
	 * @param List of getting resources
	 * @return
	 * @throws ServerException
	 */
	public void portTrade(int index, ResourceList toGive, ResourceList toGet)
	{

	}

	/**
	 * Chat a message to other players
	 * 
	 * @param message
	 * @return
	 * @throws ServerException
	 */
	public void chat(String message)
	{

	}

	/**
	 * Logs a players move in the log
	 * 
	 * @param index of player taking action
	 * @param message
	 * @return
	 * @throws ServerException
	 */
	public void log(int index, String message)
	{

	}

	/**
	 * Buys a development card for a specific player
	 * 
	 * @param index of player buying dev card
	 * @return
	 * @throws InvalidAction
	 */
	public void buyDevCard(int index)
	{
		
	}

	/**
	 * Builds a road for a specific player
	 * 
	 * @param index of player building road
	 * @return
	 * @throws InvalidAction
	 */
	public void buildRoad(int index)
	{
		
	}

	/**
	 * Builds a town for a specific player
	 * 
	 * @param index of player building town
	 * @return
	 * @throws InvalidAction
	 */
	public void buildTown(int index)
	{
		
	}

	/**
	 * Builds a city for a specific player
	 * 
	 * @param index of player building city
	 * @return
	 * @throws InvalidAction
	 */
	public void buildCity(int index)
	{
		
	}

	/**
	 * Places the robber in a hex specified by a specific player
	 * 
	 * @param index of player using robber
	 * @return
	 * @throws InvalidAction
	 */
	public void placeRobber(int index)
	{
		
	}

}
