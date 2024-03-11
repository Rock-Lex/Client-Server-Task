import org.apache.commons.net.telnet.TelnetClient;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class Task6 {

    public static void main(String[] args) {
        //get the time
        //String server = "india.colorado.edu";
        //int port = 13; // Standard Telnet-Port

        //Telehack
        //String server = "telehack.com";
        //int port = 23;

        //Legend of the Red Dragon
        //String server = "lord.stabs.org";
        //int port = 23;

        //BatMUD
        //String server = "batmud.bat.org";
        //int port = 23;



        //Cuban Bar (not working, exit code 130)
        //String server = "52.88.68.92";
        //int port = 1234;

        //weather (not working, exit code 130)
        //String server = "rainmaker.wunderground.com";
        //int port = 3000;

        //starwars (not working, exit code 130)
        //String server = "towel.blinkenlights.nl";
        //int port = 23;

        //Avalon: The Legend Lives (not working, connection refused)
        //String server = "avalon-rpg.com";
        //int port = 23;

        //Zombie MUD (not working, exit code 130)
        //String server = "zombiemud.org";
        //int port = 23;



        TelnetClient telnetClient = new TelnetClient();

        try {
            // Verbindung zum Server herstellen
            telnetClient.connect(server, port);

            // Eingabe- und Ausgabestrom vom Telnet-Client abrufen
            InputStream inputStream = telnetClient.getInputStream();
            PrintStream outputStream = new PrintStream(telnetClient.getOutputStream());

            // Lesen und Schreiben von Daten
            byte[] buff = new byte[1024];
            int bytesRead;
            String command = "ls\r\n"; // Beispielbefehl
            outputStream.print(command);
            outputStream.flush();

            // Antwort des Servers lesen
            while ((bytesRead = inputStream.read(buff)) != -1) {
                System.out.write(buff, 0, bytesRead);
                if (inputStream.available() < 1) {
                    break;
                }
            }

            // Verbindung trennen
            telnetClient.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




/*
    public static void main starwars(String[] args){
        //Star Wars asciimation
        towel.blinkenlights.nl 23
    }

*/






/*
    public static void main(String[] args) {
        System.out.println("Gib die Adresse des Telnet Servers ein:");
        System.out.println("Wähle: starwars, gettime oder weather");

        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();

        daytime(input);
*/







}


//Freie telnet server; Quelle: https://store.chipkin.com/articles/telnet-list-of-telnet-servers
//Freie telnet server; Quelle: https://www.telnet.org/htm/places.htm