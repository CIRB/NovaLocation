package irisnet.nova.client;

import chrriis.common.UIUtils;
import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import javax.swing.*;
import java.awt.*;

/**
 * Date: 27/05/11
 * Time: 12:03
 */
public class WawsClient {

    public static void main(String[] args) throws Exception{

        UIUtils.setPreferredLookAndFeel();
        NativeInterface.open();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("Nova Location (Prototype)");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.getContentPane().add(new SimpleBrowserPanel(), BorderLayout.CENTER);
                frame.setSize(800, 600);
                frame.setLocationByPlatform(true);
                frame.setVisible(true);
            }
        });
        NativeInterface.runEventPump();

    }
}
