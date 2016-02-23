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
			((IJoinGameView)this.getView()).setGames(games, localPlayer);
		}
		catch (ServerException e)
		{
			e.printStackTrace();
		}
		getJoinGameView().showModal();
	}

	@Override
	public void startCreateNewGame() {
		
		getNewGameView().showModal();
	}

	@Override
	public void cancelCreateNewGame() {
		
		getNewGameView().closeModal();
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
			e.printStackTrace();
		}
	}

	@Override
	public void startJoinGame(GameInfo game)
	{
		System.out.println("Joining a game: " + game.toString());
		IAction joinAction = new JoinAction(game);
		this.setJoinAction(joinAction);
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
		}
		catch (ServerException e)
		{
			e.printStackTrace();
		}
		getJoinGameView().closeModal();
	}

	@Override
	public void joinGame(CatanColor color)
	{
		System.out.println("JoinGame has been called");
		// If join succeeded
		getSelectColorView().closeModal();
		getJoinGameView().closeModal();
		((JoinAction)getJoinAction()).setColor(color);
		joinAction.execute();
	}

	private class JoinAction implements IAction
	{
		private GameInfo game;
		private CatanColor color;

		private JoinAction(GameInfo game)
		{
			this.game = game;
		}

		private void setColor(CatanColor color)
		{
			this.color = color;
		}

		@Override
		public void execute()
		{
			try
			{
				Facade.joinGame(game.getId(), color);
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
}

