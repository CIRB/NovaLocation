package irisnet.nova.client;

import chrriis.common.UIUtils;
import chrriis.dj.nativeswing.swtimpl.NativeInterface;

import javax.swing.*;
import java.awt.*;
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

        final String urlFrontEnd;
        String rel = link.split(";")[1];

        if(rel.equals("rel=next"))
            urlFrontEnd = link.split(";")[0];
        else
            urlFrontEnd = null;


        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String line = reader.readLine();
         while (line != null){

             System.out.println(line);
             line = reader.readLine();
         }

        connection.disconnect();


        UIUtils.setPreferredLookAndFeel();
        NativeInterface.open();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("Nova Location - Test");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.getContentPane().add(new SimpleBrowserPanel(urlFrontEnd), BorderLayout.CENTER);
                frame.setSize(800, 600);
                frame.setLocationByPlatform(true);
                frame.setVisible(true);
            }
        });
        NativeInterface.runEventPump();

    }

}
