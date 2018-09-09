package net.pl3x.bukkit.clc;

import org.bstats.bukkit.Metrics;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.loot.LootTable;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class ClearLootChest extends JavaPlugin {
    @Override
    public void onEnable() {
        new Metrics(this);

        saveDefaultConfig();

        try {
            Class.forName("com.destroystokyo.paper.PaperConfig");
        } catch (ClassNotFoundException e) {
            getLogger().severe("###########################################");
            getLogger().severe("#                                         #");
            getLogger().severe("#          Server is unsupported          #");
            getLogger().severe("#   Please install Paper server v1.13.1   #");
            getLogger().severe("#     Plugin will now disable itself      #");
            getLogger().severe("#                                         #");
            getLogger().severe("###########################################");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        try {
            Class.forName("com.destroystokyo.paper.event.entity.PhantomPreSpawnEvent");
        } catch (ClassNotFoundException e) {
            getLogger().severe("###########################################");
            getLogger().severe("#                                         #");
            getLogger().severe("#           Server is outdated            #");
            getLogger().severe("#  Please update to latest Paper version  #");
            getLogger().severe("#     Plugin will now disable itself      #");
            getLogger().severe("#                                         #");
            getLogger().severe("###########################################");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        getServer().getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onInventoryOpen(InventoryOpenEvent event) {
                if (event.getInventory().getType() != InventoryType.CHEST) {
                    return; // not a chest
                }
                InventoryHolder holder = event.getInventory().getHolder();
                if (!(holder instanceof Chest)) {
                    return; // not a chest
                }
                LootTable lootTable = ((Chest) holder).getLootTable();
                if (lootTable == null) {
                    return; // chest has no loot table
                }
                String lootTableKey = lootTable.getKey().toString();
                List<String> clearList = getConfig().getStringList("clear-loot-tables-on-use");
                if (clearList == null || clearList.isEmpty()) {
                    return; // clear list is empty
                }
                if (!clearList.contains(lootTableKey)) {
                    return; // chest does not contain this loot table
                }
                Chest chest = ((Chest) holder);
                chest.setLootTable(null); // clear the loot table
                chest.update(true, true);
                if (getConfig().getBoolean("show-cleared-loot-tables")) {
                    getLogger().info("Chest loot table cleared!");
                    getLogger().info(" -  Location: " + chest.getLocation());
                    getLogger().info(" -  Player: " + event.getPlayer().getName());
                    getLogger().info(" -  LootTable: " + lootTableKey);
                }
            }
        }, this);
    }
}
