package stackkeeper.stackkeeper;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class StackKeeper extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getAction() == InventoryAction.PLACE_ALL) {
            if (event.getCursor().getType() != Material.POTION &&
                event.getCursor().getType() != Material.SPLASH_POTION &&
                event.getCursor().getType() != Material.LINGERING_POTION)
                return;
            new BukkitRunnable() {
                Player player = (Player) event.getWhoClicked();

                @Override
                public void run() {
                    event.getClickedInventory().getItem(event.getSlot()).setAmount(event.getCursor().getAmount() + 1);
                    player.setItemOnCursor(new ItemStack(Material.AIR, 1));
                }
            }.runTaskLater(this, 1);
        }
    }

    @EventHandler
    public void onClick(InventoryDragEvent event) {
        if (event.getCursor() != null) {
            if (event.getCursor().getType() == Material.POTION ||
                event.getCursor().getType() == Material.SPLASH_POTION ||
                event.getCursor().getType() == Material.LINGERING_POTION) {
                event.setCancelled(true);
            }
        }
    }
}