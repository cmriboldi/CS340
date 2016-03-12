package server.data;

/**
 * Created by Joshua on 3/11/2016.
 */
public class UserInfo
{
    private int id;
    private String name;
    private String password;

    public UserInfo()
    {

    }

    public UserInfo(String name, String password)
    {
        this.name = name;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toJSON()
    {
        return "{\"name:\":\"" + name + "\",\"password\":\"" + password + "\",\"playerID\":" + id + "}";
    }
}
