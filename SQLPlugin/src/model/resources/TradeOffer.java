package model.resources;

public class TradeOffer
{
	private ResourceList resourcesOffer;
	private int sender;
	private int receiver;
	
	public TradeOffer()
	{
		this.sender = -1;
		this.receiver = -1;
		this.resourcesOffer = new ResourceList();
	}
	
	public TradeOffer(ResourceList resourcesOffer, int sender, int receiver)
	{
		this.setResourcesOffer(resourcesOffer);
		this.setSender(sender);
		this.setReceiver(receiver);
	}

	public int getSender()
	{
		return sender;
	}

	public void setSender(int sender)
	{
		this.sender = sender;
	}

	public int getReceiver()
	{
		return receiver;
	}

	public void setReceiver(int receiver)
	{
		this.receiver = receiver;
	}

	public ResourceList getResourcesOffer()
	{
		return resourcesOffer;
	}

	public void setResourcesOffer(ResourceList resourcesOffer)
	{
		this.resourcesOffer = resourcesOffer;
	}
	
}
