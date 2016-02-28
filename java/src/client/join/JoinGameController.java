package client.join;

import shared.definitions.CatanColor;
import client.base.*;
import client.data.*;
import client.misc.*;
import clientfacade.Facade;
import serverProxy.ServerException;


/**
 * Implementation for the join game controller
 */
public class JoinGameController extends Controller implements IJoinGameController {

	private INewGameView newGameView;
	private ISelectColorView selectColorView;
	private IMessageView messageView;
	private IAction joinAction;
	private GameInfo gameToJoin;
	
	/**
	 * JoinGameController constructor
	 * 
	 * @param view Join game view
	 * @param newGameView New game view
	 * @param selectColorView Select color view
	 * @param messageView Message view (used to display error messages that occur while the user is joining a game)
	 */
	public JoinGameController(IJoinGameView view, INewGameView newGameView, 
								ISelectColorView selectColorView, IMessageView messageView) 
	{

		super(view);

		setNewGameView(newGameView);
		setSelectColorView(selectColorView);
		setMessageView(messageView);
	}
	
	public IJoinGameView getJoinGameView() {
		
		return (IJoinGameView)super.getView();
	}
	
	/**
	 * Returns the action to be executed when the user joins a game
	 * 
	 * @return The action to be executed when the user joins a game
	 */
	public IAction getJoinAction() {
		
		return joinAction;
	}

	/**
	 * Sets the action to be executed when the user joins a game
	 * 
	 * @param value The action to be executed when the user joins a game
	 */
	public void setJoinAction(IAction value) {	
		
		joinAction = value;
	}
	
	public INewGameView getNewGameView() {
		
		return newGameView;
	}

	public void setNewGameView(INewGameView newGameView) {
		
		this.newGameView = newGameView;
	}
	
	public ISelectColorView getSelectColorView() {
		
		return selectColorView;
	}
	public void setSelectColorView(ISelectColorView selectColorView) {
		
		this.selectColorView = selectColorView;
	}
	
	public IMessageView getMessageView() {
		
		return messageView;
	}
	public void setMessageView(IMessageView messageView) {
		
		this.messageView = messageView;
	}

	@Override
	public void start()
	{
		try
		{
			GameInfo[] games = Facade.listGames();
			PlayerInfo localPlayer = Facade.getLocalPlayerInfo();
			getJoinGameView().setGames(games, localPlayer);

			if(!Facade.hasGameStarted())
				getJoinGameView().showModal();
		}
		catch (ServerException e)
		{
			messageView.setTitle("Server Error");
			messageView.setMessage("SERVER NOT RESPONDING");
			messageView.showModal();
			e.printStackTrace();
		}
	}

	@Override
	public void startCreateNewGame()
	{
		getNewGameView().showModal();
	}

	@Override
	public void cancelCreateNewGame()
	{
		try
		{
			GameInfo[] games = Facade.listGames();
			PlayerInfo localPlayer = Facade.getLocalPlayerInfo();
			((IJoinGameView)this.getView()).setGames(games, localPlayer);
			getNewGameView().closeModal();
		}
		catch (ServerException e)
		{
			messageView.setTitle("Server Error");
			messageView.setMessage("SERVER NOT RESPONDING");
			messageView.showModal();
			e.printStackTrace();
		}
	}

	@Override
	public void createNewGame()
	{
		try
		{
			INewGameView view = this.getNewGameView();
			Facade.createGame(view.getRandomlyPlaceHexes(), view.getRandomlyPlaceNumbers(), view.getUseRandomPorts(), view.getTitle());
			GameInfo[] games = Facade.listGames();
			PlayerInfo localPlayer = Facade.getLocalPlayerInfo();
			((IJoinGameView)this.getView()).setGames(games, localPlayer);
			getNewGameView().closeModal();
		}
		catch (ServerException e)
		{
			messageView.setTitle("Server Error");
			messageView.setMessage("SERVER NOT RESPONDING");
			messageView.showModal();
			e.printStackTrace();
		}
	}

	@Override
	public void startJoinGame(GameInfo game)
	{
		this.gameToJoin = game;
		for(PlayerInfo player : game.getPlayers())
		{
			if(player.getColor() != null && !Facade.getLocalPlayerInfo().equals(player))
			{
				getSelectColorView().setColorEnabled(player.getColor(), false);
			}
		}
		getSelectColorView().showModal();
	}

	@Override
	public void cancelJoinGame()
	{
		try
		{
			GameInfo[] games = Facade.listGames();
			PlayerInfo localPlayer = Facade.getLocalPlayerInfo();
			((IJoinGameView)this.getView()).setGames(games, localPlayer);
			getJoinGameView().closeModal();
		}
		catch (ServerException e)
		{
			messageView.setTitle("Server Error");
			messageView.setMessage("SERVER NOT RESPONDING");
			messageView.showModal();
			e.printStackTrace();
		}
	}

	@Override
	public void joinGame(CatanColor color)
	{
		// If join succeeded
		try
		{
			Facade.joinGame(this.gameToJoin.getId(), color);

			getSelectColorView().closeModal();
			getSelectColorView().setColorEnabled(color, false);
			getJoinGameView().closeModal();
			joinAction.execute();
		}
		catch (ServerException e)
		{
			messageView.setTitle("Server Error");
			messageView.setMessage("GAME NOT JOINED");
			messageView.showModal();
			e.printStackTrace();
		}
	}
}

