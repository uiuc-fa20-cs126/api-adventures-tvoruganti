package student.adventure;

public class Person {

    private final String name;
    private String dialogue;
    private final String guiltyDialogue;

    public Person(String newName, String newDial, String newGuilDial) {
        name = newName;
        dialogue = newDial;
        guiltyDialogue = newGuilDial;
    }

    public String getName() {
        return name;
    }

    public String getDialogue() {
        return dialogue;
    }

    public String getGuiltyDialogue() {
        return guiltyDialogue;
    }

    public void setDialogue(String newDialogue) {
        dialogue = newDialogue;
    }
}
