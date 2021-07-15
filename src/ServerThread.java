import java.net.*;
import java.io.*;
import java.time.*;
import java.util.*;

public class ServerThread extends Thread{

    Socket sock;
    PrintWriter writeSock; // socketio
    PrintWriter logWrite;  // log writer
    BufferedReader readSock;

    public ServerThread (Socket s, PrintWriter write)
    {
        sock = s;
        try {
            logWrite = write;
            writeSock = new PrintWriter(sock.getOutputStream(),true);
            readSock = new BufferedReader( new InputStreamReader(
                    sock.getInputStream() ) );
        }
        catch(IOException except)
        {

        }
    }

    public void run()
    {
        boolean user = false;
        String date=LocalDateTime.now().toString(); String internet= sock.getInetAddress().toString(); int port = sock.getPort();String output = ""; String name="";
        String outLine = ("New Connection: Date / Time: " + date +" Internet Addresss: " + internet + " Port#: "+ port + "\nWelcome to Enigma!  Please enter your Name: ");
        String intruder = "Intruder Detected and deflected!";
        String authorized = "Authorized User Granted Access.\n";
        writeSock.println(outLine);
        System.out.println(outLine);
        logWrite.println(outLine);

        while(!user) {
            try {
                name = readSock.readLine();
            } catch (IOException except) {
                writeSock.println("Exception: " + except);
                logWrite.println("Exception: " + except);
            }

            if (name.toLowerCase().equals("Rob") || name.toLowerCase().equals("Bond")) {
                logWrite.println(intruder);
                writeSock.println(intruder);
            } else {
                logWrite.println(authorized);
                writeSock.println(authorized);
                user= true;
            }
        }
        boolean quitTime = false;
        while( !quitTime )
        {
            try
            {

                String inLine = readSock.readLine();
                PolyAlphabet enigma = new PolyAlphabet(inLine);
                String check = inLine.toLowerCase();
                if (check.equals("hello"))
                {
                    writeSock.println( "Welcome to Enigma. To customize your cipher please enter shift 1:");
                    int c1 = Integer.parseInt(readSock.readLine());
                    writeSock.println( "Welcome to Enigma. To customize your cipher please enter shift 1:");
                    int c2 = Integer.parseInt(readSock.readLine());
                    logWrite.println("Cipher Shift 1: " + c1 + ", Cipher Shift 2: " + c2);

                    while(!quitTime)
                    {
                        writeSock.println("Enter Text:");
                        String textSwap = readSock.readLine();
                        output = (enigma.cipher(textSwap,c1,c2));
                        writeSock.println(output);
                        System.out.println(output);
                    }
                }
                if( check.equals("quit"))
                {
                    writeSock.println("connection closed");
                    writeSock.flush();
                    logWrite.println("Connection Terminatated at: "+ LocalDateTime.now().toString());
                    quitTime = true;
                    sock.close();
                }

                output = (enigma.cipher(inLine));
                writeSock.println(output);
                writeSock.flush();
                System.out.println(output);

            }


            catch(IOException except)
            {
                writeSock.println("Exception: "+ except);
                logWrite.println("Exception: "+ except);
            }
        }
    }
}
