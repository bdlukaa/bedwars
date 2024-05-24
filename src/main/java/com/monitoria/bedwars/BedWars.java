package com.monitoria.bedwars;

import com.monitoria.bedwars.elements.StartCommand;
import com.monitoria.bedwars.game.Game;
import com.monitoria.bedwars.game.GameListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;



public final class BedWars extends JavaPlugin {

    public static Game game;

    @Override
    public void onEnable() {
        Bukkit.getLogger().info(ChatColor.GREEN + "Enabled " + this.getName());

        game = new Game();
        getServer().getPluginManager().registerEvents(new GameListener(game), this);
        this.getCommand("iniciar_bedwars").setExecutor(new StartCommand());
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info(ChatColor.RED + "Disabled " + this.getName());
    }
}
