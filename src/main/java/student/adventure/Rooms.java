package student.adventure;

import java.util.List;

public class Rooms {

    private final List<Room> rooms;
    private final String backgroundStory;

    public Rooms(List<Room> rms, String bgs) {
        rooms = rms;
        backgroundStory = bgs;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public String getBackgroundStory() {
        return backgroundStory;
    }
}
