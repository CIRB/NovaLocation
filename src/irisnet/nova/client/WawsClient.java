package irisnet.nova.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Date: 27/05/11
 * Time: 12:03
 */
public class WawsClient {

    public static void main(String[] args) throws Exception{

        URL url = new URL("http://tomcat.adress:8080/nova/location/brugis");
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setDoOutput(true);
        connection.setInstanceFollowRedirects(false);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/xml");

        OutputStream os = connection.getOutputStream();
        os.write("<location><iddosdossier>4</iddosdossier></location>".getBytes());
        os.flush();

        if(connection.getResponseCode() != HttpURLConnection.HTTP_OK){
            throw new RuntimeException("Failed to create locationBrugis item.");
        }

        System.out.println("\n Link : " + connection.getHeaderField("Link"));

        String link = connection.getHeaderField("Link").replace("<", "").replace(">", "");

        String urlFrontEnd;
        String rel = link.split(";")[1];

        if(rel.equals("next"))
            urlFrontEnd = link.split(";")[0];


        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String line = reader.readLine();
         while (line != null){

             System.out.println(line);
             line = reader.readLine();
         }

        connection.disconnect();

    }

}
