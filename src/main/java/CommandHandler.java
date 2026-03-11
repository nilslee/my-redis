import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class CommandHandler {

  void commandResponse(Socket clientSocket) throws IOException {
    InputStream inputStream = clientSocket.getInputStream();
    OutputStream outputStream = clientSocket.getOutputStream();

    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
    for (String inputLine = br.readLine(); inputLine != null; inputLine = br.readLine()) {
      outputStream.write("+PONG\r\n".getBytes());
    }
    br.close();
  }
}