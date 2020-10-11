package edu.networks.pop3.client.controller;

import edu.networks.pop3.client.model.Client;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Base64;

public class ClientController {
  private Client client;

  ClientController(Client client){
    this.client = client;
  }

  public void sendMessage(String message){
    client.getWriter().println(message);
    System.out.println("[CLIENT] " + message);
    client.appendToLogLn("[CLIENT]" + message);
  }

  public String receiveAnswer() throws IOException {
    String answer = client.getIn().readLine();
    client.appendToLogLn("[SERVER] " + answer);
    System.out.println("[SERVER] " + answer);
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

  public String getMailBoxStatistics() throws IOException {
    sendMessage("STAT");
    return receiveAnswer();
  }

  public String getMailBoxList() throws IOException {
    sendMessage("LIST");
    return receiveAnswer();
  }

  public String getMailBoxList(String messageNumber) throws IOException {
    sendMessage("LIST " + messageNumber);
    return receiveAnswer();
  }

  public String getMessage(String number) throws IOException {
    sendMessage("RETR " + number);
    return receiveAnswer();
  }

  public boolean deleteMessage(String number) throws IOException {
    sendMessage("DELE " + number);
    return receiveAnswer().startsWith("+OK");
  }

  public boolean checkConnection() throws IOException {
    sendMessage("NOOP");
    client.getSocket().setSoTimeout(500);
    try {
      return receiveAnswer().startsWith("+OK");
    } catch (SocketTimeoutException e) {
      return false;
    }
  }

  public boolean resetMessagesMarks() throws IOException {
    sendMessage("RSET");
    return receiveAnswer().startsWith("+OK");
  }

  public String getMessageLines(String messageNumber, String lines) throws IOException {
    sendMessage("TOP " + messageNumber + " " + lines);
    return receiveAnswer();
  }

}
  