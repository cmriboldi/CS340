package clientfacade;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import client.data.GameInfo;
import client.data.PlayerInfo;
import model.CatanModel;
import model.options.Options;
import model.players.Player;
import model.resources.ResourceList;
import serverProxy.Poller;
import serverProxy.RealProxy;
import serverProxy.ServerException;
import shared.definitions.CatanColor;
import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import shared.exceptions.player.PlayerNameNotFoundException;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;


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
public class Facade extends Observable {
    private static Facade _instance;

    private CatanModel catanModel;
    private RealProxy proxy;


    //**********************************************************************************
    // Constructors
    //**********************************************************************************
    private Facade() {
        this.proxy = new RealProxy();
    }

    private static Facade instance() {

        if (_instance == null)
            _instance = new Facade();

        return _instance;
    }

    private CatanModel _getCatanModel() {
        return this.catanModel;
    }

    public static CatanModel getCatanModel() {
        return instance()._getCatanModel();
    }

    private RealProxy _getProxy() {
        return this.proxy;
    }

    public static RealProxy getProxy() {
        return instance()._getProxy();
    }

    //**********************************************************************************
    // Observable Functions
    //**********************************************************************************
    private void _updateView() {
        if (this.catanModel != null) {
            this.setChanged();
            this.notifyObservers();
        }
    }

    private void _setView(CatanModel catanModel) {
        this.catanModel = catanModel;
        this._updateView();
    }

    public static void setView(CatanModel catanModel) {
        instance()._setView(catanModel);
    }

    private void _addObserver(Observer obs) {
        this.addObserver(obs);
    }

    public static void addObserverStatic(Observer obs) {
        instance()._addObserver(obs);
    }

    //**********************************************************************************
    // Chat Functions
    //**********************************************************************************

    /**
     * Chat a message to other players
     *
     * @param message
     * @throws ServerException
     */
    private void _chat(String message) throws ServerException {
        this._setView(this.proxy.sendChat(this._getLocalPlayerIndex(), message));
    }

    public static void chat(String message) throws ServerException {
        instance()._chat(message);
    }

    /**
     * @return
     */
    private List<String> _getChatMessages() {
        return this.catanModel.chatManager.chatMessages();
    }

    public static List<String> getChatMessages() {
        return instance()._getChatMessages();
    }

    /**
     * @return
     */
    private List<String> _getChatSources() {
        return this.catanModel.chatManager.chatSources();
    }

    public static List<String> getChatSources() {
        return instance()._getChatSources();
    }

    /**
     * @return
     */
    private List<String> _getHistoryMessages() {
        return this.catanModel.chatManager.historyMessages();
    }

    public static List<String> getHistoryMessages() {
        return instance()._getHistoryMessages();
    }

    /**
     * @return
     */
    private List<String> _getHistorySources() {
        return this.catanModel.chatManager.historySources();
    }

    public static List<String> getHistorySources() {
        return instance()._getHistorySources();
    }

    /**
     * @param name
     * @return
     * @throws PlayerNameNotFoundException
     */
    private CatanColor _getColorByName(String name) throws PlayerNameNotFoundException {
        return this.catanModel.playerManager.getPlayerColor(name);
    }

    public static CatanColor getColorByName(String name) throws PlayerNameNotFoundException {
        return instance()._getColorByName(name);
    }

    //**********************************************************************************
    // Map Interaction Functions
    //**********************************************************************************

    /**
     * Builds a road for a specific player
     *
     * @param index of player building road
     * @throws ServerException
     */
    //PRIVATE METHOD
    private void _buildRoad(int index, EdgeLocation edge, boolean free) throws ServerException {
        this._setView(this.proxy.buildRoad(index, edge, free));
    }

    //PUBLIC METHOD
    public static void buildRoad(int index, EdgeLocation edge, boolean free) throws ServerException {
        instance()._buildRoad(index, edge, free);
    }

    /**
     * Builds a town for a specific player
     *
     * @throws ServerException
     */
    //PRIVATE METHOD
    private void _buildTown(int playerIndex, VertexLocation vert, boolean free) throws ServerException {
        this._setView(this.proxy.buildSettlement(playerIndex, vert, free));

    }

    //PUBLIC METHOD
    public static void buildTown(int playerIndex, VertexLocation vertex, boolean free) throws ServerException {
        instance()._buildTown(playerIndex, vertex, free);
    }


    /**
     * Builds a city for a specific player
     *
     * @throws ServerException
     */
    //PRIVATE METHOD
    private void _buildCity(int playerIndex, VertexLocation vertex) throws ServerException {
        this._setView(this.proxy.buildCity(playerIndex, vertex));

    }

