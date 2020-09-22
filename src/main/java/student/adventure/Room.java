package student.adventure;

import java.util.List;

public class Room {

    private final String room_name;
    private final Person person;
    private final List<Item> items;
    private final List<String> adjacent_rooms;
    private final List<String> possibleDirections;

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
