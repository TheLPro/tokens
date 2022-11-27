package me.thelpro.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.thelpro.Tokensubmission;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class placeholders extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return "token";
    }

    @Override
    public @NotNull String getAuthor() {
        return "TheLPro";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {

        Tokensubmission plugin = Tokensubmission.getPlugin();
        if (params.equals("tokens")) {
            if (!player.hasPlayedBefore())
                plugin.getConfig().addDefault(player.getName(), 0);
            int tokens = plugin.getConfig().getInt(player.getName());

            return Integer.toString(tokens);
        }
        return "null";
    }
}
