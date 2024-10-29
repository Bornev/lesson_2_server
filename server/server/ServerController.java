package server.server;

import server.client.ClientController;

import java.util.ArrayList;
import java.util.List;

public class ServerController {
    public static final String LOG_PATH = "src/server/log.txt";

    private List<ClientController> clientControllerList;
    private boolean isServerRunning;
    private ServerView serverView;
    private ServerRepository serverRepository;

    public ServerController(ServerView serverView, ServerRepository serverRepository) {
        this.clientControllerList = new ArrayList<>(); // Инициализация списка
        this.isServerRunning = false;
        this.serverView = serverView;
        this.serverRepository = serverRepository;
    }

    public boolean connectUser(ClientController clientController) {
        if (!isServerRunning) {
            return false;
        }
        clientControllerList.add(clientController);
        return true;
    }

    public void disconnectUser(ClientController clientController) {
        clientControllerList.remove(clientController);
        if (clientController != null) {
            clientController.disconnectedFromServer();
        }
    }

    public void startServer() {
        if (isServerRunning) {
            serverView.appendLog("Сервер уже был запущен");
        } else {
            isServerRunning = true;
            serverView.appendLog("Сервер запущен!");
        }
    }

    public void stopServer() {
        if (!isServerRunning) {
            serverView.appendLog("Сервер уже был остановлен");
        } else {
            isServerRunning = false;
            while (!clientControllerList.isEmpty()) {
                disconnectUser(clientControllerList.get(clientControllerList.size() - 1));
            }
            serverView.appendLog("Сервер остановлен!");
        }
    }

    public void message(String text) {
        if (!isServerRunning) {
            return;
        }
        serverView.appendLog(text);
        answerAllClients(text);
        serverRepository.saveInLog(text);
    }

    private void answerAllClients(String text) {
        for (ClientController clientController : clientControllerList) {
            clientController.answerFromServer(text);
        }
    }

    public String getHistory() {
        return serverRepository.readLog();
    }

    public void setServerView(ServerWindow serverView) {
        this.serverView = serverView;
    }
}
