package serverProxy;

import clientfacade.Facade;

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
			System.out.println("Polling server");

			Thread.sleep((long)(seconds*1000));
			if(Facade.getCatanModel() == null)
				server.getGameModel(0);
			else
				server.getGameModel(Facade.getCatanModel().getVersion());
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
