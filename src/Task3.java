import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;

public class Task3 {
    public static void main(String[] args) {
        System.out.println("Gib die Adresse des Daytime Servers ein:");
        System.out.println("Wähle z.B. time.nist.gov aus");
        
        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();

        try (Socket socket = new Socket(input, 13)) {
            InputStream in = socket.getInputStream();
            int c;
            while ((c = in.read()) != -1) {
                System.out.print((char) c);
            }
        } catch (IOException e) {
            System.out.println("Ein Fehler ist aufgetreten: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

// Serverliste mit Port 13 offen:
// https://tf.nist.gov/tf-cgi/servers.cgi
// Port 123 ist heutzutage üblicher verwendet