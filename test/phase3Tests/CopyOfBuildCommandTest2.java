package phase3Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import serverProxy.MockProxy;
import serverProxy.ServerException;
import serverProxy.ServerProxy;
import clientfacade.Facade;

public class CopyOfBuildCommandTest2 {

	@Test
	public void test() throws ServerException {
		ServerProxy server = new MockProxy();
		server.getGameModel(0);
		assertNotEquals(Facade.getCatanModel(), null);
	}

}
