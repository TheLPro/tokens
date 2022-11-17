package me.thelpro.commands;

import me.clip.placeholderapi.PlaceholderAPI;
import me.thelpro.Tokensubmission;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class tokenCommand implements CommandExecutor {

    public Tokensubmission plugin = Tokensubmission.getPlugin();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        Player player = (Player) sender;
        FileConfiguration config = plugin.getConfig();
        plugin.saveDefaultConfig();
        if (!player.hasPlayedBefore()) {
            config.addDefault(player.getName(), 0);
            config.options().copyDefaults(true);
            plugin.saveConfig();
        }

        if (args[0].equalsIgnoreCase("import") || args[0].equalsIgnoreCase("i")) {
            if (!player.hasPermission("token.blacklist")) {
                player.sendMessage(ChatColor.RED + "You may not preform this command!");
            } else if (player.hasPermission("token.blacklist")) {

                ItemStack playerItem = player.getInventory().getItemInMainHand();
                int playerItemAmount = playerItem.getAmount();
                int playerItemIndex = player.getInventory().getHeldItemSlot();
                Material playerItemMaterial = playerItem.getType();

                ItemStack token = new ItemStack(playerItemMaterial, playerItemAmount);
                ItemMeta tokenMeta = token.getItemMeta();
                tokenMeta.setDisplayName(ChatColor.YELLOW + "Token");
                ArrayList<String> lore = new ArrayList<String>();
                lore.add("This is a token. Use /token s to submit it.");
                tokenMeta.setLore(lore);
                token.setItemMeta(tokenMeta);
                playerItem.setAmount(0);
                player.getInventory().addItem( token);

            }
        }

        if (args[0].equalsIgnoreCase("submit") || args[0].equalsIgnoreCase("s")) {
            if (!player.hasPermission("token.blacklist")) {
                player.sendMessage(ChatColor.RED + "You may not preform this command!");
            } else if (player.hasPermission("token.blacklist")) {

                if (player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "Token")) {

                    int oldTokens = config.getInt(player.getName(), 0);
                    int newTokens = player.getInventory().getItemInMainHand().getAmount();
                    int finalTokens = oldTokens + newTokens;

                    config.set(player.getName(), finalTokens);
                    player.getInventory().getItemInMainHand().setAmount(0);
                    plugin.saveConfig();

                }

            }
        }

        if (args[0].equalsIgnoreCase("reset")) {

            config.set(args[1], 0);
            plugin.saveConfig();

        }

        return true;
    }
}
