package student.client;

import java.io.FileNotFoundException;
import student.adventure.Console;

public class RunClient {

  public static void main(String[] args){
    Console console = new Console("src/main/resources/adventuremap.json", true);
    console.play();
  }
}
