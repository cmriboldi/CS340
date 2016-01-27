package testMains;

import serverProxy.RealProxy;
import serverProxy.ServerException;

public class ProxyTestMain 
{
	public static void main(String[] args) 
	{
		RealProxy server = new RealProxy();
		try 
		{
			server.userLogin("test", "test");
		} 
		catch (ServerException e) 
		{
			e.printStackTrace();
		}
	}

}
