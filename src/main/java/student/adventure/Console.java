package student.adventure;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Console {

  private final GameEngine eng;
  private final Scanner scanner;
  private String inputAction;
  private String secondInput;

  /**
   * Instantiates Console and its variables
   *
   * @throws FileNotFoundException if file directory is invalid
   */
  public Console(String directory, boolean random) throws FileNotFoundException {
    if (directory == null) {
      throw new IllegalArgumentException();
    }
    eng = new GameEngine(directory, random);
    scanner = new Scanner(System.in);
    inputAction = "";
    secondInput = "";
    System.out.println(eng.getBackgroundStory());
  }

  /**
   * This is where the game takes place. Game ends when while loop breaks.
   */
  public void play() {
    while (true) {
      System.out.println(eng.examine());//prints room info after every action
      if (getNextInput()) {
        break;
      }
      if (doAction()) {
        break;
      }
      if (inputAction.equals("speak")) {
        System.out.println(eng.getDialogue());
        if (getNextInput()) {
          break;
        }
        if (doAction()) {
          break;
        }
      }
    }
  }

  /**
   * Gets the next command from terminal
   *
   * @return true if the program should terminate
   */
  private boolean getNextInput() {
    System.out.print(">");
    inputAction = scanner.next().trim().toLowerCase();
    //gets first input and checks to see if you only need one
    if (inputAction.equals("exit") || inputAction.equals("quit")) {
      return true;
    } else if (inputAction.equals("speak") || inputAction.equals("examine")) {
      secondInput = "";
    } else {
      secondInput = scanner.next().trim().toLowerCase();//gets second input if required
    }
    return false;
  }

  /**
   * Does an action if valid or asks for a new action if the input on is invalid.
   *
   * @return true if the program should terminate
   */
  private boolean doAction() {
    String output = eng.isValidCommand(inputAction, secondInput);
    //asks for a new input if passed action was invalid
    while (!output.equals("Success") && !inputAction.equals("speak") && !output.equals("correct")
        && !output.equals("fail") && !output.equals("try again")) {
      if (output.equals("quit") || output.equals("exit")) {
        return true;
      }
      System.out.println(output);//prints string returned by engine, usually error message
      getNextInput();
      output = eng.isValidCommand(inputAction, secondInput);
    }
    return isWin(output);
  }

  /**
   * If checkWin method from engine is called, turns the returned value into an output.
   *
   * @param checkWin returned value from checkWin
   * @return true if the program should terminate
   */
  private boolean isWin(String checkWin) {
    switch (checkWin) {
      case "try again":
        System.out.println("Incorrect. Please try again.");
        break;
      case "correct":
        System.out.println("You Win!");
        return true;
      case "fail":
        System.out.println("Incorrect. That was your last attempt. You Lose.");
        return true;
      default:
        return false;
    }
    return false;
  }
}




