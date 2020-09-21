package student.adventure;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SanitizeData {
  private final Map<String, Room> roomList;
  private Rooms tempRMS;
  private String currRoom;
  private String backgroundStory;

  public SanitizeData(String directory, boolean randomize) {
    if (directory == null) {
      throw new IllegalArgumentException();
    }
    roomList = new HashMap<>();
    try {
      Gson gson = new Gson();
      BufferedReader br = new BufferedReader(new FileReader(directory));
      tempRMS = gson.fromJson(br, Rooms.class);
    } catch (Exception e){
      System.out.println("FileNotFoundException");
    }
    List<Room> rooms = tempRMS.getRooms();
    backgroundStory = tempRMS.getBackgroundStory();
    currRoom = rooms.get(rooms.size() - 1).getName().toLowerCase();
    if (randomize) { //randomizes which item and person are the murder culprits
      int randomIndex = (int) (Math.random() * (rooms.size() - 1));
      rooms.get(randomIndex).getPerson()
          .setDialogue(rooms.get(randomIndex).getPerson().getGuiltyDialogue());
      rooms.get(randomIndex).getItems().get(0).setMurderWeapon(true);
    }
    for (Room room : rooms) {
      roomList.put(room.getName().toLowerCase(), room);
    }
  }

  public Map<String, Room> getRoomList() {
    return roomList;
  }

  public String getCurrRoom(){
    return currRoom;
  }

  public String getBackgroundStory() {
    return backgroundStory;
  }
}
