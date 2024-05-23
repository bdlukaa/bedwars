package com.monitoria.bedwars.elements;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;

import java.util.Timer;
import java.util.TimerTask;

public class ItemSpawner {

    public Location location;

    public Material itemType;

    public Timer timer;

    public ItemSpawner(Location location, Material itemType) {
        this.location = location;
        this.itemType = itemType;
    }

    public void startTimer() {
        timer = new Timer();
        TimerTask task = new SpawnTask(this);
        timer.schedule(task, 1000, 100000);
    }


    int switcher = 0;

    public void spawn() {
        if (itemType == null) {
            if (switcher % 2 == 0) {
                location.getWorld().dropItem(location, new ItemStack(Material.IRON_INGOT));
                location.getWorld().dropItem(location, new ItemStack(Material.IRON_INGOT));
                location.getWorld().dropItem(location, new ItemStack(Material.IRON_INGOT));
            } else {
                location.getWorld().dropItem(location, new ItemStack(Material.GOLD_INGOT));
                location.getWorld().dropItem(location, new ItemStack(Material.GOLD_INGOT));
                location.getWorld().dropItem(location, new ItemStack(Material.GOLD_INGOT));
            }
            switcher++;
            System.out.println("Spawnou item");
        } else {
            location.getWorld().dropItem(location, new ItemStack(itemType));
        }
    }

    class SpawnTask extends TimerTask {
        ItemSpawner itemSpawner;

        SpawnTask(ItemSpawner spawner) {
            this.itemSpawner = spawner;
        }

        public void run() {
            System.out.println("Spawnando");
            itemSpawner.spawn();
        }
    }

}
