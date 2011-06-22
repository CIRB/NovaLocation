package irisnet.nova.client;

import chrriis.common.UIUtils;
import chrriis.dj.nativeswing.swtimpl.NativeInterface;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Date: 27/05/11
 * Time: 12:03
 */
public class WawsClient {

     private final static Pattern LINK_TOKEN = Pattern.compile("<(http.+>;rel=\\w+),<(http.+)>;rel=\\w+");
     //private final static Pattern LINK_TOKEN = Pattern.compile("(<(http://.+)>;rel=\\w+,?){2}");

    public static void main(String[] args) throws Exception{

        URL url = new URL("http://192.168.48.128:8080/nova/brugis/locations");
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

        Map headers = connection.getHeaderFields();

        String link = headers.get("Link").toString().replace("[", "").replace("]", "").replace(" ", "");

        Matcher matcher = LINK_TOKEN.matcher(link);
        System.out.println(matcher.find());

        System.out.println(link);

        final String putGmlinUrl = matcher.group(1);
        final String urlFrontEnd = matcher.group(2);

        //getHeaderField("Link").replace("<", "").replace(">", "");

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
                try {
                    frame.getContentPane().add(new SimpleBrowserPanel(URLDecoder.decode(urlFrontEnd,"UTF-8"), URLDecoder.decode(putGmlinUrl,"UTF-8")), BorderLayout.CENTER);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                frame.setSize(800, 600);
                frame.setLocationByPlatform(true);
                frame.setVisible(true);
            }
        });
        NativeInterface.runEventPump();

    }
}
