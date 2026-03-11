import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
  public static void main(String[] args) {
    // You can use print statements as follows for debugging, they'll be visible when running tests.
    System.out.println("Logs from your program will appear here!");
    CommandHandler commandHandler = new CommandHandler();

    //  Uncomment the code below to pass the first stage
    int port = 6379;
    try (ServerSocket serverSocket = new ServerSocket(port)) {
      // Since the tester restarts your program quite often, setting SO_REUSEADDR
      // ensures that we don't run into 'Address already in use' errors
      serverSocket.setReuseAddress(true);

      // Infinite loop to accept multiple clients
      while (true) {
        // Wait for connection from client. (Blocking)
        try (Socket clientSocket = serverSocket.accept()) {
          commandHandler.commandResponse(clientSocket);
        } catch (java.net.SocketException e) {
          // Specific handling for reset
          if (e.getMessage().contains("Connection reset")) {
            // Log at a lower level or ignore as it is a common client behavior
            System.out.println("Client reset the connection gracefully (handled).");
          } else {
            System.out.println("Unexpected Socket Exception: " + e);
          }
        } catch (IOException e) {
          System.err.println("General IO Error: " + e.getMessage());
        }
      }
    } catch (IOException e) {
      System.out.println("IOException: " + e.getMessage());
    }
  }
}
