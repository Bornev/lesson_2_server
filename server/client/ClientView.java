package server.client;

public interface ClientView {
    void showMessage(String message);
    void disconnectedFromServer();
}