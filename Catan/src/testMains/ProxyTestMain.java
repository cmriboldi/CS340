package testMains;

import serverProxy.RealProxy;
import serverProxy.ServerException;
import shared.definitions.CatanColor;

public class ProxyTestMain 
{
	public static void main(String[] args) 
	{
		RealProxy server = new RealProxy();
		try 
		{
			server.userLogin("test", "test");
			server.joinGame(3, CatanColor.RED);
			server.getGameModel(1);
		} 
		catch (ServerException e) 
		{
			e.printStackTrace();
		}
	}

}
