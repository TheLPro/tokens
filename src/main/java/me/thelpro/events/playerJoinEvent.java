package me.thelpro.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Objects;

public class playerJoinEvent implements Listener {

    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent e) {

        Player player = e.getPlayer();

        if (player.hasPermission("token.loginclear")) {

            ArrayList<String> lore = new ArrayList<String>();
            lore.add(ChatColor.translateAlternateColorCodes('&', "&6/prizes"));
            int slotChecked = 0;
            int tokensRemoved = 0;
            int below5 = 0;
            ArrayList<Integer> removalSlots = new ArrayList<>();

            for (ItemStack item : player.getInventory().getContents()){

                ItemStack slotItem = player.getInventory().getItem(slotChecked);

                if (slotItem == null) {
                    slotChecked++;
                    continue;
                }

                if (!slotItem.hasItemMeta()) {
                    slotChecked++;
                    continue;
                }
                ItemMeta slotItemMeta = slotItem.getItemMeta();

                if (!slotItemMeta.hasLore()) {
                    slotChecked++;
                    continue;
                }

                if (slotItemMeta.getDisplayName().equals(ChatColor.LIGHT_PURPLE + "Season Token") && slotItemMeta.getLore().equals(lore)) {
                    tokensRemoved = tokensRemoved + item.getAmount();
                    removalSlots.add(slotChecked);

                    slotChecked++;
                    continue;
                }
                else {
                    slotChecked++;
                    continue;
                }
            }
            if (tokensRemoved > 5) {
                for (int i = 0; i < removalSlots.size(); i++) {;
                    Objects.requireNonNull(player.getInventory().getItem(removalSlots.get(i))).setAmount(0);
                }
                System.out.println("[Tokens] Removed " + tokensRemoved + " tokens.");
            }
            if (tokensRemoved < 5)
                System.out.println("[Tokens] Player had " + tokensRemoved + " tokens. Did not remove.");
            return;
        } else if (!player.hasPermission("token.loginclear")) {
            return;
        }
    }
}