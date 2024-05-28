package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Server extends Thread{
    private DatagramSocket socket;
    private byte [] buffer;
    public Server(int port)
    {
        try
        {
            this.socket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        this.buffer=new byte[500];

    }

    @Override
    public void run() {

       // DatagramPacket packet=new DatagramPacket(buffer,buffer.length);
        while(true)
        {
            try
            {
                byte[] buffer = new byte[256];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                String received = new String(packet.getData(), 0, packet.getLength());
                System.out.println("RECEIVED: " + received);
                String msg;
                if (received.equals("login"))
                    msg = "logged in";
                else if (received.equals("logout"))
                    msg = "logout";
                else
                    msg = "echo-" + received;
                buffer = msg.getBytes();
                packet = new DatagramPacket(buffer, buffer.length, packet.getAddress(), packet.getPort());
                socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        Server server=new Server(4445);
        server.start();
    }
}