    //PUBLIC METHOD
    public static void buildCity(int playerIndex, VertexLocation vertex) throws ServerException {
        instance()._buildCity(playerIndex, vertex);
    }


    //**********************************************************************************
    // Game Phase Control
    //**********************************************************************************

    /**
     * Generates a new map for a new game
     *
     * @param randomNumbers
     * @param randomHexes
     * @param randomPorts
     * @throws ServerException
     */
    private void _newMap(boolean randomNumbers, boolean randomHexes, boolean randomPorts) {

    }


    /**
     * Get the current game model for the Client
     *
     * @return The current CatanModel of the game
     * @throws ServerException
     */
    private CatanModel _getModel() {
        return null;
    }


    /**
     * Advances a player through the phases of his turn, and updates a players options
     *
     * @param index of player
     * @throws ServerException
     */
    private void _advancePhase(int index) {

    }

    /**
     * Advances the turn from one player to another, and updates a players options
     *
     * @throws ServerException
     */
    private void _advanceTurn() {

    }

    /**
     * Rolls the dice and determines resource allocations and robber usage
     *
     * @return
     * @throws ServerException
     */
    private int _roll() throws ServerException {
        int maximum = 100;
        int die1 = (int) (Math.random() * maximum);
        int die2 = (int) (Math.random() * maximum);
        die1 = (die1 % 6) + 1;
        die2 = (die2 % 6) + 1;
        int total = die1 + die2;

        this._setView(this.proxy.rollNumber(this._getLocalPlayerIndex(), total));
        return total;
    }

    public static int roll() throws ServerException {
        return instance()._roll();
    }
    
    private Boolean _isMyTurn()
    {
    	if(this.catanModel.playerManager.turnTracker.getTurnIndex() == this._getLocalPlayerIndex())
    		return true;
    	return false;
    }
    
    public static Boolean isMyturn()
    {
    	return instance()._isMyTurn();
    }
    
    private String _getTurnStatus()
    {
    	return this.catanModel.playerManager.turnTracker.getStatus();
    }
    
    public static String getTurnStatus()
	{
		return instance()._getTurnStatus();
	}
    
    private int _getResourceAmount(ResourceType type)
    {
    	return this.catanModel.resourceManager.getResourceCount(this._getLocalPlayerIndex(), type);
    }

	public static int getResourceAmount(ResourceType type)
	{
		return instance()._getResourceAmount(type);
	}
	
	private void _discard(int wood, int brick, int sheep, int wheat, int ore) throws ServerException
	{
		
		this._setView(this.proxy.discardCards(this._getLocalPlayerIndex(), new ResourceList(brick,ore,sheep,wheat,wood)));
	}

	public static void discard(int wood, int brick, int sheep, int wheat, int ore) throws ServerException
	{
		instance()._discard(wood, brick, sheep, wheat, ore);
		
	}


    //**********************************************************************************
    // Trade Functions
    //**********************************************************************************

    /**
     * Set up a trade between players
     *
     * @param traderIndex of trading player
     * @param tradeeIndex of tradee player
     * @param toGive      of giving resources
     * @param toAsk       of asking resources
     * @throws ServerException
     */
    private void _domesticTrade(int traderIndex, int tradeeIndex, ResourceList toGive, ResourceList toAsk) {


    }

    /**
     * Trades a players resources through an available port
     *
     * @param ratio  of player trading
     * @param toGive of giving resources
     * @param toGet  of getting resources
     * @return
     * @throws ServerException
     */
    private void _portTrade(int ratio, ResourceType toGive, ResourceType toGet) throws ServerException {
        this._setView(this.proxy.maritimeTrade(this._getLocalPlayerIndex(), ratio, toGive, toGet));
    }

    public static void portTrade(int ratio, ResourceType toGive, ResourceType toGet) throws ServerException {
        instance()._portTrade(ratio, toGive, toGet);
    }

    //**********************************************************************************
    // DevCardFunctions
    //**********************************************************************************

    /**
     * Buys a development card for a specific player
     *
     * @throws ServerException
     */
    private void _buyDevCard() throws ServerException {
        this._setView(this.proxy.buyDevCard(this._getLocalPlayerIndex()));
    }

    public static void buyDevCard() throws ServerException {
        instance()._buyDevCard();
    }

    private void _playMonumentCard() throws ServerException {
        this._setView(this.proxy.monument(this._getLocalPlayerIndex()));
    }

    public static void playMonumentCard() throws ServerException {
        instance()._playMonumentCard();
    }

    private void _playMonopolyCard(ResourceType resource) throws ServerException {
        this._setView(this.proxy.monopoly(this._getLocalPlayerIndex(), resource));
    }

