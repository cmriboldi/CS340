package client.join;

import client.base.*;
import client.data.PlayerInfo;
import clientfacade.Facade;
import model.CatanModel;
import model.players.Player;
import serverProxy.ServerException;

import java.util.ArrayList;
import java.util.List;
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
			List<PlayerInfo> playersList = new ArrayList<>();
			System.out.println("There are " + players.length + " players");
			for(int i = 0; i < players.length; i++)
			{
				//System.out.println("Player " + i + " name: " + players[i].getName());
				if(players[i] != null)
				{
					PlayerInfo playerInfo = new PlayerInfo();
					playerInfo.setId(players[i].getId());
					playerInfo.setName(players[i].getName());
					playerInfo.setColor(players[i].getColor());
					playersList.add(playerInfo);
				}

			}

			PlayerInfo[] playersInfo = new PlayerInfo[playersList.size()];
			for(int i = 0; i < playersList.size(); i++)
			{
				playersInfo[i] = playersList.get(i);
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

