package info.preva1l.fadlc.menus;

import info.preva1l.fadlc.managers.LayoutManager;
import info.preva1l.fadlc.managers.UserManager;
import info.preva1l.fadlc.menus.lib.ItemBuilder;
import info.preva1l.fadlc.menus.lib.pagination.PaginatedFastInv;
import info.preva1l.fadlc.menus.lib.pagination.PaginatedItem;
import info.preva1l.fadlc.models.claim.IClaimProfile;
import info.preva1l.fadlc.models.user.OnlineUser;
import info.preva1l.fadlc.utils.Text;
import org.bukkit.entity.Player;

import java.util.List;

public class ProfilesMenu extends PaginatedFastInv {
    private final OnlineUser user;

    public ProfilesMenu(Player player) {
        super(player, LayoutManager.MenuType.PROFILES, List.of(11, 12, 13, 14, 15, 20, 21, 22, 23, 24));
        this.user = UserManager.getInstance().getUser(player.getUniqueId()).orElseThrow();

        setPaginationMappings(getLayout().paginationSlots());

        placeFillerItems();
        placeNavigationItems();
        updatePagination();
    }

    private void placeNavigationItems() {
        setItem(getLayout().buttonSlots().getOrDefault(LayoutManager.ButtonType.BACK, -1),
                getLang().getItemStack("").getBase());
    }

    @Override
    protected void fillPaginationItems() {
        for (IClaimProfile profile : user.getClaim().getProfiles().values()) {
            ItemBuilder itemStack = new ItemBuilder(profile.getIcon())
                    .name(Text.legacyMessage(profile.getName()))
                    .lore("profile");


            addPaginationItem(new PaginatedItem(itemStack.build(), (e) -> {
            }));
        }
    }

    @Override
    protected void addPaginationControls() {

    }
}
