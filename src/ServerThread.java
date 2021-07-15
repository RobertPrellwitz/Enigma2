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
        String outLine = ("New Connection: Date / Time: " + date +" Internet Addresss: " + internet + " Port#: "+ port) ;
        String greeting ="Welcome to Enigma Version 1!  \nPlease Enter your Name: ";
        String intruder = "Intruder Detected and deflected!\n";
        String tryAgain = " You may attempt Logging in again!";
        String authorized = "Authorized User Granted Access.\n ";
        String command = "Enter Command: ";
        String end = "end";
        writeSock.println(outLine);
        writeSock.println(greeting);
        writeSock.println(end);
        System.out.println(outLine);
        logWrite.println(outLine);

        while(!user) {
            try {
                name = readSock.readLine();
            } catch (IOException except) {
                writeSock.println("Exception: " + except);
                logWrite.println("Exception: " + except);
            }

            if (name.toLowerCase().equals("rob") || name.toLowerCase().equals("bond")) {
                logWrite.println(intruder);
                writeSock.println(intruder + tryAgain);
                writeSock.println(end);

            } else {
                logWrite.println(authorized);
                writeSock.println(authorized + command);
                writeSock.println(end);
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
                    writeSock.println( "Welcome to Enhanced Enigma. \nTo customize your cipher please enter shift 1:");
                    writeSock.println(end);
                    int c1 = Integer.parseInt(readSock.readLine());
                    writeSock.println( "Now please enter shift 2:");
                    writeSock.println(end);
                    int c2 = Integer.parseInt(readSock.readLine());
                    logWrite.println("Cipher Shift 1: " + c1 + ", Cipher Shift 2: " + c2);
                    writeSock.println("Enter Text:");
                    writeSock.println(end);
                    while(!quitTime)
                    {
                        String textSwap = readSock.readLine();
                        if( textSwap.equals("quit"))
                        {
                            writeSock.println("connection closed");
                            logWrite.println("Connection Terminatated at: "+ LocalDateTime.now().toString());
                            writeSock.println(end);
                            quitTime = true;
                            sock.close();
                        }
                        output = (enigma.cipher(textSwap,c1,c2));
                        writeSock.println(output);
                        writeSock.println(end);
                        System.out.println(output);
                    }
                }
                if( check.equals("quit"))
                {
                    writeSock.println("connection closed");
                    writeSock.println(end);
                    logWrite.println("Connection Terminatated at: "+ LocalDateTime.now().toString());
                    quitTime = true;
                    sock.close();
                }

                output = (enigma.cipher(inLine));
                writeSock.println(output);
                writeSock.println(end);
                System.out.println(output);

            }


            catch(IOException except)
            {
                writeSock.println("Exception: "+ except);
                logWrite.println("Exception: "+ except);
            }
        }
    }
    public void quit(String check)
    {

    }
}
