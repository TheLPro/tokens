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
import org.bukkit.permissions.Permissible;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class tokenCommand implements CommandExecutor {

    public Tokensubmission plugin = Tokensubmission.getPlugin();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        Player player = (Player) sender;
        ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&6/prizes"));

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
        if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "-----------------------&f[&eTokens&f]-----------------------\n/token i &7to make a item into a token.\n&f/token s &7to submit a token.\n&f/token info &7to view info about plugin.\n&f-----------------------------------------------------"));
            return true;
        }

        else if (args[0].equalsIgnoreCase("import") || args[0].equalsIgnoreCase("i")) {
            if (!player.hasPermission("token.blacklist")) {
                player.sendMessage(ChatColor.RED + "You may not preform this command!");
            } else if (player.hasPermission("token.blacklist")) {

                ItemStack playerItem = player.getInventory().getItemInMainHand();
                int playerItemAmount = playerItem.getAmount();
                Material playerItemMaterial = playerItem.getType();

                ItemStack token = new ItemStack(playerItemMaterial, playerItemAmount);
                ItemMeta tokenMeta = token.getItemMeta();

                tokenMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Season Token");
                tokenMeta.setLore(lore);
                token.setItemMeta(tokenMeta);

                playerItem.setAmount(0);
                player.getInventory().addItem( token);

                return true;

            }
        }

        else if (args[0].equalsIgnoreCase("submit") || args[0].equalsIgnoreCase("s")) {
            if (!player.hasPermission("token.blacklist") || !player.hasPermission("token.submit")) {
                player.sendMessage(ChatColor.RED + "You may not preform this command!");
            } else if (!player.hasPermission("token.blacklist") || player.hasPermission("token.submit")) {

                int oldTokens = config.getInt(player.getName(), 0);
                int newTokens = 0;

                int slotChecked = 0;

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
                            newTokens = newTokens + slotItem.getAmount();
                            slotItem.setAmount(0);
                            slotChecked++;
                            continue;
                    }
                    else {
                        slotChecked++;
                        continue;
                    }
                }

                if (newTokens == 0) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f[&eTokens&f] &7There were no tokens for you to submit!"));
                }

                config.set(player.getName(), newTokens + oldTokens);
                oldTokens = newTokens + oldTokens;
                plugin.saveConfig();

                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f[&eTokens&f] &7You have submitted &f" + newTokens + " tokens&7. Total tokens: &f" + oldTokens));

                return true;

            }
        }
        if (args[0].equalsIgnoreCase("info")) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f[&eTokens&f] &7Plugin commissioned from: &fTheLPro"));
            return true;
        }
        else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f[&eTokens&f] &f/token help &7for help."));
        }
        return true;
    }
}
