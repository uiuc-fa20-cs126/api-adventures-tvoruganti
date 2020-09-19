package student.client;

import java.io.FileNotFoundException;
import student.adventure.Console;

public class Main {

  public static void main(String[] args) throws FileNotFoundException {
    Console console = new Console("src/main/resources/adventuremap.json", true);
    console.play();
  }
}