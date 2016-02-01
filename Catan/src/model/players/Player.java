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

	/** The name of this player */
	private String name;

	/** Integer indication of this players turn index */
	private int playerIndex;

	/** A unique ID to distinguish from other players */
	private IdNumber id;

	private int settlementsRemaining;
	
	private int citiesRemaining; 
	
	private int roadsRemaining; 
	
	

	public int getSettlementsRemaining() {
		return settlementsRemaining;
	}

	public void setSettlementsRemaining(int settlementsRemaining) {
		this.settlementsRemaining = settlementsRemaining;
	}

	public int getCitiesRemaining() {
		return citiesRemaining;
	}

	public void setCitiesRemaining(int citiesRemaining) {
		this.citiesRemaining = citiesRemaining;
	}

	public int getRoadsRemaining() {
		return roadsRemaining;
	}

	public void setRoadsRemaining(int roadsRemaining) {
		this.roadsRemaining = roadsRemaining;
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


	/*
	/** A list of the pieces the player can still play 
	private Pieces pieces;

	public Pieces getPieces() {
		return pieces;
	}

	public void setPieces(Pieces pieces) {
		this.pieces = pieces;
	}*/


}