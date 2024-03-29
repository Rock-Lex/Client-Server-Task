package uebung2;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.Scanner;

public class Task5_2 {
    public static void main(String[] args) {
        String url_google = "http://www.google.de/index.html";
        String url_tagesschau = "http://www.tagesschau.de/index.html";

        System.out.println("Wähle aus \n1. Google\n2. Tagesschau");
        Scanner scanner = new Scanner(System.in);
        int auswahl = Integer.parseInt(scanner.next());

        HttpGet request;
        if(auswahl == 1){
            request = new HttpGet(url_google);
        } else {
            request = new HttpGet(url_tagesschau);
        }
        try {
            HttpClient client = HttpClients.createDefault();
            HttpResponse response = client.execute(request);
            String responseBody = EntityUtils.toString(response.getEntity());
            System.out.println(responseBody);
        } catch(Exception e) {
            System.out.println(e);
        } finally {
            scanner.close();
        }
    }
}
