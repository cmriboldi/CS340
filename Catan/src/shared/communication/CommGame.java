package shared.communication;

/**
 * The communication object used to transfer information about games
 * @author Christian Riboldi
 * @author Clayton Condie
 * @author Jacob Brewer
 * @author Joshua Powers
 * @author Joshua Van Steeter
 * @version 1.0 Build Winter 2016.
 */
public class CommGame 
{
	private String title;
	private int id;
	private CommPlayer[] players;
	
	/**
	 * 
	 */
	public CommGame()
	{
		
	}

	/**
	 * @param title Name of the game
	 * @param id Game ID
	 * @param players Array of players in the game
	 */
	public CommGame(String title, int id, CommPlayer[] players)
	{
		this.title = title;
		this.id = id;
		this.players = players;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public CommPlayer[] getPlayers() {
		return players;
	}

	public void setPlayers(CommPlayer[] players) {
		this.players = players;
	}
}
