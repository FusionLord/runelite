package net.runelite.client.plugins.ammoviewer;

import com.google.inject.Provides;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import javax.inject.Inject;

@PluginDescriptor(
		name = "Ammo Viewer",
		description = "Adds an overlay displaying the amount of ammo you have remaining.",
		tags = {"alternate", "items", "overlay", "magic", "range"},
		enabledByDefault = false
)
public class AmmoViewerPlugin extends Plugin
{
	@Inject
	private AmmoViewerOverlay overlay;

	@Inject
	private OverlayManager overlayManager;

	@Provides
	AmmoViewerConfig getConfig(ConfigManager configManager)
	{
		return configManager.getConfig(AmmoViewerConfig.class);
	}

	@Override
	protected void startUp() throws Exception
	{
		overlayManager.add(overlay);
	}

	@Override
	protected void shutDown() throws Exception
	{
		overlayManager.remove(overlay);
	}
}
