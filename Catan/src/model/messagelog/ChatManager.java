package model.messagelog;

import java.util.List;

public class ChatManager
{
	private List<Line> chatMessages;
	private List<Line> gameHistory;
	
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
	public void SendMessage(String message, String source)
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
	public void LogAction(String message, String source)
	{
		Line line = new Line(message, source);
		gameHistory.add(line);
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
