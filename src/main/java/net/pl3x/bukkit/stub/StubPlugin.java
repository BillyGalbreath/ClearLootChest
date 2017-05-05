package net.pl3x.bukkit.stub;

import net.pl3x.bukkit.stub.command.CmdStub;
import net.pl3x.bukkit.stub.configuration.Config;
import net.pl3x.bukkit.stub.configuration.Lang;
import net.pl3x.bukkit.stub.listener.StubListener;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class StubPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        Config.reload();
        Lang.reload();

        if (!getServer().getPluginManager().isPluginEnabled("WorldEdit")) {
            Logger.error("# WorldEdit NOT found and/or enabled!");
            Logger.error("# This plugin requires WorldEdit to be installed and enabled!");
            return;
        }

        getServer().getPluginManager().registerEvents(new StubListener(this), this);

        getCommand("gptrades").setExecutor(new CmdStub(this));

        Logger.info(getName() + " v" + getDescription().getVersion() + " enabled!");
    }

    @Override
    public void onDisable() {
        Logger.info(getName() + " disabled.");
    }

    public static StubPlugin getPlugin() {
        return StubPlugin.getPlugin(StubPlugin.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                "&4" + getName() + " is disabled. See console log for more information."));
        return true;
    }
}
