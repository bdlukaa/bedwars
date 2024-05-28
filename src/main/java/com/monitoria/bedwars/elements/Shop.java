package com.monitoria.bedwars.elements;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class Shop {

    public static Inventory inv;

    public static void initializeShop() {
        inv = Bukkit.createInventory(null, 9 * 3, "Loja :)");

        Shop.prices = new ArrayList<>();
        // blocos
        inv.addItem(createGuiItem(new ItemStack(Material.STONE_SWORD, 1), new ItemStack(Material.IRON_INGOT, 7), "Espada de Pedra", "§a7 barras de ferro"));
        inv.addItem(createGuiItem(new ItemStack(Material.IRON_SWORD, 1), new ItemStack(Material.GOLD_INGOT, 7), "Espada de Ferro", "§a14 barras de ouro"));
        inv.addItem(createGuiItem(new ItemStack(Material.WHITE_WOOL, 64), new ItemStack(Material.IRON_INGOT, 8), "Lâ", "§a8 barras de ferro"));
        inv.addItem(createGuiItem(new ItemStack(Material.GLASS, 12), new ItemStack(Material.IRON_INGOT, 16), "Vidro", "§a16 barras de ferro"));
        inv.addItem(createGuiItem(new ItemStack(Material.OBSIDIAN, 2), new ItemStack(Material.EMERALD, 8), "Obsidian", "§a8 esmeraldas"));
        inv.addItem(createGuiItem(new ItemStack(Material.DIAMOND_PICKAXE, 1), new ItemStack(Material.EMERALD, 8), "Picareta de Diamante", "§a8 esmeraldas"));

        // items
        inv.addItem(createGuiItem(new ItemStack(Material.EGG, 2), new ItemStack(Material.IRON_INGOT, 12), "Ovos", "§a12 ferros"));
        inv.addItem(createGuiItem(new ItemStack(Material.ENDER_PEARL, 2), new ItemStack(Material.DIAMOND, 16), "Pérola do Fim", "§a16 diamantes"));
        inv.addItem(createGuiItem(new ItemStack(Material.IRON_CHESTPLATE, 1), new ItemStack(Material.DIAMOND, 24), "Peitoral", "§a24 diamantes"));
    }

    public static ArrayList<ItemStack> prices = new ArrayList<ItemStack>();

    public void abrirLoja(Player player) {
        player.openInventory(inv);
    }

    // Nice little method to create a gui item with a custom name, and description
    protected static ItemStack createGuiItem(final ItemStack item, final ItemStack price, final String name, final String... lore) {
        final ItemMeta meta = item.getItemMeta();

        // Set the name of the item
        meta.setDisplayName(name);

        // Set the lore of the item
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        prices.add(price);

        return item;
    }

}
