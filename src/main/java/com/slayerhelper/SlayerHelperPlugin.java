package com.slayerhelper;

import com.slayerhelper.ui.panels.SlayerPluginPanel;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.util.ImageUtil;

import javax.inject.Inject;
import java.awt.image.BufferedImage;

@Slf4j
@PluginDescriptor(
	name = "Slayer Helper"
)
public class SlayerHelperPlugin extends Plugin {
	private static final String ICON_PATH = "/images/icon.png";

	@Inject
	private ClientToolbar clientToolbar;

	private SlayerPluginPanel slayerPanel;
	private NavigationButton navButton;

	@Override
	protected void startUp() {
		slayerPanel = injector.getInstance(SlayerPluginPanel.class);
		navButton = getNavButton();
		clientToolbar.addNavigation(navButton);
	}

	@Override
	protected void shutDown() {
		if (navButton != null) {
			clientToolbar.removeNavigation(navButton);
		}
	}

	private NavigationButton getNavButton() {
		BufferedImage bufferedImage = ImageUtil.loadImageResource(getClass(), ICON_PATH);
		if (bufferedImage == null) {
			log.error("Can't find image @ " + ICON_PATH);
		}

		return NavigationButton.builder()
				.tooltip("Slayer Helper")
				.icon(bufferedImage)
				.priority(10)
				.panel(slayerPanel)
				.build();
	}
}

