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
  private final List<Item> inventory;
  private final Map<String, Room> roomList;
  private int falseGuesses;


  public AdventureState(String currRoom, List<Item> inv, Map<String, Room> listRooms, int fG) {
    currentRoom = currRoom;
    inventory = inv;
    roomList = listRooms;
    falseGuesses = fG;
  }

  public String getCurrentRoom() {
    return currentRoom;
  }

  public List<Item> getInventory() {
    return inventory;
  }

  public Map<String, Room> getRoomList() {
    return roomList;
  }

  public int getFalseGuesses() {
    return falseGuesses;
  }
}
