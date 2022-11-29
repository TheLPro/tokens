package me.thelpro.events;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.*;

public class blockPlaceEvent implements Listener {

    @EventHandler
    public void blockPlaceEvent(PlayerInteractEvent e) {

        ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&6/prizes"));

        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (e.getItem() != null && e.getItem().hasItemMeta() && e.getItem().getItemMeta().getLore().equals(lore) && e.getItem().getItemMeta().getDisplayName().equals(ChatColor.LIGHT_PURPLE + "Season Token")) {
                e.setCancelled(true);
            }
        }
    }
}