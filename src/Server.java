import java.io.IOException;
import java.net.*;
import java.io.*;

public class Server {

    public static void main(String[] args) throws IOException {
        // write your code
        Server enigma = new Server();
        enigma.run();
    }
    public void run () {
        int portNum = 5764;

        PrintWriter writeSock = null;
        try {
            ServerSocket servSock = new ServerSocket(portNum);
            writeSock = new PrintWriter(new FileOutputStream("Enigma.log"), true);

            while (true) {
                Socket sock = servSock.accept();
                ServerThread servThread = new ServerThread(sock, writeSock);
                servThread.start();

            }
        } catch (IOException exp) {

            writeSock.println("ErrorLog: " + exp);
        }
    }
}