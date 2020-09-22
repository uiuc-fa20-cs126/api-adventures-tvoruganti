package student.adventure;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import java.util.*;
import org.junit.Before;
import org.junit.Test;
import student.server.AdventureState;
import student.server.Command;
import student.server.GameStatus;

import static org.junit.Assert.*;

public class AdventureTest {

    GameEngine eng;

    @Before
    public void setUp() {
        eng = new GameEngine("src/main/resources/testMap.json", false);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullJSON(){
        GameEngine engine = new GameEngine(null, false);
    }

    @Test(expected = NullPointerException.class)
    public void testEmptyJSON(){
        GameEngine engine = new GameEngine("src/main/resources/empty.json", false);
    }

    @Test(expected = NullPointerException.class)
    public void testInvalidJSON(){
        GameEngine engine = new GameEngine("src/main/resources/invalidJSON.json", false);
    }

    @Test
    public void testInvalidCommand() {
        String toAssert = "I don't know what that action means!";
        assertThat(eng.isValidCommand("hello", "run"), is(toAssert));
    }

    @Test
    public void testValidGoToRoom() {
        String toAssert = "";
        assertThat(eng.isValidCommand("go", "southwest"), is(toAssert));
    }

    @Test
    public void testInvalidGoToRoom() {
        String toAssert = "You can't go in that direction!";
        assertThat(eng.isValidCommand("go", "north"), is(toAssert));
    }

    @Test
    public void testWordBeforeAction() {
        String toAssert = "I don't know what that action means!";
        assertThat(eng.isValidCommand("nogo", "north"), is(toAssert));
    }

    @Test
    public void testWordAfterAction() {
        String toAssert = "I don't know what that action means!";
        assertThat(eng.isValidCommand("goto", "north"), is(toAssert));
    }

    @Test
    public void testEdgeCaseCaps() {
        String toAssert = "";
        assertThat(eng.isValidCommand("gO", "soUthWEst"), is(toAssert));
    }

    @Test
    public void testEdgeCaseSpace() {
        String toAssert = "";
        assertThat(eng.isValidCommand(" go ", " southwest "), is(toAssert));
    }

    @Test
    public void testEdgeCaseSpaceAndCaps() {
        String toAssert = "";
        assertThat(eng.isValidCommand(" gO ", " SoUThWEst "), is(toAssert));
    }

    @Test
    public void testInvalidTakeItem() {
        String toAssert = "That is an invalid item!";
        assertThat(eng.isValidCommand("take", "something"), is(toAssert));
    }

    @Test
    public void testValidTakeItem() {
        String toAssert = "";
        assertThat(eng.isValidCommand("take", "bucket"), is(toAssert));
    }

    @Test
    public void testNoItemsToTake() {
        String toAssert = "There are no items to take!";
        eng.isValidCommand("go", "southwest");
        assertThat(eng.isValidCommand("take", "item"), is(toAssert));
    }

    @Test
    public void testNothingToDrop() {
        String toAssert = "You have nothing to drop!";
        assertThat(eng.isValidCommand("drop", "pitchfork"), is(toAssert));
    }

    @Test
    public void testValidDropItem() {
        String toAssert = "";
        eng.isValidCommand("take", "bucket");//must have at least one item in your inventory
        assertThat(eng.isValidCommand("drop", "bucket"), is(toAssert));
    }

    @Test
    public void testInvalidDropItem() {
        String toAssert = "That is an invalid item!";
        eng.isValidCommand("take", "bucket");//must have at least one item in your inventory
        assertThat(eng.isValidCommand("drop", "pitchfork"), is(toAssert));
    }

    @Test
    public void testInvalidWin() {
        String toAssert = "try again";
        eng.isValidCommand("take",
                "bucket"); //take the bucket out of the room, otherwise player would win
        assertThat(eng.isValidCommand("check", "win"), is(toAssert));
    }

    @Test
    public void testValidWin() {
        String toAssert = "correct";
        assertThat(eng.isValidCommand("check", "win"), is(toAssert));
    }

    @Test
    public void testSpeak() {
        String toAssert = "Security Guard: Have you found the weapon?";
        assertThat(eng.isValidCommand("speak", ""), is(toAssert));
    }

    @Test
    public void testQuit() {
        String toAssert = "quit";
        assertThat(eng.isValidCommand("quit", ""), is(toAssert));
    }

    @Test
    public void testExamine() {
        String toAssert = "You are at the Security." + "\n" +
                "You can talk to the Security Guard." + "\n" +
                "Items in room: [Bucket]." + "\n" +
                "Items in your inventory: []." + "\n" +
                "You can go in these directions: [Southwest].";
        assertThat(eng.isValidCommand("examine", "room"), is(toAssert));
    }

    @Test
    public void testGetCommandOptions() {
        Map<String, List<String>> expected = new HashMap<>();
        List<String> empty = new ArrayList<>();
        empty.add("");
        List<String> goPlace = new ArrayList<>();
        goPlace.add("Southwest");
        expected.put("go", goPlace);
        expected.put("examine", empty);
        expected.put("speak", empty);
        List<String> checkWin = new ArrayList<>();
        checkWin.add("win");
        expected.put("check", checkWin);
        expected.put("drop", new ArrayList<>());
        List<String> toTake = new ArrayList<>();
        toTake.add("Bucket");
        expected.put("take", toTake);
        assertEquals(eng.getCommandOptions(), expected);
    }

    @Test
    public void testExecuteCommand() {
        List<Item> items = new ArrayList<>();
        items.add(new Item("Bucket", true));
        AdventureState state = new AdventureState("security", 0);
        GameStatus currStatus = new GameStatus(false, 0, "", "", "", state, eng.getCommandOptions(), items, eng.getStartingRoomList());
        Command command = new Command("check", "win");
        assertThat(eng.executeCommand(currStatus, command).getMessage(), is("correct"));
    }
}

