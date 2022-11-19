package me.thelpro.commands;

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
        if (config.get(player.getName()) == null) {
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
                Material playerItemMaterial = playerItem.getType();

                ItemStack token = new ItemStack(playerItemMaterial, playerItemAmount);
                ItemMeta tokenMeta = token.getItemMeta();

                tokenMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Season Token");
                ArrayList<String> lore = new ArrayList<String>();
                lore.add(ChatColor.translateAlternateColorCodes('&', "&6/prizes"));
                tokenMeta.setLore(lore);
                token.setItemMeta(tokenMeta);

                playerItem.setAmount(0);
                player.getInventory().addItem( token);

            }
        }

        if (args[0].equalsIgnoreCase("submit") || args[0].equalsIgnoreCase("s")) {
            if (!player.hasPermission("token.blacklist") || !player.hasPermission("token.submit")) {
                player.sendMessage(ChatColor.RED + "You may not preform this command!");
            } else if (player.hasPermission("token.blacklist") || player.hasPermission("token.submit")) {

                int oldTokens = config.getInt(player.getName(), 0);
                int newTokens = 0;

                int slotChecked = 0;

                while (slotChecked != 36) {

                    ItemStack slotItem = player.getInventory().getItem(slotChecked);

                    if (slotItem == null) {
                        slotChecked++;
                        continue;
                    }

                    ItemMeta slotItemMeta = slotItem.getItemMeta();

                    if (!slotItem.hasItemMeta()) {
                        slotChecked++;
                        continue;
                    }

                    if (slotItemMeta.getDisplayName().equals(ChatColor.LIGHT_PURPLE + "Season Token")) {

                        newTokens = newTokens + slotItem.getAmount();
                        slotItem.setAmount(0);
                        slotChecked++;

                    }
                }

                player.sendMessage(Integer.toString(newTokens));
                config.set(player.getName(), newTokens + oldTokens);
                plugin.saveConfig();

                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f[&eTokens&f] &7You have submitted &f" + newTokens + " tokens&7. Total tokens: &f%token_tokens%"));

            }
        }

        if (args[0].equalsIgnoreCase("info")) {

            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f[&eTokens&f] &7Plugin made by: &fTheLPro"));

        }

        return true;
    }
}
