package edu.networks.pop3.client.view;

public interface IClientConsoleView {
  boolean connectToServer();
  boolean authorise();
  void runMessageInterface();
}
