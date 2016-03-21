package model.messagelog;

import java.util.ArrayList;
import java.util.List;

public class ChatManager
{
	private List<Line> chatMessages;
	private List<Line> gameHistory;
	
	public ChatManager()
	{
		this.chatMessages = new ArrayList<Line>();
		this.gameHistory = new ArrayList<Line>();
	}
	
	public ChatManager(List<Line> chatMessages, List<Line> gameHistory)
	{
		this.chatMessages = chatMessages;
		this.gameHistory = gameHistory;
	}

	/**
	 * update the chatMessages list
	 * @param player sending message
	 * @param string containing message
	 * @return
	 */
	public void sendMessage(String message, String source)
	{
		Line line = new Line(message, source);
		chatMessages.add(line);
	}
	
	/**
	 * update the chatMessages list
	 * @param player taking action
	 * @param string with action taken message
	 * @return
	 */
	public void logAction(String message, String source)
	{
		Line line = new Line(message, source);
		gameHistory.add(line);
	}
	
	public List<String> chatMessages()
	{
		List<String> messages = new ArrayList<String>();
		for(int i = 0; i < chatMessages.size(); i++)
		{
			messages.add(chatMessages.get(i).getMessage());
		}
		return messages;
	}
	
	public List<String> chatSources()
	{
		List<String> sources = new ArrayList<String>();
		for(int i = 0; i < chatMessages.size(); i++)
		{
			sources.add(chatMessages.get(i).getSource());
		}
		return sources;
	}
	
	public List<String> historyMessages()
	{
		List<String> messages = new ArrayList<String>();
		for(int i = 0; i < gameHistory.size(); i++)
		{
			messages.add(gameHistory.get(i).getMessage());
		}
		return messages;
	}
	
	public List<String> historySources()
	{
		List<String> sources = new ArrayList<String>();
		for(int i = 0; i < gameHistory.size(); i++)
		{
			sources.add(gameHistory.get(i).getSource());
		}
		return sources;
	}

	public List<Line> getChatMessages() {
		return chatMessages;
	}

	public void setChatMessages(List<Line> chatMessages) {
		this.chatMessages = chatMessages;
	}

	public List<Line> getGameHistory() {
		return gameHistory;
	}

	public void setGameHistory(List<Line> gameHistory) {
		this.gameHistory = gameHistory;
	}
	
	
}
