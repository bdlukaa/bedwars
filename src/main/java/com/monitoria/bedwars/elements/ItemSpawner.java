package com.monitoria.bedwars.elements;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Timer;
import java.util.TimerTask;

public class ItemSpawner {

    public Location location;

    public Material itemType;

    public int factor = 0;

    public ItemSpawner(Location location, Material itemType, int factor) {
        this.location = location;
        this.itemType = itemType;
        this.factor = factor;
        if (factor == 0) {
            this.factor = 100;
        }
    }

    public void startTimer() {
        new BukkitRunnable() {
            @Override
            public void run() {
                spawn();
            }

        }.runTaskTimer(
                Bukkit.getPluginManager().getPlugin("BedWars"),
                0,
                factor
        );
    }


    int switcher = 0;

    public void spawn() {
        World world = location.getWorld();
        if (world == null) return;
        if (itemType == null) {
            if (switcher % 2 == 0) {
                world.dropItem(location, new ItemStack(Material.IRON_INGOT));
                world.dropItem(location, new ItemStack(Material.IRON_INGOT));
                world.dropItem(location, new ItemStack(Material.IRON_INGOT));
            } else if (switcher % 5 == 0) {
                world.dropItem(location, new ItemStack(Material.GOLD_INGOT));
                world.dropItem(location, new ItemStack(Material.GOLD_INGOT));
                world.dropItem(location, new ItemStack(Material.GOLD_INGOT));
            }
            switcher++;
            System.out.println("Spawnou item");
        } else {
            world.dropItem(location, new ItemStack(itemType));
        }
    }

}
