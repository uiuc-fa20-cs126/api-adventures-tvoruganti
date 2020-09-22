package student.adventure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import student.server.AdventureState;
import student.server.Command;
import student.server.GameStatus;

public class GameEngine {

    private final AdventureState startingState;
    private final List<Item> startingInv;
    private final Map<String, Room> startingRoomList;
    private final String backgroundStory;
    private String currRoom;
    private Map<String, Room> roomList;
    private List<Item> inventory;
    private int falseGuesses;

    /**
     * Initializes variables
     *
     * @param directory String that represents file path
     * @param randomize boolean that represents whether the game should randomize the murderer
     */
    public GameEngine(String directory, boolean randomize) {
        SanitizeData deserializer = new SanitizeData(directory, randomize);
        roomList = deserializer.getRoomList();
        startingRoomList = roomList;
        currRoom = deserializer.getCurrRoom();
        inventory = new ArrayList<>();
        startingInv = new ArrayList<>();
        falseGuesses = 0;
        //Story for the game, printed only once at beginning of the game
        backgroundStory = deserializer.getBackgroundStory();
        startingState = new AdventureState(currRoom, falseGuesses);
    }

    /**
     * Checks if the first passed string is a valid action and checks if the second passed string is a
     * valid object to do the action on.
     *
     * @param command     First string that represents the action
     * @param secondInput Second string that represents the object
     * @return A String that describes a problem with the input or if that action was successful.
     */
    public String isValidCommand(String command, String secondInput) {
        switch (command.trim().toLowerCase()) {
            case "go":
                return goToRoom(secondInput.trim().toLowerCase());
            case "take":
                return takeItem(secondInput.trim().toLowerCase());
            case "drop":
                return dropItem(secondInput.trim().toLowerCase());
            case "speak":
                return getDialogue();
            case "check":
                return checkWin();
            case "examine":
                return examine();
            case "quit":
            case "exit":
                return command;
            default:
                break;
        }
        return "I don't know what that action means!";
    }

    /**
     * Takes item out of player's inventory and places it in room.
     *
     * @param item Item to place in room
     * @return String that represents the result of trying to drop the item.
     */
    private String dropItem(String item) {
        if (inventory.isEmpty()) {
            return "You have nothing to drop!";
        }
        int itemPos = isItemValid(item, inventory);//finds if item is in the inventory
        if (itemPos != -1) {
            //adds item to room and removes it from inventory
            roomList.get(currRoom).getItems().add(inventory.remove(itemPos));
        } else {
            return "That is an invalid item!";
        }
        return "";
    }

