package net.runelite.client.plugins.ammoviewer;

import net.runelite.api.*;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.ImageComponent;
import net.runelite.client.ui.overlay.components.PanelComponent;
import org.apache.commons.lang3.ArrayUtils;

import javax.inject.Inject;
import java.awt.*;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

class AmmoViewerOverlay extends Overlay
{
	private static final int[] thrownIds = {

			// Throwing Kinves

			863, // Iron knife
			864, // Bronze knife
			865, // Steel knife
			866, // Mithril knife
			867, // Adamant knife
			868, // Rune knife
			869, // Black knife
			870, // Bronze knife (p)
			871, // Iron knife (p)
			872, // Steel knife (p)
			873, // Mithril knife (p)
			874, // Black knife (p)
			875, // Adamant knife (p)
			876, // Rune knife (p)
			5654, // Bronze knife (p+)
			5655, // Iron knife (p+)
			5656, // Steel knife (p+)
			5657, // Mithril knife (p+)
			5658, // Black knife (p+)
			5659, // Adamant knife (p+)
			5660, // Rune knife (p+)
			5661, // Bronze knife (p++)
			5662, // Iron knife (p++)
			5663, // Steel knife (p++)
			5664, // Mithril knife (p++)
			5665, // Black knife (p++)
			5666, // Adamant knife (p++)
			5667, // Rune knife (p++)

			// Darts

			806, // Bronze dart
			807, // Iron dart
			808, // Steel dart
			809, // Mithril dart
			810, // Adamant dart
			811, // Rune dart
			812, // Bronze dart (p)
			813, // Iron dart (p)
			814, // Steel dart (p)
			815, // Mithril dart (p)
			816, // Adamant dart (p)
			817, // Rune dart (p)
			818, // Poisoned dart (p)
			3093, // Black dart
			3094, // Black dart (p)
			5628, // Bronze dart (p+)
			5629, // Iron dart (p+)
			5630, // Steel dart (p+)
			5631, // Black dart (p+)
			5632, // Mithril dart (p+)
			5633, // Adamant dart (p+)
			5634, // Rune dart (p+)
			5635, // Bronze dart (p++)
			5636, // Iron dart (p++)
			5637, // Steel dart (p++)
			5638, // Black dart (p++)
			5639, // Mithril dart (p++)
			5640, // Adamant dart (p++)
			5641, // Rune dart (p++)
			11230, // Dragon dart
			11231, // Dragon dart (p)
			11233, // Dragon dart (p+)
			11234, // Dragon dart (p++)
	};

	private final Client client;
	private final ItemManager itemManager;

	@Inject
	private AmmoViewerConfig config;

	private final PanelComponent panelComponent = new PanelComponent();

	@Inject
	private AmmoViewerOverlay(Client client, ItemManager itemManager)
	{
		setPosition(OverlayPosition.BOTTOM_LEFT);
		panelComponent.setWrapping(4);
		panelComponent.setGap(new Point(6, 4));
		panelComponent.setOrientation(PanelComponent.Orientation.HORIZONTAL);
		this.client = client;
		this.itemManager = itemManager;
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		final ItemContainer equipment = client.getItemContainer(InventoryID.EQUIPMENT);

		if (equipment == null)
		{
			return null;
		}

		panelComponent.getChildren().clear();

		final List<Item> items = new ArrayList<>();
		
		Item equip;
		if (equipment.getItems().length > 13) // Do to a bug where the AMMO slot doesn't exist if it is empty.
		{
			equip = equipment.getItems()[EquipmentInventorySlot.AMMO.getSlotIdx()];
			if (config.showRanged() && equipment.getItems().length > 12 && equip.getId() != -1)
				items.add(equip);
		}

		equip = equipment.getItems()[EquipmentInventorySlot.WEAPON.getSlotIdx()];
		if (config.showRanged() && equip.getId() != -1 && ArrayUtils.contains(thrownIds, equip.getId()))
			items.add(equip);

		for (Item item : items)
			if (item.getQuantity() > 0)
			{
				final BufferedImage image = getImage(item);
				if (image != null)
					panelComponent.getChildren().add(new ImageComponent(image));
			}

		return panelComponent.render(graphics);
	}

	private BufferedImage getImage(Item item)
	{
		return itemManager.getImage(item.getId(), item.getQuantity(), item.getQuantity() > 1);
	}
}
