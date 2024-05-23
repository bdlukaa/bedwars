package com.monitoria.bedwars.game;

import com.monitoria.bedwars.elements.Team;
import org.bukkit.Color;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class Game {

    List<Team> teams = new ArrayList<>();

    GameStatus status = GameStatus.iniciando;

    Timer timer;

    public int lobbyX = 0;
    public int lobbyZ = 0;
    public int lobbyY = 92;

    List itemSpawnPoints;

    public Game() {
        teams.add(new Team(Color.RED, -108, 50, -47));
        teams.add(new Team(Color.PURPLE, -52, 50, -46));
        teams.add(new Team(Color.BLACK, -106, 50, 47));
        teams.add(new Team(Color.WHITE, 43, 50, 50));
        teams.add(new Team(Color.BLUE, 99, 50, 50));
    }


    void iniciar() {
        for (int i = 0; i < teams.size(); i++) {
            Team team = teams.get(i);
            team.teleportPlayerToSpawn(team.player1);
            team.teleportPlayerToSpawn(team.player2);
            team.teleportPlayerToSpawn(team.player3);
        }
    }

    void finalizar() {

    }

    void ganhar() {

    }


}
