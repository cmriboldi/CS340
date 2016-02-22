package shared.communication.JSON;

import model.resources.ResourceList;

public class OfferTradeJSON 
{
	@SuppressWarnings("unused")
	private String type;
	private int playerIndex;
	private ResourceList offer;
	private int receiver;

	public OfferTradeJSON(int playerIndex, ResourceList offer, int receiver)
	{
		this.type = "offerTrade";
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
