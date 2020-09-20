package student.server;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.*;
import student.adventure.Item;
import student.adventure.Room;

/**
 * A class to represent values in a game state.
 * <p>
 * Note: these fields should be JSON-serializable values, like Strings, ints, floats, doubles, etc.
 * Please don't nest objects, as the frontend won't know how to display them.
 * <p>
 * Good example: private String shoppingList;
 * <p>
 * Bad example: private ShoppingList shoppingList;
 */
@JsonSerialize
public class AdventureState {

  private String currentRoom;
  private int falseGuesses;
  private String inventory;

  public AdventureState(String currRoom,  int fG, String inv) {
    currentRoom = currRoom;
    falseGuesses = fG;
    inventory = inv;
  }

  public String getCurrentRoom() {
    return currentRoom;
  }

  public int getFalseGuesses() {
    return falseGuesses;
  }
}
