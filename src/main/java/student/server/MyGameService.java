package student.server;

import java.util.*;
import student.adventure.GameEngine;

public class MyGameService implements AdventureService {

  private int idCounter;
  private Map<Integer, GameStatus> gamesList;
  private GameEngine engine;

  public MyGameService() {
    idCounter = 0;
    gamesList = new HashMap<>();
    engine = new GameEngine("src/main/resources/adventuremap.json", true);
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
    Map<String, List<String>> comOpts = new HashMap<>();
    GameStatus newGame = new GameStatus(false, idCounter, engine.getBackgroundStory(), "", "",
        engine.getStartingState(), engine.getCommandOptions());
    gamesList.put(idCounter, newGame);
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
  }

  /**
   * Returns a sorted leaderboard of player "high" scores.
   *
   * @return a sorted map of player names to scores
   */
  @Override
  public SortedMap<String, Integer> fetchLeaderboard() {
    return null;
  }
}
