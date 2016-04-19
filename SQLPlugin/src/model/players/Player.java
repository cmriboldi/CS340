package model.players;


import definitions.CatanColor;
import definitions.PieceType;
import shared.exceptions.player.InvalidColorException;

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
	private boolean longestRoad = false;

	/** boolean value indicating whether or not this player currently possesses the largest army */
	private boolean largestArmy = false;

	/** an integer count of how many points this player possesses */
	private int points = -1;

	/** The color of this player's pieces */
	private CatanColor color = CatanColor.WHITE;

	/** The name of this player */
	private String name = "uninitializedName";

	/** Integer indication of this players turn index */
	private int playerIndex = -1;

	/** A unique ID to distinguish from other players */
	private int id = -1;
	
	/** A list of the pieces the player can still play*/
	private Pieces pieces = new Pieces();

	public Player()
	{
		this.pieces = new Pieces();
		this.name = null; // check to see if name is null. If it's null then this player hasn't been initialized. 
	}
	
	//First Log In Constructor
	public Player(String playerName, int playerID, CatanColor selectedColor, int playerIndex)
	{
		this.pieces = new Pieces();
		this.name = playerName; 
		this.id = playerID; 
		this.color = selectedColor; 
		this.longestRoad = false; 
		this.largestArmy = false; 
		this.points = 0; 
		this.playerIndex = playerIndex; 
		
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

	public boolean hasPiece(PieceType piece)
	{
		return this.pieces.hasPiece(piece);
	}

	public void incrementPoints()
	{
		this.points++;
	}

	public void decrementPieceCount(PieceType piece)
	{
		this.pieces.removePiece(piece);
	}

	public void incrementPieceCount(PieceType piece)
	{
		this.pieces.addPiece(piece);
	}

	public void decrementPoints()
	{
		this.points--;
	}

}