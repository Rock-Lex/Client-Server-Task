import java.io.*;
import java.net.*;
import java.util.Scanner;

import org.apache.commons.net.telnet.*;

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
    public static void main(String[] args) {
        // Uncomment the following block to allow user input for server selection
        System.out.println("Choose a Telnet server by typing its name:");
        //System.out.println("starwars");
        System.out.println("gettime");
        System.out.println("telehack");
        System.out.println("lord");
        System.out.println("batmud");
        //System.out.println("weather");
        System.out.println("exit\n");

        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();

        try {
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

    public static void executeTelnet(String server, int port) throws IOException {
        // Verbindung zum Server herstellen
        Socket socket = new Socket(server, port);

        try {
            // Eingabe- und Ausgabe-Streams für die Kommunikation mit dem Server
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);

            // Befehl an den Server senden (hier wird nur ein einfacher Befehl "ls" gesendet)
            writer.println("gamer");
            writer.println("1");

            // Antwort des Servers lesen und ausgeben
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } finally {
            // Verbindung schließen
            socket.close();
        }
    }

}