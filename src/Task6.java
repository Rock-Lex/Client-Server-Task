import org.apache.commons.net.telnet.TelnetClient;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class Task6 {

    public static void main(String[] args) {

        //Telehack
//        String server = "telehack.com";
//        int port = 23;

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
