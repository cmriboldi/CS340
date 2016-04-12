package app.communication;


import app.model.resources.ResourceList;

public class OfferTradeJSON extends IJavaJSON
{
	private int playerIndex;
	private ResourceList offer;
	private int receiver;

	public OfferTradeJSON(int playerIndex, ResourceList offer, int receiver)
	{
		super("offerTrade");
		this.playerIndex = playerIndex;
		this.offer = offer;
		this.receiver = receiver;
	}

	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}

	public ResourceList getOffer() {
		return offer;
	}

	public void setOffer(ResourceList offer) {
		this.offer = offer;
	}

	public int getReceiver() {
		return receiver;
	}

	public void setReceiver(int receiver) {
		this.receiver = receiver;
	}
}
