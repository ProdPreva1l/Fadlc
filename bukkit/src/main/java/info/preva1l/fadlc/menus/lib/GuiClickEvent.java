package info.preva1l.fadlc.menus.lib;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.DragType;
import org.jetbrains.annotations.Nullable;

@Getter
public class GuiClickEvent {
    private final Player player;
    private final int slot;
    private final ClickType type;
    private final boolean isDrag;
    @Nullable
    private DragType dragType;

    public GuiClickEvent(Player player, int slot, ClickType type) {
        this.player = player;
        this.slot = slot;
        this.type = type;
        this.isDrag = false;
    }

    public GuiClickEvent(Player player, int slot, @Nullable DragType dragType) {
        this.player = player;
        this.slot = slot;
        this.type = ClickType.LEFT;
        this.isDrag = true;
        this.dragType = dragType;
    }
}
