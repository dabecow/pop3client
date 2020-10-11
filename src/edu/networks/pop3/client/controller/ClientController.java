package edu.networks.pop3.client.controller;

import edu.networks.pop3.client.model.Client;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Base64;

public class ClientController implements IClientController{
  private final Client client;

  public ClientController(Client client){
    this.client = client;
  }

  private void sendMessage(String message){
    client.getWriter().println(message);
    client.appendToLogLn("[CLIENT] " + message);
  }

  public String receiveAnswer() throws IOException {

    String answer = "";

    do {
      int c = client.getIn().read();
      answer+=(char)c;
    } while(client.getIn().ready());

    client.appendToLogLn("[SERVER] " + answer);
    return answer;
  }

  @Override
  public void connectClient(String host, int port) throws IOException {
    client.setSocket(new Socket(host, port));
    client.initStreams();

  }

  public boolean authoriseBase64(String username, String password) throws IOException {
    sendMessage("USER " + Base64.getEncoder().encodeToString(username.getBytes()));

    if (receiveAnswer().toLowerCase().startsWith("-err"))
      return false;
    sendMessage("PASS " + Base64.getEncoder().encodeToString(password.getBytes()));
    return !receiveAnswer().toLowerCase().startsWith("-err");
  }

  @Override
  public boolean authorise(String username, String password) throws IOException {
    sendMessage("USER " + username);

    if (receiveAnswer().toLowerCase().startsWith("-err"))
      return false;
    sendMessage("PASS " + password);
    return !receiveAnswer().toLowerCase().startsWith("-err");
  }

  public String quit() throws IOException {
    sendMessage("QUIT");
    String answer = receiveAnswer();
    client.getSocket().close();
    return answer;
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

  public String deleteMessage(String number) throws IOException {
    sendMessage("DELE " + number);
    return receiveAnswer();
  }

  public String checkConnection() throws IOException {
    sendMessage("NOOP");
    client.getSocket().setSoTimeout(500);
    try {
      return receiveAnswer();
    } catch (SocketTimeoutException e) {
      return "No connection";
    }
  }

  public String resetMessagesMarks() throws IOException {
    sendMessage("RSET");
    return receiveAnswer();
  }

  public String getMessageLines(String messageNumber, String lines) throws IOException {
    sendMessage("TOP " + messageNumber + " " + lines);
    return receiveAnswer();
  }

  @Override
  public String getLog() {
    return client.getLog();
  }

}
  