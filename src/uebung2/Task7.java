package uebung2;

import java.io.*;
import java.net.*;
import java.nio.Buffer;
import java.util.Scanner;

import org.apache.commons.net.telnet.*;

//import static jdk.internal.org.jline.utils.Colors.s;

/*
Analysieren Sie die Möglichkeiten zur Implementierung eines Java-
Serves mit Hilfe der Klasse ServerSocket.
- Verwendung der Klasse
- Angebotene Methoden
Implementieren Sie eine einfache Serveranwendung die mit einem Telnet-Client (arbeitet oberhalb von TCP/IP) kommunizieren kann.
Testen Sie die Anwendung zuerst indem Client und Server (localhost <portNumber>)auf einem System arbeiteten, dann aber auch im lokalen Netzwerkbereich.
Bem.: Telnet steht über die Kommandozeile zur Verfügung und erlaubt eine zeichenweise Übertragung zwischen Client und Server.
Optional: Einsatz von Apache Commons Net für den Telnet-Client
 */

public class Task7 {

    public static void main(String[] args) throws IOException {
        scanner_loop();
    }

    public static void scanner_loop() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n***************************************** ");
            System.out.println("Choose a Telnet server writing the name: ");
            //System.out.println("starwars");
            System.out.println("gettime");
            System.out.println("telehack");
            System.out.println("lord");
            System.out.println("batmud");
            System.out.println("localhost");
            //System.out.println("weather");
            System.out.println("exit");
            System.out.println("*****************************************\n ");
            String input = scanner.nextLine();

            try {
                if(input.equals("exit")) {
                    break;
                }
                if(input.equals("localTelnet")) {
//                executeTelnetlocal("localhost", 1234);
                    break;
                }

                switch (input.toLowerCase()) {
                    case "starwars":
                        executeTelnet("towel.blinkenlights.nl", 23);
                        break;
                    case "gettime":
                        executeTelnet("india.colorado.edu", 13);
                        break;
                    case "telehack":
                        executeTelnet("telehack.com", 23);
                        break;
                    case "lord":
                        executeTelnet("lord.stabs.org", 23);
                        break;
                    case "batmud":
                        executeTelnet("batmud.bat.org", 23);
                        break;
                    case "weather":
                        executeTelnet("rainmaker.wunderground.com", 3000);
                        break;
                    case "exit":
                        executeTelnet("india.colorado.edu", 13);
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (IOException e) {
                e.printStackTrace();

            }

        }

    }

    public static void executeTelnet(String server, int port) throws IOException {
        Socket socket = new Socket(server, port);

        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        BufferedReader userInputReader = new BufferedReader(new InputStreamReader(System.in));

        try {
//            String userInput;
//            while ((userInput = userInputReader.readLine()) != null) {
//                writer.println(userInput);
            writer.println("ls");

//                if (userInput.equals("exit")) {
//                    socket.close();
//                    break;
//                }

                // Antwort des Servers lesen und ausgeben
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }

            // Verbindung schließen
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public static void executeTelnetlocal(String server, int port) throws IOException {
//        Socket socket = new Socket(server, port);
//
//
//    }

}