package uebung2;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;

/*
Verwenden Sie eine einfache Socket-Verbindung für die Abfrage des auf UNIX-Systemen verfügbaren DayTime-Service auf Port 13.
- Verbindung aufbauen
- Daten lesen bzw. schreiben
- Verbindung abbauen
- Recherchieren Sie nach im Internet frei zugänglichen UNIX-Servern d.h. der Port 13 ist offen! – mindestens 2 Server benennen
- Übergeben Sie den entsprechenden Hostnamen und berück- sichtigen Sie die dabei ggf. auftretenden Fehler.
Bem.: Moderne Zeitsynchronisation erfolgt via NTP oder SNTP!
 */


public class Task3 {
    public static void daytime(String input){
        try (Socket socket = new Socket(input, 13)) {
            InputStream in = socket.getInputStream();
            int c;
            while ((c = in.read()) != -1) {
                System.out.print((char) c);
            }
        } catch (IOException e) {
            System.out.println("Ein Fehler ist aufgetreten: " + e.getMessage());
        }
    }
    public static void main(String[] args) {
        System.out.println("Gib die Adresse des Daytime Servers ein:");
        System.out.println("Wähle z.B. time.nist.gov aus, time.fu-berlin.de, ntp.dianacht.de, " + 
        "zeit.fu-berlin.de");
        
        System.out.print("Eingabe: ");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();

        daytime(input);
    }
}

// Serverliste mit Port 13 offen:
// https://tf.nist.gov/tf-cgi/servers.cgi
// Port 123 ist heutzutage üblicher verwendet