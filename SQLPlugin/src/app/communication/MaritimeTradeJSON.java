package app.communication;

public class MaritimeTradeJSON extends IJavaJSON
{
	private int playerIndex;
	private int ratio;
	private String inputResource;
	private String outputResource;

	public MaritimeTradeJSON(int playerIndex, int ratio, String inputResource, String outputResource)
	{
		super("maritimeTrade");
		this.ratio = ratio;
		this.playerIndex = playerIndex;
		this.inputResource = inputResource;
		this.outputResource = outputResource;
	}

	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}

	public int getRatio() {
		return ratio;
	}

	public void setRatio(int ratio) {
		this.ratio = ratio;
	}

	public String getInputResource() {
		return inputResource;
	}

	public void setInputResource(String inputResource) {
		this.inputResource = inputResource;
	}

	public String getOutputResource() {
		return outputResource;
	}

	public void setOutputResource(String outputResource) {
		this.outputResource = outputResource;
	}
	
}
