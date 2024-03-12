import java.net.UnknownHostException;
import java.util.Scanner;
import java.net.InetAddress;

/*
Implementieren Sie eine Java-Anwendung unter Verwendung der Klasse InetAddress des Paktes java.net.
- Eingabe des Domain-Namens und Ausgabe der IP-Adresse
- Eingabe der IP-Adresse und Ausgabe des Domain-Namens
- Testen Sie ihr Programm in folgender Weise
- Eingabe des Domain-Namen „localhost“
- Eingabe von 10 verschiedenen Domain-Namen (z.B. www.google.de)
- Eingabe von 10 verschiedenen IP-Adressen (z.B. 194.94.22.142)
- Überprüfen Sie die erhaltenen IP-Adressen mit Hilfe des Browsers
 */


public class Task2 {
    public static void scanner_loop() throws UnknownHostException {
        Scanner scanner = new Scanner(System.in);
        String choice = "";
        while (true) {
            System.out.println("Translation: \n1. Domain to IP\n"
    + "2. IP to Domain\n0. exit");
            choice = scanner.nextLine();
            int choice_i = -2;

            try {
                choice_i = Integer.parseInt(choice);
            } catch (Exception e) {
                System.out.println("Error. Pleas try again");
            }

            if (choice_i == 0) {
                System.out.println("You pressed exit!");
                break;
            } else if (choice_i == 1) {
                System.out.println("Input Domain Name:");
                String domain_name = scanner.nextLine();
                try {
                    System.out.println(InetAddress.getByName(domain_name));
                } catch (UnknownHostException e) {
                    System.out.println("Unknown Domain Name :(");
                } catch (Exception e) {
                    System.out.println("Error. Pleas try again");
                }
            }
            else if (choice_i == 2) {
                System.out.println("Input IP:");
                String ip_address = scanner.nextLine();

                try {
                    InetAddress address = InetAddress.getByName(ip_address);
                    String hostName = address.getHostName();
                    System.out.println(hostName);
                } catch (UnknownHostException e) {
                    System.out.println("Unknown IP :(");
                } catch (Exception e) {
                    System.out.println("Error. Pleas try again");
                }
            }
        }

    }

    public static void main(String[] args) throws UnknownHostException {
        scanner_loop();
    }
}
