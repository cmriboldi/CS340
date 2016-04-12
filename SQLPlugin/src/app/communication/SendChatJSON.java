package app.communication;

public class SendChatJSON extends IJavaJSON
{
	private int playerIndex;
	private String content;
	
	public SendChatJSON(int playerIndex, String content)
	{
		super("sendChat");
		this.playerIndex = playerIndex;
		this.content = content;
	}

	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
