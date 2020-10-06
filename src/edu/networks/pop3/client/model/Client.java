package edu.networks.pop3.client.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
  private final Socket socket;
  private final BufferedReader in;
  private final PrintWriter writer;
  private String log;

  public Client(String host, int port) throws IOException {
    socket = new Socket(host, port);
    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    writer = new PrintWriter(socket.getOutputStream(), true);
    log = "Log:\n";
  }

  public void setLog(String log) {
    this.log = log;
  }

  public String getLog() {
    return log;
  }



}
