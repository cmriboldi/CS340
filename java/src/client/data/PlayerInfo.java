package client.data;

import shared.definitions.*;

/**
 * Used to pass player information into views<br>
 * <br>
 * PROPERTIES:<br>
 * <ul>
 * <li>Id: Unique player ID</li>
 * <li>PlayerIndex: Player's order in the game [0-3]</li>
 * <li>Name: Player's name (non-empty string)</li>
 * <li>Color: Player's color (cannot be null)</li>
 * </ul>
 * 
 */
public class PlayerInfo
{
	private String color;
	private String name;
	private int id;
	private int playerIndex;

	public PlayerInfo()
	{
		setId(-1);
		setPlayerIndex(-1);
		setName("");
	}
	
	public PlayerInfo(int id, String name, String color)
	{
		this.id = id;
		this.name = name;
		this.color = color;
		setPlayerIndex(-1);
	}
	
	public PlayerInfo(int id, int playerIndex, String name, String color)
	{
		this.id = id;
		this.playerIndex = playerIndex;
		this.name = name;
		this.color = color;
	}
	
	public int getId()
	{
		return id;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public int getPlayerIndex()
	{
		return playerIndex;
	}
	
	public void setPlayerIndex(int playerIndex)
	{
		this.playerIndex = playerIndex;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public CatanColor getColor()
	{
		return CatanColor.toCatanColor(color);
	}
	
	public void setColor(CatanColor color)
	{
		this.color = color.toString();
	}

	@Override
	public int hashCode()
	{
		return 31 * this.id;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		final PlayerInfo other = (PlayerInfo) obj;
		
		return this.id == other.id;
	}

	@Override
	public String toString()
	{
		return "{id: " + id +
				", playerIndex: " + 0 +
				", name: " + name +
				", color: " + color.toString() + "}";
	}
}

