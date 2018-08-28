package net.runelite.client.plugins.ammoviewer;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("ammoviewer")
public interface AmmoViewerConfig extends Config
{
	@ConfigItem(
			keyName = "showranged",
			name = "Ranged Ammo",
			description = "Show Ranged ammo in overlay."
	)
	default boolean showRanged()
	{
		return true;
	}
}
