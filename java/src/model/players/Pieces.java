package model.players;

/** The Pieces class keeps track of how many settlements, cities, and roads a player has.
* @author Christian Riboldi
* @author Clayton Condie
* @author Jacob Brewer
* @author Joshua Powers
* @author Joshua Van Steeter
* @version 1.0 Build Jan, 2016.
*/
import shared.definitions.PieceType;

public class Pieces
{
	private int settlements;
	private int cities;
	private int roads;

	public Pieces()
	{
		this.settlements = 5;
		this.cities = 4;
		this.roads = 15;
	}

	/**
	 * Adds a piece of the given type to the Pieces.
	 * 
	 * @param piece The type of piece being added. City, Settlement, or road.
	 */
	public void addPiece(PieceType piece)
	{
		switch (piece)
		{
		case ROAD:
			this.roads++;
			break;
		case SETTLEMENT:
			this.settlements++;
			break;
		case CITY:
			this.cities++;
			break;
		default:
			break;
		}
	}

	/**
	 * Removes a piece of the given type to the Pieces.
	 * 
	 * @param piece The type of piece being removed. City, Settlement, or road.
	 */
	public void removePiece(PieceType piece)
	{
		switch (piece)
		{
		case ROAD:
			this.roads--;
			break;
		case SETTLEMENT:
			this.settlements--;
			break;
		case CITY:
			this.cities--;
			break;
		default:
			break;
		}
	}

	public int getSettlements()
	{
		return settlements;
	}

	public int getCities()
	{
		return cities;
	}

	public int getRoads()
	{
		return roads;
	}

	public void setSettlements(int settlements) {
		this.settlements = settlements;
	}

	public void setCities(int cities) {
		this.cities = cities;
	}

	public void setRoads(int roads) {
		this.roads = roads;
	}

	public Pieces(int settlements, int cities, int roads) {
		super();
		this.settlements = settlements;
		this.cities = cities;
		this.roads = roads;
	}

	public boolean hasPiece(PieceType piece)
	{
		boolean hasPiece = false;
		
		switch (piece)
		{
		case ROAD:
			hasPiece = this.roads > 0 ? true : false;
			break;
		case SETTLEMENT:
			hasPiece = this.settlements > 0 ? true : false;
			break;
		case CITY:
			hasPiece = this.cities > 0 ? true : false;
			break;
		default:
			hasPiece = false;
			break;
		}
		return hasPiece;
	}

	
	
}
