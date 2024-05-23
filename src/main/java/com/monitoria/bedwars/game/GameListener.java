package com.monitoria.bedwars.game;

import com.monitoria.bedwars.elements.Team;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.EventHandler;

public class GameListener implements Listener {
    //Colocar limite de altura;
    //Colocar no modo Aventura quando entra e modo Survival quando inicia;
    //Lojinha e tals
    Game game;

    public GameListener(Game game) {
        this.game = game;
    }

    int playersJoined = 0;

    @EventHandler
    void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.teleport(new Location(player.getWorld(), game.lobbyX, game.lobbyY, game.lobbyZ));
        player.setGameMode(GameMode.ADVENTURE);

        playersJoined++;
        boolean added = false;
        for (int i = 0; i < game.teams.toArray().length; i++) {
            Team team = game.teams.get(i);

            if (team.player1 == null) {
                team.player1 = player;
                added = true;
                break;
            } else if (team.player2 == null) {
                team.player2 = player;
                added = true;
                break;
            } else if (team.player3 == null) {
                team.player3 = player;
                added = true;
                break;
            }
        }

        if (!added) {
            player.setGameMode(GameMode.SPECTATOR);
        }

        // if (playersJoined >= 6) {
            game.iniciar();
        // }
    }

    @EventHandler
    void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        Team team = null;
        for (int i = 0; i < game.teams.toArray().length; i++) {
            Team t = game.teams.get(i);

            if (t.player1 == player) {
                team = t;
                break;
            } else if (t.player2 == player) {
                team = t;
                break;
            } else if (t.player3 == null) {
                team = t;
                break;
            }
        }
        if (team != null) {
            if (team.isBedActive) {
                team.teleportPlayerToSpawn(player);
            } else {
                player.setGameMode(GameMode.SPECTATOR);
            }
        }

        Team aliveTeam = null;
        int teamsAlives = 0;
        for (int i = 0; i < game.teams.toArray().length; i++) {
            Team t = game.teams.get(i);
            if (t.amountAlive() >= 1) {
                teamsAlives++;
                aliveTeam = t;
            }
        }
        if (teamsAlives == 1) {
            // GANHAR
            game.ganhar(aliveTeam);
        }
    }

    @EventHandler
    void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        Team team = null;
        if (block.getType() == Material.RED_BED) {
            team = game.teams.get(0);
        } else if (block.getType() == Material.BLUE_BED) {
            team = game.teams.get(1);
        } else if (block.getType() == Material.BLACK_BED) {
            team = game.teams.get(2);
        } else if (block.getType() == Material.WHITE_BED) {
            team = game.teams.get(3);
        } else if (block.getType() == Material.PURPLE_BED) {
            team = game.teams.get(4);
        }
        if (team != null) {
            team.isBedActive = false;
            System.out.println("Cama " + team.color.toString() + " quebrada");
        }
    }

}
