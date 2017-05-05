package net.pl3x.bukkit.stub.listener;

import net.pl3x.bukkit.stub.Logger;
import net.pl3x.bukkit.stub.StubPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class StubListener implements Listener {
    private final StubPlugin plugin;

    public StubListener(StubPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        Logger.debug("Player " + player.getName() + " has joined the server.");
    }
}
