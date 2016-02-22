package test.server;

import static org.junit.Assert.*;

import org.junit.*;

import clientfacade.Facade;
import serverProxy.MockProxy;
import serverProxy.ServerException;
import serverProxy.ServerProxy;

public class MockProxyTest {

	@Test
	public void test() throws ServerException {
		ServerProxy server = new MockProxy();
		server.getGameModel(0);
		assertNotEquals(Facade.getCatanModel(), null);
	}

}
