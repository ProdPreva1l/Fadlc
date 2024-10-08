package info.preva1l.fadlc.menus;

import info.preva1l.fadlc.managers.LayoutManager;
import info.preva1l.fadlc.menus.lib.FastInv;
import org.bukkit.entity.Player;

public class ProfilesMenu extends FastInv {
    private final Player player;

    public ProfilesMenu(Player player) {
        super(LayoutManager.MenuType.PROFILES);
        this.player = player;

        placeFillerItems();
    }

    private void placeNavigationItems() {
        setItem(getLayout().buttonSlots().getOrDefault(LayoutManager.ButtonType.BACK, -1),
                getLang().getItemStack("").getBase());
    }
}
