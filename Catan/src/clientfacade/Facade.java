package clientfacade;

import java.util.List;

import model.CatanModel;
import model.resources.ResourceList;
import serverProxy.JSONDeserializer;
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
	private static Facade _instance;
	private Facade(){}
	private static Facade instance() {
		
		if (_instance == null)
			_instance = new Facade();

		return _instance;
	}
	
	
	private CatanModel catanModel;
	
	
	private void _updateView(CatanModel catanModel)
	{
		this.catanModel = catanModel;
	}	
	public static void updateView(CatanModel catanModel)
	{
		instance()._updateView(catanModel);
	}
	
	
	private CatanModel _getCatanModel()
	{
		return this.catanModel;
	}	
	public static CatanModel getCatanModel()
	{
		return instance()._getCatanModel();
	}
	
	/**
	 * Validates user credentials
	 * 
	 * @param userName
	 * @param password
	 * @return The authenticated User object
	 * @throws InvalidCredentials
	 */
	private CommUser _authenticateUser(String userName, String password)
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
	private CommUser _registerUser(String userName, String password, String passConfirm)
	{
		return null;
	}

	/**
	 * Generates a game list for the Client
	 * 
	 * @return A list of all the current games
	 * @throws ServerException
	 */
	private List<CommGame> _listGames()
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
	private void _createGame(String userName)
	{
		
	}

	/**
	 * Joins a new game from the game list
	 * 
	 * @param userName
	 * @return The CatanModel of the game
	 * @throws ServerException
	 */
	private CatanModel _joinGame(String userName)
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
	private void _newMap(boolean randomNumbers, boolean randomHexes, boolean randomPorts)
	{

	}

	/**
	 * Get the current game model for the Client
	 * 
	 * @return The current CatanModel of the game
	 * @throws ServerException
	 */
	private CatanModel _getModel()
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
	private void _advancePhase(int index)
	{

	}

	/**
	 * Advances the turn from one player to another, and updates a players options
	 * 
	 * @return
	 * @throws ServerException
	 */
	private void _advanceTurn()
	{

	}

	/**
	 * Rolls the dice and determines resource allocations and robber usage
	 * 
	 * @return
	 * @throws ServerException
	 */
	private void _roll()
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
	private void _domesticTrade(int traderIndex, int tradeeIndex, ResourceList toGive, ResourceList toAsk)
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
	private void _portTrade(int index, ResourceList toGive, ResourceList toGet)
	{

	}

	/**
	 * Chat a message to other players
	 * 
	 * @param message
	 * @return
	 * @throws ServerException
	 */
	private void _chat(String message)
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
	private void _log(int index, String message)
	{

	}

	/**
	 * Buys a development card for a specific player
	 * 
	 * @param index of player buying dev card
	 * @return
	 * @throws InvalidAction
	 */
	private void _buyDevCard(int index)
	{
		
	}

	/**
	 * Builds a road for a specific player
	 * 
	 * @param index of player building road
	 * @return
	 * @throws InvalidAction
	 */
	private void _buildRoad(int index)
	{
		
	}

	/**
	 * Builds a town for a specific player
	 * 
	 * @param index of player building town
	 * @return
	 * @throws InvalidAction
	 */
	private void _buildTown(int index)
	{
		
	}

	/**
	 * Builds a city for a specific player
	 * 
	 * @param index of player building city
	 * @return
	 * @throws InvalidAction
	 */
	private void _buildCity(int index)
	{
		
	}

	/**
	 * Places the robber in a hex specified by a specific player
	 * 
	 * @param index of player using robber
	 * @return
	 * @throws InvalidAction
	 */
	private void _placeRobber(int index)
	{
		
	}

}
