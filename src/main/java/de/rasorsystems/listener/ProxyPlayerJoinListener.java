
package de.rasorsystems.listener;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import de.rasorsystems.MySQL;
import de.rasorsystems.BanAPI;
import net.md_5.bungee.api.event.PostLoginEvent;
import de.rasorsystems.Main;
import net.md_5.bungee.api.plugin.Listener;

public class ProxyPlayerJoinListener implements Listener
{
    private final Main main;
    
    public ProxyPlayerJoinListener(final Main main) {
        this.main = main;
    }
    
    @EventHandler
    public void onHandle(final PostLoginEvent e) {
        final ProxiedPlayer pp = e.getPlayer();
        if (BanAPI.isPermBanned(pp.getUniqueId().toString())) {
            Main.getInstance().getProxy().getPlayer(pp.getName()).disconnect(Main.getInstance().getConfiguration().getString("ban-1").replace("&", "§") + "\n\n" + Main.getInstance().getConfiguration().getString("permban").replace("&", "§") + "\n\n" + Main.getInstance().getConfiguration().getString("banreason").replace("&", "§").replace("%reason%", BanAPI.getReason(pp.getUniqueId().toString())) + "\n\n" + Main.getInstance().getConfiguration().getString("bantime").replace("&", "§").replace("%weeks%", "" + BanAPI.getRaimingTimeSep(pp.getUniqueId().toString(), "weeks")).replace("%hours%", "" + BanAPI.getRaimingTimeSep(pp.getUniqueId().toString(), "hours")).replace("%days%", "" + BanAPI.getRaimingTimeSep(pp.getUniqueId().toString(), "days")).replace("%minutes%", "" + BanAPI.getRaimingTimeSep(pp.getUniqueId().toString(), "minutes")).replace("%secounds%", "" + BanAPI.getRaimingTimeSep(pp.getUniqueId().toString(), "seconds")) + "\n\n" + Main.getInstance().getConfiguration().getString("banend").replace("&", "§") + "\n\n" + Main.getInstance().getConfiguration().getString("ban-1").replace("&", "§"));
        }
        else if (BanAPI.isTempBanned(pp.getUniqueId().toString())) {
            if (BanAPI.getEnd(pp.getUniqueId().toString()) <= System.currentTimeMillis()) {
                BanAPI.unban(pp.getUniqueId().toString());
            }
            else {
                Main.getInstance().getProxy().getPlayer(pp.getName()).disconnect(Main.getInstance().getConfiguration().getString("ban-1").replace("&", "§") + "\n\n" + Main.getInstance().getConfiguration().getString("ban").replace("&", "§") + "\n\n" + Main.getInstance().getConfiguration().getString("banreason").replace("&", "§").replace("%reason%", BanAPI.getReason(pp.getUniqueId().toString())) + "\n\n" + Main.getInstance().getConfiguration().getString("bantime").replace("&", "§").replace("%weeks%", "" + BanAPI.getRaimingTimeSep(pp.getUniqueId().toString(), "weeks")).replace("%hours%", "" + BanAPI.getRaimingTimeSep(pp.getUniqueId().toString(), "hours")).replace("%days%", "" + BanAPI.getRaimingTimeSep(pp.getUniqueId().toString(), "days")).replace("%minutes%", "" + BanAPI.getRaimingTimeSep(pp.getUniqueId().toString(), "minutes")).replace("%secounds%", "" + BanAPI.getRaimingTimeSep(pp.getUniqueId().toString(), "seconds")) + "\n\n" + Main.getInstance().getConfiguration().getString("banend").replace("&", "§") + "\n\n" + Main.getInstance().getConfiguration().getString("ban-1").replace("&", "§"));
            }
        }
        if (this.main.getConfiguration().getBoolean("maintenancestatus")) {
            if(!e.getPlayer().hasPermission("rasorsystems.bypass.maintenance")){
                pp.disconnect((Main.getInstance().getConfiguration().getString("maintenancekick-1").replace("&", "§") + "\n\n" + Main.getInstance().getConfiguration().getString("maintenancekick").replace("&", "§")) + "\n\n" + Main.getInstance().getConfiguration().getString("maintenancekick-1").replace("&", "§"));
            }
        }
    }
}
