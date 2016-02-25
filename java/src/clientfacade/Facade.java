package clientfacade;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import client.data.GameInfo;
import client.data.PlayerInfo;
import model.CatanModel;
import model.options.Options;
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
    private void _updateView()
    {
    	this.setChanged();
    	this.notifyObservers();
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
        this._setView(this.proxy.sendChat(catanModel.playerManager.getTurnTracker().getTurnIndex(), message));
    }

    public static void chat(String message) throws ServerException {
        instance()._chat(message);
    }

    /**
     *
     * @return
     */
    private List<String> _getChatMessages() {
        return this.catanModel.chatManager.chatMessages();
    }

    public static List<String> getChatMessages() {
        return instance()._getChatMessages();
    }

    /**
     *
     * @return
     */
    private List<String> _getChatSources() {
        return this.catanModel.chatManager.chatSources();
    }

    public static List<String> getChatSources() {
        return instance()._getChatSources();
    }

    /**
     *
     * @return
     */
    private List<String> _getHistoryMessages() {
        return this.catanModel.chatManager.historyMessages();
    }

    public static List<String> getHistoryMessages() {
        return instance()._getHistoryMessages();
    }

    /**
     *
     * @return
     */
    private List<String> _getHistorySources() {
        return this.catanModel.chatManager.historySources();
    }

    public static List<String> getHistorySources() {
        return instance()._getHistorySources();
    }
    
    /**
    *
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
    private void _buildRoad(int index, EdgeLocation edge, boolean free) throws ServerException {
    	this._setView(this.proxy.buildRoad(index, edge, free));
    }

    public static void buildRoad (int index, EdgeLocation edge, boolean free) throws ServerException
    {
    	instance()._buildRoad(index, edge, free);
    }
    
    /**
     * Builds a town for a specific player
     *
     * @param index of player building town
     * @throws ServerException 
     */
    private void _buildTown(int playerIndex, VertexLocation vert, boolean free) throws ServerException {
    	proxy.buildSettlement(playerIndex, vert, free);

    }
    
    public static void buildTown (int playerIndex, VertexLocation vertex, boolean free) throws ServerException
    {
    	instance()._buildTown(playerIndex, vertex, free);
    }
    

    /**
     * Builds a city for a specific player
     *
     * @param index of player building city
     */
    private void _buildCity(int index) {

    }

    /**
     * Places the robber in a hex specified by a specific player
     *
     * @param index of player using robber
     */
    private void _placeRobber(int index) {

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

        this._setView(this.proxy.rollNumber(this._getLocalPlayerInfo().getPlayerIndex(), total));
        return total;
    }

    public static int roll() throws ServerException {
        return instance()._roll();
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
     * @param index  of player trading
     * @param toGive of giving resources
     * @param toGet  of getting resources
     * @return
     * @throws ServerException
     */
    private void _portTrade(int index, ResourceType toGive, ResourceType toGet) throws ServerException {
    	this._setView(this.proxy.maritimeTrade(getLocalPlayerInfo().getId(), index, toGive, toGet));
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
     * @param index of player buying dev card
     * @throws ServerException 
     */
    private void _buyDevCard() throws ServerException {
    	this._setView(this.proxy.buyDevCard(this._getLocalPlayerInfo().getPlayerIndex()));
    }
    
    public static void buyDevCard() throws ServerException
    {
    	instance()._buyDevCard();
    }
    
    private void _playMonumentCard() throws ServerException
    {
    	this._setView(this.proxy.monument(this._getLocalPlayerInfo().getPlayerIndex()));
    }
    
    public static void playMonumentCard() throws ServerException
    {
    	instance()._playMonumentCard();
    }
    
    private void _playMonopolyCard(ResourceType resource) throws ServerException
    {
    	this._setView(this.proxy.monopoly(this._getLocalPlayerInfo().getPlayerIndex(), resource));
    }
    
    public static void playMonopolyCard(ResourceType resource) throws ServerException
    {
    	instance()._playMonopolyCard(resource);
    }
    
    private void _playYearOfPlentyCard(ResourceType resource1, ResourceType resource2) throws ServerException
    {
    	this._setView(this.proxy.yearOfPlenty(this._getLocalPlayerInfo().getPlayerIndex(), resource1, resource2));
    }
    
    public static void playYearOfPlentyCard(ResourceType resource1, ResourceType resource2) throws ServerException
    {
    	instance()._playYearOfPlentyCard(resource1, resource2);
    }
    
    private void _playRoadBuildCard(EdgeLocation spot1, EdgeLocation spot2) throws ServerException
    {
    	this._setView(this.proxy.roadBuilding(this._getLocalPlayerInfo().getPlayerIndex(), spot1, spot2));
    }
    
    public static void playRoadBuildCard(EdgeLocation spot1, EdgeLocation spot2) throws ServerException
    {
    	instance()._playRoadBuildCard(spot1, spot2);
    }
    
    private void _playSoldierCard(int victimIndex, HexLocation hexLocation) throws ServerException
    {
    	this._setView(proxy.soldier(this._getLocalPlayerInfo().getPlayerIndex(), victimIndex, hexLocation));
    }
    
    public static void playSoldierCard(int victimIndex, HexLocation hexLocation) throws ServerException
    {
    	instance()._playSoldierCard(victimIndex, hexLocation);
    }
    
    private List<DevCardType> _getPlayerDevTypes()
    {
    	List<DevCardType> types = new ArrayList<DevCardType>();
    	types.add(DevCardType.MONOPOLY);
    	types.add(DevCardType.MONUMENT);
    	types.add(DevCardType.ROAD_BUILD);
    	types.add(DevCardType.SOLDIER);
    	types.add(DevCardType.YEAR_OF_PLENTY);   	
    	return types;
    }
    
    public static List<DevCardType> getPlayerDevTypes()
    {
    	return instance()._getPlayerDevTypes();
    }
    
    private List<Integer> _getPlayerDevAmounts()
    {
    	List<Integer> amounts = new ArrayList<Integer>();
    	amounts.add(this.catanModel.cardManager.playerDevCardCount(this._getLocalPlayerInfo().getPlayerIndex(), DevCardType.MONOPOLY));
    	amounts.add(this.catanModel.cardManager.playerDevCardCount(this._getLocalPlayerInfo().getPlayerIndex(), DevCardType.MONUMENT));
    	amounts.add(this.catanModel.cardManager.playerDevCardCount(this._getLocalPlayerInfo().getPlayerIndex(), DevCardType.ROAD_BUILD));
    	amounts.add(this.catanModel.cardManager.playerDevCardCount(this._getLocalPlayerInfo().getPlayerIndex(), DevCardType.SOLDIER));
    	amounts.add(this.catanModel.cardManager.playerDevCardCount(this._getLocalPlayerInfo().getPlayerIndex(), DevCardType.YEAR_OF_PLENTY));
    	return amounts;
    }
    
    public static List<Integer> getPlayerDevAmounts()
    {
    	return instance()._getPlayerDevAmounts();
    }
    
    private List<Boolean> _getPlayerDevPlayables()
    {
    	List<Boolean> play = new ArrayList<Boolean>();
    	play.add(this.catanModel.cardManager.canPlayDevCard(this._getLocalPlayerInfo().getPlayerIndex(), DevCardType.MONOPOLY));
    	play.add(this.catanModel.cardManager.canPlayDevCard(this._getLocalPlayerInfo().getPlayerIndex(), DevCardType.MONUMENT));
    	play.add(this.catanModel.cardManager.canPlayDevCard(this._getLocalPlayerInfo().getPlayerIndex(), DevCardType.ROAD_BUILD));
    	play.add(this.catanModel.cardManager.canPlayDevCard(this._getLocalPlayerInfo().getPlayerIndex(), DevCardType.SOLDIER));
    	play.add(this.catanModel.cardManager.canPlayDevCard(this._getLocalPlayerInfo().getPlayerIndex(), DevCardType.YEAR_OF_PLENTY));
    	return play;
    }
    
    public static List<Boolean> getPlayerDevPlayables()
    {
    	return instance()._getPlayerDevPlayables();
    }


    ///////////////////////////// Joshua Van Steeter's section of the Facade ////////////////////////////

    private void _addAI(String AIType) throws ServerException
    {
    	this.proxy.addAI(AIType);
    }

    public static void addAI(String AIType) throws ServerException
    {
        instance()._addAI(AIType);
    }

    /**
     *
     * @return
     */
    private PlayerInfo _getLocalPlayerInfo() {
        return this.proxy.getLocalPlayerInfo();
    }

    public static PlayerInfo getLocalPlayerInfo() {
        return instance()._getLocalPlayerInfo();
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
     * @param randomTiles
     * @param randomNumbers
     * @param randomPorts
     * @param title
     * @return
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
     * @param gameId
     * @param color
     * @throws ServerException
     */
    public static void joinGame(int gameId, CatanColor color) throws ServerException {
        instance()._joinGame(gameId, color);
    }

    private void _joinGame(int gameId, CatanColor color) throws ServerException {
        this.proxy.joinGame(gameId, color);
    }

    /**
     *
     * @return
     * @throws ServerException
     */
    private CatanModel _getGameModel() throws ServerException {
        return this.proxy.getGameModel();
    }

    public static CatanModel getGameModel() throws ServerException {
        return instance()._getGameModel();
    }

    /**
     *
     * @param modelNumber
     * @return
     * @throws ServerException
     */
    private CatanModel _getGameModel(int modelNumber) throws ServerException {
        return this.proxy.getGameModel(modelNumber);
    }

    public static CatanModel getGameModel(int modelNumber) throws ServerException {
        return instance()._getGameModel(modelNumber);
    }

    /**
     *
     * @return
     * @throws ServerException
     */
    private String[] _listAI() throws ServerException {
        return this.proxy.listAI();
    }

    public static String[] listAI() throws ServerException {
        return instance()._listAI();
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
     * @param username
     * @param password
     * @return User object associated with the new registered user
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
