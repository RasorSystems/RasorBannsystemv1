

package de.rasorsystems.commands;

import java.util.List;
import java.text.SimpleDateFormat;
import de.rasorsystems.MuteAPI;
import de.rasorsystems.Main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class CMD_Mutelist extends Command
{
    public CMD_Mutelist(final String name) {
        super(name);
    }
    
    public void execute(final CommandSender sender, final String[] args) {
        if (sender.hasPermission("rasorsystems.command.mutelist")) {
            if (args.length != 0) {
                sender.sendMessage(Main.getInstance().getConfiguration().getString("prefix").replace("&", "§") + Main.getInstance().getConfiguration().getString("mutelistusage").replace("&", "§"));
            }
            else if (args.length == 0) {
                final List<String> list = MuteAPI.getMutedPlayers();
                if (list.size() == 0) {
                    sender.sendMessage(Main.getInstance().getConfiguration().getString("prefix").replace("&", "§") + Main.getInstance().getConfiguration().getString("noplayersmuted").replace("&", "§"));
                }else {
                    sender.sendMessage(Main.getInstance().getConfiguration().getString("mutelist-1").replace("&", "§"));
                    for (final String allMuted : MuteAPI.getMutedPlayers()) {
                        sender.sendMessage(Main.getInstance().getConfiguration().getString("mutelist").replace("&", "§").replace("%player%", allMuted).replace("%reason%", MuteAPI.getReason(CMD_Unban.loadUUID(allMuted))).replace("%time%", new SimpleDateFormat("dd.MM.yyyy HH:mm").format(MuteAPI.getEnd(CMD_Unban.loadUUID(allMuted)))));
                    }
                    sender.sendMessage(Main.getInstance().getConfiguration().getString("mutelist-1").replace("&", "§"));
                }
            }
        }
        else {
            sender.sendMessage(Main.getInstance().getConfiguration().getString("prefix").replace("&", "§") + Main.getInstance().getConfiguration().getString("noperm").replace("&", "§"));;
        }
    }
}
