package clientfacade;

import client.data.GameInfo;
import client.data.PlayerInfo;
import model.CatanModel;
import model.resources.ResourceList;
import serverProxy.Poller;
import serverProxy.RealProxy;
import serverProxy.ServerException;
import shared.definitions.CatanColor;
import shared.exceptions.player.PlayerNameNotFoundException;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

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

    //**********************************************************************************
    // Observable Functions
    //**********************************************************************************
    private void _updateView(CatanModel catanModel) {
        this.catanModel = catanModel;
        this.setChanged();
        this.notifyObservers(this.catanModel);
    }

    public static void updateView(CatanModel catanModel) {
        instance()._updateView(catanModel);
    }

    /**
     *
     * @param obs The Object observing the Facade
     */
    public static void addObserverStatic(Observer obs) {
        instance().addObserver(obs);
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
        this.catanModel = proxy.sendChat(catanModel.playerManager.getTurnTracker().getTurnIndex(), message);
        this.setChanged();
        this.notifyObservers();
    }

    public static void chat(String message) throws ServerException {
        _instance._chat(message);
    }

    /**
     *
     * @return
     */
    private List<String> _getChatMessages() {
        return this.catanModel.chatManager.chatMessages();
    }

    public static List<String> getChatMessages() {
        return _instance._getChatMessages();
    }

    /**
     *
     * @return
     */
    private List<String> _getChatSources() {
        return this.catanModel.chatManager.chatSources();
    }

    public static List<String> getChatSources() {
        return _instance._getChatSources();
    }

    /**
     *
     * @return
     */
    private List<String> _getHistoryMessages() {
        return this.catanModel.chatManager.historyMessages();
    }

    public static List<String> getHistoryMessages() {
        return _instance._getHistoryMessages();
    }

    /**
     *
     * @return
     */
    private List<String> _getHistorySources() {
        return this.catanModel.chatManager.historySources();
    }

    public static List<String> getHistorySources() {
        return _instance._getHistorySources();
    }

    //**********************************************************************************
    // Map Interaction Functions
    //**********************************************************************************

    /**
     * Builds a road for a specific player
     *
     * @param index of player building road
     */
    private void _buildRoad(int index) {

    }

    /**
     * Builds a town for a specific player
     *
     * @param index of player building town
     */
    private void _buildTown(int index) {

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

        this.catanModel = proxy.rollNumber(catanModel.getPlayerManager().getTurnTracker().getTurnIndex(), total);
        this.setChanged();
        this.notifyObservers();
        return total;
    }

    public static int roll() throws ServerException {
        return _instance._roll();
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
    private void _portTrade(int index, ResourceList toGive, ResourceList toGet) {


    }


    ///////////////////////////// Joshua Van Steeter's section of the Facade ////////////////////////////

    private void _addAI(String AIType) throws ServerException
    {
        proxy.addAI(AIType);
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
        return proxy.getLocalPlayerInfo();
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
        return proxy.listGames();
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
        return proxy.createGame(randomTiles, randomNumbers, randomPorts, title);
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
        proxy.joinGame(gameId, color);
    }

    /**
     *
     * @return
     * @throws ServerException
     */
    private CatanModel _getGameModel() throws ServerException {
        return proxy.getGameModel();
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
        return proxy.getGameModel(modelNumber);
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
        return proxy.listAI();
    }

    public static String[] listAI() throws ServerException {
        return instance()._listAI();
    }

    ////////////////////////////// My section.... not yours..... mine........./////////////////////////////////////////


    /**
     *
     * @param name
     * @return
     * @throws PlayerNameNotFoundException
     */
    private CatanColor _getColorByName(String name) throws PlayerNameNotFoundException {
        return catanModel.playerManager.getPlayerColor(name);
    }

    public static CatanColor getColorByName(String name) throws PlayerNameNotFoundException {
        return _instance._getColorByName(name);
    }

    /**
     * Logs a players move in the log
     *
     * @param index   of player taking action
     * @param message
     * @return
     * @throws ServerException
     */
    private void _log(int index, String message) {

    }

    private void _login(String username, String password) throws ServerException {
        proxy.userLogin(username, password);
    }

    public static void login(String username, String password) throws ServerException {
        instance()._login(username, password);
    }

    /**
     * Buys a development card for a specific player
     *
     * @param index of player buying dev card
     */
    private void _buyDevCard(int index) {

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
        proxy.userRegister(username, password);
    }

    public static void register(String username, String password) throws ServerException {
        instance()._register(username, password);
    }

    //**********************************************************************************
    // Not Organized Yet
    //**********************************************************************************

    /**
     *
     * @return
     */
    private CatanModel _getCatanModel() {
        return this.catanModel;
    }

    public static CatanModel getCatanModel() {
        return instance()._getCatanModel();
    }

    /**
     *
     */
    private void _startPoller() {
        (new Thread(new Poller(proxy))).start();
    }

    public static void startPoller() {
        instance()._startPoller();
    }


}
