package student.adventure;

import java.util.List;

public class Room {

    private final String room_name;
    private final Person person;
    private final List<Item> items;
    private final List<String> adjacent_rooms;
    private final List<String> possibleDirections;
    private final String imageURL;
    private final String soundURL;


    public Room(String roomname, Person ppl, List<Item> item, List<String> rooms, List<String> pD, String imageURL, String soundURL) {
        room_name = roomname;
        person = ppl;
        items = item;
        adjacent_rooms = rooms;
        possibleDirections = pD;
        this.imageURL = imageURL;
        this.soundURL = soundURL;
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

    public String getImageURL() {
        return imageURL;
    }

    public String getSoundURL() {
        return soundURL;
    }
}
