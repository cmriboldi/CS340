package server.database;

import client.data.GameInfo;
import server.data.PlayerInfo;
import model.CatanModel;
import model.players.Player;
import server.command.ICommand;
import server.data.UserData;

import java.util.*;

/**
 * Created by Joshua on 3/10/2016.
 */
public class VolatileDatabase implements IDatabase {
    private Map<Integer, UserData> players;
    private Map<String, Integer> playerKeys;
    private Map<Integer, GameData> games;
    private Map<Integer, Stack<ICommand>> commandStacks;
    private int playerIndex;
    private int gameIndex;

    public VolatileDatabase() {
        players = new HashMap<>();
        playerKeys = new HashMap<>();
        games = new HashMap<>();
        commandStacks = new HashMap<>();
        playerIndex = 0;
        gameIndex = 0;
        addGame("Default", new CatanModel(false, false, false));
        addUser(new UserData("string", "string"));
    }

    @Override
    public void addUser(UserData user) {
        user.setId(playerIndex);
        players.put(playerIndex, user);
        playerKeys.put(user.getName(), playerIndex);
        playerIndex++;
    }

    @Override
    public void addGame(String name, CatanModel game) {
        games.put(gameIndex, new GameData(gameIndex, name, game));
        commandStacks.put(gameIndex, new Stack<ICommand>());
        gameIndex++;
    }

    @Override
    public void addCommand(int gameID, ICommand command) {
        commandStacks.get(gameID).add(command);
    }

    @Override
    public GameInfo createGame(boolean randomTiles, boolean randomNumbers, boolean randomPorts, String name) {
        CatanModel newGame = new CatanModel(randomTiles, randomNumbers, randomPorts);
        List players = new ArrayList<>();
        Player[] playerArray = newGame.getPlayerManager().getCatanPlayers();

        for (int j = 0; j < playerArray.length; j++) {
            if (playerArray[j] != null) {
                players.add(new PlayerInfo(playerArray[j].getId(), playerArray[j].getName(), playerArray[j].getColor().toString()));
            } else {
                players.add(new Object());
            }
        }
        GameInfo game = new GameInfo(gameIndex, name, players);
        addGame(name, newGame);

        return game;
    }

    @Override
    public UserData getUserById(int id) {
        return players.get(id);
    }

    @Override
    public UserData getUserByName(String name) {
        if (playerKeys.get(name) == null) {
            return null;
        }

        int key = playerKeys.get(name);
        return players.get(key);
    }

    @Override
    public CatanModel getGameModel(int gameId) {
        return games.get(gameId).getModel();
    }

    @Override
    public void updateGameModel(int gameId, CatanModel model) {
        GameData oldGame = games.get(gameId);
        games.remove(gameId);
        games.put(gameId, new GameData(oldGame.getGameID(), oldGame.getName(), model));
    }

    @Override
    public void deleteGameModel(int gameId) {
        games.remove(gameId);
    }

    @Override
    public GameInfo[] listGames() {
        List<GameInfo> gameList = new ArrayList<>();

        for (int i = 0; i < games.size(); i++) {
            List players = new ArrayList<>();
            Player[] playerArray = this.games.get(i).getModel().getPlayerManager().getCatanPlayers();

            for (int j = 0; j < playerArray.length; j++) {
                if (playerArray[j] != null) {
                    players.add(new PlayerInfo(playerArray[j].getId(), -1, playerArray[j].getName(), playerArray[j].getColor().toString()));
                } else {
                    players.add(new Object());
                }
            }
            gameList.add(new GameInfo(i, this.games.get(i).getName(), players));
        }

        GameInfo[] gameArray = new GameInfo[gameList.size()];
        for (int i = 0; i < gameList.size(); i++) {
            gameArray[i] = gameList.get(i);
        }

        return gameArray;
    }
}
