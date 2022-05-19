package com.WikiSearch.UI;

import net.runelite.client.ui.ColorScheme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class WikiSearchResultPanel extends JPanel {
    private final JLabel qLabel = new JLabel();
    private JPanel qHeader;

    String URL;
    String name;
    public WikiSearchResultPanel(String _name, String _url, WikiSearchPanel MainPanel) {
        this.URL = _url;
        this.name = _name;
        WikiSearchResultPanel thisP = this;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                qLabel.setForeground(ColorScheme.BRAND_ORANGE);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                qLabel.setForeground(Color.white);
            }
        });
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                Desktop desktop = java.awt.Desktop.getDesktop();
                try {
                    URI oURL = new URI(
                            _url);
                    desktop.browse(oURL);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        setLayout(new GridBagLayout());
        setBackground(ColorScheme.DARKER_GRAY_COLOR);
        qLabel.setText(name);
        qLabel.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        add(qLabel);



    }
}
