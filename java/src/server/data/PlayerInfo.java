package server.data;

import shared.definitions.CatanColor;

/**
 * Created by Clayton on 3/21/2016.
 */
public class PlayerInfo
{
    private String color;
    private String name;
    private int id;

    public PlayerInfo()
    {
        setId(-1);
        setName("");
    }

    public PlayerInfo(int id, String name, String color)
    {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    public PlayerInfo(int id, int playerIndex, String name, String color)
    {
        this.id = id;
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
    public String toString()
    {
        return "{id: " + id +
                ", playerIndex: " + 0 +
                ", name: " + name +
                ", color: " + color.toString() + "}";
    }
}
