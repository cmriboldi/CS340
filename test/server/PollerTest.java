package test.server;

import static org.junit.Assert.*;

import org.junit.*;

import clientfacade.Facade;
import serverProxy.MockProxy;
import serverProxy.Poller;

public class PollerTest {

	@Test
	public void test() throws InterruptedException {
		(new Thread(new Poller(new MockProxy()))).start();
		Thread.sleep(10000);
		assertEquals(Facade.getCatanModel(),null);
	}
}
