package server;

import server.client.ClientController;
import server.client.ClientGUI;
import server.server.ServerController;
import server.server.ServerRepositoryImpl;
import server.server.ServerWindow;

public class Main {
    public static void main(String[] args) {
        // Создание объектов репозитория и контроллера сервера
        ServerRepositoryImpl serverRepository = new ServerRepositoryImpl();
        ServerController serverController = new ServerController(null, serverRepository);
        
        // Создание окна сервера с уже инициализированным контроллером
        ServerWindow serverWindow = new ServerWindow(serverController, serverRepository);
        
        // Установка представления сервера в контроллере
        serverController.setServerView(serverWindow);

        // Создание объектов клиента1
        ClientGUI clientGUI1 = new ClientGUI();
        ClientController clientController1 = new ClientController(clientGUI1, serverController);
        clientGUI1.setClient(clientController1);

        // Создание объектов клиента2
        ClientGUI clientGUI2 = new ClientGUI();
        ClientController clientController2 = new ClientController(clientGUI2, serverController);
        clientGUI2.setClient(clientController2);
    }
}
