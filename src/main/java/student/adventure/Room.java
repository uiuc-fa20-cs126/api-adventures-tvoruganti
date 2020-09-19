package student.adventure;

import java.util.List;

public class Room {

  private String room_name;
  private Person person;
  private List<Item> items;
  private List<String> adjacent_rooms;
  private List<String> possibleDirections;

  public Room(String roomname, Person ppl, List<Item> item, List<String> rooms, List<String> pD) {
    room_name = roomname;
    person = ppl;
    items = item;
    adjacent_rooms = rooms;
    possibleDirections = pD;
  }

  public String getName() {
    return room_name;
  }

  public Person getPerson() {
    return person;
  }

  public List<Item> getItems() {
    return items;
  }

  public List<String> getAdjacentRooms() {
    return adjacent_rooms;
  }

  public List<String> getPossibleDirections() {
    return possibleDirections;
  }
}
