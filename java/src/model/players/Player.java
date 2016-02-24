package model.players;

import java.util.ArrayList;
import shared.definitions.CatanColor;
import shared.exceptions.player.InvalidColorException;
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

	/** The name of this player */
	private String name;

	/** Integer indication of this players turn index */
	private int playerIndex;

	/** A unique ID to distinguish from other players */
	private int id;
	
	/** A list of the pieces the player can still play*/
	private Pieces pieces;

	public Player()
	{
		this.pieces = new Pieces();
	}
	
	public int getSettlementsRemaining() {
		return pieces.getSettlements();
	}

	public void setSettlementsRemaining(int settlementsRemaining) {
		pieces.setSettlements(settlementsRemaining);
	}

	public int getCitiesRemaining() {
		return pieces.getCities();
	}

	public void setCitiesRemaining(int citiesRemaining) {
		pieces.setCities(citiesRemaining);
	}

	public int getRoadsRemaining() {
		return pieces.getRoads();
	}

	public void setRoadsRemaining(int roadsRemaining) {
		pieces.setRoads(roadsRemaining);
	}

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
	public int getId()
	{
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id)
	{
		this.id = id;
	}
	
	public Pieces getPieces() 
	{
		return pieces;
	}

	public void setPieces(Pieces pieces) 
	{
		this.pieces = pieces;
	}

	public void setColor(String color) throws InvalidColorException
	{
		//red, green, blue, yellow, puce, brown, white, purple, orange are the valid colors.
		switch (color)
		{
		case "red":
			this.color = CatanColor.RED;
			break;
		case "green":
			this.color = CatanColor.GREEN;
			break;
		case "blue":
			this.color = CatanColor.BLUE;
			break;
		case "yellow":
			this.color = CatanColor.YELLOW;
			break;
		case "puce":
			this.color = CatanColor.PUCE;
			break;
		case "brown":
			this.color = CatanColor.BROWN;
			break;
		case "white":
			this.color = CatanColor.WHITE;
			break;
		case "purple":
			this.color = CatanColor.PURPLE;
			break;
		case "orange":
			this.color = CatanColor.ORANGE;
			break;
		default:
			throw new InvalidColorException();
		}
	}

}