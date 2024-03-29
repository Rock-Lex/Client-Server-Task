package uebung2;

/*
Schreiben Sie einen einfachen HTTP-Client welcher den Zugriff auf die Inhalte eines Webservers mittels des GET-Befehls erlaubt.
- Socket-Verbindung aufbauen
- OutputStream  zum Senden der GET-Befehle
- InputStream  Verarbeitung der Ausgabe
Testen Sie diesen Client mit verschiedenen Web-Servern
- www.google.de und der Datei index.html
- www.tagesschau.de under der Datei index.html
Optional: Einsatz von Apache HTTP Components zur Implementierung
 */


import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Task5 {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("google.com", 80);
            OutputStream out = socket.getOutputStream();
            InputStream in = socket.getInputStream();

            // Get Command
            String s = "GET " + "/" + " HTTP/1.0" + "\r\n\r\n";
            out.write(s.getBytes());

            int len;
            byte[] b = new byte[100];

            while ((len = in.read(b)) != -1) {
                System.out.write(b, 0, len);
            }

        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
