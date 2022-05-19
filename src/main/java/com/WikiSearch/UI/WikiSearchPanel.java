package com.WikiSearch.UI;

import com.WikiSearch.Helpers.WikiSearchHelper;

import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.ui.components.IconTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class WikiSearchPanel extends PluginPanel {

    private final IconTextField searchBar = new IconTextField();

    List<WikiSearchResultPanel> panels = new ArrayList<>();
    List<WikiSearchResultPanel> SearchHistory = new ArrayList<>();


    public WikiSearchPanel()
    {
        super();

        this.setLayout(new GridLayout(0, 1, 2,2));
        this.setBorder(new EmptyBorder(10, 10, 10, 10));
        searchBar.setIcon(IconTextField.Icon.SEARCH);
        searchBar.setPreferredSize(new Dimension(PluginPanel.WIDTH - 10, 30));
        searchBar.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        searchBar.setHoverBackgroundColor(ColorScheme.DARK_GRAY_HOVER_COLOR);
        searchBar.getDocument().addDocumentListener(new DocumentListener()
        {
            @Override
            public void insertUpdate(DocumentEvent e)
            {
                try {
                    onSearchBarChanged();
                } catch (ExecutionException ex) {
                    throw new RuntimeException(ex);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                } catch (TimeoutException ex) {
                    throw new RuntimeException(ex);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e)
            {
                try {
                    onSearchBarChanged();
                } catch (ExecutionException ex) {
                    throw new RuntimeException(ex);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                } catch (TimeoutException ex) {
                    throw new RuntimeException(ex);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e)
            {
                try {
                    onSearchBarChanged();
                } catch (ExecutionException ex) {
                    throw new RuntimeException(ex);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                } catch (TimeoutException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        add(searchBar);

    }
    private void onSearchBarChanged() throws ExecutionException, InterruptedException, TimeoutException {
        if (panels.size() != 0) {
            panels.forEach((this::remove));
        }
        final String text = searchBar.getText();
        if (text.length() == 0){
            SearchHistory.forEach((this::add));
            revalidate();
        }else {
            String Formatted = text.replace(" ", "%20");
            HashMap<String, String> results = WikiSearchHelper.SearchApi(Formatted);

            results.forEach((key, value) -> {
                this.AddSearchResult(key, value);
                revalidate();
            });
        }
    }

    public void AddSearchResult(String result, String url){
        WikiSearchResultPanel rPanel = new WikiSearchResultPanel(result, url, this);
        this.add(rPanel);
        panels.add(rPanel);
    }


}