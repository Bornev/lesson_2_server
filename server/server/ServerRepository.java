package server.server;

public interface ServerRepository {
    String readLog();
    void saveInLog(String text);
}