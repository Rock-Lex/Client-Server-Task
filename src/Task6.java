import org.apache.commons.net.telnet.TelnetClient;
import java.io.InputStream;
import java.io.PrintStream;

public class Task6 {

    public static void main(String[] args) {
        String server = "india.colorado.edu";
        int port = 13; // Standard Telnet-Port

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

}
