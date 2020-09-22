package student.client;

import student.adventure.Console;

public class RunClient {

    public static void main(String[] args) {
        Console console = new Console("src/main/resources/adventuremap.json", true);
        console.play();

    }
}
