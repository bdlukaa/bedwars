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

            for (Player teamPlayer : team.getPlayers()) {
                if (teamPlayer == null) {
                    teamPlayer = player;
                    added = true;
                    break;
                }
            }
        }

        if (!added) {
            player.setGameMode(GameMode.SPECTATOR);
        }

        if (playersJoined >= 6) {
            game.iniciar();
        }
    }

    @EventHandler
    void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        Team team = null;
        for (Team t : game.teams) {
            for (Player teamPlayer : team.getPlayers()) {
                if (teamPlayer == player) {
                    team = t;
                    break;
                }
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

        for (Team team : game.teams) {
            if (block.getType() == team.bedMaterial) {
                team.isBedActive = false;
                System.out.println("Cama " + team.color.toString() + " quebrada");
            }
        }
    }

}
