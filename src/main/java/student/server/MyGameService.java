package student.server;

import java.sql.*;
import java.util.*;

import student.adventure.GameEngine;

public class MyGameService implements AdventureService {

    private int idCounter;
    private final Map<Integer, GameStatus> gamesList;
    private final GameEngine engine;
    private final static String DATABASE_URL = "jdbc:sqlite:src/main/resources/adventure.db";
    private Connection dbConnection = null;

    public MyGameService() {
        idCounter = 0;
        gamesList = new HashMap<>();
        engine = new GameEngine("src/main/resources/adventuremap.json", true);
        try {
            dbConnection = DriverManager.getConnection(DATABASE_URL);
        } catch (Exception e) {
            System.out.println("SQLException");
        }
    }

    /**
     * Resets the service to its initial state.
     */
    @Override
    public void reset() {
        idCounter = 0;
        gamesList.clear();
    }

    /**
     * Creates a new Adventure game and stores it.
     *
     * @return the id of the game.
     */
    @Override
    public int newGame() throws AdventureException {
        try {
            GameStatus newGame = new GameStatus(false, idCounter, engine.getBackgroundStory(),
                    "", "", engine.getStartingState(), engine.getCommandOptions(), engine.getStartingInv(),
                    engine.getStartingRoomList());
            gamesList.put(idCounter, newGame);
        } catch (Exception e) {
            throw new AdventureException("New game could not be created");
        }
        return idCounter++;
    }

    /**
     * Returns the state of the game instance associated with the given ID.
     *
     * @param id the instance id
     * @return the current state of the game
     */
    @Override
    public GameStatus getGame(int id) {
        return gamesList.get(id);
    }

    /**
     * Removes & destroys a game instance with the given ID.
     *
     * @param id the instance id
     * @return false if the instance could not be found and/or was not deleted
     */
    @Override
    public boolean destroyGame(int id) {
        if (gamesList.containsKey(id)) {
            gamesList.remove(id);
            return true;
        }
        return false;
    }

    /**
     * Executes a command on the game instance with the given id, changing the game state if
     * applicable.
     *
     * @param id      the instance id
     * @param command the issued command
     */
    @Override
    public void executeCommand(int id, Command command) {
        GameStatus newGameStatus = engine.executeCommand(gamesList.get(id), command);
        gamesList.replace(id, newGameStatus);
        if (newGameStatus.getMessage().equals("correct")) {
            insertIntoDatabase(command.getPlayerName(), newGameStatus.getState().getFalseGuesses());
        }
    }

    /**
     * Inserts a new entry into the leaderboard database
     *
     * @param newName  name to insert with entry
     * @param newScore score to insert with entry
     */
    private void insertIntoDatabase(String newName, int newScore) {
        try {
            Statement stmt = dbConnection.createStatement();
            String sql = "INSERT INTO leaderboard_tvorug2 (name, score) VALUES ('" + newName + "', " + newScore + ")";
            stmt.execute(sql);
        } catch (Exception e) {
            System.out.println("SQLException");
        }
    }

    /**
     * Returns a sorted leaderboard of player "high" scores.
     *
     * @return a sorted map of player names to scores
     */
    @Override
    public Map<String, Integer> fetchLeaderboard() {
        Map<String, Integer> leaderboard = new HashMap<>();
        String query = "SELECT name, score FROM leaderboard_tvorug2 ORDER BY Score ASC";
        try {
            Statement stmt = dbConnection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            //ResultSet code from https://docs.oracle.com/javase/tutorial/jdbc/basics/retrieving.html
            while (rs.next()) {
                String name = rs.getString("name");
                int score = rs.getInt("score");
                leaderboard.put(name, score);
            }
        } catch (SQLException e) {
            System.out.println("SQLException");
        }
        return leaderboard;
    }
}
