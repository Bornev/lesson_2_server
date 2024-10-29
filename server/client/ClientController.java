package server.client;

import server.server.ServerController;

public class ClientController {
    private boolean connected;
    private String name;
    private ClientView clientView;
    private ServerController server;

    public ClientController(ClientView clientView, ServerController server) {
        this.clientView = clientView;
        this.server = server;
    }

    public boolean connectToServer(String name) {
        this.name = name;
        if (server.connectUser(this)) {
            clientView.showMessage("Вы успешно подключились!\n");
            connected = true;
            String log = server.getHistory();
            if (log != null) {
                clientView.showMessage(log);
            }
            return true;
        } else {
            clientView.showMessage("Подключение не удалось");
            return false;
        }
    }

    public void disconnectedFromServer() {
        if (connected) {
            connected = false;
            clientView.disconnectedFromServer();
            clientView.showMessage("Вы были отключены от сервера!");
        }
    }

    public void disconnectFromServer() {
        server.disconnectUser(this);
    }

    public void answerFromServer(String text) {
        clientView.showMessage(text);
    }

    public void message(String text) {
        if (connected) {
            if (!text.isEmpty()) {
                server.message(name + ": " + text);
            }
        } else {
            clientView.showMessage("Нет подключения к серверу");
        }
    }
}