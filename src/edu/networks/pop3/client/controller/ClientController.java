package edu.networks.pop3.client.controller;

import edu.networks.pop3.client.model.Client;
import java.io.IOException;
import java.util.Base64;

public class ClientController {
  private Client client;

  ClientController(Client client){
    this.client = client;
  }

  public void sendMessage(String message){
    System.out.println("[CLIENT] " + message);
    client.getWriter().println(message);
    client.appendToLog("[CLIENT]" + message + "\n");
  }

  public String receiveAnswer() throws IOException {
    String answer = client.getIn().readLine();
    client.appendToLog("[SERVER] " + answer + "\n");
    return answer;
  }

  public boolean authorise(String username, String password) throws IOException {
    sendMessage("USER " + Base64.getEncoder().encodeToString(username.getBytes()));

    if (receiveAnswer().toLowerCase().startsWith("-err"))
      return false;
    sendMessage("PASS " + Base64.getEncoder().encodeToString(password.getBytes()));
    return !receiveAnswer().toLowerCase().startsWith("-err");
  }

  public boolean quit() throws IOException {
    sendMessage("QUIT");
    return receiveAnswer().toLowerCase().startsWith("+ok");
  }

}
