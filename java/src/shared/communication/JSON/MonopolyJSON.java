package shared.communication.JSON;

public class MonopolyJSON 
{
	@SuppressWarnings("unused")
	private String type;
	private String resource;
	private int playerIndex;
	
	public MonopolyJSON(int playerIndex, String resource)
	{
		this.type = "Monopoly";
		this.resource = resource;
		this.playerIndex = playerIndex;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}

}
