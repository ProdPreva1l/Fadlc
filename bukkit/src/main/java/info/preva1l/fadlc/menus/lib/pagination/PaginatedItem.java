package info.preva1l.fadlc.menus.lib.pagination;

import info.preva1l.fadlc.menus.lib.GuiClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public record PaginatedItem(ItemStack itemStack, Consumer<GuiClickEvent> eventConsumer) {
}
