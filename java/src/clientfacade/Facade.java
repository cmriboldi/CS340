package clientfacade;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import client.data.GameInfo;
import client.data.PlayerInfo;
import model.CatanModel;
import model.resources.ResourceList;
import serverProxy.RealProxy;
import serverProxy.ServerException;
import shared.communication.*;
import shared.definitions.CatanColor;
import shared.exceptions.player.PlayerNameNotFoundException;

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

    public static void addObserverStatic(Observer obs) {
        instance().addObserver(obs);
    }

    //**********************************************************************************
    // Chat Functions
    //**********************************************************************************

    /**
     * Chat a message to other players
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


    private List<String> _getChatMessages() {
        List<String> messages = this.catanModel.chatManager.chatMessages();
        return messages;
    }


    public static List<String> getChatMessages() {
        return _instance._getChatMessages();
    }


    private List<String> _getChatSources() {
        List<String> sources = this.catanModel.chatManager.chatSources();
        return sources;
    }


    public static List<String> getChatSources() {
        return _instance._getChatSources();
    }


    private List<String> _getHistoryMessages() {
        List<String> messages = this.catanModel.chatManager.historyMessages();
        return messages;
    }


    public static List<String> getHistoryMessages() {
        return _instance._getHistoryMessages();
    }


    private List<String> _getHistorySources() {
        List<String> sources = this.catanModel.chatManager.historySources();
        return sources;
    }

    public static List<String> getHistorySources() {
        return _instance._getHistorySources();
    }


    private CatanModel _getCatanModel() {
        return this.catanModel;
    }

    public static CatanModel getCatanModel() {
        return instance()._getCatanModel();
    }

    /**
     * Validates user credentials
     *
     * @param userName
     * @param password
     * @return The authenticated User object
     */
    private CommUser _authenticateUser(String userName, String password) {
        return null;
    }

    ///////////////////////////// Joshua Van Steeter's section of the Facade ////////////////////////////

    private PlayerInfo _getLocalPlayerInfo() {
        return proxy.getLocalPlayerInfo();
    }

    public static PlayerInfo getLocalPlayerInfo() {
        return instance()._getLocalPlayerInfo();
    }

    private GameInfo[] _listGames() throws ServerException {
        return proxy.listGames();
    }

    /**
     * Generates a game list for the Client
     *
     * @return A list of all the current games
     * @throws ServerException
     */
    public static GameInfo[] listGames() throws ServerException {
        return instance()._listGames();
    }

    private GameInfo _createGame(boolean randomTiles, boolean randomNumbers, boolean randomPorts, String title) throws ServerException {
        return proxy.createGame(randomTiles, randomNumbers, randomPorts, title);
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

    private void _joinGame(int gameId, CatanColor color) throws ServerException {
        proxy.joinGame(gameId, color);
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

    ////////////////////////////// My section.... not yours..... mine........./////////////////////////////////////////

    //**********************************************************************************
    // Game Phase Control
    //**********************************************************************************

    /**
     * Generates a new map for a new game
     *
     * @param randomNumbers
     * @param randomHexes
     * @param randomPorts
     * @return
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
     * @return
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
     * @param index  of player trading
     * @param toGive of giving resources
     * @param toGet  of getting resources
     * @return
     * @throws ServerException
     */
    private void _portTrade(int index, ResourceList toGive, ResourceList toGet) {



    }




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



}
