package serverProxy;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import model.CatanModel;
import shared.communication.*;

public class AuthProxy 
{
	private String authCookie;
	private String urlBase;
	
	public AuthProxy(String authCookie)
	{
		this.authCookie = "catan.user=" + authCookie;
		urlBase = "http://localhost:8081";
	}
	
	public CatanModel getGameModel(int modelNumber) throws ServerException 
	{
		String response = (String) get("/game/model");
		JsonObject json = new Gson().fromJson(response, JsonObject.class);
		System.out.println(json.toString());
		return null;
	}
	
	public void joinGame(int gameId, String color) throws ServerException 
	{
		CommJoin join = new CommJoin(gameId, color);
		URL url;
		try 
		{
			url = new URL(urlBase + "/games/join");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(10000);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Cookie", authCookie);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.connect();
						
			DataOutputStream writer = new DataOutputStream(conn.getOutputStream());
			byte[] send = new Gson().toJson(join).getBytes();
			writer.write(send);
			writer.close();
						
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK)
			{
				String cookie = conn.getHeaderField("Set-cookie");
				cookie = cookie.replace("catan.game=", "");
				cookie = cookie.replace(";Path=/;", "");
				int gameID = Integer.parseInt(cookie);
				authCookie = authCookie.concat("; catan.game=" + gameID);
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line;
				while((line = br.readLine()) != null)
				{
					sb.append(line + "\n");
				}
				br.close();
			}
			else if(conn.getResponseCode() != HttpURLConnection.HTTP_OK)
			{
				throw new ServerException("Server Response Code: " + conn.getResponseCode() + " " + conn.getResponseMessage());
			}
			else
			{
				throw new ServerException("No Response from Server");
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
	}
	
	public CatanModel resetGame() throws ServerException
	{
		String response = (String) post("/game/reset", null);
		JsonObject json = new Gson().fromJson(response, JsonObject.class);
		System.out.println(json.toString());
		return null;
	}
	
	private Object get(String urlPath) throws ServerException
	{
		URL url;
		try 
		{
			url = new URL(urlBase + urlPath);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(10000);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Cookie", authCookie);
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
			else if(conn.getResponseCode() != HttpURLConnection.HTTP_OK)
			{
				throw new ServerException("Server Response Code: " + conn.getResponseCode() + " " + conn.getResponseMessage());
			}
			else
			{
				throw new ServerException("No Response from Server");
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
	
	private Object post(String urlPath, Object data) throws ServerException
	{
		URL url;
		try 
		{
			url = new URL(urlBase + urlPath);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(10000);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Cookie", authCookie);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.connect();
			
			DataOutputStream writer = new DataOutputStream(conn.getOutputStream());
			byte[] send = new Gson().toJson(data).getBytes();
			writer.write(send);
			writer.close();
			
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
			else if(conn.getResponseCode() != HttpURLConnection.HTTP_OK)
			{
				throw new ServerException("Server Response Code: " + conn.getResponseCode() + " " + conn.getResponseMessage());
			}
			else
			{
				throw new ServerException("No Response from Server");
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
}
