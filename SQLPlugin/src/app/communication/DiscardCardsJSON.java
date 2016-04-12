package app.communication;


import app.model.resources.ResourceList;

public class DiscardCardsJSON extends IJavaJSON
{
	private int playerIndex;
	private ResourceList discardedCards;

	public DiscardCardsJSON(int playerIndex, ResourceList discardedCards)
	{
		super("discardCards");
		this.playerIndex = playerIndex;
		this.discardedCards = discardedCards;
	}

	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}

	public ResourceList getDiscardedCards() {
		return discardedCards;
	}

	public void setDiscardedCards(ResourceList discardedCards) {
		this.discardedCards = discardedCards;
	}
}
