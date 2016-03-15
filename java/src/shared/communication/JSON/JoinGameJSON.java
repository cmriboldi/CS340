package shared.communication.JSON;

/**
 * Created by Joshua on 3/12/2016.
 */
public class JoinGameJSON
{
    private int id;
    private String color;

    public JoinGameJSON()
    {

    }

    public JoinGameJSON(int id, String color)
    {
        this.id = id;
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

    public String getColor()
    {
        return color;
    }

    public void setColor(String color)
    {
        this.color = color;
    }
}
