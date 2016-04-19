package SQLPlugin.src.serverProxy;


import SQLPlugin.src.clientfacade.Facade;
import server.exception.ServerException;
import model.CatanModel;

/**
 * The poller will poll the server regularly and commit any changes to the game model.
 * 
 * @author Christian Riboldi
 * @author Clayton Condie
 * @author Jacob Brewer
 * @author Joshua Powers
 * @author Joshua Van Steeter
 * @version 1.0 Build Winter 2016.
 */
public class Poller implements Runnable
{
	private ServerProxy server;
	private int modelNumber;
	/**
	 * @param server The server that will be used to poll from
	 */
	public Poller(ServerProxy server)
	{
		this.server = server;
	}

	/**
	 * Start the tread that will regularly poll the server for changes.
	 * 
	 * @param seconds Amount of time in seconds between polls.
	 * @throws InterruptedException 
	 * @throws ServerException 
	 */
	public void start(int seconds) throws InterruptedException, ServerException
	{
		for(;;)
		{
			if(Facade.getCatanModel() == null)
			{
				Facade.setView(server.getGameModel());
			}
			else
			{
				CatanModel model = server.getGameModel(Facade.getCatanModel().getVersion());
				if(model == null)
				{
					model = server.getGameModel();
					if(Facade.howManyPlayers(model) != Facade.howManyPlayers(Facade.getCatanModel()))
					{
						Facade.setView(model);
					}
				}
				else
					Facade.setView(model);
			}
			Thread.sleep((long)(seconds*1000));
		}
	}

	@Override
	public void run() 
	{
		try {
			this.start(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ServerException e) {
			e.printStackTrace();
		}		
	}
}
