package necesseplus.item;

import necesse.inventory.item.matItem.MatItem;
import necesse.gfx.gameTooltips.ListGameTooltips;
import necesse.entity.mobs.PlayerMob;
import necesse.inventory.InventoryItem;
import necesse.engine.localization.Localization;

public class AncientVultureHead extends MatItem {
	public AncientVultureHead() {
		super(1, Rarity.LEGENDARY);
	}

	@Override
    public ListGameTooltips getTooltips(InventoryItem item, PlayerMob perspective) {
        ListGameTooltips tooltips = super.getTooltips(item, perspective);
        tooltips.add(Localization.translate("itemtooltip", "ancientvultureheadtip"));
        return tooltips;
    }
}

