package test.server;

import model.CatanModel;
import serverProxy.JSONDeserializer;
import shared.exceptions.player.GeneralPlayerException;
import shared.exceptions.player.InvalidTurnStatusException;
import shared.exceptions.player.TurnIndexException;
import test.TestJSON;

public class DeserializerTestMain 
{
	public static void main(String[] args) 
	{
		try 
		{
			System.out.println("Test Deserializer");
			System.out.println(TestJSON.get());
			CatanModel model = JSONDeserializer.deserialize(TestJSON.get());
			System.out.println("Well it didn't throw any exceptions");
		} 
		catch (TurnIndexException | InvalidTurnStatusException | GeneralPlayerException e) 
		{
			
			e.printStackTrace();
		}
	}
}
