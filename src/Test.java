import edu.networks.pop3.client.controller.ClientController;
import edu.networks.pop3.client.controller.IClientController;
import edu.networks.pop3.client.model.Client;
import edu.networks.pop3.client.view.ClientConsoleView;
import edu.networks.pop3.client.view.IClientConsoleView;

public class Test {

  public static void main(String[] args) {
    Client client = new Client();
    IClientController iClientController = new ClientController(client);
    IClientConsoleView iClientConsoleView = new ClientConsoleView(iClientController);

    boolean isConnected;
    do {
      isConnected = iClientConsoleView.connectToServer();
    }
    while (!isConnected);

    boolean isAuthorised;
    do {
      isAuthorised = iClientConsoleView.authorise();
    } while (!isAuthorised);

    iClientConsoleView.runMessageInterface();
  }
}
