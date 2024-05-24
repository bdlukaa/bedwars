package com.monitoria.bedwars.game;

import com.monitoria.bedwars.elements.Shop;
import com.monitoria.bedwars.elements.Team;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

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
        player.getInventory().clear();

        playersJoined++;
        boolean added = false;
        for (Team team : game.teams) {
            if (team.player1 == null) {
                team.player1 = player;
                added = true;
                break;
            }
        }

        if (!added) {
            for (Team team : game.teams) {
                if (team.player2 == null) {
                    team.player2 = player;
                    added = true;
                    break;
                }
            }
        }

        if (!added) {
            for (Team team : game.teams) {
                if (team.player3 == null) {
                    team.player3 = player;
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

                event.setDropItems(false);
            }
        }
    }

    @EventHandler
    void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
        Entity entity = event.getRightClicked();
        if (entity instanceof Villager) {
            Shop shop = new Shop();
            shop.abrirLoja(event.getPlayer());
        }

    }

    @EventHandler
    void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        Inventory playerInv = player.getInventory();
        playerInv.addItem(new ItemStack(Material.WOODEN_SWORD));
    }

    @EventHandler
    void onInventoryClick(InventoryClickEvent event) {

        if (event.getInventory() == Shop.inv) {
            int slot = event.getRawSlot();
            ItemStack stack = event.getInventory().getItem(slot);
            ItemStack price = Shop.prices.get(slot);

            Player player = (Player) event.getWhoClicked();
            Inventory playerInventory = player.getInventory();

            if (playerInventory.containsAtLeast(price, price.getAmount())) {
                playerInventory.addItem(stack);
            }
        }

    }

}
