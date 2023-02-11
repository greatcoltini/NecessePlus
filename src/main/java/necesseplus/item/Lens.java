package necesseplus.item;

import necesse.inventory.item.matItem.MatItem;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.entity.mobs.PlayerMob;
import necesse.inventory.InventoryItem;
import necesse.engine.localization.Localization;

public class Lens extends MatItem {
	public Lens() {
		super(1, Rarity.COMMON);
	}

	@Override
    public ListGameTooltips getTooltips(InventoryItem item, PlayerMob perspective) {
        ListGameTooltips tooltips = super.getTooltips(item, perspective);
        tooltips.add(Localization.translate("itemtooltip", "lens"));
        return tooltips;
    }
}

