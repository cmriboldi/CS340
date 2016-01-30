package shared.communication.JSON;

public class SendChatJSON 
{
	@SuppressWarnings("unused")
	private String type;
	private int playerIndex;
	private String content;
	
	public SendChatJSON(int playerIndex, String content)
	{
		this.type = "sendChat";
		this.playerIndex = playerIndex;
		this.content = content;
	}

	public int getSendChat() {
		return playerIndex;
	}

	public void setSendChat(int sendChat) {
		this.playerIndex = sendChat;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
