package test.server;

import static org.junit.Assert.*;
import org.junit.*;

import model.CatanModel;
import serverProxy.JSONDeserializer;
import shared.exceptions.player.GeneralPlayerException;
import shared.exceptions.player.InvalidTurnStatusException;
import shared.exceptions.player.TurnIndexException;
import test.TestJSON;

public class JSONDeserializerTest {

	@Test
	public void testDeserialize() throws TurnIndexException, InvalidTurnStatusException, GeneralPlayerException 
	{
		CatanModel model = JSONDeserializer.deserialize(TestJSON.get());
		assertNotEquals(model, null);
	}
}
