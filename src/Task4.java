import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;

import java.net.InetAddress;
import java.util.Scanner;

/*
Implementieren Sie einen NTP-Client (Network Time Protocol)
- Einsatz der Apache Commons NetTM Library.
- Test mit mind. 5 verschiedenen Zeit-Servern.
- Interpretieren der zur Verfügung gestellten Informationen.
stratum: 2, version:3, mode:4, poll:0, precision:-6, delay:2048, dispersion(ms):48.5076904296875
Bewertung zur Verwendung von NTP-Zeitservern
- Überblick zu den Inhalten des Request for Comments - RFC 1305.
- Vorteile gegenüber dem einfache Daytime-Service?
- Einsatz bei MacOS, Windows oder sonstiges?
 */


public class Task4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String ntpServerAddress = scanner.next();
        NTPUDPClient client = new NTPUDPClient();

        client.setDefaultTimeout(10000);
        try {
            client.open();
            InetAddress hostAddr = InetAddress.getByName(ntpServerAddress);
            TimeInfo info = client.getTime(hostAddr);

            processTimeInfo(info);
        } catch (Exception e) {
            System.out.println("Ein Fehler ist aufgetreten: " + e.getMessage());
        } finally {
            client.close();
        }
    }

    private static void processTimeInfo(TimeInfo info) {
        info.computeDetails();

        if (info.getOffset() != null) {
            long currentTime = System.currentTimeMillis();
            long ntpTime = currentTime + info.getOffset();
            System.out.println("Aktuelle Zeit: " + new java.util.Date(ntpTime));
        } else {
            System.out.println("Es konnte keine Zeitverschiebung berechnet werden.");
        }
    }
}
