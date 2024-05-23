package com.monitoria.bedwars.elements;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Team {

    public Player player1;
    public Player player2;
    public Player player3;

    // Spawn points
    public int x;
    public int y;
    public int z;

    Color color;

    public boolean isBedActive = true;

    public Team(Color color, int x, int y, int z) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void teleportPlayerToSpawn(Player player) {
        player.teleport(new Location(player.getWorld(), x, y, z));
    }

}
