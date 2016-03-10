package server;

/**
 * Created by Joshua on 3/9/2016.
 */
public class AuthToken
{
    private String name;
    private String password;
    private int playerID;
    private int gameID;

    public AuthToken()
    {

    }

    public AuthToken(String name, String password, int playerID, int gameID)
    {
        this.name = name;
        this.password = password;
        this.playerID = playerID;
        this.gameID = gameID;
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

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }
}
