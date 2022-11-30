package me.thelpro.events;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;

import java.util.ArrayList;

public class inventoryClickEvent implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void inventoryInteractEvent(InventoryClickEvent e) {

        ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&6/prizes"));

        if (e.getView().getTopInventory().getType().equals(InventoryType.ENDER_CHEST)) {
            if (e.getCurrentItem() != null && e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasLore() && e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.LIGHT_PURPLE + "Season Token") && e.getCurrentItem().getItemMeta().getLore().equals(lore)) {
                e.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&', "&f[&eTokens&f] &cYou cannot put a token here!"));
                e.setCancelled(true);
            } else if (e.getClick().equals(ClickType.NUMBER_KEY)) {
                    e.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&', "&f[&eTokens&f] &cYou cannot put a token here!"));
                    e.setCancelled(true);
                }
            }
        }
    }