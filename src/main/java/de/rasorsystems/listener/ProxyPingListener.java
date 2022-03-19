

package de.rasorsystems.listener;

import net.md_5.bungee.event.EventHandler;
import java.util.UUID;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.event.ProxyPingEvent;
import de.rasorsystems.Main;
import net.md_5.bungee.api.plugin.Listener;

public class ProxyPingListener implements Listener
{
    private final Main main;
    
    public ProxyPingListener(final Main main) {
        this.main = main;
    }
    
    @EventHandler
    public void onPing(final ProxyPingEvent e) {
        if (Main.getInstance().getConfiguration().getBoolean("usemotd")) {
            final ServerPing ping = e.getResponse();
            final ServerPing.Players players = ping.getPlayers();
            final ServerPing.Protocol version = ping.getVersion();
            if (this.main.getConfiguration().getBoolean("maintenancestatus")) {
                version.setName(Main.getInstance().getConfiguration().getString("maintenenance-version").replace("&", "§"));
                version.setProtocol(2);
                players.setSample(new ServerPing.PlayerInfo[]{new ServerPing.PlayerInfo(this.main.getConfiguration().getString("motd-hover").replace("&", "§"), UUID.randomUUID())});
                ping.setDescription(this.main.getConfiguration().getString("maintenancemotd-1").replace("&", "§") + "\n" + this.main.getConfiguration().getString("maintenancemotd-2").replace("&", "§"));
                e.setResponse(ping);
            } else {
                version.setName("§7" + this.main.getConfiguration().getInt("max_player") + "§8/§7" + e.getConnection().getListener().getMaxPlayers());
                players.setSample(new ServerPing.PlayerInfo[]{new ServerPing.PlayerInfo(this.main.getConfiguration().getString("motd-hover").replace("&", "§"), UUID.randomUUID())});
                ping.setDescription(this.main.getConfiguration().getString("motd-1").replace("&", "§") + "\n" + this.main.getConfiguration().getString("motd-2").replace("&", "§"));
                e.setResponse(ping);
            }
        }
    }
}
