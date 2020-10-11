package edu.networks.pop3.client.view;

import edu.networks.pop3.client.controller.IClientController;
import java.io.IOException;
import java.util.Scanner;

public class ClientConsoleView implements IClientConsoleView{

  public static final String ANSI_RED = "\u001B[31m";
  public static final String ANSI_GREEN = "\u001B[32m";
  public static final String ANSI_PURPLE = "\u001B[35m";
  public static final String ANSI_CYAN = "\u001B[36m";
  public static final String ANSI_RESET = "\u001B[0m";

  private final IClientController iClientController;
  private final Scanner scanner;

  public ClientConsoleView(IClientController iClientController){
    this.iClientController = iClientController;
    this.scanner = new Scanner(System.in);
  }

  public boolean connectToServer(){
    String host;
    System.out.println(ANSI_CYAN + "\n"
        + "\n"
        + "           ____       _____         ___            __ \n"
        + "    ____  / __ \\____ |__  /   _____/ (_)__  ____  / /_\n"
        + "   / __ \\/ / / / __ \\ /_ <   / ___/ / / _ \\/ __ \\/ __/\n"
        + "  / /_/ / /_/ / /_/ /__/ /  / /__/ / /  __/ / / / /_  \n"
        + " / .___/\\____/ .___/____/   \\___/_/_/\\___/_/ /_/\\__/  \n"
        + "/_/         /_/                                       \n"
        + "\n\nConnect to the SMTP server (TSL is not supported).\n");

    System.out.print("Enter host\n>>>");
    host = scanner.nextLine();

    System.out.println("Standard port is 110.");

    try {
      iClientController.connectClient(host, 110);
      System.out.println(ANSI_GREEN + "\nSuccessful connecting to server:\n" + iClientController.receiveAnswer());
      return true;
    } catch (IOException e) {
      System.out.print(ANSI_RED + "\nIO error. Try again.\n" + ANSI_RESET);
      return false;
    }
  }

  public boolean authorise(){
    String username, password;

    System.out.println( ANSI_PURPLE + "You have to authorise to the server");

    System.out.print("Enter your username\n>>>");
    username = scanner.nextLine();

    System.out.print("Enter your password\n>>>");
    password = scanner.nextLine();

    try {
      if (iClientController.authorise(username, password)){
        System.out.println(ANSI_GREEN + "Successful authorisation" + ANSI_RESET);
        return true;
      } else return false;
    } catch (IOException e) {
      return false;
    }
  }

  private void printMenu(){
    System.out.println(ANSI_PURPLE + "\n\n1. Get mailbox statistics\n2. Get mailbox list\n"
        + "3. Get message from list\n4. Get message content\n5. Delete message\n"
        + "6. Check connection\n7. Reset messages deletion marks\n8. Get message lines\n"
        + "9. Quit\n" + ANSI_RESET);
  }

  public void runMessageInterface(){
    while (true){

      printMenu();

      try {
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice){
          case 1:
            System.out.println(iClientController.getMailBoxStatistics());
            break;
          case 2:
            System.out.println(iClientController.getMailBoxList());
            break;
          case 3:
            System.out.println("Enter message's number");
            System.out.println(iClientController.getMailBoxList(scanner.nextLine()));
            break;
          case 4:
            System.out.println("Enter message's number");
            System.out.println(iClientController.getMessage(scanner.nextLine()));
            break;
          case 5:
            System.out.println("Enter message's number");
            System.out.println(iClientController.deleteMessage(scanner.nextLine()));
            break;
          case 6:
            System.out.println(iClientController.checkConnection());
            break;
          case 7:
            System.out.println(iClientController.resetMessagesMarks());
            break;
          case 8:
            System.out.println("Enter the number of the message, space, and the number of lines");
            System.out.println(iClientController.getMessageLines(scanner.nextLine(), scanner.nextLine()));
            break;
          case 9:
            System.out.println(iClientController.quit());
            System.out.println(iClientController.getLog());
            return;
          default:
            System.out.println(ANSI_RED + "Wrong input, please, try again" + ANSI_RESET);
            break;
        }
      } catch (Exception e) {
        System.out.println(ANSI_RED + "Wrong input, please, try again" + ANSI_RESET);
      }
    }
  }

}


