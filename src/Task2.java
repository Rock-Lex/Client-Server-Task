import java.net.UnknownHostException;
import java.util.Scanner;
import java.net.InetAddress;

public class Task2 {
    public static void scanner_loop() throws UnknownHostException {
        Scanner scanner = new Scanner(System.in);
        String choice = "";
        while (true) {
            System.out.println("Translation: \n1. Domain to IP\n2. IP to Domain\n0. exit");
            choice = scanner.nextLine();
            int choice_i = Integer.parseInt(choice);
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
                }
            }
        }

    }

    public static void main(String[] args) throws UnknownHostException {
        scanner_loop();
    }
}
