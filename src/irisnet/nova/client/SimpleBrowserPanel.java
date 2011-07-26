package irisnet.nova.client;

import chrriis.common.UIUtils;
import chrriis.common.WebServer;
import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserAdapter;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserCommandEvent;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserNavigationParameters;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.Map;


public class SimpleBrowserPanel extends JPanel {

    final JWebBrowser webBrowser;

    public SimpleBrowserPanel() {

        super(new BorderLayout());

        String urlFrontEnd = "http://192.168.48.128:8080/location";
        webBrowser = new JWebBrowser();
        webBrowser.setButtonBarVisible(false);
        webBrowser.setStatusBarVisible(false);
        webBrowser.setMenuBarVisible(false);
        webBrowser.setLocationBarVisible(false);
        webBrowser.addWebBrowserListener(new WebBrowserAdapter() {
            @Override
            public void commandReceived(WebBrowserCommandEvent e) {
                String command = e.getCommand();
                Object[] parameters = e.getParameters();
                System.out.print("sendNSCommand ... executed : " + command + " : " + parameters);
            }
        });

//        webBrowser.navigate("http://staging.brugis.irisnet.be/NovaMap/map.aspx?refnova=145&extent=146621.939,173645.069,146681.939,173665.069");

        // Let's generate the page with the resulting HTTP headers dynamically.
        //webBrowser.navigate(WebServer.getDefaultWebServer().getDynamicContentURL(NavigationParameters.this.getClass().getName(), "header-viewer.html"), parameters);

        webBrowser.navigate(urlFrontEnd);
        add(webBrowser, BorderLayout.CENTER);

    }

}
