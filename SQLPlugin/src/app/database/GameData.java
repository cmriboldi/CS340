package app.database;

import app.model.*;
/**
 * Created by jvanstee on 3/16/2016.
 */
public class GameData
{
    private int gameID;
    private String name;
    private CatanModel model;

    public GameData()
    {
        this.gameID = -1;
        this.name = null;
        this.model = null;
    }

    public GameData(int gameID, String name, CatanModel model)
    {
        this.gameID = gameID;
        this.name = name;
        this.model = model;
    }

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CatanModel getModel() {
        return model;
    }

    public void setModel(CatanModel model) {
        this.model = model;
    }
}
