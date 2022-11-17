package me.thelpro;

import me.thelpro.commands.tokenCommand;
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

    }


    public static Tokensubmission getPlugin() {
        return plugin;
    }

}