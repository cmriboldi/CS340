package serverProxy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import model.resources.ResourceList;
import shared.communication.CommGame;
import shared.communication.GameModelJSON;
import shared.definitions.CatanColor;
import shared.definitions.Command;
import shared.definitions.LogLevel;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

/**
 * The 'real' proxy will communicate with the server over http protocol to interact with the server
 * api.
 * 
 * @author Christian Riboldi
 * @author Clayton Condie
 * @author Jacob Brewer
 * @author Joshua Powers
 * @author Joshua Van Steeter
 * @version 1.0 Build Winter 2016.
 */
public class RealProxy implements ServerProxy
{
	private String urlBase;
	
	public RealProxy()
	{
		urlBase = "http://localhost:8081";
	}

	@Override
	public void userLogin(String username, String password) throws ServerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void userRegister(String username, String password) throws ServerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<CommGame> listGames() 
	{
		String obj = (String) get("/games/list");
		JsonArray json = new Gson().fromJson(obj, JsonArray.class);
		System.out.println(json.get(3));
		return null;
	}

	@Override
	public CommGame createGame(boolean randomTiles, boolean randomNumbers, boolean randomPorts, String name) {
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
	public GameModelJSON getGameModel(int modelNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameModelJSON resetGame() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameModelJSON setCommands(List<Command> commands) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Command> getCommands() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addAI(String AIType) throws ServerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<String> listAI() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameModelJSON sendChat(int playerIndex, String content) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameModelJSON rollNumber(int playerIndex, int number) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameModelJSON robPlayer(int playerIndex, int victimIndex, HexLocation hexLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameModelJSON finishTurn(int playerIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameModelJSON buyDevCard(int playerIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameModelJSON yearOfPlenty(int playerIndex, ResourceType resource1, ResourceType resource2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameModelJSON roadBuilding(int playerIndex, EdgeLocation spot1, EdgeLocation spot2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameModelJSON soldier(int playerIndex, int victimIndex, HexLocation hexLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameModelJSON monopoly(int playerIndex, ResourceType resource) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameModelJSON monument(int playerIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameModelJSON buildRoad(int playerIndex, EdgeLocation roadLocation, boolean free) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameModelJSON buildSettlement(int playerIndex, VertexLocation vertexLocation, boolean free) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameModelJSON buildCity(int playerIndex, VertexLocation vertexLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameModelJSON offerTrade(int playerIndex, int receiver, ResourceList offer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameModelJSON acceptTrade(int playerIndex, boolean willAccept) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameModelJSON maritimeTrade(int playerIndex, int ratio, ResourceType input, ResourceType output) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameModelJSON discardCards(int playerIndex, ResourceList discardedCards) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void changeLogLevel(LogLevel logLevel) throws ServerException {
		// TODO Auto-generated method stub
		
	}
	
	private Object get(String urlPath)
	{
		//GsonBuilder builder = new GsonBuilder();
		//Gson gson = builder.create();
		URL url;
		try 
		{
			url = new URL(urlBase + urlPath);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(10000);
			conn.setRequestMethod("GET");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.connect();
			
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK)
			{
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line;
				while((line = br.readLine()) != null)
				{
					sb.append(line + "\n");
				}
				br.close();
				
				return sb.toString();
			}
		} 
		catch (MalformedURLException e) 
		{
			e.printStackTrace();
		} 
		catch (ProtocolException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	/*private Object post(String urlPath, Object data)
	{
		GsonBuilder builder = new GsonBuilder();
		URL url;
		try 
		{
			url = new URL(urlBase + urlPath);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(10000);
			conn.setRequestMethod("POST");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.connect();
			
			xstream.toXML(data, conn.getOutputStream());
			conn.getOutputStream().close();
			
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK)
			{
				InputStreamReader ipr = new InputStreamReader(conn.getInputStream());
				System.out.println("start IPR");
				while(ipr.ready())
				{
					int iprOut = ipr.read();
					char[] iprOutChat = Character.toChars(iprOut);
					System.out.print(iprOutChat);
				}
				System.out.println("\nend IPR");
				
				Object result = xstream.fromXML(conn.getInputStream());
				return result;
			}
		} 
		catch (MalformedURLException e) 
		{
			e.printStackTrace();
		} 
		catch (ProtocolException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return null;
	}*/


}
