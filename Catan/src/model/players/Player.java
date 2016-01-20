package model.players;

import java.util.ArrayList;

/** The Player class contains all necessary information about a given player
* @author Christian Riboldi
* @author Clayton Condie
* @author Jacob Brewer
* @author Joshua Powers
* @author Joshua Van Steeter
* @version 1.0 Build Jan, 2016.
*/
public class Player {

	
	/** boolean value indicating whether or not this player currently possesses the longest road */
	boolean longestRoad; 
	
	/** boolean value indicating whether or not this player currently possesses the largest army */
	boolean largestArmy; 
	
	/** an integer count of how many points this player possesses */
	int points; 
	
	/** This is a pointer to this player's resource bank account */ 
	Object bankAccount; // TODO: update with Christian's class
	
	/** The color of this player's pieces */
	String color; 
	
	/** boolean indication of whether or not this player has already discarded cards this turn */ 
	boolean discarded; 
	
	/** Count of this player's monuments */ 
	int monuments; 
	
	/** The name of this player */ 
	String name; 
	
	/** An ArrayList of the new dev cards this player purchased this turn */ 
	ArrayList<Object> newDevCards; 
	
	/** An ArrayList of the dev cards this player had previous to this turn */ 
	ArrayList<Object> oldDevCards; 
	
	/** Integer indication of this players turn index */ 
	int playerIndex; 
	
	/** Boolean indication of whether or this player has played a dev card this turn */ 
	boolean playedDevCard; 
	
	/** A unique ID to distinguish from other players */
	int id; 
	
	/** Integer Count of the number of cities that remain for this player */ 
	int citiesRemaining; 
	
	/** Integer Count of the number of roads that remain for this player */ 
	int roadsRemaining; 
	
	/** Integer Count of the number of settlements that remain for this player */ 
	int settlementsRemaining; 
	
	/** number of soldiers that this player possesses */ 
	int soldiers;

	/**
	 * @return the longestRoad
	 */
	public boolean isLongestRoad() {
		return longestRoad;
	}

	/**
	 * @param longestRoad the longestRoad to set
	 */
	public void setLongestRoad(boolean longestRoad) {
		this.longestRoad = longestRoad;
	}

	/**
	 * @return the largestArmy
	 */
	public boolean isLargestArmy() {
		return largestArmy;
	}

	/**
	 * @param largestArmy the largestArmy to set
	 */
	public void setLargestArmy(boolean largestArmy) {
		this.largestArmy = largestArmy;
	}

	/**
	 * @return the points
	 */
	public int getPoints() {
		return points;
	}

	/**
	 * @param points the points to set
	 */
	public void setPoints(int points) {
		this.points = points;
	}

	/**
	 * @return the bankAccount
	 */
	public Object getBankAccount() {
		return bankAccount;
	}

	/**
	 * @param bankAccount the bankAccount to set
	 */
	public void setBankAccount(Object bankAccount) {
		this.bankAccount = bankAccount;
	}

	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * @return the discarded
	 */
	public boolean isDiscarded() {
		return discarded;
	}

	/**
	 * @param discarded the discarded to set
	 */
	public void setDiscarded(boolean discarded) {
		this.discarded = discarded;
	}

	/**
	 * @return the monuments
	 */
	public int getMonuments() {
		return monuments;
	}

	/**
	 * @param monuments the monuments to set
	 */
	public void setMonuments(int monuments) {
		this.monuments = monuments;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the newDevCards
	 */
	public ArrayList<Object> getNewDevCards() {
		return newDevCards;
	}

	/**
	 * @param newDevCards the newDevCards to set
	 */
	public void setNewDevCards(ArrayList<Object> newDevCards) {
		this.newDevCards = newDevCards;
	}

	/**
	 * @return the oldDevCards
	 */
	public ArrayList<Object> getOldDevCards() {
		return oldDevCards;
	}

	/**
	 * @param oldDevCards the oldDevCards to set
	 */
	public void setOldDevCards(ArrayList<Object> oldDevCards) {
		this.oldDevCards = oldDevCards;
	}

	/**
	 * @return the playerIndex
	 */
	public int getPlayerIndex() {
		return playerIndex;
	}

	/**
	 * @param playerIndex the playerIndex to set
	 */
	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}

	/**
	 * @return the playedDevCard
	 */
	public boolean isPlayedDevCard() {
		return playedDevCard;
	}

	/**
	 * @param playedDevCard the playedDevCard to set
	 */
	public void setPlayedDevCard(boolean playedDevCard) {
		this.playedDevCard = playedDevCard;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the citiesRemaining
	 */
	public int getCitiesRemaining() {
		return citiesRemaining;
	}

	/**
	 * @param citiesRemaining the citiesRemaining to set
	 */
	public void setCitiesRemaining(int citiesRemaining) {
		this.citiesRemaining = citiesRemaining;
	}

	/**
	 * @return the roadsRemaining
	 */
	public int getRoadsRemaining() {
		return roadsRemaining;
	}

	/**
	 * @param roadsRemaining the roadsRemaining to set
	 */
	public void setRoadsRemaining(int roadsRemaining) {
		this.roadsRemaining = roadsRemaining;
	}

	/**
	 * @return the settlementsRemaining
	 */
	public int getSettlementsRemaining() {
		return settlementsRemaining;
	}

	/**
	 * @param settlementsRemaining the settlementsRemaining to set
	 */
	public void setSettlementsRemaining(int settlementsRemaining) {
		this.settlementsRemaining = settlementsRemaining;
	}

	/**
	 * @return the soldiers
	 */
	public int getSoldiers() {
		return soldiers;
	}

	/**
	 * @param soldiers the soldiers to set
	 */
	public void setSoldiers(int soldiers) {
		this.soldiers = soldiers;
	} 
	
	
	
	
}



/*

 ** cities (number): How many cities this player has left to play,
 **  color (string): The color of this player.,
 ** discarded (boolean): Whether this player has discarded or not already this discard phase.,
 ** monuments (number): How many monuments this player has played.,
 ** name (string),
 ** newDevCards (DevCardList): The dev cards the player bought this turn.,
 ** oldDevCards (DevCardList): The dev cards the player had when the turn started.,
 ** playerIndex (index): What place in the array is this player? 0-3. It determines their turn order.
 ** This is used often everywhere.,
 ** playedDevCard (boolean): Whether the player has played a dev card this turn.,
 ** playerID (integer): The unique playerID. This is used to pick the client player apart from the
 ** others. This is only used here and in your cookie.,
 ** resources (ResourceList): The resource cards this player has.,
 ** roads (number),
 ** settlements (integer),
 ** soldiers (integer),
 ** victoryPoints (integer)

*/