    /**
     * Finds if the passed item is in the passed list
     *
     * @param item     String that represents the name of the item to find
     * @param toLookIn List to look for the item in
     * @return Index the item is at in the list or -1 if it isn't
     */
    private int isItemValid(String item, List<Item> toLookIn) {
        for (int i = 0; i < toLookIn.size(); i++) {
            if (item.equals(toLookIn.get(i).getName().toLowerCase())) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Takes an item from a room and places it in the player's inventory.
     *
     * @param item Item to take
     * @return String that represents the result of trying to take the item.
     */
    private String takeItem(String item) {
        if (roomList.get(currRoom).getItems().isEmpty()) {
            return "There are no items to take!";
        }
        //finds if item is in the inventory
        int itemPos = isItemValid(item, roomList.get(currRoom).getItems());
        if (itemPos != -1) {
            //adds item to inventory and removes it from the room
            inventory.add(roomList.get(currRoom).getItems().remove(itemPos));
        } else {
            return "That is an invalid item!";
        }
        return "";
    }

    /**
     * Checks if a room is a valid room to go to.
     *
     * @param direction String that represents direction entered by player
     * @return index of direction in that list, -1 if it isn't in list
     */
    private int isRoomValid(String direction) {
        for (int i = 0; i < roomList.get(currRoom).getPossibleDirections().size(); i++) {
            if (direction.equals(roomList.get(currRoom).getPossibleDirections().get(i).toLowerCase())) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Sets the current room to the room in the passed direction
     *
     * @param direction Direction to travel in
     * @return String that represents the result of going in that direction
     */
    private String goToRoom(String direction) {
        int index = isRoomValid(direction);
        if (index != -1) {
            //sets current room to the room in the passed direction
            currRoom = roomList.get(currRoom).getAdjacentRooms().get(index).toLowerCase();
        } else {
            return "You can't go in that direction!";
        }
        return "";
    }

    /**
     * Checks if the current state is winning. Winning state in that there is 1 item in security with
     * a true value for isMurderWeapon
     *
     * @return String that represents how successful player is.
     */
    private String checkWin() {
        //checks all win conditions
        if (currRoom.equals("security") && roomList.get(currRoom).getItems().size() == 1
                && roomList.get(currRoom).getItems().get(0).isMurderWeapon()) {
            return "correct";
        }
        //keeps track of how many times the player has failed to find the murder weapon
        falseGuesses++;
        return "try again";
    }

    /**
     * Examines your current situation
     *
     * @return all information player needs
     */
    public String examine() {
        return "You are at the " + roomList.get(currRoom).getName() + ".\n" +
                "You can talk to the " + roomList.get(currRoom).getPerson().getName() + ".\n" +
                "Items in room: " + roomList.get(currRoom).getItems().toString() + ".\n" +
                "Items in your inventory: " + inventory.toString() + ".\n" +
                "You can go in these directions: " + roomList.get(currRoom).getPossibleDirections()
                .toString() + ".";
    }

    //returns dialogue of person in the current room
    public String getDialogue() {
        return roomList.get(currRoom).getPerson().getName() + ": " + roomList.get(currRoom).getPerson()
                .getDialogue();
    }

    //returns background story of the game
    public String getBackgroundStory() {
        return backgroundStory;
    }

    //returns the Starting room
    public AdventureState getStartingState() {
        return startingState;
    }

    //returns empty list
    public List<Item> getStartingInv() {
        return startingInv;
    }

    //returns original setup of rooms and items
    public Map<String, Room> getStartingRoomList() {
        return startingRoomList;
    }

    //returns list of item names give a list of items
    private List<String> listItemtoString(List<Item> items) {
        List<String> itemsToStrings = new ArrayList<>();
        for (Item item : items) {
            itemsToStrings.add(item.getName());
        }
        return itemsToStrings;
    }

    /**
     * Executes a command
     *
     * @param currGameStatus Game to perform the command on
     * @param command        Command to perform
     * @return Game Status after performing the command.
     */
    public GameStatus executeCommand(GameStatus currGameStatus, Command command) {
        AdventureState currState = currGameStatus.getState();
        roomList = currGameStatus.getRoomList();
        currRoom = currState.getCurrentRoom().toLowerCase();
        inventory = currGameStatus.getInventory();
        falseGuesses = currState.getFalseGuesses();
        String message = isValidCommand(command.getCommandName().toLowerCase(),
                command.getCommandValue().toLowerCase());
        AdventureState newState = new AdventureState(currRoom, falseGuesses);
        return new GameStatus(false, currGameStatus.getId(), message, "", "",
                newState, getCommandOptions(), inventory, roomList);
    }

    /**
     * Gets all possible commands for the current room
     *
     * @return Map with commands and all possible things to perform commands on
     */
    public Map<String, List<String>> getCommandOptions() {
        Map<String, List<String>> commandOptions = new HashMap<>();
        commandOptions.put("go", roomList.get(currRoom).getPossibleDirections());
        List<String> emptyList = new ArrayList<>();
        emptyList.add("");
        commandOptions.put("examine", emptyList);
        commandOptions.put("speak", emptyList);
        if (currRoom.equals("security")) {
            List<String> win = new ArrayList<>();
            win.add("win");
            commandOptions.put("check", win);
        }
        commandOptions.put("drop", listItemtoString(inventory));
        commandOptions
                .put("take", listItemtoString(roomList.get(currRoom).getItems()));
        return commandOptions;
    }
}