    public static void playMonopolyCard(ResourceType resource) throws ServerException {
        instance()._playMonopolyCard(resource);
    }

    private void _playYearOfPlentyCard(ResourceType resource1, ResourceType resource2) throws ServerException {
        this._setView(this.proxy.yearOfPlenty(this._getLocalPlayerIndex(), resource1, resource2));
    }

    public static void playYearOfPlentyCard(ResourceType resource1, ResourceType resource2) throws ServerException {
        instance()._playYearOfPlentyCard(resource1, resource2);
    }

    private void _playRoadBuildCard(EdgeLocation spot1, EdgeLocation spot2) throws ServerException {
        this._setView(this.proxy.roadBuilding(this._getLocalPlayerIndex(), spot1, spot2));
    }

    public static void playRoadBuildCard(EdgeLocation spot1, EdgeLocation spot2) throws ServerException {
        instance()._playRoadBuildCard(spot1, spot2);
    }

    private void _playSoldierCard(int victimIndex, HexLocation hexLocation) throws ServerException {
        this._setView(proxy.soldier(this._getLocalPlayerIndex(), victimIndex, hexLocation));
    }

    public static void playSoldierCard(int victimIndex, HexLocation hexLocation) throws ServerException {
        instance()._playSoldierCard(victimIndex, hexLocation);
    }

    private List<DevCardType> _getPlayerDevTypes() {
        List<DevCardType> types = new ArrayList<DevCardType>();
        types.add(DevCardType.MONOPOLY);
        types.add(DevCardType.MONUMENT);
        types.add(DevCardType.ROAD_BUILD);
        types.add(DevCardType.SOLDIER);
        types.add(DevCardType.YEAR_OF_PLENTY);
        return types;
    }

    public static List<DevCardType> getPlayerDevTypes() {
        return instance()._getPlayerDevTypes();
    }

    private List<Integer> _getPlayerDevAmounts() {
        List<Integer> amounts = new ArrayList<Integer>();
        amounts.add(this.catanModel.cardManager.playerDevCardCount(this._getLocalPlayerIndex(), DevCardType.MONOPOLY));
        amounts.add(this.catanModel.cardManager.playerDevCardCount(this._getLocalPlayerIndex(), DevCardType.MONUMENT));
        amounts.add(this.catanModel.cardManager.playerDevCardCount(this._getLocalPlayerIndex(), DevCardType.ROAD_BUILD));
        amounts.add(this.catanModel.cardManager.playerDevCardCount(this._getLocalPlayerIndex(), DevCardType.SOLDIER));
        amounts.add(this.catanModel.cardManager.playerDevCardCount(this._getLocalPlayerIndex(), DevCardType.YEAR_OF_PLENTY));
        return amounts;
    }

    public static List<Integer> getPlayerDevAmounts() {
        return instance()._getPlayerDevAmounts();
    }

    private List<Boolean> _getPlayerDevPlayables() {
        List<Boolean> play = new ArrayList<Boolean>();
        play.add(this.catanModel.cardManager.canPlayDevCard(this._getLocalPlayerIndex(), DevCardType.MONOPOLY));
        play.add(this.catanModel.cardManager.canPlayDevCard(this._getLocalPlayerIndex(), DevCardType.MONUMENT));
        play.add(this.catanModel.cardManager.canPlayDevCard(this._getLocalPlayerIndex(), DevCardType.ROAD_BUILD));
        play.add(this.catanModel.cardManager.canPlayDevCard(this._getLocalPlayerIndex(), DevCardType.SOLDIER));
        play.add(this.catanModel.cardManager.canPlayDevCard(this._getLocalPlayerIndex(), DevCardType.YEAR_OF_PLENTY));
        return play;
    }

    public static List<Boolean> getPlayerDevPlayables() {
        return instance()._getPlayerDevPlayables();
    }


    ///////////////////////////// Joshua Van Steeter's section of the Facade ////////////////////////////

    private void _addAI(String AIType) throws ServerException {
        this.proxy.addAI(AIType);
    }

    public static void addAI(String AIType) throws ServerException {
        instance()._addAI(AIType);
    }

    private PlayerInfo _getLocalPlayerInfo() {
        return this.proxy.getLocalPlayerInfo();
    }

    public static PlayerInfo getLocalPlayerInfo() {
        return instance()._getLocalPlayerInfo();
    }

    private int _getLocalPlayerIndex() {
        int playerIndex = _getLocalPlayerInfo().getPlayerIndex();
        if (playerIndex < 0 || playerIndex >= 3) {
            playerIndex = _getCatanModel().playerManager.getIndexFromId(this.proxy.getLocalPlayerInfo().getId());
            _getLocalPlayerInfo().setPlayerIndex(playerIndex);
        }
        return playerIndex;
    }

