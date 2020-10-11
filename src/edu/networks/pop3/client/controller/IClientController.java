package edu.networks.pop3.client.controller;

import java.io.IOException;

public interface IClientController {
  public String receiveAnswer() throws IOException;
  void connectClient(String host, int port) throws IOException;
  boolean authoriseBase64(String username, String password) throws IOException;
  boolean authorise(String username, String password) throws IOException;
  String quit() throws IOException;
  String getMailBoxStatistics() throws IOException;
  String getMailBoxList() throws IOException;
  String getMailBoxList(String messageNumber) throws IOException;
  String getMessage(String number) throws IOException;
  String deleteMessage(String number) throws IOException;
  String checkConnection() throws IOException;
  String resetMessagesMarks() throws IOException;
  String getMessageLines(String messageNumber, String lines) throws IOException;
  String getLog();
  }
