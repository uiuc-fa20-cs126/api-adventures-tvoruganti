package student.adventure;

import java.util.List;

public class Rooms {

  private List<Room> rooms;
  private String backgroundStory;

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
