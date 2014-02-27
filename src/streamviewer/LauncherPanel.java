/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package streamviewer;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

/**
 *
 * @author Kevin
 */
public class LauncherPanel extends JPanel
{
    private GroupLayout layout;
    
    private JLabel streamAddressLabel = new JLabel("Stream URL");
    private JComboBox streamAddressChooser;
    private ActionListener streamAddressListener;
    private JCheckBox favoriteBox;
    
    private JLabel qualityChooserLabel = new JLabel("Quality");
    private JComboBox qualityChooser;
    private String[] qualityChoice = {"Best", "Worst"};
    
    private JButton startStreamButton;
    private ActionListener startStreamListener;
    
    
    private String urlParam = "";
    private String qualityParam = "";
    
    private StreamManager streamManager = new StreamManager();
    // Layout definitions
    private final int TEXTFIELD_H = 28;
    
    private final int STREAM_ADDRESS_FIELD_W_MIN = 180;
    private final int STREAM_ADDRESS_FIELD_W_PREF = 200;
    private final int STREAM_ADDRESS_FIELD_W_MAX = 220;
    
    public LauncherPanel()
    {
        layout = new GroupLayout(this);
        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);
                setLayout(layout);
                
        qualityChooser = new JComboBox(qualityChoice);
        qualityChooser.setToolTipText("Choose stream quality");
        
        streamManager.createChannel("http://www.twitch.tv/yellowpete", true);
        
        streamAddressChooser = new JComboBox(streamManager.getFavChannels().toArray());
        streamAddressChooser.setEditable(true);
        streamAddressChooser.setMaximumRowCount(10);
        streamAddressChooser.setToolTipText("Stream URL (eg. http://www.twitch.tv/darkfaith93)");
        
        
        favoriteBox = new JCheckBox("Bookmark", streamManager.getCurrentChannel().isFavorite());
        favoriteBox.addItemListener(new ItemListener()
        {
            @Override
            public void itemStateChanged(ItemEvent ie)
            {
                boolean selected = favoriteBox.isSelected();
                
                if (streamManager.setFavorite(selected))
                {
                    if (selected)
                    {
                        streamAddressChooser.addItem(streamManager.getCurrentChannel());
                    }
                    else
                    {
                        if (streamAddressChooser.getItemCount() > 1)
                            streamAddressChooser.removeItem(streamManager.getCurrentChannel());
                    }
                }

            }
        });
        
        streamAddressListener = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (!streamAddressChooser.isPopupVisible())
                {
                    streamManager.createChannel(streamAddressChooser.getSelectedItem() + "", favoriteBox.isSelected());
                }
                else
                {
                    streamManager.setCurrentChannel(streamAddressChooser.getSelectedItem() + "");
                }
                favoriteBox.setSelected(streamManager.getCurrentChannel().isFavorite());
            }
        };
        streamAddressChooser.addActionListener(streamAddressListener);
        
        startStreamButton = new JButton("Launch Stream");
        streamAddressChooser.setToolTipText("Start viewing stream in VLC");
        
        /*
         * 
         */
        startStreamListener = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent ae)
            {
                try
                {
                    urlParam = streamAddressChooser.getSelectedItem().toString();
                    qualityParam = (String) qualityChooser.getSelectedItem();
                    new ProcessBuilder("livestreamer.exe", urlParam, qualityParam).start();
                } catch (IOException ex) {
                    livestreamerNotDetected(startStreamButton);
                }
            }
        };
        startStreamButton.addActionListener(startStreamListener);
        addComponents();
    }
    
    private void livestreamerNotDetected(Component cp)
    {
        try
        {
            String title = "No Livestreamer";
            final URI lswebsite = new URI("https://github.com/chrippa/livestreamer/releases");
            JEditorPane pane = new JEditorPane("text/html", "<html><body>Livestreamer installation not detected.<br>"
                    + "<a href=\"https://github.com/chrippa/livestreamer/releases/\">Download Livestreamer</a>"
                    + "</body></html>");
            pane.setEditable(false);
            pane.setBackground(cp.getBackground());
            pane.addHyperlinkListener(new HyperlinkListener()
            {
                @Override
                public void hyperlinkUpdate(HyperlinkEvent he)
                {
                    try
                    {
                        if (he.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED))
                            Desktop.getDesktop().browse(lswebsite);
                    } catch (IOException ex1)
                    {
                        Logger.getLogger(LauncherPanel.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
            } );
            JOptionPane.showMessageDialog(cp, pane, title, JOptionPane.ERROR_MESSAGE);
        } catch (URISyntaxException ex1)
        {
            Logger.getLogger(LauncherPanel.class.getName()).log(Level.SEVERE, null, ex1);
        }
    }
    
    private void addComponents()
    {
        layout.setHorizontalGroup(layout
        .createSequentialGroup()
            .addGroup(layout
            .createParallelGroup()
                .addComponent(streamAddressLabel)
                .addComponent(streamAddressChooser, STREAM_ADDRESS_FIELD_W_MIN, STREAM_ADDRESS_FIELD_W_PREF, STREAM_ADDRESS_FIELD_W_MAX)
            )
                .addComponent(favoriteBox)
            .addGroup(layout
            .createParallelGroup()
                .addComponent(qualityChooserLabel)
                .addComponent(qualityChooser, 80, 90, 100)
            )
            .addComponent(startStreamButton)
        );
        
        layout.setVerticalGroup(layout
        .createSequentialGroup()
            .addGroup(layout
            .createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(streamAddressLabel)
                    .addComponent(qualityChooserLabel)
                )
            .addGroup(layout
            .createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(streamAddressChooser, TEXTFIELD_H, TEXTFIELD_H, TEXTFIELD_H)
                .addComponent(favoriteBox)
                .addComponent(qualityChooser, 28, 28, 28)
                .addComponent(startStreamButton)
                )
            
        );
    }
}
