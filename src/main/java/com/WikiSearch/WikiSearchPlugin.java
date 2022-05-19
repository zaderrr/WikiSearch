package com.WikiSearch;

import com.WikiSearch.UI.WikiSearchPanel;
import com.google.inject.Provides;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;

@Slf4j
@PluginDescriptor(
		name = "Wiki Search",
		description = "Allows you to search the OSRS wiki in client",
		tags = { "wiki", "search", "help" }
)
public class WikiSearchPlugin extends Plugin

{
	@Inject
	private Client client;


	@Inject
	ClientToolbar clientToolbar;

	NavigationButton navButton;
	@Inject
	private WikiSearchConfig config;

	@Override
	protected void startUp() throws Exception
	{
		log.info("Wiki Search started!");
		WikiSearchPanel panel = new WikiSearchPanel();

		final BufferedImage icon = ImageIO.read(new FileInputStream("icon.png"));

		navButton = NavigationButton.builder()
				.tooltip("Wiki Search")
				.icon(icon)
				.priority(15)
				.panel(panel)
				.build();

		clientToolbar.addNavigation(navButton);
	}

	@Override
	protected void shutDown() throws Exception
	{
		clientToolbar.removeNavigation(navButton);
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN)
		{

		}
	}

	@Provides
	WikiSearchConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(WikiSearchConfig.class);
	}
}