    public static int getLocalPlayerIndex() {
        return instance()._getLocalPlayerIndex();
    }


    /**
     * Generates a game list for the Client
     *
     * @return A list of all the current games
     * @throws ServerException
     */
    private GameInfo[] _listGames() throws ServerException {
        return this.proxy.listGames();
    }

    public static GameInfo[] listGames() throws ServerException {
        return instance()._listGames();
    }

    /**
     * Creates a new game with a specific title
     *
     * @param randomTiles boolean representing whether the new game should have random tiles
     * @param randomNumbers boolean representing whether the new game should have random Number tiles
     * @param randomPorts boolean representing whether the new game should have random Port locations
     * @param title string creates the human readable title
     * @return Game infor from the proxy
     */
    public static GameInfo createGame(boolean randomTiles, boolean randomNumbers, boolean randomPorts, String title) throws ServerException {
        return instance()._createGame(randomTiles, randomNumbers, randomPorts, title);
    }

    private GameInfo _createGame(boolean randomTiles, boolean randomNumbers, boolean randomPorts, String title) throws ServerException {
        return this.proxy.createGame(randomTiles, randomNumbers, randomPorts, title);
    }

    /**
     * Joins a new game from the game list
     *
     * @param gameId The gameId from the join game menu
     * @param color CatanColor represntation of the color the player would like to join with
     * @throws ServerException
     */
    public static void joinGame(int gameId, CatanColor color) throws ServerException {
        instance()._joinGame(gameId, color);
    }

    private void _joinGame(int gameId, CatanColor color) throws ServerException {
        this.proxy.joinGame(gameId, color);
        this._setView(this.catanModel = this.proxy.getGameModel());
    }

    /**
     * @return CatanModel from the proxy
     * @throws ServerException
     */
    private CatanModel _getGameModel() throws ServerException {
        return this.proxy.getGameModel();
    }

    public static CatanModel getGameModel() throws ServerException {
        return instance()._getGameModel();
    }

    /**
     * @param modelNumber retrieves the CatanModel from the proxy associated with this modelNumber
     * @return the CatanModel from the proxy
     * @throws ServerException
     */
    private CatanModel _getGameModel(int modelNumber) throws ServerException {
        return this.proxy.getGameModel(modelNumber);
    }

    public static CatanModel getGameModel(int modelNumber) throws ServerException {
        return instance()._getGameModel(modelNumber);
    }

    /**
     * @return A list of AI types from the proxy
     * @throws ServerException
     */
    private String[] _listAI() throws ServerException {
        return this.proxy.listAI();
    }

    public static String[] listAI() throws ServerException {
        return instance()._listAI();
    }

    /**
     * Will return false if either the player has not joined a game yet, or the joined game does not
     * have four players yet.  Otherwise return true.
     *
     * @return Whether game has started.
     */
    public static boolean hasGameStarted()
    {
        return instance()._hasGameStarted();
    }

    private boolean _hasGameStarted()
    {
        if(this.catanModel == null)
        {
            return false;
        }

        Player[] players = this.catanModel.getPlayerManager().getCatanPlayers();
        for(int i = 0; i < players.length; i++)
        {
            if(players[i] == null)
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the number of players that have already joined the game.
     * Used to check the poll if another player has joined.
     *
     * @return Number of non null players in the model
     */
    public static int howManyPlayers(CatanModel model)
    {
        return instance()._howManyPlayers(model);
    }

    private int _howManyPlayers(CatanModel model)
    {
        if(this.catanModel == null)
        {
            return 0;
        }

        int count = 0;
        Player[] players = model.getPlayerManager().getCatanPlayers();
        for(int i = 0; i < players.length; i++)
        {
            if(players[i] != null)
            {
                count++;
            }
        }
        return count;
    }

    ////////////////////////////// My section.... not yours..... mine........./////////////////////////////////////////

    private void _login(String username, String password) throws ServerException {
        this.proxy.userLogin(username, password);
    }

    public static void login(String username, String password) throws ServerException {
        instance()._login(username, password);
    }

    /**
     * Registers a new user
     *
     * @param username the users username identifier
     * @param password the password assicated with the given username
     * @throws ServerException
     */
    private void _register(String username, String password) throws ServerException {
        this.proxy.userRegister(username, password);
    }

    public static void register(String username, String password) throws ServerException {
        instance()._register(username, password);
    }

    //**********************************************************************************
    // Not Organized Yet
    //**********************************************************************************

    /**
     *
     */
    private void _startPoller() {
        (new Thread(new Poller(this.proxy))).start();
    }

    public static void startPoller() {
        instance()._startPoller();
    }

}