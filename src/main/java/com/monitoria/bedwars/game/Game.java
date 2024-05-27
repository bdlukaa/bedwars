package com.monitoria.bedwars.game;

import com.monitoria.bedwars.elements.ItemSpawner;
import com.monitoria.bedwars.elements.Team;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class Game {

    List<Team> teams = new ArrayList<>();

    GameStatus status = GameStatus.iniciando;

    public int lobbyX = 0;
    public int lobbyZ = 0;
    public int lobbyY = 92;

    List<ItemSpawner> itemSpawnPoints = new ArrayList<>();

    public Game() {
        resetar();
    }


    public void iniciar() {
        for (World w : Bukkit.getWorlds()) {
            for (org.bukkit.entity.Entity e : w
                    .getEntities()) {
                if (e instanceof Villager) {
                    e.remove();
                }
            }
        }
        
        for (Team team : teams) {
            for (Player player : team.getPlayers()) {
                if (player != null) {
                    team.teleportPlayerToSpawn(player);
                    player.setGameMode(GameMode.SURVIVAL);
                    player.setRespawnLocation(new Location(player.getWorld(), team.x, team.y, team.z));
                    player.getInventory().clear();
                    player.sendMessage("Você está no time" + team.color.toString()+"!");
                    
                    //11 - mudar a cor do nome do player para a cor do time dele
                    player.setDisplayName(team.color.toString() + player.getDisplayName());
                }
            }

            World world = Bukkit.getWorld("world");
            Entity entity = world.spawnEntity(new Location(world, team.x, team.y - 2, team.z), EntityType.VILLAGER);
            if (entity instanceof Villager) {
                Villager villager = (Villager) entity;
                villager.setInvulnerable(true);
                //desliga "inteligencia" das entidades
                villager.setAI(false);
                villager.setCustomName("§aCainã");
            }

            // Começar o timer dos itemSpawners
            team.itemSpawner.startTimer();
        }
    }

    void finalizar() {
        Bukkit.broadcastMessage("FINALIZANDO JOGO");
        for (Team team : teams) {
            for (Player player : team.getPlayers()) {
                player.setRespawnLocation(new Location(player.getWorld(), lobbyX, lobbyY, lobbyZ));
                player.getInventory().clear();
            }
        }

        for (World w : Bukkit.getWorlds()) {
            for (org.bukkit.entity.Entity e : w
                    .getEntities()) {
                if (e instanceof Villager) {
                    e.remove();
                }
            }
        }
    }

    void ganhar(Team team) {
        Bukkit.broadcastMessage("O time " + team.color.toString() + " GANHOU!!");
        new BukkitRunnable() {

            public void run() {
                finalizar();
            }

        }.runTaskLater(Bukkit.getPluginManager().getPlugin("BedWars"), 1000 * 5);
    }

    void resetar() {
        World world = Bukkit.getWorld("world");

        teams.clear();
        teams.add(new Team(Color.RED, -108, 52, -47, new ItemSpawner(new Location(world, -108, 50, -52), null, 0), Material.RED_BED));
        teams.add(new Team(Color.PURPLE, -52, 52, -46, new ItemSpawner(new Location(world, -52, 50, -52), null, 0), Material.PURPLE_BED));
        teams.add(new Team(Color.BLACK, -106, 52, 47, new ItemSpawner(new Location(world, -106, 50, 54), null, 0), Material.BLACK_BED));
        teams.add(new Team(Color.WHITE, 43, 52, 50, new ItemSpawner(new Location(world, 43, 50, 54), null, 0), Material.WHITE_BED));
        teams.add(new Team(Color.BLUE, 99, 52, 50, new ItemSpawner(new Location(world, 99, 50, 54), null, 0), Material.BLUE_BED));


        itemSpawnPoints.clear();
        itemSpawnPoints.add(new ItemSpawner(new Location(world, 69, 51, 0), Material.DIAMOND, 300));
        itemSpawnPoints.add(new ItemSpawner(new Location(world, 127, 51, 1), Material.EMERALD, 500));
        itemSpawnPoints.add(new ItemSpawner(new Location(world, 2, 45, -9), Material.EMERALD, 500));
        itemSpawnPoints.add(new ItemSpawner(new Location(world, -12, 57, 10), Material.EMERALD, 500));
        itemSpawnPoints.add(new ItemSpawner(new Location(world, -136, 51, 0), Material.EMERALD, 500));
        itemSpawnPoints.add(new ItemSpawner(new Location(world, -78, 51, 1), Material.DIAMOND, 300));
    }


}
