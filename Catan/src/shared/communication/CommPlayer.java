package shared.communication;

/**
 * Communication object transferring information on players
 * 
 * @author Christian Riboldi
 * @author Clayton Condie
 * @author Jacob Brewer
 * @author Joshua Powers
 * @author Joshua Van Steeter
 * @version 1.0 Build Winter 2016.
 */
public class CommPlayer
{
	private String color;
	private String name;
	private int id;

	/**
	 * 
	 */
	public CommPlayer()
	{

	}

	/**
	 * @param color
	 * @param name
	 * @param id
	 */
	public CommPlayer(String color, String name, int id)
	{
		this.color = color;
		this.name = name;
		this.id = id;
	}

	public String getColor()
	{
		return color;
	}

	public void setColor(String color)
	{
		this.color = color;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}
}
