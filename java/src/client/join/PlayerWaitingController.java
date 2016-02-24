package client.join;

import client.base.*;
import client.data.PlayerInfo;
import clientfacade.Facade;
import model.CatanModel;
import model.players.Player;
import serverProxy.ServerException;

import java.util.Observable;
import java.util.Observer;


/**
 * Implementation for the player waiting controller
 */
public class PlayerWaitingController extends Controller implements IPlayerWaitingController, Observer {

	public PlayerWaitingController(IPlayerWaitingView view)
	{
		super(view);
	}

	@Override
	public IPlayerWaitingView getView()
	{
		return (IPlayerWaitingView)super.getView();
	}

	@Override
	public void start()
	{
		try
		{
			getView().setAIChoices(Facade.listAI());
			CatanModel catan = Facade.getGameModel();
			Player[] players = catan.getPlayerManager().getCatanPlayers();
			PlayerInfo[] playersInfo = new PlayerInfo[players.length];
			for(int i = 0; i < players.length; i++)
			{
				PlayerInfo playerInfo = new PlayerInfo();
				playerInfo.setId(players[i].getId().getId());
				playerInfo.setName(players[i].getName());
				playerInfo.setColor(players[i].getColor());
				playersInfo[i] = playerInfo;
			}
			getView().setPlayers(playersInfo);
			getView().showModal();
		}
		catch (ServerException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void addAI()
	{
		getView().closeModal();
	}

	@Override
	public void update(Observable o, Object arg)
	{

	}
}

