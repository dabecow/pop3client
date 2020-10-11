package edu.networks.pop3.client.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
  private Socket socket;
  private BufferedReader in;
  private PrintWriter writer;
  private String log;

  public Client(){
    log = "Log:\n";
  }

  public void setSocket(Socket socket){
    this.socket = socket;
  }

  public void initStreams() throws IOException {
    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    writer = new PrintWriter(socket.getOutputStream(), true);
  }

  public void setLog(String log) {
    this.log = log;
  }

  public String getLog() {
    return log;
  }

  public void appendToLog(String message){
    this.log += message;
  }

  public void appendToLogLn(String message){
    this.log += message += "\n";
  }

  public Socket getSocket() {
    return socket;
  }

  public PrintWriter getWriter() {
    return writer;
  }

  public BufferedReader getIn() {
    return in;
  }

}
