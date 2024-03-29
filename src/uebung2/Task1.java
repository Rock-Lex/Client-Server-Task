package uebung2;

import java.net.*;

/*
    Ping
 */

public class Task1 {
    public static void main(String[] args) {
        try {
            InetAddress host = InetAddress.getByName("www.zeit.de");
            long time = System.nanoTime();
            int port = 80;
            Socket sock = new Socket (host, port) ;
            sock.close();
            time = (System.nanoTime() - time) / 1000000L;
            System.out.println("Connection ok (port " + port +", time = " + time + " ms). In" + "Host Address = "
                    + host.getHostAddress()
                    + "\n"
                    + "Host Name = " + host.getHostName());
            System.exit(0);
        } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
                System.exit(1);
        }
    }
}