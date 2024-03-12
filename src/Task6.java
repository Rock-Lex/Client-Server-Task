import org.apache.commons.net.telnet.TelnetClient;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class Task6 {

    public static void main(String[] args) {

        // Uncomment the following block to allow user input for server selection
        System.out.println("Choose a Telnet server writing the name:");
        System.out.println("1. starwars");
        System.out.println("2. gettime");
        System.out.println("3. telehack");
        System.out.println("4. lord");
        System.out.println("5. batmud");
        System.out.println("6. weather");

        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();

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
            default:
                System.out.println("Invalid choice.");
        }

        // Uncomment the above block and comment out the line below if you want to prompt the user for server selection
        executeTelnet("india.colorado.edu", 13); // Default option if user input is not used
    }

    private static void executeTelnet(String server, int port) {
        TelnetClient telnetClient = new TelnetClient();

        try {
            telnetClient.connect(server, port);

            InputStream inputStream = telnetClient.getInputStream();
            PrintStream outputStream = new PrintStream(telnetClient.getOutputStream());

            byte[] buff = new byte[1024];
            int bytesRead;
            String command = "ls\r\n"; // Example command
            outputStream.print(command);
            outputStream.flush();

            while ((bytesRead = inputStream.read(buff)) != -1) {
                System.out.write(buff, 0, bytesRead);
                if (inputStream.available() < 1) {
                    break;
                }
            }

            telnetClient.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



//Freie telnet server; Quelle: https://store.chipkin.com/articles/telnet-list-of-telnet-servers
//Freie telnet server; Quelle: https://www.telnet.org/htm/places.htm



// Notizen
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