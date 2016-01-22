package model.players;

import java.util.ArrayList;
import shared.definitions.CatanColor;
import shared.communication.*;

/**
 * The Player class contains all necessary information about a given player
 * 
 * @author Christian Riboldi
 * @author Clayton Condie
 * @author Jacob Brewer
 * @author Joshua Powers
 * @author Joshua Van Steeter
 * @version 1.0 Build Jan, 2016.
 */
public class Player
{

	/** boolean value indicating whether or not this player currently possesses the longest road */
	private boolean longestRoad;

	/** boolean value indicating whether or not this player currently possesses the largest army */
	private boolean largestArmy;

	/** an integer count of how many points this player possesses */
	private int points;

	/** The color of this player's pieces */
	private CatanColor color;

	/** boolean indication of whether or not this player has already discarded cards this turn */
	private boolean discarded;

	/** Count of this player's monuments */
	private int monuments;

	/** The name of this player */
	private String name;

	/** Integer indication of this players turn index */
	private int playerIndex;

	/** A unique ID to distinguish from other players */
	private IdNumber id;

	/** A list of the pieces the player can still play */
	private Pieces piecesRemaining;

	/**
	 * @return the longestRoad
	 */
	public boolean isLongestRoad()
	{
		return longestRoad;
	}

	/**
	 * @param longestRoad the longestRoad to set
	 */
	public void setLongestRoad(boolean longestRoad)
	{
		this.longestRoad = longestRoad;
	}

	/**
	 * @return the largestArmy
	 */
	public boolean isLargestArmy()
	{
		return largestArmy;
	}

	/**
	 * @param largestArmy the largestArmy to set
	 */
	public void setLargestArmy(boolean largestArmy)
	{
		this.largestArmy = largestArmy;
	}

	/**
	 * @return the points
	 */
	public int getPoints()
	{
		return points;
	}

	/**
	 * @param points the points to set
	 */
	public void setPoints(int points)
	{
		this.points = points;
	}

	/**
	 * @return the color
	 */
	public CatanColor getColor()
	{
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(CatanColor color)
	{
		this.color = color;
	}

	/**
	 * @return the discarded
	 */
	public boolean isDiscarded()
	{
		return discarded;
	}

	/**
	 * @param discarded the discarded to set
	 */
	public void setDiscarded(boolean discarded)
	{
		this.discarded = discarded;
	}

	/**
	 * @return the monuments
	 */
	public int getMonuments()
	{
		return monuments;
	}

	/**
	 * @param monuments the monuments to set
	 */
	public void setMonuments(int monuments)
	{
		this.monuments = monuments;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return the playerIndex
	 */
	public int getPlayerIndex()
	{
		return playerIndex;
	}

	/**
	 * @param playerIndex the playerIndex to set
	 */
	public void setPlayerIndex(int playerIndex)
	{
		this.playerIndex = playerIndex;
	}

	/**
	 * @return the id
	 */
	public IdNumber getId()
	{
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(IdNumber id)
	{
		this.id = id;
	}

	/**
	 * @return the citiesRemaining
	 */
	public int getCitiesRemaining()
	{
		return piecesRemaining.getCities();
	}

	/**
	 * @return the roadsRemaining
	 */
	public int getRoadsRemaining()
	{
		return piecesRemaining.getSettlements();
	}

	/**
	 * @return the settlementsRemaining
	 */
	public int getSettlementsRemaining()
	{
		return piecesRemaining.getSettlements();
	}

}