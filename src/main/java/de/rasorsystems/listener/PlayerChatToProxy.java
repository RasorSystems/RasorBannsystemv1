

package de.rasorsystems.listener;

import net.md_5.bungee.event.EventHandler;

import java.text.SimpleDateFormat;
import de.rasorsystems.MySQL;
import de.rasorsystems.MuteAPI;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import de.rasorsystems.Main;
import net.md_5.bungee.api.plugin.Listener;

public class PlayerChatToProxy implements Listener
{
    private final Main main;
    
    public PlayerChatToProxy(final Main main) {
        this.main = main;
    }
    
    @EventHandler
    public void onhandle(final ChatEvent e) {
        final ProxiedPlayer p = (ProxiedPlayer)e.getSender();

        if (!e.isCancelled() && !e.getMessage().startsWith("/")) {
            if (MuteAPI.isPermMuted(p.getUniqueId().toString())) {
                e.setCancelled(true);
                p.sendMessage(Main.getInstance().getConfiguration().getString("prefix").replace("&", "§") +Main.getInstance().getConfiguration().getString("permmutechat").replace("&", "§").replace("%reason%", MuteAPI.getReason(p.getUniqueId().toString())).replaceAll("&", "§"));
                p.sendMessage(Main.getInstance().getConfiguration().getString("prefix").replace("&", "§") +Main.getInstance().getConfiguration().getString("mutechattime").replace("%time%", new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(MuteAPI.getEnd(p.getUniqueId().toString()))).replaceAll("&", "§"));
            }
            else if (MuteAPI.isTempMuted(p.getUniqueId().toString())) {
                if (MuteAPI.getEnd(p.getUniqueId().toString()) <= System.currentTimeMillis()) {
                    e.setCancelled(false);
                    MuteAPI.unMute(p.getUniqueId().toString());
                    p.sendMessage(Main.getInstance().getConfiguration().getString("prefix") + Main.getInstance().getConfiguration().getString("unmuted").replace("&", "§"));
                }
                else if (MuteAPI.getEnd(p.getUniqueId().toString()) >= System.currentTimeMillis()) {
                    e.setCancelled(true);
                    p.sendMessage(Main.getInstance().getConfiguration().getString("prefix").replace("&", "§") +Main.getInstance().getConfiguration().getString("mutechat").replace("&", "§").replace("%reason%", MuteAPI.getReason(p.getUniqueId().toString())).replaceAll("&", "§"));
                    p.sendMessage(Main.getInstance().getConfiguration().getString("prefix").replace("&", "§") +Main.getInstance().getConfiguration().getString("mutechattime").replace("%time%", new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(MuteAPI.getEnd(p.getUniqueId().toString()))).replaceAll("&", "§"));
                }
                else {
                    e.setCancelled(false);
                }
            }
            else {
                e.setCancelled(false);
            }
        }
    }
}
