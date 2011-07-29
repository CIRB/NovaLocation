package irisnet.nova.client;

import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import javax.swing.*;
import java.awt.*;


public class SimpleBrowserPanel extends JPanel {

    final JWebBrowser webBrowser;
    static final String URL = "http://ws.irisnetlab.be/gui/prototype.html";

    public SimpleBrowserPanel() {

        super(new BorderLayout());

        webBrowser = new JWebBrowser();
        webBrowser.setButtonBarVisible(false);
        webBrowser.setStatusBarVisible(false);
        webBrowser.setMenuBarVisible(false);
        webBrowser.setLocationBarVisible(false);
        webBrowser.navigate(URL);
        add(webBrowser, BorderLayout.CENTER);

    }

}
