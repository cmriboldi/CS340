package serverProxy;

import java.io.IOException;
import java.util.List;

import com.google.gson.JsonArray;

import clientfacade.Facade;
import model.CatanModel;
import model.resources.ResourceList;
import shared.communication.CommGame;
import shared.definitions.CatanColor;
import shared.definitions.LogLevel;
import shared.definitions.ResourceType;
import shared.exceptions.player.GeneralPlayerException;
import shared.exceptions.player.InvalidTurnStatusException;
import shared.exceptions.player.TurnIndexException;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import test.JsonFileLoader;

/**
 * The 'mock' proxy inherits all functions from the ServerProxy interface but will not be used for
 * acutal communication purposes with the server. The mock proxy will be used only for testing
 * purposes and will return hard coded replies.
 * 
 * @author Christian Riboldi
 * @author Clayton Condie
 * @author Jacob Brewer
 * @author Joshua Powers
 * @author Joshua Van Steeter
 * @version 1.0 Build Winter 2016.
 */
public class MockProxy implements ServerProxy
{

	CatanModel catanModel;

	@Override
	public void userLogin(String username, String password) throws ServerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void userRegister(String username, String password) throws ServerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<CommGame> listGames() throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CommGame createGame(boolean randomTiles, boolean randomNumbers, boolean randomPorts, String name)
			throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void joinGame(int gameId, CatanColor color) throws ServerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveGame(int gameId, String fileName) throws ServerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadGame(String fileName) throws ServerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public CatanModel getGameModel(int version) throws ServerException {
		String json = null;
		try {
			json = JsonFileLoader.readFile("defaultModel.json");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			return catanModel = JSONDeserializer.deserialize(json);
		} catch (TurnIndexException e) {
			e.printStackTrace();
		} catch (InvalidTurnStatusException e) {
			e.printStackTrace();
		} catch (GeneralPlayerException e) {
			e.printStackTrace();
		}
		Facade.updateView(catanModel);
		return null;
	}

	@Override
	public CatanModel resetGame() throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CatanModel setCommands(JsonArray commands) throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonArray getCommands() throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addAI(String AIType) throws ServerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<String> listAI() throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CatanModel sendChat(int playerIndex, String content) throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CatanModel rollNumber(int playerIndex, int number) throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CatanModel robPlayer(int playerIndex, int victimIndex, HexLocation hexLocation) throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CatanModel finishTurn(int playerIndex) throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CatanModel buyDevCard(int playerIndex) throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CatanModel yearOfPlenty(int playerIndex, ResourceType resource1, ResourceType resource2)
			throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CatanModel roadBuilding(int playerIndex, EdgeLocation spot1, EdgeLocation spot2) throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CatanModel soldier(int playerIndex, int victimIndex, HexLocation hexLocation) throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CatanModel monopoly(int playerIndex, ResourceType resource) throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CatanModel monument(int playerIndex) throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CatanModel buildRoad(int playerIndex, EdgeLocation roadLocation, boolean free) throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CatanModel buildSettlement(int playerIndex, VertexLocation vertexLocation, boolean free)
			throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CatanModel buildCity(int playerIndex, VertexLocation vertexLocation) throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CatanModel offerTrade(int playerIndex, int receiver, ResourceList offer) throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CatanModel acceptTrade(int playerIndex, boolean willAccept) throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CatanModel maritimeTrade(int playerIndex, int ratio, ResourceType input, ResourceType output)
			throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CatanModel discardCards(int playerIndex, ResourceList discardedCards) throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void changeLogLevel(LogLevel logLevel) throws ServerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public CatanModel getGameModel() throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}
}
