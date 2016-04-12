package model.development;


import definitions.DevCardType;
import exception.NotEnoughDevCardsException;

/**
 * The DevCardList in an object that keeps track of how many of each development cards it contains.
 * 
 * @author Christian Riboldi
 * @author Clayton Condie
 * @author Jacob Brewer
 * @author Joshua Powers
 * @author Joshua Van Steeter
 * @version 1.0 Build Jan, 2016.
 */
public class DevCardList
{

	private int monopoly;
	private int monument;
	private int roadBuilder;
	private int soldier;
	private int yearOfPlenty;

	public DevCardList()
	{
		monopoly = 0;
		monument = 0;
		roadBuilder = 0;
		soldier = 0;
		yearOfPlenty = 0;
	}

	public DevCardList(int monopoly, int monument, int roadBuilder, int soldier, int yearOfPlenty)
	{
		this.monopoly = monopoly;
		this.monument = monument;
		this.roadBuilder = roadBuilder;
		this.soldier = soldier;
		this.yearOfPlenty = yearOfPlenty;
	}

	/**
	 * Adds a development card of the given type to the DevCardList.
	 * 
	 * @param devCard A DevCardType specifying the card to add.
	 */
	public void addDevCard(DevCardType devCard)
	{
		switch (devCard)
		{
		case MONOPOLY:
			monopoly++;
			break;
		case MONUMENT:
			monument++;
			break;
		case ROAD_BUILD:
			roadBuilder++;
			break;
		case SOLDIER:
			soldier++;
			break;
		case YEAR_OF_PLENTY:
			yearOfPlenty++;
			break;
		}
	}

	/**
	 * Removed a development card of the given type from the DevCardList.
	 * 
	 * @param devCard A DevCardType specifying the card to remove.
	 */
	public void removeDevCard(DevCardType devCard) throws NotEnoughDevCardsException
	{
		switch (devCard)
		{
		case MONOPOLY:
			if (monopoly == 0)
			{
				throw new NotEnoughDevCardsException("There are not enough monopoly cards to remove one.");
			}
			monopoly--;
			break;
		case MONUMENT:
			if (monument == 0)
			{
				throw new NotEnoughDevCardsException("There are not enough monument cards to remove one.");
			}
			monument--;
			break;
		case ROAD_BUILD:
			if (roadBuilder == 0)
			{
				throw new NotEnoughDevCardsException("There are not enough road builder cards to remove one.");
			}
			roadBuilder--;
			break;
		case SOLDIER:
			if (soldier == 0)
			{
				throw new NotEnoughDevCardsException("There are not enough soldier cards to remove one.");
			}
			soldier--;
			break;
		case YEAR_OF_PLENTY:
			if (yearOfPlenty == 0)
			{
				throw new NotEnoughDevCardsException("There are not enough year of plenty cards to remove one.");
			}
			yearOfPlenty--;
			break;
		}
	}
	
	/**
	 * Check if the given player has the given development card.
	 * 
	 * @param playerIndex
	 * @param devCard
	 * @return
	 */
	public boolean hasDevCard(DevCardType devCard)
	{
		boolean hasCard = false;
		switch (devCard)
		{
		case MONOPOLY:
			if (monopoly > 0)
			{
				hasCard = true;
			}
			break;
		case MONUMENT:
			if (monument > 0)
			{
				hasCard = true;
			}
			break;
		case ROAD_BUILD:
			if (roadBuilder > 0)
			{
				hasCard = true;
			}
			break;
		case SOLDIER:
			if (soldier > 0)
			{
				hasCard = true;
			}
			break;
		case YEAR_OF_PLENTY:
			if (yearOfPlenty > 0)
			{
				hasCard = true;
			}
			break;
		}
		return hasCard;
	}
	
	public int getCardTypeCount(DevCardType devCard)
	{
		int cardCount = 0;
		switch (devCard)
		{
		case MONOPOLY:
			cardCount = getMonopolyCount();
			break;
		case MONUMENT:
			cardCount = getMonumentCount();
			break;
		case ROAD_BUILD:
			cardCount = getRoadBuilderCount();
			break;
		case SOLDIER:
			cardCount = getSoldierCount();
			break;
		case YEAR_OF_PLENTY:
			cardCount = getYearOfPlentyCount();
			break;
		}
		return cardCount;
	}

	public int getMonopolyCount()
	{
		return monopoly;
	}

	public int getMonumentCount()
	{
		return monument;
	}

	public int getRoadBuilderCount()
	{
		return roadBuilder;
	}

	public int getSoldierCount()
	{
		return soldier;
	}

	public int getYearOfPlentyCount()
	{
		return yearOfPlenty;
	}

	public int getDevCardCount()
	{
		return monopoly + monument + roadBuilder + soldier + yearOfPlenty;
	}

	public void setMonopoly(int monopoly)
	{
		this.monopoly = monopoly;
	}

	public void setMonument(int monument)
	{
		this.monument = monument;
	}

	public void setRoadBuilder(int roadBuilder)
	{
		this.roadBuilder = roadBuilder;
	}

	public void setSoldier(int soldier)
	{
		this.soldier = soldier;
	}

	public void setYearOfPlenty(int yearOfPlenty)
	{
		this.yearOfPlenty = yearOfPlenty;
	}

	public void minus(PlayerDevCards playerDevCards)
	{
		for(int i = 0; i < 4; i++)
		{
			this.minus(playerDevCards.getDevCardsForPlayer(i));
		}
	}
	
	public void minus(DevCardList devCards)
	{
		this.monopoly -= devCards.monopoly;
		this.monument -= devCards.monument;
		this.roadBuilder -= devCards.roadBuilder;
		this.soldier -= devCards.soldier;
		this.yearOfPlenty -= devCards.yearOfPlenty;
	}

	public void plus(DevCardList devCards)
	{
		this.monopoly += devCards.monopoly;
		this.monument += devCards.monument;
		this.roadBuilder += devCards.roadBuilder;
		this.soldier += devCards.soldier;
		this.yearOfPlenty += devCards.yearOfPlenty;
	}

	@Override
	public String toString()
	{
		return "DevCardList [monopoly=" + monopoly + ", monument=" + monument + ", roadBuilder=" + roadBuilder
				+ ", soldier=" + soldier + ", yearOfPlenty=" + yearOfPlenty + "]";
	}

}
