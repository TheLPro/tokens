package me.thelpro;

import me.thelpro.commands.tokenCommand;
import me.thelpro.commands.tokenTab;
import me.thelpro.events.inventoryClickEvent;
import me.thelpro.events.playerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Tokensubmission extends JavaPlugin {

    private static Tokensubmission plugin;

    @Override
    public void onEnable() {

        plugin = this;
        getConfig().options().copyDefaults(true);
        saveConfig();

        new me.thelpro.placeholders.placeholders().register();
        getCommand("token").setExecutor(new tokenCommand());
        getServer().getPluginManager().registerEvents(new playerJoinEvent(), this);
        getServer().getPluginManager().registerEvents(new inventoryClickEvent(), this);
        getCommand("token").setTabCompleter(new tokenTab());

    }


    public static Tokensubmission getPlugin() {
        return plugin;
    }

}