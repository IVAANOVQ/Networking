package tcp;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client extends Thread {
    private String serverName;
    private int serverPort;

    public Client(String serverName, int serverPort) {
        this.serverName = serverName;
        this.serverPort = serverPort;
    }

    @Override
    public void run() {
        Socket socket = null;
        BufferedReader reader = null;
        BufferedWriter writer = null;
        try {
            socket = new Socket(InetAddress.getByName(serverName), serverPort);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            writer.write("login\n");
            writer.write("Host: localhost\n");
            writer.write("User-Agent: ClientTCP\n");
            writer.write("logout\n");
            writer.write("\n");
            writer.flush();

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("Client received: " + line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.flush();
                    writer.close();
                }
                if (reader != null)
                    reader.close();
                if (socket != null)
                    socket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        String serverName = "localhost";
        int serverPort = 8080;
        Client client = new Client(serverName, serverPort);
        client.start();
//        String port=System.getenv("SERVER_PORT");
//        Client client=new Client(Integer.parseInt(System.getenv("SERVER_PORT")));
//        client.start();
    }
}


