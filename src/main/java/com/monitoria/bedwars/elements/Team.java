package com.monitoria.bedwars.elements;

import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Team {

    public Player player1;
    public Player player2;
    public Player player3;

    public Player[] getPlayers() {
        Player[] players = new Player[] { player1, player2, player3 };
        return players;
    }

    // Spawn points
    public int x;
    public int y;
    public int z;

    public Color color;

    public boolean isBedActive = true;

    public ItemSpawner itemSpawner;
    public Material bedMaterial;

    public Team(Color color, int x, int y, int z, ItemSpawner itemSpawner, Material material) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.z = z;
        this.itemSpawner = itemSpawner;
        this.bedMaterial = material;
    }

    public void teleportPlayerToSpawn(Player player) {

        if (player != null) player.teleport(new Location(player.getWorld(), x, y, z));
    }

    // Ver a quantidade de jogadores do time vivos
    public int amountAlive() {
        int alive = 0;
        if (player1.getGameMode() == GameMode.SURVIVAL) alive++;
        if (player2.getGameMode() == GameMode.SURVIVAL) alive++;
        if (player3.getGameMode() == GameMode.SURVIVAL) alive++;

        return alive;
    }

}
