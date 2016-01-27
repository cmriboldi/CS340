package model.development;

import shared.definitions.DevCardType;
import shared.exceptions.development.NotEnoughDevCardsException;

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
		if (getDevCardCount() == 0)
		{
			throw new NotEnoughDevCardsException("There are not enough development cards to remove one.");
		}

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

}
