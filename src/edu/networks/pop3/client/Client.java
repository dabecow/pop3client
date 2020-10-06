package edu.networks.pop3.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Base64;

public class Client {
  private Socket socket;
  private BufferedReader in;
  private PrintWriter writer;
  private String log;

  public Client(String host, int port) throws IOException {
    socket = new Socket(host, port);
    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    writer = new PrintWriter(socket.getOutputStream(), true);
    log = "Log:\n";
  }

  public void sendMessage(String message){
    System.out.println("[CLIENT] " + message);
    writer.println(message);
    log += "[CLIENT]" + message + "\n";
  }

  public String receiveAnswer() throws IOException {
    String answer = in.readLine();
    log += "[SERVER] " + answer + "\n";
    return answer;
  }

  public void exceptionCaught(){
    log += "[CLIENT] Abort: IOException\n";
  }

  public String getLog() {
    return log;
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
