import app.database.GameData;
import app.exception.*;
import app.model.CatanModel;
import app.plugin.IGameDAO;
import app.serial.JSONDeserializer;
import app.serial.JSONSerializer;
import app.server.AuthToken;
import com.google.gson.Gson;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joshua on 4/3/2016.
 */
public class SQLGameDAO implements IGameDAO
{
    private final SQLPlugin database;

    public SQLGameDAO(SQLPlugin database)
    {
        this.database = database;
    }

    @Override
    public void addGame(GameData game) throws DatabaseException
    {
        try
        {
            PreparedStatement pstmt = database.getConnection().prepareStatement(SQLQuery.addGame());
            pstmt.setString(1, game.getName());
            pstmt.setInt(2, game.getModel().getVersion());
            System.out.println("Attempting to add: " + JSONSerializer.serialize(game.getModel()).replace("\\", "\\\\"));
            byte[] data = JSONSerializer.serialize(game.getModel()).replace("\\", "\\\\").getBytes();
            pstmt.setBytes(3, data);

            if(pstmt.executeUpdate() != 1)
                throw new DatabaseException("Cannot add game");
            pstmt.close();
        }
        catch (SQLException e)
        {
            throw new DatabaseException(e.getMessage());
        }
        catch (NullPointerException e)
        {
            throw new DatabaseException("Transaction not started: " + e.getMessage());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public GameData getGame(int gameID) throws DatabaseException
    {
        GameData game = null;
        try
        {
            Statement statement = database.getConnection().createStatement();

            ResultSet result = statement.executeQuery(SQLQuery.getGame(gameID + 1));
            if(result.next())
            {
                int id = result.getInt(1) - 1;
                String game_name = result.getString(2);
                CatanModel model = JSONDeserializer.deserialize(new String(result.getBytes(4)));
            }
            statement.close();
        }
        catch (SQLException e)
        {
            throw new DatabaseException(e.getMessage());
        }
        catch (InvalidTurnStatusException e)
        {
            e.printStackTrace();
        }
        catch (GeneralPlayerException e)
        {
            e.printStackTrace();
        }
        catch (TurnIndexException e)
        {
            e.printStackTrace();
        }
        return game;
    }

    @Override
    public GameData[] getAllGames() throws DatabaseException
    {
        GameData[] games = null;
        try
        {
            Statement statement = database.getConnection().createStatement();

            ResultSet result = statement.executeQuery(SQLQuery.getAllGames());
            List<GameData> list = new ArrayList<>();
            while(result.next())
            {
                int id = result.getInt(1) - 1;
                String game_name = result.getString(2);
                CatanModel model = JSONDeserializer.deserialize(new String(result.getBytes(3)));
                GameData newGame = new GameData(id, game_name, model);
                list.add(newGame);
            }

            games = new GameData[list.size()];
            for(int i = 0; i < list.size(); i++)
            {
                games[i] = list.get(i);
            }
            statement.close();
        }
        catch (SQLException e)
        {
            throw new DatabaseException(e.getMessage());
        }
        catch (InvalidTurnStatusException e)
        {
            e.printStackTrace();
        }
        catch (GeneralPlayerException e)
        {
            e.printStackTrace();
        }
        catch (TurnIndexException e)
        {
            e.printStackTrace();
        }

        return games;
    }

    @Override
    public void updateGame(int gameID) throws DatabaseException
    {
        try
        {
            CatanModel newVersion = database.getFacade().getGameModel(new AuthToken("", "", -1, gameID));
            PreparedStatement pstmt = database.getConnection().prepareStatement(SQLQuery.updateGame());
            pstmt.setInt(1, newVersion.getVersion());
            byte[] data = new Gson().toJson(JSONSerializer.serialize(newVersion)).getBytes();
            pstmt.setBytes(2, data);
            pstmt.setInt(3, gameID + 1);

            if(pstmt.executeUpdate() != 1)
                throw new DatabaseException("Cannot update game");
            else
            {
                Statement statement = database.getConnection().createStatement();
                statement.execute(SQLQuery.deleteAllCommandsForGame(gameID + 1));
                statement.close();
            }
            pstmt.close();
        }
        catch (ServerException e)
        {
            throw new DatabaseException(e.getMessage());
        }
        catch (SQLException e)
        {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public void deleteGame(int gameID) throws DatabaseException
    {
        try
        {
            Statement statement = database.getConnection().createStatement();
            statement.execute(SQLQuery.deleteGame(gameID + 1));
            statement.close();
        }
        catch (SQLException e)
        {
            throw new DatabaseException(e.getMessage());
        }
    }
}
