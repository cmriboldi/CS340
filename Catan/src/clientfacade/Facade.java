package clientfacade;

import java.util.List;

public class Facade 
{
	public Facade()
	{
		
	}
	
	/**
	 * Validates user credentials
	 * @param userName
	 * @param password
	 * @return The authenticated User object
	 * @throws ClientException 
	 */
	public User AuthenticateUser()
	{
		return null;
	}
	
	/**
	 * Registers a new user
	 * @param userName
	 * @param password
	 * @param passConfirm
	 * @return User object associated with the new registered user
	 * @throws ClientException 
	 */
	public User RegisterUser()
	{
		return null;
	}
	
	/**
	 * Generates a game list for the Client
	 * @return List<Game>
	 * @throws ClientException 
	 */
	public List<Game> ListGames()
	{
		return null;
	}
	
	/**
	 * Creates a new game with a specific user
	 * @param userName
	 * @return Game
	 * @throws ClientException 
	 */
	public Game CreateGame()
	{
		return null;
	}
	
	/**
	 * Joins a new game from the game list
	 * @param userName
	 * @return Game
	 * @throws ClientException 
	 */
	public Game JoinGame()
	{
		return null;
	}
	
	/**
	 * Generates a new map for a new game
	 * @param randomNumbers
	 * @param randomHexes
	 * @param randomPorts
	 * @return The CatanMap object containing Map information
	 * @throws ClientException 
	 */
	public CatanMap NewMap(boolean randomNumbers, boolean randomHexes, boolean randomPorts)
	{
		return null;
	}
	
	/**
	 * Set up a new player for a user. Creates association for user, player and color
	 * @param userName
	 * @param color
	 * @return Game
	 * @throws ClientException 
	 */
	public void NewPlayer(String color)
	{
		
	}
	
	/**
	 * Get the current game model for the Client
	 * @param userName
	 * @return Game
	 * @throws ClientException 
	 */
	public Game GetModel()
	{
		return null;
	}
	
	/**
	 * Advances a player through the phases of his turn, and updates a players options
	 * @param userName
	 * @return
	 * @throws ClientException 
	 */
	public void AdvancePhase()
	{
		
	}
	
	/**
	 * Advances the turn from one player to another, and updates a players options
	 * @return
	 * @throws ClientException 
	 */
	public void AdvanceTurn()
	{
		
	}
	
	/**
	 * Rolls the dice and determines resource allocations and robber usage
	 * @return
	 * @throws ClientException 
	 */
	public void Roll()
	{
		
	}
	
	/**
	 * Allocates resource to a player
	 * @param userName
	 * @return
	 * @throws ClientException 
	 */
	public void AllocateResource()
	{
		
	}
	
	/**
	 * Handles the allocation of games resources for initial setup
	 * @return
	 * @throws ClientException 
	 */
	public void AllocateInitResource()
	{
		
	}
	
	/**
	 * Set up a trade between players
	 * @param fromPlayer
	 * @param toPlayer
	 * @param toGive
	 * @param toAsk
	 * @return
	 * @throws ClientException 
	 */
	public void DomesticTrade()
	{
		
	}
	
	/**
	 * Trades a players resources through an available port
	 * @param userName
	 * @param giving
	 * @param getting
	 * @return
	 * @throws ClientException 
	 */
	public void MaritimeTrade()
	{
		
	}
	
	/**
	 * Chat a message to other players
	 * @param userName
	 * @param message
	 * @return
	 * @throws ClientException 
	 */
	public void Chat(String message)
	{
		
	}
	
	/**
	 * Logs a players move in the log
	 * @param userName
	 * @param message
	 * @return
	 * @throws ClientException 
	 */
	public void Log()
	{
		
	}
	
	/**
	 * Buys a development card for a specific player
	 * @param userName
	 * @return True if successful False otherwise
	 * @throws ClientException 
	 */
	public boolean BuyDevCard()
	{
		return true;
	}
	
	/**
	 * Builds a road for a specific player
	 * @param userName
	 * @return True if successful False otherwise
	 * @throws ClientException 
	 */
	public boolean BuildRoad()
	{
		return true;
	}
	
	/**
	 * Builds a town for a specific player
	 * @param userName
	 * @return True if successful False otherwise
	 * @throws ClientException 
	 */
	public boolean BuildTown()
	{
		return true;
	}
	
	/**
	 * Builds a city for a specific player
	 * @param userName
	 * @return True if successful False otherwise
	 * @throws ClientException 
	 */
	public boolean BuiltCity()
	{
		return true;
	}
	
	/**
	 * Places the robber in a hex specified by a specific player
	 * @param userName
	 * @return True if successful False otherwise
	 * @throws ClientException 
	 */
	public boolean PlaceRobber()
	{
		return true;
	}
	
	
}

